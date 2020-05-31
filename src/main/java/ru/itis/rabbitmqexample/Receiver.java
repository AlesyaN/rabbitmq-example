package ru.itis.rabbitmqexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.rabbitmqexample.downloaders.Downloader;
import ru.itis.rabbitmqexample.downloaders.JpgDownloader;
import ru.itis.rabbitmqexample.downloaders.PngDownloader;

import java.io.IOException;

@Slf4j
@Component
public class Receiver {

    private Downloader jpgDownloader;
    private Downloader pngDownloader;

    @Autowired
    public Receiver(JpgDownloader jpgDownloader, PngDownloader pngDownloader) {
        this.jpgDownloader = jpgDownloader;
        this.pngDownloader = pngDownloader;
    }

    public void receiveMessage(String message, String receiver) throws IOException {
        log.info("Start downloading " + receiver + " : " + message);
        switch (receiver) {
            case RabbitmqExampleApplication.pngRoutingKey:
                pngDownloader.download(message);
            case RabbitmqExampleApplication.jpgRoutingKey:
                jpgDownloader.download(message);
        }
        log.info("Finished downloading " + receiver + " : " + message);
    }

    @RabbitListener(queues = "#{pngQueue.name}")
    public void receivePng(String message) throws IOException {
        receiveMessage(message, RabbitmqExampleApplication.pngRoutingKey);
    }

    @RabbitListener(queues = "#{jpgQueue.name}")
    public void receiveJpg(String message) throws IOException {
        receiveMessage(message, RabbitmqExampleApplication.jpgRoutingKey);
    }
}
