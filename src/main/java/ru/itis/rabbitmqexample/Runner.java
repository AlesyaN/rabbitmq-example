package ru.itis.rabbitmqexample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        File file = new File("images.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentFile;
        while ((currentFile = reader.readLine()) != null) {
            // в зависимости от типа файла отправляем его в конкретную очередь
            String currentRouting = currentFile.substring(currentFile.lastIndexOf(".") + 1);

            if (currentRouting.equals("jpeg")) {
                currentRouting = "jpg";
            }
            rabbitTemplate.convertAndSend(RabbitmqExampleApplication.directExchangeName, currentRouting, currentFile.getBytes());
        }
    }
}