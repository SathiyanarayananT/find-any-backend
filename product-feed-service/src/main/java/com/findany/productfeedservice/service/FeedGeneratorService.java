package com.findany.productfeedservice.service;

import com.findany.productfeedservice.constants.AppConstants;
import com.findany.productfeedservice.entity.ProductFeed;
import com.findany.productfeedservice.repository.ProductFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FeedGeneratorService {

    @Autowired
    private ProductFeedRepository productFeedRepository;

    @KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = "${kafka.consumer.group-id}")
    public void addProductToFeed(ProductFeed productFeed) {
        productFeedRepository.save(productFeed);
    }

}
