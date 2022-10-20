package com.salpreh.notification.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.salpreh.notification.models.SendNotificationDto;
import com.salpreh.notification.repositories.DevicesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseMessaging messaging;
    private final DevicesRepository devicesRepository;
    private final ModelMapper mapper;

    public boolean subscribeDevice(String deviceId, String topic) {
        try {
            var res = messaging.subscribeToTopic(List.of(deviceId), topic);

            if (!res.getErrors().isEmpty()) {
                throw new RuntimeException(
                    String.format("Errors subscribing device to topic [Dev: %s | Topic: %s]", deviceId, topic)
                );
            }
        } catch (FirebaseMessagingException | RuntimeException e) {
            log.error("Unable to subscribe device {}", deviceId, e);
        }

        return false;
    }

    public Optional<String> sendNotificationToDevice(String deviceId, SendNotificationDto.Message message) {

        Message msg = Message.builder()
            .setToken(deviceId)
            .putData("data", "test")
            .setNotification(mapper.map(message, Notification.Builder.class).build())
            .build();

        try {
            return Optional.of(messaging.send(msg));
        } catch (FirebaseMessagingException e) {
            log.error("Unable to send notification",  e);
        }

        return Optional.empty();
    }

    public Optional<String> sendNotificationToTopic(String topic, SendNotificationDto.Message message) {
        Message msg = Message.builder()
            .setTopic(topic)
            .putData("data", "test")
            .setNotification(mapper.map(message, Notification.Builder.class).build())
            .build();

        try {
            Optional.of(messaging.send(msg));
        } catch (FirebaseMessagingException e) {
            log.error("Unable to send notification",  e);
        }

        return Optional.empty();
    }
}
