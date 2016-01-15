import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class PotentialNameNodeDiscovererTest {

    /**
     * Extract potential NameNodes from the Nmap output.
     */
    @Test
    public void getPotentialNameNodesTest() {
        String nmapJsonOutput = "{  \n" +
                "   \"nmaprun\":{  \n" +
                "      \"args\":\"nmap -&#45;open -p 50070 -oX - 109.231.122.0-100\",\n" +
                "      \"startstr\":\"Fri Jan 15 15:17:37 2016\",\n" +
                "      \"xmloutputversion\":1.04,\n" +
                "      \"scanner\":\"nmap\",\n" +
                "      \"start\":1452863857,\n" +
                "      \"host\":{  \n" +
                "         \"times\":{  \n" +
                "            \"rttvar\":59679,\n" +
                "            \"srtt\":76578,\n" +
                "            \"to\":315294\n" +
                "         },\n" +
                "         \"address\":{  \n" +
                "            \"addrtype\":\"ipv4\",\n" +
                "            \"addr\":\"109.231.122.54\"\n" +
                "         },\n" +
                "         \"endtime\":1452863862,\n" +
                "         \"hostnames\":\"\",\n" +
                "         \"starttime\":1452863857,\n" +
                "         \"ports\":{  \n" +
                "            \"port\":{  \n" +
                "               \"protocol\":\"tcp\",\n" +
                "               \"state\":{  \n" +
                "                  \"reason\":\"syn-ack\",\n" +
                "                  \"reason_ttl\":0,\n" +
                "                  \"state\":\"open\"\n" +
                "               },\n" +
                "               \"portid\":50070\n" +
                "            }\n" +
                "         },\n" +
                "         \"status\":{  \n" +
                "            \"reason\":\"conn-refused\",\n" +
                "            \"reason_ttl\":0,\n" +
                "            \"state\":\"up\"\n" +
                "         }\n" +
                "      },\n" +
                "      \"debugging\":{  \n" +
                "         \"level\":0\n" +
                "      },\n" +
                "      \"runstats\":{  \n" +
                "         \"hosts\":{  \n" +
                "            \"total\":101,\n" +
                "            \"up\":12,\n" +
                "            \"down\":89\n" +
                "         },\n" +
                "         \"finished\":{  \n" +
                "            \"elapsed\":5.75,\n" +
                "            \"summary\":\"Nmap done at Fri Jan 15 15:17:43 2016; 101 IP addresses (12 hosts up) scanned in 5.75 seconds\",\n" +
                "            \"exit\":\"success\",\n" +
                "            \"time\":1452863863,\n" +
                "            \"timestr\":\"Fri Jan 15 15:17:43 2016\"\n" +
                "         }\n" +
                "      },\n" +
                "      \"version\":\"6.49BETA5\",\n" +
                "      \"scaninfo\":{  \n" +
                "         \"protocol\":\"tcp\",\n" +
                "         \"numservices\":1,\n" +
                "         \"services\":50070,\n" +
                "         \"type\":\"connect\"\n" +
                "      },\n" +
                "      \"verbose\":{  \n" +
                "         \"level\":0\n" +
                "      }\n" +
                "   }\n" +
                "}";
        PotentialNameNodeDiscoverer pnnd = new PotentialNameNodeDiscoverer();
        List<String> potentialNameNodes = pnnd.getPotentialNameNodes(nmapJsonOutput);
        assertTrue(potentialNameNodes.size() == 1);
    }
}
