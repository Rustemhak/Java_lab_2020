package ru.itis.app;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.*;
import ru.itis.utils.*; //can change

import java.util.concurrent.*;

/* first example
$ java -jar download.jar --mode=one-thread --files=https://sun1-88.userapi.com/F7y87TRbLijZ2j6lVBXU734_bQ2vN2kXklnjnw/RDkR0vPynN0.jpg --folder=C:/Users/Rustem/Images
*/
/* second example
$ java -jar download.jar  --mode=multi-thread --count=10 --files=https://sun9-71.userapi.com/bbrVukUXhrieg-FRPWfM588xnbsW4C5wcHl1XA/QDBKZp4Kw3w.jpg,https://sun9-66.userapi.com/c855536/v855536432/1fbb75/2tuaOEdp0XI.jpg,https://sun9-5.userapi.com/c854524/v854524168/1793ea/DvH_7edSQgE.jpg --folder=C:/Users/Rustem/Images
*/
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
