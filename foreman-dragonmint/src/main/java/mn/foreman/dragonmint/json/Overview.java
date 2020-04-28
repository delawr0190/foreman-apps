package mn.foreman.dragonmint.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The following model provides a {@link Overview} model object of a response
 * from dragonmint:
 *
 * <pre>
 *     POST /api/overview
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "success": true,
 *   "type": "T2",
 *   "hardware": {
 *     "status": "06:32:15 up 11 days, 22:58, load average: 0.00, 0.05, 0.07",
 *     "memUsed": 124468,
 *     "memFree": 126096,
 *     "memTotal": 250564,
 *     "cacheUsed": 37232,
 *     "cacheFree": 145460,
 *     "cacheTotal": 182692
 *   },
 *   "network": {
 *     "dhcp": "dhcp",
 *     "ipaddress": "10.30.9.17",
 *     "netmask": "255.255.255.0",
 *     "gateway": "10.30.9.254",
 *     "dns1": "192.168.16.101",
 *     "dns2": "10.30.60.126"
 *   },
 *   "version": {
 *     "hwver": "g19",
 *     "ethaddr": "a0: b0:45:21:5b: f0",
 *     "build_date": "23rd of May 2018 07:17 AM",
 *     "platform_v": "t2_20180523_071756"
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Overview {

    /** The type. */
    @JsonProperty("type")
    public String type;
}