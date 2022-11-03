package com.tyc.rabbitmq.consumer;

import com.tyc.rabbitmq.model.CreateProfile;
import com.tyc.rabbitmq.model.Message;
import com.tyc.repository.entity.UserProfile;
import com.tyc.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {
    private final UserProfileService userProfileService;

    @RabbitListener(queues = "queue-auth-create-user")
    public void consumeMessage(CreateProfile profile) {
        userProfileService.save(UserProfile.builder()
                        .authId(profile.getAuthId())
                        .email(profile.getEmail())
                        .username(profile.getUsername())
                .build());
    }
}
