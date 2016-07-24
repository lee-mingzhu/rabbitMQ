package com.lee.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by mingzhu on 2016/7/24.
 */
public class NewTask {

    public static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        String message = getMessage(args);
        channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }

    private static String getMessage(String[] args) {
        if(args.length < 1){
            return "Hello World!";
        }
        return joinStrings(args, " ");
    }

    private static String joinStrings(String[] args, String s) {
        int length = args.length;
        if(length == 0){
            return "";
        }
        StringBuilder word = new StringBuilder(args[0]);
        for(int i = 1; i < args.length; i ++){
            word.append(s).append(args[i]);
        }
        return word.toString();
    }
}
