package com.tyc.rabbitmq.producer;

import com.tyc.rabbitmq.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducer{
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message, Long code) {
        rabbitTemplate.convertAndSend("auth-direct-exchange",
                "bind-key-create-user",
                Message.builder()
                        .message(message)
                        .code(code)
                        .build());
    }
}
