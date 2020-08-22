package me.vallezw.messengerbackend.notifications.database;

import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
