package mn.foreman.model.metadata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** An enum representing API versions. */
public enum ApiVersion {

    /** API version 1.0.0. */
    V1_0_0("1.0.0"),

    /** An unknown API version. */
    UNKNOWN("unknown");

    /** All of the version, mapped by version string. */
    private static final ConcurrentMap<String, ApiVersion> VERSIONS =
            new ConcurrentHashMap<>();

    static {
        for (final ApiVersion apiVersion : values()) {
            VERSIONS.put(
                    apiVersion.getVersion(),
                    apiVersion);
        }
    }

    /** The version. */
    private final String version;

    /**
     * Constructor.
     *
     * @param version The version.
     */
    ApiVersion(final String version) {
        this.version = version;
    }

    @JsonCreator
    public static ApiVersion forValue(final String value) {
        return VERSIONS.getOrDefault(
                value,
                UNKNOWN);
    }

    /**
     * Returns the version.
     *
     * @return The version.
     */
    public String getVersion() {
        return this.version;
    }

    @JsonValue
    public String toValue() {
        return this.version;
    }
}
