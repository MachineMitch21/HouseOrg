/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author mitch
 */
public class FileManip {
    private byte[] buffer = new byte[1024];
    private static File file;
    
    public void setFile(File file){
        this.file = file;
    }
    
    public File getFile(){
        return file;
    }
    
    private String checkImgExtension(String imageName, File imageFile){
        String fileName = imageFile.getName();
        fileName = fileName.substring(0, fileName.indexOf('.'));
        return fileName;
    }
    
    public boolean checkEquality(String imageName, File imageFile){
        System.out.println(imageFile.getName());
        String fileName = checkImgExtension(imageName, imageFile);
        
        if(imageName.equals(fileName) || imageName.equals(imageFile.getName())){
            return true;
        }else{
            return false;
        }
    }
    
    public void copyFileToDir(String sourceDir, String targetDir){
        File source = new File(sourceDir);
        File target = new File(targetDir);
        
        try{
            FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(target + "/" + source.getName());
            
            int bufferSize;
            while((bufferSize = in.read(buffer)) > 0){
                out.write(buffer, 0, bufferSize);
            }
            in.close();
            out.close();
            System.out.println("Copying completed successfully");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Trouble copying file to directory");
        }
    }
    
    public byte[] readFile(File file){
        ByteArrayOutputStream fileByteArray = null;
        
        try{
            FileInputStream in = new FileInputStream(file);
            fileByteArray = new ByteArrayOutputStream();
            
            int bufferSize;
            while((bufferSize = in.read(buffer)) > 0){
                fileByteArray.write(buffer, 0, bufferSize);
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        return fileByteArray != null ? fileByteArray.toByteArray() : null;
    }
}
