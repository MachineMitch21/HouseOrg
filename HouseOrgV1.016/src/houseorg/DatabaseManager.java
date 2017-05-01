/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import java.sql.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 *
 * @author mitch
 * 
 */
public class DatabaseManager {
   
    //String DBN parameter will be the database to be connected to in each method
    
    
    //global resultset for the DatabaseManager that will be assigned the value of a resultset within try
    //blocks and then returned at the end of method processing (will be set to null at the start of each 
    //process to keep from returning wrong values)
                                         
    public static Connection con;
    public static String currentUser = null;
    
    public void createDatabase(String DBN){
        File dbFile = new File(verifyPath(DBN));
        try{
            if(dbFile.createNewFile()){
                System.out.println("Created new file");
            }else{
                System.out.println("Couldn't create file");
            }
        }catch(IOException e){
            System.out.println("Could not create file");
        }
    }
    
    public String verifyPath(String DBN){
        if(DBN.contains(".db")){
            return DBN;
        }else{
            return DBN + ".db";
        }
    }
    
    public Connection connectToDatabase(String DBN){
         
        String url = "jdbc:sqlite:" + DBN;
        File dbFile = new File(DBN);
        Connection conn = null;
        
        if(dbFile.exists()){
            OutputManager om = new OutputManager();
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Connection established..");
        }catch(Exception e){
            e.printStackTrace();
        }
        }else{
            createDatabase("House.db");
        }
       return conn;
    }
    
    public void createTable(String DBN, String tableName, String columns){
        OutputManager OM = new OutputManager();
         String url = "jdbc:sqlite:" + DBN;
        String stGuts;                              //string to be passed into executeUpdate method
        
        stGuts = "create table if not exists " + tableName + " " + columns + ";";
        try{
           
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            st.executeUpdate(stGuts);
            con.commit();
            
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't add table");
        }
    }
    
    public ResultSet selectAllFrom(String DBN, String tableName){
        OutputManager OM = new OutputManager();
         String url = "jdbc:sqlite:" + DBN;
        String stGuts;                              //string to be passed into executeQuery method
        ResultSet rs = null;
        stGuts = "select * from " + tableName + ";";
        try{
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            rs = st.executeQuery(stGuts);
            con.commit();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            OM.setErrorMsg(e.getMessage());
            
        }
        return rs;
    }
    
    public ResultSet selectColumnFrom(String DBN, String tableName, String columns){
        
        ResultSet rs = null;
        
        OutputManager OM = new OutputManager();
        String url = "jdbc:sqlite:" + DBN;
        String stGuts = "select " + columns + " from " + tableName;
        
        try{
            Connection conn = con;
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            rs = st.executeQuery(stGuts);
            con.commit();
            
        }catch(SQLException e){
            OM.setErrorMsg(e.getMessage());
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet selectFromWhere(String DBN, String tableName, String column, String value){
        ResultSet rs = null;
        OutputManager OM = new OutputManager();
        String url = "jdbc:sqlite:" + DBN;
        
        String stGuts = "select * from " + tableName + " where " + "(" + column + ")" + " = " + "'" + value + "'" + ";";
        try{
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            rs = st.executeQuery(stGuts);
            con.commit();
        }catch(SQLException e){
            OM.setErrorMsg(e.getMessage());
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public File getImageFile(String imageName){
        ResultSet rs = null;
        File file = new File(imageName);
        String stGuts = "select * from Images where (imageName)='"+imageName+"';";
        System.out.println(imageName);
        
        try{
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            rs = st.executeQuery(stGuts);
            
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(file);
            while(rs.next()){
                InputStream in = rs.getBinaryStream("image");
                
                while(in.read(buffer) > 0){
                    fos.write(buffer);
                }
            }
            con.commit();
                
            rs.close();
            fos.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
        
        
        
        return file;
    }
    
    public void insertIntoImages(String DBN, String columns, String values, String imageName){
     
        String stGuts = "insert into " + "images" + columns + " values " + values + ";";
        String updateSt = "Update images SET image = ? where imageName =" + "'" + imageName + "';";
        
        try{
                con.setAutoCommit(false);
                Statement st = con.createStatement();
                st.executeUpdate(stGuts);
                con.commit();
                    FileManip fManip = new FileManip();
                    PreparedStatement pSt = con.prepareStatement(updateSt);
                    pSt.setBytes(1, fManip.readFile(fManip.getFile()));
                    pSt.executeUpdate();
                    con.commit();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    public void insertInto(String DBN, String tableName, String columns, String values){
        OutputManager OM = new OutputManager();
        String stGuts = "insert into " + tableName + columns + " values " + values + ";";
        
        ResultSet rs = null;
        
            try{
                con.setAutoCommit(false);
                Statement st = con.createStatement();
                st.executeUpdate(stGuts);
                
                con.commit();
                
                
                
                if(tableName.equals("Images")){
                    

                }
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("Something happened" + " " + e.getMessage());
            }
    }
    
    public void deleteRecord(String tableName, String RecordToDeleteBy, String value){
        
        String stGuts = "delete from " + tableName + " where " + RecordToDeleteBy + "= " + "'" + value + "'" + ";";
        try{
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            st.executeUpdate(stGuts);
            con.commit();
            
        }catch(SQLException e){
            //OM.setErrorMsg(e.getMessage());
            System.out.println("Something happened while deleting a record " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void deleteTable(String DBN, String tableName){
        OutputManager OM = new OutputManager();
        String url = "jdbc:sqlite:" + DBN;
        String stGuts = "drop table " + tableName;
        
        try{
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            st.executeUpdate(stGuts);
            
            con.commit();
            
        }catch(SQLException e){
            OM.setErrorMsg(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void deleteDatabase(String DBN){
         OutputManager OM = new OutputManager();
        String url = "jdbc:sqlite:" + DBN;
        String stGuts = "drop database " + DBN;
        
        try{
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            st.executeUpdate(stGuts);
            
            con.commit();
           
        }catch(SQLException e){
            OM.setErrorMsg(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}

