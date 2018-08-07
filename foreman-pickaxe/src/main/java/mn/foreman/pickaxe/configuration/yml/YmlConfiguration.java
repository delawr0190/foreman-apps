package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** A YML-based {@link Configuration} parser. */
public class YmlConfiguration
        implements Configuration {

    /** The antminer configurations. */
    private final List<Map<String, String>> antConfigs;

    /** The user's API key. */
    private final String apiKey;

    /** The baikal configurations. */
    private final List<Map<String, String>> baikalConfigs;

    /** The bminer configurations. */
    private final List<Map<String, String>> bminerConfigs;

    /** The castxmr configurations. */
    private final List<Map<String, String>> castxmrConfigs;

    /** The ccminer configurations. */
    private final List<Map<String, String>> ccminerConfigs;

    /** The claymore configs. */
    private final List<Map<String, String>> claymoreConfigs;

    /** The cryptodredge configurations. */
    private final List<Map<String, String>> cryptoDredgeConfigs;

    /** The dstm configurations. */
    private final List<Map<String, String>> dstmConfigs;

    /** The ethminer configurations. */
    private final List<Map<String, String>> ethminerConfigs;

    /** The ewbf configs. */
    private final List<Map<String, String>> ewbfConfigs;

    /** The excavator configs. */
    private final List<Map<String, String>> excavatorConfigs;

    /** The FOREMAN API URL. */
    private final String foremanApiUrl;

    /** The jceminer configurations. */
    private final List<Map<String, String>> jceminerConfigs;

    /** The lolminer configurations. */
    private final List<Map<String, String>> lolminerConfigs;

    /** The phoenix configs. */
    private final List<Map<String, String>> phoenixConfigs;

    /** How frequently to poll, in seconds. */
    private final int pollFrequencyInSeconds;

    /** The sgminer configurations. */
    private final List<Map<String, String>> sgminerConfigs;

    /** The srbminer configurations. */
    private final List<Map<String, String>> srbminerConfigs;

    /** The trex configurations. */
    private final List<Map<String, String>> trexConfigs;

    /** The wildrig configs. */
    private final List<Map<String, String>> wildrigConfigs;

    /** The xmrig configs. */
    private final List<Map<String, String>> xmrigConfigs;

    /** The zenemy configurations. */
    private final List<Map<String, String>> zenemyConfigs;

    /**
     * Constructor.
     *
     * <p>Note: intentionally hidden (built via JACKSON).</p>
     *
     * @param foremanApiUrl          The FOREMAN API URL.
     * @param apiKey                 The API key.
     * @param antConfigs             The antminer configs.
     * @param baikalConfigs          The baikal configs.
     * @param bminerConfigs          The bminer configs.
     * @param castxmrConfigs         The castxmr configs.
     * @param ccminerConfigs         The ccminer configs.
     * @param claymoreConfigs        The claymore configs.
     * @param dstmConfigs            The dstm configs.
     * @param ethminerConfigs        The ethminer configs.
     * @param ewbfConfigs            The ewbf configs.
     * @param excavatorConfigs       The excavator configs.
     * @param jceminerConfigs        The jceminer configs.
     * @param lolminerConfigs        The lolminer configs.
     * @param phoenixConfigs         The phoenix configs.
     * @param sgminerConfigs         The sgminer configs.
     * @param srbminerConfigs        The srbminer configs.
     * @param trexConfigs            The trex configs.
     * @param xmrigConfigs           The xmrig configs.
     * @param pollFrequencyInSeconds How frequently to poll, in seconds.
     */
    private YmlConfiguration(
            @JsonProperty("foremanApiUrl") final String foremanApiUrl,
            @JsonProperty("apiKey") final String apiKey,
            @JsonProperty("antminers") final List<Map<String, String>> antConfigs,
            @JsonProperty("baikal") final List<Map<String, String>> baikalConfigs,
            @JsonProperty("bminers") final List<Map<String, String>> bminerConfigs,
            @JsonProperty("castxmr") final List<Map<String, String>> castxmrConfigs,
            @JsonProperty("ccminers") final List<Map<String, String>> ccminerConfigs,
            @JsonProperty("claymores") final List<Map<String, String>> claymoreConfigs,
            @JsonProperty("cryptodredges") final List<Map<String, String>> cryptoDredgeConfigs,
            @JsonProperty("dstms") final List<Map<String, String>> dstmConfigs,
            @JsonProperty("ethminers") final List<Map<String, String>> ethminerConfigs,
            @JsonProperty("ewbfs") final List<Map<String, String>> ewbfConfigs,
            @JsonProperty("excavators") final List<Map<String, String>> excavatorConfigs,
            @JsonProperty("jceminer") final List<Map<String, String>> jceminerConfigs,
            @JsonProperty("lolminer") final List<Map<String, String>> lolminerConfigs,
            @JsonProperty("phoenix") final List<Map<String, String>> phoenixConfigs,
            @JsonProperty("sgminer") final List<Map<String, String>> sgminerConfigs,
            @JsonProperty("srbminers") final List<Map<String, String>> srbminerConfigs,
            @JsonProperty("trex") final List<Map<String, String>> trexConfigs,
            @JsonProperty("wildrig") final List<Map<String, String>> wildrigConfigs,
            @JsonProperty("xmrigs") final List<Map<String, String>> xmrigConfigs,
            @JsonProperty("zenemys") final List<Map<String, String>> zenemyConfigs,
            @JsonProperty("pollFrequencyInSeconds") int pollFrequencyInSeconds) {
        Validate.notEmpty(
                foremanApiUrl,
                "foremanApiUrl cannot be empty");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, pollFrequencyInSeconds,
                "pollFrequencyInSeconds must be positive");
        this.foremanApiUrl = foremanApiUrl;
        this.apiKey = apiKey;
        this.antConfigs = toConfigs(antConfigs);
        this.baikalConfigs = toConfigs(baikalConfigs);
        this.bminerConfigs = toConfigs(bminerConfigs);
        this.castxmrConfigs = toConfigs(castxmrConfigs);
        this.ccminerConfigs = toConfigs(ccminerConfigs);
        this.claymoreConfigs = toConfigs(claymoreConfigs);
        this.cryptoDredgeConfigs = toConfigs(cryptoDredgeConfigs);
        this.dstmConfigs = toConfigs(dstmConfigs);
        this.ethminerConfigs = toConfigs(ethminerConfigs);
        this.ewbfConfigs = toConfigs(ewbfConfigs);
        this.excavatorConfigs = toConfigs(excavatorConfigs);
        this.jceminerConfigs = toConfigs(jceminerConfigs);
        this.lolminerConfigs = toConfigs(lolminerConfigs);
        this.phoenixConfigs = toConfigs(phoenixConfigs);
        this.sgminerConfigs = toConfigs(sgminerConfigs);
        this.srbminerConfigs = toConfigs(srbminerConfigs);
        this.trexConfigs = toConfigs(trexConfigs);
        this.wildrigConfigs = toConfigs(wildrigConfigs);
        this.xmrigConfigs = toConfigs(xmrigConfigs);
        this.zenemyConfigs = toConfigs(zenemyConfigs);
        this.pollFrequencyInSeconds = pollFrequencyInSeconds;
    }

    @Override
    public List<Map<String, String>> getAntminerConfigs() {
        return Collections.unmodifiableList(this.antConfigs);
    }

    @Override
    public String getApiKey() {
        return this.apiKey;
    }

    @Override
    public List<Map<String, String>> getBaikalConfigs() {
        return Collections.unmodifiableList(this.baikalConfigs);
    }

    @Override
    public List<Map<String, String>> getBminerConfigs() {
        return Collections.unmodifiableList(this.bminerConfigs);
    }

    @Override
    public List<Map<String, String>> getCastxmrConfigs() {
        return Collections.unmodifiableList(this.castxmrConfigs);
    }

    @Override
    public List<Map<String, String>> getCcminerConfigs() {
        return Collections.unmodifiableList(this.ccminerConfigs);
    }

    @Override
    public List<Map<String, String>> getClaymoreConfigs() {
        return Collections.unmodifiableList(this.claymoreConfigs);
    }

    @Override
    public List<Map<String, String>> getCryptoDredgeConfigs() {
        return Collections.unmodifiableList(this.cryptoDredgeConfigs);
    }

    @Override
    public List<Map<String, String>> getDstmConfigs() {
        return Collections.unmodifiableList(this.dstmConfigs);
    }

    @Override
    public List<Map<String, String>> getEthminerConfigs() {
        return Collections.unmodifiableList(this.ethminerConfigs);
    }

    @Override
    public List<Map<String, String>> getEwbfConfigs() {
        return Collections.unmodifiableList(this.ewbfConfigs);
    }

    @Override
    public List<Map<String, String>> getExcavatorConfigs() {
        return Collections.unmodifiableList(this.excavatorConfigs);
    }

    @Override
    public String getForemanApiUrl() {
        return this.foremanApiUrl;
    }

    @Override
    public List<Map<String, String>> getJceminerConfigs() {
        return Collections.unmodifiableList(this.jceminerConfigs);
    }

    @Override
    public List<Map<String, String>> getLolminerConfigs() {
        return Collections.unmodifiableList(this.lolminerConfigs);
    }

    @Override
    public List<Map<String, String>> getPhoenixConfigs() {
        return Collections.unmodifiableList(this.phoenixConfigs);
    }

    @Override
    public int getPollFrequencyInSeconds() {
        return this.pollFrequencyInSeconds;
    }

    @Override
    public List<Map<String, String>> getSgminerConfigs() {
        return Collections.unmodifiableList(this.sgminerConfigs);
    }

    @Override
    public List<Map<String, String>> getSrbminerConfigs() {
        return Collections.unmodifiableList(this.srbminerConfigs);
    }

    @Override
    public List<Map<String, String>> getTrexConfigs() {
        return Collections.unmodifiableList(this.trexConfigs);
    }

    @Override
    public List<Map<String, String>> getWildrigConfigs() {
        return Collections.unmodifiableList(this.wildrigConfigs);
    }

    @Override
    public List<Map<String, String>> getXmrigConfigs() {
        return Collections.unmodifiableList(this.xmrigConfigs);
    }

    @Override
    public List<Map<String, String>> getZenemyConfigs() {
        return Collections.unmodifiableList(this.zenemyConfigs);
    }

    /**
     * Safe-copies the provided configuration.
     *
     * @param configs The configurations.
     *
     * @return The safe copy.
     */
    private static List<Map<String, String>> toConfigs(
            final List<Map<String, String>> configs) {
        return (configs != null
                ? new ArrayList<>(configs)
                : Collections.emptyList());
    }
}