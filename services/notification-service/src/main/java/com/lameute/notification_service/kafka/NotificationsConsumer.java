package com.lameute.notification_service.kafka;

import com.lameute.notification_service.model.Enums.NotificationType;
import com.lameute.notification_service.model.Notification;
import com.lameute.notification_service.repo.NotificationRepo;
import com.lameute.notification_service.service.EmailService;
import com.lameute.notification_service.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@Slf4j
public class NotificationsConsumer {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    EmailService emailService;

    @KafkaListener(topics = "ride-topic")
    public void consumeRideStartNotifications(RideInfo rideInfo) {
        log.info(format("Consuming the message from ride-topic Topic:: %s", rideInfo));
        List<Notification> notifications = new ArrayList<>();
        for (UserResponse user : rideInfo.users()){
            notifications.add(
                    Notification.builder()
                            .id(sequenceGeneratorService.generateSequence(Notification.SEQUENCE_NAME))
                            .type(NotificationType.RIDE_ONGOING)
                            .notificationDate(LocalDate.now())
                            .notificationTime(LocalTime.now())
                            .user(user)
                            .build()
            );
        }
        notificationRepo.saveAll(notifications);

        for (UserResponse user : rideInfo.users()){
            emailService.sendEmail(
                    user.email(),
                    "Your ride just started",
                    "Your ride "+rideInfo.departurePlace()+" to "+ rideInfo.arrivalPlace()+
                            "just started make sure to be at meeting point and start riding." +
                            "Have a good ride \uD83D\uDE97"
            );
        }

    }
}
