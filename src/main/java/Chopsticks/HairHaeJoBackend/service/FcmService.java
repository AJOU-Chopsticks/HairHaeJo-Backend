package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.util.List;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/hairhaejo-91afb/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String token, String title, String body) throws Exception {
        String message = makeMessage(token, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build();

        Response response = client.newCall(request).execute();
    }

    private String makeMessage(String token,String title, String body)
        throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
            .message(FcmMessage.Message.builder()
                .notification(FcmMessage.Notification.builder().title(title).body(body).build())
                .token(token)
                .build()
            )
            .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws Exception {
        String firebaseConfigPath = "fireBase/hairhaejo-91afb-firebase-adminsdk-t5qz0-4a96c1dd39.json";

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
            .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }
}
