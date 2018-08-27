package mn.foreman.util;

/** Utilities for extracting the version number. */
public class VersionUtils {

    /**
     * Constructor.
     *
     * Note: intentionally hidden.
     */
    private VersionUtils() {
        // Do nothing
    }

    /**
     * Returns the application version.
     *
     * @return The application version.
     */
    public static String getVersion() {
        return VersionUtils.class.getPackage().getImplementationVersion();
    }
}