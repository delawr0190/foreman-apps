package mn.foreman.antminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.io.Query;
import mn.foreman.model.error.EmptySiteException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/** A collection of utility functions for antminer processing. */
public class AntminerUtils {

    /** The known bOS types. */
    private static final List<AntminerType> BRAIINS_TYPES;

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AntminerUtils.class);

    /** The mapper for parsing json. */
    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    /** The type key. */
    private static String TYPE = "Type";

    static {
        final List<AntminerType> braiinsTypes =
                Arrays.stream(AntminerType.values())
                        .filter(AntminerType::isBraiins)
                        .collect(Collectors.toList());
        BRAIINS_TYPES = Collections.unmodifiableList(braiinsTypes);
    }

    /**
     * Obtains the miner conf.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param realm    The realm.
     * @param uri      The URI.
     * @param username The username.
     * @param password The password.
     *
     * @return The conf.
     *
     * @throws Exception on failure.
     */
    public static Map<String, Object> getConf(
            final String ip,
            final int port,
            final String realm,
            final String uri,
            final String username,
            final String password) throws Exception {
        final AtomicReference<Map<String, Object>> minerConfRef =
                new AtomicReference<>();
        Query.digestGet(
                ip,
                port,
                realm,
                uri,
                username,
                password,
                (code, s) -> {
                    try {
                        minerConfRef.set(
                                OBJECT_MAPPER.readValue(
                                        // Patch fan-ctrl, if needed
                                        s.replace(": ,", ": false,"),
                                        new TypeReference<Map<String, Object>>() {

                                        }));
                    } catch (final IOException e) {
                        LOG.warn("Exception occurred while querying", e);
                    }
                },
                5,
                TimeUnit.SECONDS);
        return minerConfRef.get();
    }

    /**
     * Returns a system attribute.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param realm    The realm.
     * @param key      The key.
     *
     * @return The attribute value.
     */
    public static Optional<String> getSystemAttribute(
            final String ip,
            final int port,
            final String username,
            final String password,
            final String realm,
            final String key) {
        final AtomicReference<String> attribute = new AtomicReference<>();
        try {
            Query.digestGet(
                    ip,
                    port,
                    realm,
                    "/cgi-bin/get_system_info.cgi",
                    username,
                    password,
                    (code, s) -> {
                        try {
                            final Map<String, Object> conf =
                                    OBJECT_MAPPER.readValue(
                                            s,
                                            new TypeReference<Map<String, Object>>() {
                                            });
                            final String typeValue =
                                    conf.getOrDefault(
                                            key,
                                            "").toString();
                            attribute.set(typeValue);
                        } catch (final Exception e) {
                            // Ignore if we can't get
                        }
                    },
                    5,
                    TimeUnit.SECONDS);
        } catch (final Exception e) {
            // Ignore if we can't get
        }
        return Optional.ofNullable(attribute.get());
    }

    /**
     * Gets the type.
     *
     * @param ip           The ip.
     * @param port         The port.
     * @param webPort      The web port.
     * @param realm        The realm.
     * @param username     The username.
     * @param password     The password.
     * @param typeCallback The callback.
     *
     * @return The type.
     */
    public static Optional<AntminerType> getType(
            final String ip,
            final int port,
            final int webPort,
            final String realm,
            final String username,
            final String password,
            final TypeCallback typeCallback) {
        final AtomicReference<AntminerType> finalType =
                new AtomicReference<>();
        final CgMiner cgMiner =
                new CgMiner.Builder(new Context(), Collections.emptyList())
                        .setApiIp(ip)
                        .setApiPort(port)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.VERSION)
                                        .build(),
                                (builder, response) -> {
                                    final AtomicReference<AntminerType> type = new AtomicReference<>();
                                    try {
                                        AntminerUtils.toType(
                                                response.getValues(),
                                                typeCallback)
                                                .ifPresent(type::set);
                                    } catch (final EmptySiteException ese) {
                                        // Ignore
                                    }
                                    finalType.set(type.get());
                                })
                        .build();

        try {
            cgMiner.getStats();
        } catch (final Exception e) {
            LOG.warn("Exception occurred while querying", e);
        }

        if (finalType.get() == null) {
            // Attempt to use the system info
            try {
                getSystemAttribute(
                        ip,
                        webPort,
                        username,
                        password,
                        realm,
                        "minertype").ifPresent(
                        value -> {
                            typeCallback.accept(
                                    "",
                                    "",
                                    value);
                            AntminerType.forModel("Type", value).ifPresent(finalType::set);
                        });
            } catch (final Exception e) {
                LOG.warn("Exception occurred while querying", e);
            }
        }

        return Optional.ofNullable(finalType.get());
    }

    /**
     * Checks to see if the miner is a new generation.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param realm    The realm.
     * @param username The username.
     * @param password The password.
     *
     * @return Whether or not the miner is a new gen.
     *
     * @throws Exception on failure.
     */
    public static boolean isNewGen(
            final String ip,
            final int port,
            final String realm,
            final String username,
            final String password) throws Exception {
        final Map<String, Object> minerConf =
                getConf(
                        ip,
                        port,
                        realm,
                        "/cgi-bin/get_miner_conf.cgi",
                        username,
                        password);
        return minerConf.containsKey("bitmain-pwth");
    }

    /**
     * Finds the {@link AntminerType} from the provided versions response.
     *
     * @param responseValues The response values.
     * @param callback       The callback.
     *
     * @return The type.
     *
     * @throws EmptySiteException if type couldn't be determined.
     */
    public static Optional<AntminerType> toType(
            final Map<String, List<Map<String, String>>> responseValues,
            final TypeCallback callback)
            throws EmptySiteException {
        final Map<String, String> values = toVersion(responseValues);
        Optional<AntminerType> type = toBraiinsType(values);
        if (type.isPresent()) {
            callback.accept(
                    values.getOrDefault("Type", ""),
                    values.getOrDefault("CompileTime", ""),
                    type.get().getIdentifier());
        } else {
            final String finalType = values.get(TYPE);
            type = AntminerType.forModel(TYPE, finalType);
            callback.accept(
                    values.getOrDefault("Type", ""),
                    values.getOrDefault("CompileTime", ""),
                    finalType);
        }
        return type;
    }

    /**
     * Gets the version info from the response values.
     *
     * @param responseValues The response values.
     *
     * @return The version info, if found.
     *
     * @throws EmptySiteException if not found.
     */
    static Map<String, String> toVersion(
            final Map<String, List<Map<String, String>>> responseValues)
            throws EmptySiteException {
        return responseValues
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("VERSION"))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(AntminerUtils::isKnown)
                .findFirst()
                .orElseThrow(EmptySiteException::new);
    }

    /**
     * Checks to see if the type is known.
     *
     * @param values The values to inspect.
     *
     * @return The type.
     */
    private static boolean isKnown(final Map<String, String> values) {
        return values.containsKey(TYPE) || toBraiinsType(values).isPresent();
    }

    /**
     * Checks to see if the provided values are braiins.
     *
     * @param values The values.
     *
     * @return The type, if found.
     */
    private static Optional<AntminerType> toBraiinsType(final Map<String, String> values) {
        return BRAIINS_TYPES
                .stream()
                .filter(type -> values.containsKey(type.getIdentifier()))
                .findFirst();
    }

    /** A mechanism for notifying the listener that a type was determined. */
    public interface TypeCallback {

        /**
         * Provides the version type, compile time, and sanitized type.
         *
         * @param versionType        The version type.
         * @param versionCompileTime The compile time.
         * @param sanitizedType      The sanitized type.
         */
        void accept(
                String versionType,
                String versionCompileTime,
                String sanitizedType);
    }
}
