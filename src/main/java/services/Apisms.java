package services;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class Apisms {
    public  static void SendSMS(String nom) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"messages\":[{\"destinations\":[{\"to\":\"21696618158\"}],\"from\":\"Gym House\",\"text\":\"Hello,\\n\\n ce produit a presque terminer !!! Charger "+nom+"\"}]}");
        Request request = new Request.Builder()
                .url("https://ggeqlj.api.infobip.com/sms/2/text/advanced")
                .method("POST", body)
                .addHeader("Authorization", "App 6666cc5039134929c258f578868c8fc6-9304f026-b274-4829-ae17-da9c72bad82e")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        // Use response as needed, e.g., to get response body
        String responseBody = response.body().string();
        System.out.println(responseBody);
    }
}
