package com.lameute.notification_service.kafka;

import com.lameute.notification_service.model.Enums.NotificationType;
import com.lameute.notification_service.model.Notification;
import com.lameute.notification_service.repo.NotificationRepo;
import com.lameute.notification_service.service.EmailService;
import com.lameute.notification_service.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationsConsumer {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    EmailService emailService;

    @KafkaListener(topics = "rideStart-topic")
    public void consumeRideStartNotifications(RideInfo rideInfo) {
        log.info("Consuming the message from rideStart-topic Topic:: {}", rideInfo);
        List<Notification> notifications = new ArrayList<>();

        /* For each passenger in the RideInfo consumed from the ride service we construct
            a notification object
         */
        for (UserResponse user : rideInfo.passengers()){
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

        /* For each expeditor in the RideInfo consumed from the ride service we construct
            a notification object
         */
        for (UserResponse user : rideInfo.expeditors()){
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

        // We store all the notifications in database
        notificationRepo.saveAll(notifications);

        // Then for every passenger concerned by the ride we notify them by emailing them
        for (UserResponse user : rideInfo.passengers()){
            emailService.sendEmail(
                    user.email(),
                    "Your ride just started",
                    "Your ride "+rideInfo.departurePlace()+" to "+ rideInfo.arrivalPlace()+
                            " just started make sure to be at meeting point and start riding." +
                            "Have a good ride \uD83D\uDE97"
            );
        }

        // Then for every expeditor concerned by the ride we notify them by emailing them
        for (UserResponse user : rideInfo.passengers()){
            emailService.sendEmail(
                    user.email(),
                    "Your packet expedition just started",
                    "Your packet expedition from "+rideInfo.departurePlace()+" to "+ rideInfo.arrivalPlace()+
                            " just started make sure recipient should be at meeting point on time for " +
                            "proper delivey of your packet \uD83D\uDCE6."
            );
        }

    }

    @KafkaListener(topics = "rideStop-topic")
    public void consumeRideStoptNotifications(RideInfo rideInfo) {
        log.info("Consuming the message from rideStop-topic Topic:: {}", rideInfo);
        List<Notification> notifications = new ArrayList<>();

        /* For each passenger in the RideInfo consumed from the ride service we construct
            a notification object
         */
        for (UserResponse user : rideInfo.passengers()){
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

        /* For each expeditor in the RideInfo consumed from the ride service we construct
            a notification object
         */
        for (UserResponse user : rideInfo.expeditors()){
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

        // We store all the notifications in database
        notificationRepo.saveAll(notifications);

        // Then for every passenger concerned by the ride we notify them by emailing them
        for (UserResponse user : rideInfo.passengers()){
            emailService.sendEmail(
                    user.email(),
                    "Your ride just ended",
                    "Your ride "+rideInfo.departurePlace()+" to "+ rideInfo.arrivalPlace()+
                            " just ended hope you had a good ride.\uD83D\uDE4F"
            );
        }

        // Then for every expeditor concerned by the ride we notify them by emailing them
        for (UserResponse user : rideInfo.passengers()){
            emailService.sendEmail(
                    user.email(),
                    "Your packet expedition just ended",
                    "Your packet expedition from "+rideInfo.departurePlace()+" to "+ rideInfo.arrivalPlace()+
                            " just reached destination. Hope your satisfied by the expedition.\uD83D\uDE4F"
            );
        }

    }
}
