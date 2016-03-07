import dto.DeadNode;
import dto.LiveNode;
import dto.LiveNodeMinimal;
import nodediscovery.HdfsNodeExtractor;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;

import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Parameter;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class HdfsNodeExtractorTest {

    /**
     * Start HTTP server until the test ends.
     */
    @Rule
    public MockServerRule mockServerRule = new MockServerRule(1080, this);

    /**
     * Extract NameNode data from HTTP request.
     */
    @Test
    public void nodeExtractorTester() {
        new MockServerClient("localhost", 1080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/jmx")
                                .withQueryStringParameters(new Parameter("qry", "Hadoop:service=NameNode,name=NameNodeInfo"))
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\n" +
                                        "  \"beans\": [\n" +
                                        "    {\n" +
                                        "      \"name\": \"Hadoop:service=NameNode,name=NameNodeInfo\",\n" +
                                        "      \"modelerType\": \"org.apache.hadoop.hdfs.server.namenode.FSNamesystem\",\n" +
                                        "      \"Threads\": 137,\n" +
                                        "      \"Total\": 3294872129536,\n" +
                                        "      \"ClusterId\": \"cluster12\",\n" +
                                        "      \"UpgradeFinalized\": true,\n" +
                                        "      \"Version\": \"2.6.0-cdh5.4.5, rab14c89fe25e9fb3f9de4fb852c21365b7c5608b \",\n" +
                                        "      \"Used\": 29004062653,\n" +
                                        "      \"Free\": 3096767340544,\n" +
                                        "      \"Safemode\": \"\",\n" +
                                        "      \"NonDfsUsedSpace\": 169100726339,\n" +
                                        "      \"PercentUsed\": 0.8802788,\n" +
                                        "      \"BlockPoolUsedSpace\": 29004062653,\n" +
                                        "      \"PercentBlockPoolUsed\": 0.8802788,\n" +
                                        "      \"PercentRemaining\": 93.98748,\n" +
                                        "      \"CacheCapacity\": 20869808128,\n" +
                                        "      \"CacheUsed\": 0,\n" +
                                        "      \"TotalBlocks\": 15031,\n" +
                                        "      \"TotalFiles\": 32211,\n" +
                                        "      \"NumberOfMissingBlocks\": 0,\n" +
                                        "      \"NumberOfMissingBlocksWithReplicationFactorOne\": 0,\n" +
                                        "      \"LiveNodes\": \"{\\\"dice.cdh5.w11.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.156:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.156:0\\\",\\\"xferaddr\\\":\\\"109.231.122.156:50010\\\",\\\"lastContact\\\":1,\\\"usedSpace\\\":1676079104,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":13574643712,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":1343,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":1676079104,\\\"remaining\\\":238200979456,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":1676079104,\\\"blockPoolUsedPercent\\\":0.6613012,\\\"volfails\\\":0},\\\"dice.cdh5.w7.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.231:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.231:0\\\",\\\"xferaddr\\\":\\\"109.231.122.231:50010\\\",\\\"lastContact\\\":1,\\\"usedSpace\\\":229081021,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":20534997059,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":326,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":229081021,\\\"remaining\\\":232687624192,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":229081021,\\\"blockPoolUsedPercent\\\":0.09038449,\\\"volfails\\\":0},\\\"dice.cdh5.w13.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.127:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.127:0\\\",\\\"xferaddr\\\":\\\"109.231.122.127:50010\\\",\\\"lastContact\\\":2,\\\"usedSpace\\\":3441164288,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":10621693952,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":7984,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":3441164288,\\\"remaining\\\":239388844032,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":3441164288,\\\"blockPoolUsedPercent\\\":1.3577199,\\\"volfails\\\":0},\\\"dice.cdh5.w3.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.164:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.164:0\\\",\\\"xferaddr\\\":\\\"109.231.122.164:50010\\\",\\\"lastContact\\\":0,\\\"usedSpace\\\":2538962944,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":9522995200,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":5564,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":2538962944,\\\"remaining\\\":241389744128,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":2538962944,\\\"blockPoolUsedPercent\\\":1.0017542,\\\"volfails\\\":0},\\\"dice.cdh5.w6.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.130:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.130:0\\\",\\\"xferaddr\\\":\\\"109.231.122.130:50010\\\",\\\"lastContact\\\":1,\\\"usedSpace\\\":671739904,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":16247328768,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":652,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":671739904,\\\"remaining\\\":236532633600,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":671739904,\\\"blockPoolUsedPercent\\\":0.26503664,\\\"volfails\\\":0},\\\"dice.cdh5.w8.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.194:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.194:0\\\",\\\"xferaddr\\\":\\\"109.231.122.194:50010\\\",\\\"lastContact\\\":2,\\\"usedSpace\\\":3952783360,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":9998598144,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":5504,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":3952783360,\\\"remaining\\\":239500320768,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":3952783360,\\\"blockPoolUsedPercent\\\":1.5595806,\\\"volfails\\\":0},\\\"dice.cdh5.w12.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.240:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.240:0\\\",\\\"xferaddr\\\":\\\"109.231.122.240:50010\\\",\\\"lastContact\\\":2,\\\"usedSpace\\\":3851300864,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":11018133504,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":6261,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":3851300864,\\\"remaining\\\":238582267904,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":3851300864,\\\"blockPoolUsedPercent\\\":1.5195404,\\\"volfails\\\":0},\\\"dice.cdh5.w2.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.173:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.173:0\\\",\\\"xferaddr\\\":\\\"109.231.122.173:50010\\\",\\\"lastContact\\\":1,\\\"usedSpace\\\":2178146304,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":11910160384,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":2375,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":2178146304,\\\"remaining\\\":239363395584,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":2178146304,\\\"blockPoolUsedPercent\\\":0.85939306,\\\"volfails\\\":0},\\\"dice.cdh5.w5.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.201:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.201:0\\\",\\\"xferaddr\\\":\\\"109.231.122.201:50010\\\",\\\"lastContact\\\":2,\\\"usedSpace\\\":2716872704,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":10512719872,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":4419,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":2716872704,\\\"remaining\\\":240222109696,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":2716872704,\\\"blockPoolUsedPercent\\\":1.0719489,\\\"volfails\\\":0},\\\"dice.cdh5.w9.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.182:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.182:0\\\",\\\"xferaddr\\\":\\\"109.231.122.182:50010\\\",\\\"lastContact\\\":2,\\\"usedSpace\\\":2601267200,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":12172259328,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":2649,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":2601267200,\\\"remaining\\\":238678175744,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":2601267200,\\\"blockPoolUsedPercent\\\":1.0263364,\\\"volfails\\\":0},\\\"dice.cdh5.w1.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.187:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.187:0\\\",\\\"xferaddr\\\":\\\"109.231.122.187:50010\\\",\\\"lastContact\\\":1,\\\"usedSpace\\\":1724194816,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":13779677184,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":2447,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":1724194816,\\\"remaining\\\":237947830272,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":1724194816,\\\"blockPoolUsedPercent\\\":0.6802854,\\\"volfails\\\":0},\\\"dice.cdh5.w10.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.207:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.207:0\\\",\\\"xferaddr\\\":\\\"109.231.122.207:50010\\\",\\\"lastContact\\\":1,\\\"usedSpace\\\":1514024960,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":18332155904,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":1136,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":1514024960,\\\"remaining\\\":233605521408,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":1514024960,\\\"blockPoolUsedPercent\\\":0.59736234,\\\"volfails\\\":0},\\\"dice.cdh5.w4.internal\\\":{\\\"infoAddr\\\":\\\"109.231.122.233:50075\\\",\\\"infoSecureAddr\\\":\\\"109.231.122.233:0\\\",\\\"xferaddr\\\":\\\"109.231.122.233:50010\\\",\\\"lastContact\\\":2,\\\"usedSpace\\\":1908445184,\\\"adminState\\\":\\\"In Service\\\",\\\"nonDfsUsedSpace\\\":10875363328,\\\"capacity\\\":253451702272,\\\"numBlocks\\\":4433,\\\"version\\\":\\\"2.6.0-cdh5.4.5\\\",\\\"used\\\":1908445184,\\\"remaining\\\":240667893760,\\\"blockScheduled\\\":0,\\\"blockPoolUsed\\\":1908445184,\\\"blockPoolUsedPercent\\\":0.7529818,\\\"volfails\\\":0}}\",\n" +
                                        "      \"DeadNodes\": \"{}\",\n" +
                                        "      \"DecomNodes\": \"{}\",\n" +
                                        "      \"BlockPoolId\": \"BP-1436051383-109.231.122.228-1441915598128\",\n" +
                                        "      \"NameDirStatuses\": \"{\\\"failed\\\":{},\\\"active\\\":{\\\"/dfs/nn\\\":\\\"IMAGE_AND_EDITS\\\"}}\",\n" +
                                        "      \"NodeUsage\": \"{\\\"nodeUsage\\\":{\\\"min\\\":\\\"0.09%\\\",\\\"median\\\":\\\"0.86%\\\",\\\"max\\\":\\\"1.56%\\\",\\\"stdDev\\\":\\\"0.43%\\\"}}\",\n" +
                                        "      \"NameJournalStatus\": \"[{\\\"stream\\\":\\\"EditLogFileOutputStream(/dfs/nn/current/edits_inprogress_0000000000002047410)\\\",\\\"manager\\\":\\\"FileJournalManager(root=/dfs/nn)\\\",\\\"required\\\":\\\"false\\\",\\\"disabled\\\":\\\"false\\\"}]\",\n" +
                                        "      \"JournalTransactionInfo\": \"{\\\"LastAppliedOrWrittenTxId\\\":\\\"2047829\\\",\\\"MostRecentCheckpointTxId\\\":\\\"2047409\\\"}\",\n" +
                                        "      \"NNStarted\": \"Thu Jan 14 11:14:52 UTC 2016\",\n" +
                                        "      \"CompileInfo\": \"2015-08-12T21:11Z by jenkins from Unknown\",\n" +
                                        "      \"CorruptFiles\": \"[]\",\n" +
                                        "      \"DistinctVersionCount\": 1,\n" +
                                        "      \"DistinctVersions\": [\n" +
                                        "        {\n" +
                                        "          \"key\": \"2.6.0-cdh5.4.5\",\n" +
                                        "          \"value\": 13\n" +
                                        "        }\n" +
                                        "      ],\n" +
                                        "      \"SoftwareVersion\": \"2.6.0-cdh5.4.5\",\n" +
                                        "      \"RollingUpgradeStatus\": null\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}")
                );

        String url = "http://localhost:1080/jmx?qry=Hadoop:service=NameNode,name=NameNodeInfo";
        HdfsNodeExtractor nodeExtractor = new HdfsNodeExtractor(url);

        List<LiveNode> liveNodeList = nodeExtractor.getLiveDataNodes();
        List<LiveNodeMinimal> liveNodeMinimalList = nodeExtractor.getLiveDatNodesMinimal();
        List<DeadNode> deadNodeList = nodeExtractor.getDeadNodes();

        assertTrue(liveNodeList.size() == 13);
        assertTrue(liveNodeMinimalList.size() == 13);
        assertTrue(deadNodeList.size() == 0);
    }

}
