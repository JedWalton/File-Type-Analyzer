package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
//        String flag = args[0];
        String flag = "--naive";
        String folderWithFiles = args[0];
//        String file = args[1];
        String pattern = args[1];
        String fileType = args[2];

        File folder = new File(folderWithFiles);


        final ArrayList<KNP_algorithmThread> workers = new ArrayList<>();
        // FIFO Ordering
        final LinkedBlockingQueue<Runnable> queue;
        ExecutorService es = Executors.newCachedThreadPool();


        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            /* New thread for each file in iteration */
            for (File file : files) {
                byte[] data = Files.readAllBytes(file.toPath());
                String text = new String(data);
                es.execute(new KNP_algorithmThread(text, pattern, fileType, file));
            }
            es.shutdown();
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        }

//        String text = new String(data);
        if (flag.equals("--naive")) {
//            naive(text, pattern, fileType);
        } else if (flag.equals("--KMP")) {
//            KNP(text, pattern, fileType);
        }
    }
}
