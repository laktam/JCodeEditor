package org.mql.jcodeeditor.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mql.jcodeeditor.JExplorer;

public class FileMover {

    public static void moveFileOrDirectory(Path source, Path target) throws IOException {
        if (Files.isDirectory(source)) {
        	Path createdFolder =  Files.createDirectories(target.resolve(source.getFileName()));
            File subFiles[] = source.toFile().listFiles();
            for(File f: subFiles) {
            	moveFileOrDirectory(f.toPath(), createdFolder);
            }
            
        }else {
        	Files.move(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
        
        //delete from the old position
    }
    
    
    public static void updateFilesInNodes(DefaultMutableTreeNode sourceNode,DefaultMutableTreeNode destinationNode) {
    	File destinationFile = (File) destinationNode.getUserObject();
    	File sourceFile = (File) sourceNode.getUserObject();
    	sourceNode.setUserObject(destinationFile.toPath().resolve(sourceFile.getName()).toFile());
    	int c  = sourceNode.getChildCount();
    	for (int i = 0; i < c; i++) {
			updateFilesInNodes((DefaultMutableTreeNode) sourceNode.getChildAt(i) ,sourceNode);
		}
    }
    
    // takes tree root path
    public static Path buildPath(Path rootPath, TreePath[] filenames) {
        Path finalPath = rootPath.getParent();
        
        // Resolve each filename to the final path
//        for (String filename : filenames) {
//            finalPath = finalPath.resolve(filename);
//        }
//        System.out.println(filenames.length);
        TreePath treePath =  filenames[0];
        int pathCout = treePath.getPathCount();
        for (int i = 0; i < pathCout; i++) {
			treePath.getPathComponent(i);
			finalPath = finalPath.resolve(treePath.getPathComponent(i).toString());
		}
//        for (TreePath path : filenames) {
//            System.out.println(path.getPathComponent(1).toString());
//
//        	finalPath = finalPath.resolve(path.getPathComponent(0).toString());
//		}

        System.out.println("dst path " + finalPath);
        // final path contains the whole path of the file 
        // but we only want the folder path to move the file to
        return finalPath.getParent();
    }
}
