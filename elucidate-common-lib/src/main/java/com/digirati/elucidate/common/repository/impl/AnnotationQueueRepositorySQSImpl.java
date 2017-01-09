package com.digirati.elucidate.common.repository.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.digirati.elucidate.common.infrastructure.exception.AWSSQSException;
import com.digirati.elucidate.common.model.enumeration.QueueOperation;
import com.digirati.elucidate.common.repository.AnnotationQueueRepository;
import com.github.jsonldjava.utils.JsonUtils;

@Repository(AnnotationQueueRepositorySQSImpl.REPOSITORY_NAME)
public class AnnotationQueueRepositorySQSImpl implements AnnotationQueueRepository {

    private static final Logger LOGGER = Logger.getLogger(AnnotationQueueRepositorySQSImpl.class);

    public static final String REPOSITORY_NAME = "annotationQueueRepositorySQSImpl";

    private boolean enabled;
    private String queueUrl;
    private AmazonSQS amazonSqs;

    @Autowired
    public AnnotationQueueRepositorySQSImpl(@Value("${aws.sqs.enabled}") boolean enabled, @Value("${aws.sqs.accessKey}") String accessKey, @Value("${aws.sqs.secretKey}") String secretKey, @Value("${aws.sqs.region}") String region, @Value("${aws.sqs.queueUrl}") String queueUrl) {
        this.enabled = enabled;
        this.queueUrl = queueUrl;

        if (enabled) {
            this.amazonSqs = buildAmazonSqs(accessKey, secretKey, region);
            if (!validateQueueExists(queueUrl)) {
                throw new AWSSQSException(String.format("Target AWS SQS queue [%s] not found", queueUrl));
            }
        } else {
            LOGGER.warn("Amazon SQS publishing is disabled");
        }
    }

    private AmazonSQS buildAmazonSqs(String accessKey, String secretKey, String region) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSQS amazonSQS = new AmazonSQSClient(awsCredentials);
        amazonSQS.setRegion(Region.getRegion(Regions.valueOf(region)));
        return amazonSQS;
    }

    private boolean validateQueueExists(String queueUrl) {
        for (String targetQueueUrl : amazonSqs.listQueues().getQueueUrls()) {
            if (StringUtils.equalsIgnoreCase(queueUrl, targetQueueUrl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void sendMessage(QueueOperation queueOperation, String iri, Map<String, Object> w3cAnnotationMap, Map<String, Object> oaAnnotationMap) {
        if (enabled) {

            String jsonStr;
            if (w3cAnnotationMap != null && oaAnnotationMap != null) {
                jsonStr = buildPayload(w3cAnnotationMap, oaAnnotationMap);
            } else {
                jsonStr = "null";
            }

            SendMessageRequest sendMessageRequest = buildSendMessageRequest(queueOperation, iri, jsonStr);

            LOGGER.info(String.format("Sending payload [%s] to Amazon SQS queue [%s] as message [%s]", jsonStr, queueUrl, sendMessageRequest));
            amazonSqs.sendMessage(sendMessageRequest);
            LOGGER.info(String.format("Message [%s] successfully sent", sendMessageRequest));
        } else {
            LOGGER.warn("Amazon SQS publishing is disabled");
        }
    }

    private String buildPayload(Map<String, Object> w3cAnnotationMap, Map<String, Object> oaAnnotationMap) {

        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("w3c", w3cAnnotationMap);
        payloadMap.put("oa", oaAnnotationMap);

        try {
            return JsonUtils.toString(payloadMap);
        } catch (IOException e) {
            throw new AWSSQSException(String.format("An error occurred converting Map [%s] to JSON String", payloadMap), e);
        }
    }

    private SendMessageRequest buildSendMessageRequest(QueueOperation queueOperation, String iri, String jsonStr) {

        SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl, jsonStr);
        sendMessageRequest.addMessageAttributesEntry("operation", buildMessageAttributeValue(queueOperation.toString()));
        sendMessageRequest.addMessageAttributesEntry("iri", buildMessageAttributeValue(iri));
        return sendMessageRequest;
    }

    private MessageAttributeValue buildMessageAttributeValue(String value) {

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.setDataType("String");
        messageAttributeValue.withStringValue(value);
        return messageAttributeValue;
    }
}
