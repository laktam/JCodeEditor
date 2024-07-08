package org.mql.jcodeeditor.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import javax.swing.tree.TreePath;

public class FileMover {

    public static void moveFileOrDirectory(Path source, Path target) throws IOException {
        if (Files.isDirectory(source)) {
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = target.resolve(source.relativize(dir));
                    try {
                        Files.createDirectories(targetDir);
                    } catch (FileAlreadyExistsException e) {
                        if (!Files.isDirectory(targetDir)) {
                            throw e;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.move(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
                }
            });
        } else {
            Files.move(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    // takes tree root path
    public static Path buildPath(Path rootPath, TreePath[] filenames) {
        Path finalPath = rootPath.getParent();
        
        // Resolve each filename to the final path
//        for (String filename : filenames) {
//            finalPath = finalPath.resolve(filename);
//        }
        for (TreePath path : filenames) {
        	finalPath = finalPath.resolve(path.getPathComponent(0).toString());
		}

        System.out.println("dst path " + finalPath);
        return finalPath;
    }
}
