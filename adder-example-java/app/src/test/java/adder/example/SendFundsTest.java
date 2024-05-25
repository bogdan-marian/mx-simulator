package adder.example;

import multiversx.Address;
import multiversx.Exceptions;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import okhttp3.*;

import java.io.IOException;

public class SendFundsTest {

    private static final String SIMULATOR_URL = "http://localhost:8085";
    private static final String GENERATE_BLOCKS_URL = SIMULATOR_URL + "/simulator/generate-blocks";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient httpClient = new OkHttpClient();


    @Test
    public void sendFundsTest() throws Exceptions.AddressException, IOException {

        String aliceBench32 = "erd1w30a66fzeg9t9q8prlscetkvc03rsk76mcvz7crkap6pq6a8ehrqgekyl3";
        Address aliceAddress = Address.fromBech32(aliceBench32);

        // Send user funds
        String sendFundsUrl = SIMULATOR_URL + "/transaction/send-user-funds";
        String sendFundsJson = "{\"receiver\": \"" + aliceBench32 + "\"}";
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
