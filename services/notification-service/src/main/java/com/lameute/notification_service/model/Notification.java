package com.lameute.notification_service.model;

import com.lameute.notification_service.kafka.UserResponse;
import com.lameute.notification_service.model.Enums.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {
    @Transient
    public static final String SEQUENCE_NAME = "notifications_sequence";

    @Id
    private long id;
    private NotificationType type;
    private LocalDate notificationDate;
    private LocalTime notificationTime;
    private UserResponse user;
}
