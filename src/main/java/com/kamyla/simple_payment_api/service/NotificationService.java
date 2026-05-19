package com.kamyla.simple_payment_api.service;

import com.kamyla.simple_payment_api.dto.NotificationDTO;
import com.kamyla.simple_payment_api.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final RestClient restClient;

    @Async
    public void sendNotification(User user, String message) {
        try {
            restClient.post()
                    .uri("https://util.devi.tools/api/v1/notify")
                    .body(new NotificationDTO(user.getEmail(), message))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            logger.warn("Serviço de notificação indisponível para {}: {}", user.getEmail(), e.getMessage());
        }
    }
}
