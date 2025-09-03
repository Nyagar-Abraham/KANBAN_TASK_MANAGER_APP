package org.abraham.kanbantaskmanager.kafka;

import org.abraham.kanbantaskmanager.dtos.UserEvent;
import org.abraham.kanbantaskmanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class Consumers {


    private static final Logger log = LoggerFactory.getLogger(Consumers.class);
    private final UserService userService;

    public Consumers(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(
            topics = "user-events",
            groupId = "user_event_group_1"

    )
    public void listen(UserEvent message){
     log.info(String.format("Received user event: %s", message));
     userService.saveUser(message);

    }
}
