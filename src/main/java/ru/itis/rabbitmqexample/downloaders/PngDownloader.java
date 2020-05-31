package ru.itis.rabbitmqexample.downloaders;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Component
public class PngDownloader implements Downloader {
    @SneakyThrows
    @Override
    public void download(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = url.getFile();
        BufferedImage image = ImageIO.read(url);
        // конвертируем png -> jpg с заменой альфа-канала розовым цветом
        BufferedImage converted = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        converted.createGraphics().drawImage(image, 0, 0, Color.PINK, null);

        File output = new File("downloaded/png/" + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf(".")));
        ImageIO.write(converted, "jpg", output);
    }
}
