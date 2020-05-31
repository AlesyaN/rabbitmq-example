package ru.itis.rabbitmqexample.downloaders;

import java.io.IOException;

public interface Downloader {
    void download(String imageUrl) throws IOException;
}
