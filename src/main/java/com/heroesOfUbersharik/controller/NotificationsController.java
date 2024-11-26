package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.MyNotification;
import com.heroesOfUbersharik.repository.MyNotificationRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.time.Instant;


@RestController
public class NotificationsController {
    @Autowired
    MyNotificationRepository myNotificationRepository;
    @Autowired
    MyUserRepository userRepository;


//Returns all notifications for the current user
    @GetMapping("/getnotifications")
    public List<MyNotification> getnotifications(Principal principal){

        Date date = new Date();
        List <MyNotification> userNotifications = myNotificationRepository.findAllByUserId(userRepository.findByEmail(principal.getName()).get().getId());
        Iterator<MyNotification> iterator = userNotifications.iterator();

        while (iterator.hasNext()) {
            MyNotification notification = iterator.next();
            Date notificationDate = notification.getDate();
            Instant now = Instant.now();
            Instant notificationInstant = notificationDate.toInstant();
            Duration duration = Duration.between(notificationInstant, now);

            if (duration.toHours() > 1) {
                iterator.remove();
            }
        }
        myNotificationRepository.saveAll(userNotifications);
        return userNotifications;
    }

//Deletes the notification after pressing the button
    @DeleteMapping("/deletenotification/{id}")
    @Transactional
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        myNotificationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
