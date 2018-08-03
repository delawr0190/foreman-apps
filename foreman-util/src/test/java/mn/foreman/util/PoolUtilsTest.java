package mn.foreman.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link PoolUtils}. */
public class PoolUtilsTest {

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void sanitizeBminer() {
        assertEquals(
                "blake2s.mine.zpool.ca:5766",
                PoolUtils.sanitizeUrl(
                        "blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766"));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void sanitizeEmpty() {
        assertEquals(
                "",
                PoolUtils.sanitizeUrl(""));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void sanitizePadded() {
        assertEquals(
                "",
                PoolUtils.sanitizeUrl("    "));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void sanitizeStandard() {
        assertEquals(
                "cryptonightv7.eu.nicehash.com:3363",
                PoolUtils.sanitizeUrl(
                        "stratum+tcp://cryptonightv7.eu.nicehash.com:3363"));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void sanitizeTrailingSlash() {
        assertEquals(
                "cryptonightv7.eu.nicehash.com:3363",
                PoolUtils.sanitizeUrl(
                        "stratum+tcp://cryptonightv7.eu.nicehash.com:3363/"));
    }
}