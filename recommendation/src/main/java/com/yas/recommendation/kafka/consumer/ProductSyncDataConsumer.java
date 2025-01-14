package com.yas.recommendation.kafka.consumer;

import static com.yas.recommendation.kafka.config.consumer.ProductCdcKafkaListenerConfig.PRODUCT_CDC_LISTENER_CONTAINER_FACTORY;

import com.yas.commonlibrary.kafka.cdc.BaseCdcConsumer;
import com.yas.commonlibrary.kafka.cdc.RetrySupportDql;
import com.yas.commonlibrary.kafka.cdc.message.ProductCdcMessage;
import jakarta.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Product synchronize data consumer for pgvector.
 */
@Component
public class ProductSyncDataConsumer extends BaseCdcConsumer<ProductCdcMessage> {

    private final ProductSyncService productSyncService;

    public ProductSyncDataConsumer(ProductSyncService productSyncService) {
        this.productSyncService = productSyncService;
    }

    @KafkaListener(
        id = "product-sync-recommendation",
        groupId = "product-sync",
        topics = "${product.topic.name}",
        containerFactory = PRODUCT_CDC_LISTENER_CONTAINER_FACTORY
    )
    @RetrySupportDql(listenerContainerFactory = PRODUCT_CDC_LISTENER_CONTAINER_FACTORY)
    public void processMessage(
        @Payload(required = false) @Valid ProductCdcMessage productCdcMessage,
        @Headers MessageHeaders headers
    ) {
        processMessage(productCdcMessage, headers, productSyncService::sync);
    }
}
