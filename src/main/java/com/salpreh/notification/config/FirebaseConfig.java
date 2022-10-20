package com.salpreh.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    @SneakyThrows
    public FirebaseMessaging firebaseMessaging() {
        InputStream serviceAccount = this.getClass().getResourceAsStream("/gcp/firebase-adminsdk.json");

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        var app = FirebaseApp.initializeApp(options);

        return FirebaseMessaging.getInstance(app);
    }
}
