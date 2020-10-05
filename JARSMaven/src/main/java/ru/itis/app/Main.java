package ru.itis.app;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import ru.itis.utils.*;
import java.util.*;

import java.util.concurrent.*;


public class Main {
    public static void main(String[] argv) {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        int count = 1;
        String mode = args.mode;
        if (mode.equals("multi-thread"))
            count = args.count;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for(String file: args.files){
            executorService.submit(new Download(file, args.folder));
        }
        executorService.shutdown();
    }
}
