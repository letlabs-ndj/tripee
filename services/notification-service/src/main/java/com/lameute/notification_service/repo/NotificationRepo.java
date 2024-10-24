package com.lameute.notification_service.repo;

import com.lameute.notification_service.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepo extends MongoRepository<Notification, Long> {
}
