package mn.foreman.avalon;

import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests obtaining a MAC address from an Avalon. */
@RunWith(Parameterized.class)
public class AvalonMacITest
        extends AbstractMacITest {

    /**
     * Constructor.
     *
     * @param handlers    The handlers.
     * @param expectedMac The expected MAC.
     */
    public AvalonMacITest(
            final Map<String, ServerHandler> handlers,
            final String expectedMac) {
        super(
                new AvalonFactory(),
                Collections.singletonList(
                        new FakeHttpMinerServer(
                                8080,
                                handlers)),
                expectedMac);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                ImmutableMap.of(
                                        "/get_minerinfo.cgi",
                                        new HttpHandler(
                                                "",
                                                "minerinfoCallback({\"version\":\"20090401_fb963dc_1236943\",\"mac\":\"b4:a2:eb:31:2f:7c\", \"ipv4\":\"10.16.250.99\", \"hwtype\":\"AvalonMiner 1047\", \"sys_status\":\"1\", });")),
                                "b4:a2:eb:31:2f:7c"
                        },
                        {
                                ImmutableMap.of(
                                        "/updatedashboard.cgi",
                                        new HttpHandler(
                                                "",
                                                "DashboardCallback({\"version\":\"20051101_85dc6f0_0332654\",\"mm_nonce_log\":\"22503-7528448,23403-7624704,24303-7596032,25203-7674880,26103-7440384,27003-7607296,27903-7529472,28803-7536640,29703-7507968,30603-7416832,31503-7698432,32403-7654400,33303-7323648,34203-7853056,35103-7508992,36003-7416832,36903-7418880,37803-7542784,38703-7520256,39603-7610368,40503-7616512,41403-7447552,42303-7684096,43203-7579648,44103-7430144,45003-7571456,45903-7598080,46803-7642112,47703-7612416,48603-7462912,49503-7408640,50403-7689216,51303-7588864,52203-7432192,53103-7469056,54003-7759872,54903-7660544,55803-7401472,56703-7294976,57603-7369728,58503-7390208,59403-7451648,60303-7273472,61203-7332864,62103-7625728,63003-7553024,63903-7273472,64803-7374848,65703-7373824,66603-7209984,67503-7379968,68403-7431168,69303-7655424,70203-7564288,71103-7446528,72003-7426048,72903-7336960,73803-7348224,74703-7453696,75603-7373824,76503-7396352,77403-7468032,78304-7499776,79204-7528448,80104-7407616,81004-7522304,81904-7282688,82804-7548928,83704-7546880,84604-7555072,85504-7732224,86404-7324672,87304-7655424,88204-7878656,89104-7747584,90004-7513088,90904-7447552,91804-7616512,92704-7711744,93604-7591936,94504-7743488,95404-7319552,96304-7265280,97204-7447552,98104-7507968,99004-7297024,99904-7176192,100804-7662592,101704-6776832,102604-7518208,103504-7454720,104404-7549952,105304-7309312,106204-7354368,107104-7232512,108004-7314432,\",\"pool_share_log\":\"21030-7740000,21974-8820000,22909-7110000,23813-8100000,24720-6570000,25630-8280000,26552-7290000,27454-8640000,28356-7200000,29257-5940000,30175-6570000,31092-6840000,31996-8370000,32899-6390000,33838-7740000,34741-8640000,35646-4500000,36571-8100000,37474-6660000,38419-6480000,39323-6930000,40262-8010000,41177-7200000,42095-6480000,43068-7290000,43979-8280000,44881-7470000,45799-6390000,46705-8370000,47606-4982358,48510-9945452,49495-6988696,50412-8864398,51315-8280000,52249-7740000,53230-6899900,54139-7257492,55040-6512358,55972-6840000,56886-7380000,57832-9180000,58806-8460000,59717-7650000,60619-5481572,61529-7391890,62435-6854298,63406-6988696,64308-9407860,65340-6281940,66265-9720000,67175-7380000,68110-6570000,69020-8100000,69966-9900000,70902-7020000,71837-7020000,72752-6300000,73661-6210000,74589-8010000,75493-8280000,76395-7560000,77296-6300000,78205-8100000,79122-6930000,80035-7740000,80955-8190000,81862-8460000,82793-6750000,83706-7200000,84607-7290000,85514-6390000,86417-7830000,87317-7200000,88245-6840000,89172-7560000,90076-6660000,90993-7920000,91903-8460000,92806-7200000,93718-8820000,94628-5260786,95557-8010000,96468-7380000,97369-8730000,98297-8010000,99224-8460000,100155-6750000,101066-7650000,101970-6210000,102871-7020000,103778-7560000,104681-6120000,105587-7020000,106519-8460000,107427-7830000,108332-5940000,\",\"ghsmm_log\":\"26086-37811,27131-37771,28173-37793,29215-37823,30258-37809,31300-37628,32342-37631,33387-37836,34429-37675,35471-37570,36514-37623,37556-37630,38598-37629,39643-37633,40685-37573,41727-37625,42770-37844,43812-37861,44855-37758,45899-37796,46942-37813,47984-37833,49027-37846,50069-37850,51112-37807,52156-37785,53199-37603,54241-37480,55283-37219,56326-37202,57368-37215,58413-37071,59455-36981,60498-36995,61541-37001,62583-36985,63626-36989,64671-36878,65714-36983,66756-37011,67799-37559,68841-37237,69884-37200,70929-37218,71971-36975,73014-36982,74056-36988,75099-36998,76141-36985,77185-37023,78228-37160,79270-37064,80313-37231,81356-37297,82398-37204,83443-37397,84486-37712,85528-37619,86571-37827,87614-37833,88656-37827,89701-37800,90743-37350,91786-37644,92828-37576,93872-37581,94915-37141,95959-37161,97002-37208,98044-37197,99088-36904,100132-36827,101175-36665,102220-36701,103263-36747,104306-36763,105348-36765,106391-36756,107434-36735,108478-36973,9403-37814,10446-37841,11488-37820,12530-37812,13573-37604,14617-37414,15660-37439,16702-37295,17745-37300,18787-37631,19830-37591,20874-37794,21916-37818,22959-37812,24001-37806,25044-37831,\",\"mac\":\"b4:a2:eb:31:3b:05\", \"ipv4\":\"10.1.11.44\", \"elapsed\":\"30 h 8 m 57 s\", \"av\":\"35.39\", \"url\":\"stratum+tcp://sha256asicboost.eu.nicehash.com:3368\", \"worker\":\"xxx\", \"accepted\":\"6203\", \"reject\":\"74\", \"temperature\":\"13°C / 55°F\", \"fan\":\"25\", \"hwtype\":\"AvalonMiner 1047\", \"hash_5m\":\"35.43\", \"sys_status\":\"Work: In Work, Hash Board: 2 \", }); UpdatePageCallback({\"pageId\":\"dashboard\"});")),
                                "b4:a2:eb:31:3b:05"
                        }
                });
    }
}
