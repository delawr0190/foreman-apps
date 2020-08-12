package mn.foreman.antminer;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * A {@link BraiinsRebootAction} provides an action implementation that will
 * perform a reboot on a bOS miner over SSH.
 */
public class BraiinsRebootAction
        implements AsicAction.CompletableAction {

    /** The reboot command. */
    private static final String COMMAND = "reboot";

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BraiinsRebootAction.class);

    /** The default SSH port. */
    private static final int SSH_PORT = 22;

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws MinerException {
        final String username = args.get("username").toString();
        final String password = args.get("password").toString();

        Session session = null;
        Channel channel = null;

        try {
            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            final JSch jsch = new JSch();
            session =
                    jsch.getSession(
                            username,
                            ip,
                            SSH_PORT);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();

            final ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream();

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(COMMAND);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(byteArrayOutputStream);

            final InputStream in = channel.getInputStream();
            channel.connect();

            final byte[] tmp = new byte[1024];
            while (!channel.isClosed()) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    LOG.info("SSH read: {}", new String(tmp, 0, i));
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (final Exception e) {
                    // Ignore
                }
            }

            LOG.info("SSH channel closed (status={})",
                    channel.getExitStatus());
        } catch (final Exception e) {
            throw new MinerException("Failed to connect to miner SSH");
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

        LOG.info("Miner has rebooted");
        return true;
    }
}
