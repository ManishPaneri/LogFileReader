package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.ArrayList;

public class FileReader {
    private static final Logger LOG = LogManager.getLogger(FileReader.class);

    public static ArrayList<String> reader(String filePath){
        final ArrayList<String> logStr = new ArrayList<String>();
        if(filePath != null && !filePath.equalsIgnoreCase(" ")) {
            final File file = new File(filePath);
            if (file.exists()) {
                try (final BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
                    String currentLine;
                    while ((currentLine = br.readLine()) != null && !currentLine.equals("")) {
                        if(!logStr.contains(currentLine)){
                            logStr.add(currentLine);
                        }
                    }
                } catch (final Exception e) {
                    LOG.error("Error while reading the file " + file.getName() + " : " + e.getMessage());
                }
            }
        }
        return logStr;
    }



}
