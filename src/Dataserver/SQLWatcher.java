package Dataserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SQLWatcher implements Runnable {
    WatchService watchService;
    Path path;
    Thread t;
    ConcurrentHashMap<String, String> sqlCommands;

    public SQLWatcher (ConcurrentHashMap<String, String> map) {
        // FIXME: create the hashmap of files and shit
        this.sqlCommands = map;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        populate();
        monitor();
    }

    private void populate() {
        try {
            File folder = new File("sql/");
            File [] files = folder.listFiles();

            for (File f : files) {
                System.out.println(f.getName().replaceFirst(".sql", ""));
                String content = new String (Files.readAllBytes(Paths.get(f.getPath())));
                sqlCommands.put(f.getName().replaceFirst(".sql", ""), content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void monitor() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();

            this.path = Paths.get("sql/");
            this.path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE
            );

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");

                    File f = new File(String.valueOf("sql/" + event.context()));

                    String content = new String (Files.readAllBytes(f.toPath()));
                    sqlCommands.replace(f.getName().replaceFirst(".sql", ""), content);
                }
                key.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
