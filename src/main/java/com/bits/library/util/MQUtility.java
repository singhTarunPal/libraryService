package com.bits.library.util;

import com.bits.library.controller.LibraryController;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MQUtility {

    private final static String QUEUE_NAME = "lib_notif_queue";
    private static final Logger LOGGER = LogManager.getLogger(LibraryController.class);
    
    public static void sendMessageAsync(String message) throws Exception{
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	executor.submit(() -> {
    		try {
				sendMessage(message);
			} catch (Exception e) {
				LOGGER.error("Error occured while sending message to Queue" + e);
			}
    	});
    }

    public static void sendMessage(String message) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            LOGGER.info(" [x] Sent Message: '" + message + "'");
        }
    }
}