package ru.itis.rabbitmqexample.downloaders;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Component
public class JpgDownloader implements Downloader {

    @SneakyThrows
    @Override
    public void download(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = url.getFile();
        BufferedImage image = ImageIO.read(url);
        File output = new File("downloaded/jpg/" + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf(".")));
        ImageIO.write(image, "jpg", output);
    }
}
