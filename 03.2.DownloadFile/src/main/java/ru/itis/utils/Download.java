package ru.itis.utils;

import java.io.*;
import java.net.*;

public class Download implements Runnable {
    String url;
    String folder;

    public Download(String url, String folder) {
        this.url = url;
        this.folder = folder;
    }

    @Override
    public void run() {
        URL u;
        InputStream is;
        FileOutputStream out;

        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            is = u.openStream();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            String[] full = url.split("/");
            String name = full[full.length - 1];
            out = new FileOutputStream(folder + "\\" + name);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream os = new BufferedOutputStream(out);
        int line;
        try {
            line = bis.read();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);

        }
        while (line >= 0) {
            try {
                os.write(line);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);

            }
            try {
                line = bis.read();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);

            }
        }
        try {
            os.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);

        }
        try {
            os.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);

        }
        try {
            bis.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
