package com.salpreh.notification.controllers;


import com.salpreh.notification.models.SendNotificationDto;
import com.salpreh.notification.models.TopicSubscriptionDto;
import com.salpreh.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("subscribe")
    public ResponseEntity<Void> subscribeToTopic(@RequestBody TopicSubscriptionDto subscription) {
        var success = notificationService.subscribeDevice(subscription.getToken(), subscription.getTopic());

        if (!success) return ResponseEntity.internalServerError().build();

        return ResponseEntity.noContent()
            .build();
    }

    @PostMapping("device")
    public ResponseEntity notifyToDevice(@RequestBody SendNotificationDto request) {
        return notificationService.sendNotificationToDevice(
            request.getDestination(),
            request.getMessage()
        )
            .map(m -> ResponseEntity.noContent().build())
            .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PostMapping("topic")
    public ResponseEntity notifyToTopic(@RequestBody SendNotificationDto request) {
        return notificationService.sendNotificationToTopic(
                request.getDestination(),
                request.getMessage()
            )
            .map(m -> ResponseEntity.noContent().build())
            .orElseGet(() -> ResponseEntity.internalServerError().build());
    }
}
