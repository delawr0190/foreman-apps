package mn.foreman.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link PoolUtils}. */
public class PoolUtilsTest {

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void testSanitizeBminer() {
        assertEquals(
                "blake2s.mine.zpool.ca:5766",
                PoolUtils.sanitizeUrl(
                        "blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766"));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void testSanitizeEmpty() {
        assertEquals(
                "",
                PoolUtils.sanitizeUrl(""));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void testSanitizePadded() {
        assertEquals(
                "",
                PoolUtils.sanitizeUrl("    "));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void testSanitizePhoenix() {
        assertEquals(
                "eu1.ethermine.org:4444",
                PoolUtils.sanitizeUrl(
                        ">eu1.ethermine.org:4444"));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void testSanitizeStandard() {
        assertEquals(
                "cryptonightv7.eu.nicehash.com:3363",
                PoolUtils.sanitizeUrl(
                        "stratum+tcp://cryptonightv7.eu.nicehash.com:3363"));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void testSanitizeTrailingSlash() {
        assertEquals(
                "cryptonightv7.eu.nicehash.com:3363",
                PoolUtils.sanitizeUrl(
                        "stratum+tcp://cryptonightv7.eu.nicehash.com:3363/"));
    }

    /** Tests {@link PoolUtils#sanitizeUrl(String)}. */
    @Test
    public void testSanitizeUppercase() {
        assertEquals(
                "sha256asicboost.usa.nicehash.com:3334#xnsub",
                PoolUtils.sanitizeUrl(
                        "SHA256AsicBoost.usa.nicehash.com:3334#xnsub"));
    }
}