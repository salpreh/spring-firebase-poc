package com.salpreh.notification.models;

import lombok.Data;

@Data
public class SendNotificationDto {
    private String destination;
    private Message message;

    @Data
    public static class Message {
        private String title;
        private String body;
    }
}
