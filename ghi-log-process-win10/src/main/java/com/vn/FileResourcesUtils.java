package com.vn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileResourcesUtils {

	public static void main(String[] args) throws URISyntaxException {
//		// TODO Auto-generated method stub
//		FileResourcesUtils app = new FileResourcesUtils();
//
//        //String fileName = "database.properties";
//        String fileName = "log/2021_11_10.json";
//
//        System.out.println("getResourceAsStream : " + fileName);
//        InputStream is = app.getFileFromResourceAsStream(fileName);
//        printInputStream(is);
//
//        System.out.println("\ngetResource : " + fileName);
//        File file = app.getFileFromResource(fileName);
//        printFile(file);

	}
	
	  private InputStream getFileFromResourceAsStream(String fileName) {

	        // The class loader that loaded the class
	        ClassLoader classLoader = getClass().getClassLoader();
	        InputStream inputStream = classLoader.getResourceAsStream(fileName);

	        // the stream holding the file content
	        if (inputStream == null) {
	            throw new IllegalArgumentException("file not found! " + fileName);
	        } else {
	            return inputStream;
	        }

	    }
	  
	  private File getFileFromResource(String fileName) throws URISyntaxException{

	        ClassLoader classLoader = getClass().getClassLoader();
	        URL resource = classLoader.getResource(fileName);
	        if (resource == null) {
	            throw new IllegalArgumentException("file not found! " + fileName);
	        } else {

	            // failed if files have whitespaces or special characters
	            //return new File(resource.getFile());

	            return new File(resource.toURI());
	        }

	    }
	  
	  // print input stream
	    private static void printInputStream(InputStream is) {

	        try (InputStreamReader streamReader =
	                    new InputStreamReader(is, StandardCharsets.UTF_8);
	             BufferedReader reader = new BufferedReader(streamReader)) {

	            String line;
	            while ((line = reader.readLine()) != null) {
	                System.out.println(line);
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }

	    // print a file
	    private static void printFile(File file) {

	        List<String> lines;
	        try {
	            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
	            lines.forEach(e-> System.out.println(e.substring(0,100)));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }

}
