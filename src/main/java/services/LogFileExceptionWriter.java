package services;

import utils.Log4jCustom;

public class LogFileExceptionWriter {

    private static Log4jCustom logger = new Log4jCustom();
    private static NullPointerException nullError = new NullPointerException() ;
    private static ArithmeticException aritError = new ArithmeticException() ;


    public static void writeLogMsg(){
        try {
            for(int index=0 ; index<100 ; index++){
                Thread.sleep(100); // LOCK is held
                logger.setLogException(nullError);
                logger.setLogException(aritError);
            }
        } catch (InterruptedException e) {
            logger.setLogException(e);
        }

    }


}
