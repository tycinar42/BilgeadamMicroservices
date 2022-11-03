package com.tyc.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
/**
 * RabbitMQ ile pojo iletisim yaparken java siniflarinin serilestirilmesi gereklidir.
 * Cunku bu bilgi json olarak iletilir ve cozulur.
 */
public class Message implements Serializable {
    private String message;
    private Long code;
}
