package adder.example;

import multiversx.*;
import multiversx.Address;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BroadcastTest {

    private static final String SIMULATOR_URL = "http://localhost:8085";
    private static final String GENERATE_BLOCKS_URL = SIMULATOR_URL + "/simulator/generate-blocks";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ProxyProvider provider;

    public BroadcastTest() {
        this.provider = new ProxyProvider(SIMULATOR_URL);
    }



    @Test
    public void sendTransactions() throws Exception {
        // this test doesn't use mock http server because it is a very good example of for transactions can be sent to the chain
        NetworkConfig.getDefault().sync(this.provider);
        String aliceBench32 = "erd1w30a66fzeg9t9q8prlscetkvc03rsk76mcvz7crkap6pq6a8ehrqgekyl3";
        sendFundsTest(aliceBench32);

        Address aliceAddress = Address.fromBech32(aliceBench32);
        Account alice = new Account(aliceAddress);
        alice.sync(this.provider);
        assertTrue(alice.getBalance().compareTo(BigInteger.ZERO) > 0);

        String privateKeyOfAlice = "1a927e2af5306a9bb2ea777f73e06ecc0ac9aaa72fb4ea3fecf659451394cccf";


        Wallet walletOfAlice = new Wallet(privateKeyOfAlice);

        for (int i = 0; i < 5; i++) {
            Transaction transaction = new Transaction();
            assertEquals("chain", transaction.getChainID());
            assertEquals(1000000000, transaction.getGasPrice());

            Address addressOfBob = Address.fromBech32("erd1cux02zersde0l7hhklzhywcxk4u9n4py5tdxyx7vrvhnza2r4gmq4vw35r");

            transaction.setNonce(alice.getNonce());
            transaction.setSender(aliceAddress);
            transaction.setReceiver(addressOfBob);
            transaction.sign(walletOfAlice);
            transaction.send(this.provider);

            assertEquals(64, transaction.getTxHash().length());
            alice.incrementNonce();
        }
    }

    public void sendFundsTest(String bench32) throws Exceptions.AddressException, IOException {



        // Send user funds
        String sendFundsUrl = SIMULATOR_URL + "/transaction/send-user-funds";
        String sendFundsJson = "{\"receiver\": \"" + bench32 + "\"}";
        doPost(sendFundsUrl, sendFundsJson);

        // Generate blocks
        String generateBlocksUrl = GENERATE_BLOCKS_URL + "/3";
        doPost(generateBlocksUrl, "{}");

        System.out.println("End of test");
    }

    private void doPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            System.out.println(response.body().string());
        }
    }
}
