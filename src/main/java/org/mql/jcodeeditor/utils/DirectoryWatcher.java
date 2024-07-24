package org.mql.jcodeeditor.utils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.mql.jcodeeditor.JExplorer;

public class DirectoryWatcher extends Thread{
	private Path directoryPath;
	private JExplorer explorerTree;
	
	public DirectoryWatcher(Path directoryPath, JExplorer explorerTree) {
		this.directoryPath = directoryPath;
		this.explorerTree = explorerTree;
	}
	
	
	
	@Override
	public void run() {
		try {
 
            // Create a WatchService
            WatchService watchService = FileSystems.getDefault().newWatchService();
 
            // Register the directory for specific events
            directoryPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
 
            System.out.println("Watching directory: " + directoryPath);
 
            // Infinite loop to continuously watch for events
            while (true) {
                WatchKey key = watchService.take();
 
                for (WatchEvent<?> event : key.pollEvents()) 
                {
                    // Handle the specific event
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE ||event.kind() == StandardWatchEventKinds.ENTRY_DELETE) 
                    {
                        System.out.println("Change detected: " + event.context());
                        explorerTree.reloadRoot();
                        
                    } 
//                    else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) 
//                    {
//                        System.out.println("File deleted: " + event.context());
//                    } 
//                    else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) 
//                    {
//                        System.out.println("File modified: " + event.context());
//                    }
                }
 
                // To receive further events, reset the key
                key.reset();
            }
 
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	}
}
