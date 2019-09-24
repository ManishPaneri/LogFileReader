import services.LogFileExceptionWriter;
import services.LogFileExceptionsReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LogFileApplication {


    private static LogFileExceptionsReader fileReader = new LogFileExceptionsReader();

    private static LogFileExceptionWriter writeReader = new LogFileExceptionWriter();

    public static void main(String[] args){

        // Using Console to input data from user
        System.out.println("Input \nread/write  :-");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if(input.equalsIgnoreCase("write")){
            writeReader.writeLogMsg();
        }else if(input.equalsIgnoreCase("read")){
            System.out.println("log file number :-");
            String nfile = in.nextLine();
            int index =0;
            List<String> FilePathList = new ArrayList<>();
            while (index < Integer.valueOf(nfile)) {
                System.out.println("log filePath :-");
                String filePath = in.nextLine();
                FilePathList.add(filePath);
                index++;
            }
            fileReader.readerLogFile(FilePathList);
        }else{
            System.out.println("Please Select Correct Mode");
        }
    }

}
