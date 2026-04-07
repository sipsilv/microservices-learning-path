package dev.silvercapes.orderservice.jobs;
import dev.silvercapes.orderservice.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublishingJob {
    private final OrderEventService orderEventService;
    @Scheduled(cron = "*/5 * * * * *")
    public void publishOrderEvents() {
        log.info("Publishing Order Events at {}", Instant.now());
        orderEventService.publishOrderEvents();
    }
}
