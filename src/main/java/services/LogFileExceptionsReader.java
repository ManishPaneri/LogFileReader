package services;

import dto.LogNode;
import executor.FileExecutorService;
import utils.FileReader;
import utils.Log4jCustom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LogFileExceptionsReader {


    private static Log4jCustom logger = new Log4jCustom();

    private static FileReader fileReader = new FileReader();

    private  static FileExecutorService executorService = new FileExecutorService() ;

    public static void readerLogFile(List<String> filepathList){
        try {
            final List<CompletableFuture<HashMap<String, Integer>>> futuresList = new ArrayList<>();
            int count = filepathList.size();
            int batchCount = 0;
            int rows = 10;
            while (batchCount < count) {
                List<String> logArr = fileReader.reader(filepathList.get(batchCount));
                final CompletableFuture<HashMap<String, Integer>> future = CompletableFuture.supplyAsync(() -> {
                    return buildLogObj(logArr);
                }, executorService.buildExecutor());
                futuresList.add(future);
                batchCount++ ;
            }
            executorService.run(futuresList, filepathList);
            executorService.shutdown();
        }catch (Throwable throwable){
            logger.setLogException(throwable);
        }
    }



    public static HashMap<String, Integer>  buildLogObj(List<String> errorMsg) {
        final HashMap<String, Integer> logOutputMap = new HashMap<>();
        List<LogNode> errorObjArr = new ArrayList<>();
        if(errorMsg != null){
            errorMsg.stream().forEach(errmsg->{
                String[] msgArr = errmsg.split(" ");
                if(msgArr.length==3){
                    LogNode errorObj = new LogNode();
                    errorObj.setRequestID(msgArr[0]);
                    errorObj.setTimeStamp(msgArr[1]);
                    errorObj.setExceptionName(msgArr[2]);
                    errorObj.setLogID();
                    if(!logOutputMap.containsKey(errorObj.getLogID())){
                        if(errorObj.getLogID()!=null){
                            logOutputMap.put(errorObj.getLogID(), 1);
                        }
                    }else{
                        int  count = logOutputMap.get(errorObj.getLogID())+1;
                        logOutputMap.put(errorObj.getLogID(), count);
                    }
                }
            });

        }
        return logOutputMap;
    }


}
