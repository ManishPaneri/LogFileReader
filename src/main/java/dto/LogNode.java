package dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static javax.swing.UIManager.put;

public class LogNode {

    private HashMap<Integer, Integer> mapID =  new HashMap<Integer,Integer>() {{
        put(0,15);
        put(16,31);
        put(30,45);
        put(46,60);
    }};

    private String logID;

    private String requestID;

    private  String TimeStamp;

    private String exceptionName;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID() {
        if(this.TimeStamp != null && this.exceptionName != null){
            DateFormat format = new SimpleDateFormat(this.TimeStamp, Locale.ENGLISH);
            int getMinutes = (int) ((SimpleDateFormat) format).get2DigitYearStart().getMinutes();
            String autoCreateID ="";
            for (Map.Entry time : mapID.entrySet()) {
                int key = (int)time.getKey();
                int value = ((int)time.getValue());
                if(key< getMinutes && value > getMinutes){
                    String hour = String.valueOf(((SimpleDateFormat) format).get2DigitYearStart().getHours());
                    autoCreateID = hour+":"+key +"-"+hour+":"+ value;
                    break;
                }

            }

            this.logID = autoCreateID +"_"+this.exceptionName ;
        }
    }
}
