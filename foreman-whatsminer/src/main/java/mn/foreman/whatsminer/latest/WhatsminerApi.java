package mn.foreman.whatsminer.latest;

import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.whatsminer.latest.error.ApiException;
import mn.foreman.whatsminer.latest.error.PermissionDeniedException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** Utility for querying the Whatsminer API. */
public class WhatsminerApi {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerApi.class);

    /** The mapper for writing and reading json. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // Increase the default max key size
        Security.setProperty("crypto.policy", "unlimited");
    }

    /**
     * Runs a command against a Whatminer miner.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param password The password.
     * @param command  The command.
     * @param args     The arguments.
     *
     * @return Whether or not the command was successful.
     *
     * @throws ApiException              on failure.
     * @throws PermissionDeniedException on write API not enabled.
     */
    public static boolean runCommand(
            final String ip,
            final int port,
            final String password,
            final Command command,
            final Map<String, String> args)
            throws ApiException, PermissionDeniedException {
        return runCommand(
                ip,
                port,
                password,
                command,
                args,
                30,
                TimeUnit.SECONDS,
                response -> {
                });
    }

    /**
     * Runs a command against a Whatminer miner.
     *
     * @param ip                     The IP.
     * @param port                   The port.
     * @param password               The password.
     * @param command                The command.
     * @param args                   The arguments.
     * @param responseCallback       The callback for processing responses.
     * @param connectionTimeout      The connection timeout.
     * @param connectionTimeoutUnits The connection timeout units.
     *
     * @return Whether or not the command was successful.
     *
     * @throws ApiException              on failure.
     * @throws PermissionDeniedException on write API not enabled.
     */
    public static boolean runCommand(
            final String ip,
            final int port,
            final String password,
            final Command command,
            final Map<String, String> args,
            final int connectionTimeout,
            final TimeUnit connectionTimeoutUnits,
            final ResponseCallback responseCallback)
            throws ApiException, PermissionDeniedException {
        if (command.isWrite()) {
            return runWrite(
                    ip,
                    port,
                    password,
                    command,
                    args,
                    connectionTimeout,
                    connectionTimeoutUnits,
                    responseCallback);
        }
        return runRead(
                ip,
                port,
                command,
                args,
                connectionTimeout,
                connectionTimeoutUnits,
                responseCallback);
    }

    /**
     * Runs a non-encrypted API read.
     *
     * @param ip                     The ip.
     * @param port                   The port.
     * @param command                The command.
     * @param args                   The args.
     * @param connectionTimeout      The connection timeout.
     * @param connectionTimeoutUnits The connection timeout (units).
     * @param responseCallback       The response callback.
     *
     * @return Whether or not the command was successful.
     *
     * @throws ApiException              on failure.
     * @throws PermissionDeniedException on failure.
     */
    public static boolean runRead(
            final String ip,
            final int port,
            final Command command,
            final Map<String, String> args,
            final int connectionTimeout,
            final TimeUnit connectionTimeoutUnits,
            final ResponseCallback responseCallback)
            throws ApiException, PermissionDeniedException {
        try {
            final String response =
                    query(
                            ip,
                            port,
                            toString(
                                    toCommand(
                                            command,
                                            args,
                                            null)),
                            connectionTimeout,
                            connectionTimeoutUnits)
                            .orElseThrow(
                                    () -> new ApiException("Failed to obtain response"));

            LOG.info("Whatsminer response: {}", response);

            return processResult(
                    response,
                    responseCallback);
        } catch (final IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Decrypts the provided message with the provided key.
     *
     * @param aesKey  The key.
     * @param message The message.
     *
     * @return The decrypted message.
     *
     * @throws NoSuchPaddingException    on cipher failure.
     * @throws NoSuchAlgorithmException  on cipher failure.
     * @throws InvalidKeyException       on cipher failure.
     * @throws BadPaddingException       on cipher failure.
     * @throws IllegalBlockSizeException on cipher failure.
     */
    private static String decrypt(
            final byte[] aesKey,
            final String message)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        final SecretKeySpec spec =
                new SecretKeySpec(
                        aesKey,
                        "AES");
        final Cipher cipher =
                Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, spec);

        final byte[] decoded =
                Base64.getDecoder().decode(message.getBytes());
        final byte[] decrypted =
                cipher.doFinal(decoded);

        return new String(decrypted)
                .trim()
                .replace("\0", "");
    }

    /**
     * Encrypts the message with the provided key.
     *
     * @param aesKey  The key.
     * @param message The message.
     *
     * @return The encrypted message.
     *
     * @throws NoSuchPaddingException    on cipher failure.
     * @throws NoSuchAlgorithmException  on cipher failure.
     * @throws InvalidKeyException       on cipher failure.
     * @throws BadPaddingException       on cipher failure.
     * @throws IllegalBlockSizeException on cipher failure.
     */
    private static String encrypt(
            final byte[] aesKey,
            final String message)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        final SecretKeySpec spec =
                new SecretKeySpec(
                        aesKey,
                        "AES");
        final Cipher cipher =
                Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, spec);

        final String paddedCommand =
                Strings.padEnd(
                        message,
                        message.length() + 16 - (message.length() % 16),
                        '\0');
        final byte[] encrypted =
                cipher.doFinal(paddedCommand.getBytes());

        return Base64
                .getEncoder()
                .encodeToString(encrypted)
                .trim()
                .replace("\n", "");
    }

    /**
     * MD5 crypts the provided word with the provided salt.
     *
     * @param word The word.
     * @param salt The salt.
     *
     * @return The encrypted result.
     *
     * @throws ApiException on failure.
     */
    private static String md5Crypt(
            final String word,
            final String salt)
            throws ApiException {
        final String result =
                Md5Crypt.md5Crypt(
                        word.getBytes(),
                        String.format(
                                "$1$%s$",
                                salt));
        final String[] resultArray = result.split("\\$");
        if (resultArray.length < 3) {
            throw new ApiException("Error while calculating MD5");
        }
        return resultArray[3];
    }

    /**
     * Processes the result.
     *
     * @param response         The result.
     * @param responseCallback The response callback.
     *
     * @return Whether or not the command was successful.
     *
     * @throws IOException               on failure to read json.
     * @throws PermissionDeniedException if the write API isn't enabled.
     */
    @SuppressWarnings("unchecked")
    private static boolean processResult(
            final String response,
            final ResponseCallback responseCallback)
            throws
            IOException,
            PermissionDeniedException {
        final Map<String, Object> finalResult = readMap(response);
        if (finalResult.containsKey("Msg")) {
            responseCallback.sawMsg(finalResult.get("Msg"));
        }

        Object status = finalResult.get("STATUS");
        if (status instanceof List) {
            status = ((List<Map<String, Object>>) status).get(0).get("STATUS");
        } else {
            final String code = finalResult.get("Code").toString();
            if ("45".equals(code)) {
                throw new PermissionDeniedException("Write API must be enabled");
            }
        }
        return "S".equals(status);
    }

    /**
     * Queries the miner.
     *
     * @param ip                     The ip.
     * @param port                   The port.
     * @param toSend                 The command to send.
     * @param connectionTimeout      The connection timeout.
     * @param connectionTimeoutUnits The connection timeout units.
     *
     * @return The result, if present.
     */
    private static Optional<String> query(
            final String ip,
            final int port,
            final String toSend,
            final int connectionTimeout,
            final TimeUnit connectionTimeoutUnits) {
        Optional<String> result = Optional.empty();

        final ApiRequest apiRequest =
                new ApiRequestImpl(
                        ip,
                        port,
                        toSend);

        final Connection connection =
                ConnectionFactory.createJsonConnection(
                        apiRequest,
                        connectionTimeout,
                        connectionTimeoutUnits);
        connection.query();

        if (apiRequest.waitForCompletion(
                connectionTimeout,
                connectionTimeoutUnits)) {
            result = Optional.ofNullable(apiRequest.getResponse());
        }

        return result;
    }

    /**
     * Reads a map from the provided json string.
     *
     * @param toRead The string to parse.
     *
     * @return The map.
     *
     * @throws IOException on failure.
     */
    private static Map<String, Object> readMap(final String toRead)
            throws IOException {
        return OBJECT_MAPPER.readValue(
                toRead,
                new TypeReference<Map<String, Object>>() {
                });
    }

    /**
     * Runs a command that requires encryption.
     *
     * @param ip                     The ip.
     * @param port                   The port.
     * @param password               The password.
     * @param command                The command.
     * @param args                   The args.
     * @param connectionTimeout      The connection timeout.
     * @param connectionTimeoutUnits The connection timeout (units).
     * @param responseCallback       The response callback.
     *
     * @return Whether or not the command was successful.
     *
     * @throws ApiException              on failure.
     * @throws PermissionDeniedException on failure.
     */
    @SuppressWarnings("unchecked")
    private static boolean runWrite(
            final String ip,
            final int port,
            final String password,
            final Command command,
            final Map<String, String> args,
            final int connectionTimeout,
            final TimeUnit connectionTimeoutUnits,
            final ResponseCallback responseCallback)
            throws ApiException, PermissionDeniedException {
        try {
            // Request token
            final Map<String, Object> result =
                    readMap(
                            query(
                                    ip,
                                    port,
                                    toString(
                                            toCommand(
                                                    Command.GET_TOKEN,
                                                    Collections.emptyMap(),
                                                    null)),
                                    connectionTimeout,
                                    connectionTimeoutUnits)
                                    .orElseThrow(() -> new ApiException("Failed to obtain response")));
            LOG.debug("Msg: {}", result.get("Msg"));
            final Map<String, String> saltInfo =
                    (Map<String, String>) result.get("Msg");

            // Generate md5 and sign
            final String hostPasswordMd5 =
                    md5Crypt(
                            password,
                            saltInfo.get("salt"));
            final String hostSign =
                    md5Crypt(
                            hostPasswordMd5 + saltInfo.get("time"),
                            saltInfo.get("newsalt"));

            // Generate AES key
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] aesKey = digest.digest(hostPasswordMd5.getBytes());

            // Encrypt and encode
            final String encrypted =
                    encrypt(
                            aesKey,
                            toString(
                                    toCommand(
                                            command,
                                            args,
                                            hostSign)));

            final Map<String, Object> toSend =
                    ImmutableMap.of(
                            "enc",
                            1,
                            "data",
                            encrypted);

            String response =
                    query(
                            ip,
                            port,
                            toString(toSend),
                            connectionTimeout,
                            connectionTimeoutUnits)
                            .orElseThrow(
                                    () -> new ApiException("Failed to obtain response"));

            // Depending on the request type, could be structured differently
            final String toDecrypt;
            if (response.contains("\"enc\"")) {
                toDecrypt = readMap(response).get("enc").toString();
            } else {
                toDecrypt = response;
            }

            response =
                    decrypt(
                            aesKey,
                            toDecrypt);

            LOG.info("Whatsminer response: {}", response);

            return processResult(
                    response,
                    responseCallback);
        } catch (final NoSuchAlgorithmException |
                BadPaddingException |
                InvalidKeyException |
                NoSuchPaddingException |
                IOException |
                IllegalBlockSizeException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Generates a command from the provided command and arguments.
     *
     * @param command The command.
     * @param args    The args.
     * @param token   The optional token.
     *
     * @return The command to be sent.
     */
    private static Map<String, Object> toCommand(
            final Command command,
            final Map<String, String> args,
            final String token) {
        final Map<String, Object> commandToSend = new LinkedHashMap<>();
        commandToSend.put("cmd", command.getCommand());
        commandToSend.putAll(args);
        if (token != null) {
            commandToSend.put("token", token);
        }
        return commandToSend;
    }

    /**
     * Writes the provided map to a json string.
     *
     * @param map The map.
     *
     * @return The string.
     *
     * @throws JsonProcessingException on failure.
     */
    private static String toString(final Map<String, Object> map)
            throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(map);
    }
}
