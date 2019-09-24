package executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class FileExecutorService {

    private ExecutorService executor;

    private  int threadsCount = 15;


    public ExecutorService buildExecutor() {
        if (this.executor == null || this.executor.isShutdown() || this.executor.isTerminated()) {
            synchronized (ExecutorService.class) {
                if (this.executor == null || this.executor.isShutdown() || this.executor.isTerminated()) {
                    this.executor = Executors.newFixedThreadPool(Integer.valueOf(this.threadsCount), new ThreadFactory() {
                        public Thread newThread(final Runnable r) {
                            final Thread t = new Thread(r);
                            t.setDaemon(true);
                            return t;
                        }
                    });
                }
            }
        }
        return executor;
    }

    public void run( final List<CompletableFuture<HashMap<String, Integer>>> futuresList,List<String> filePathList){
        int BatchCount = 0;
        for (final CompletableFuture<HashMap<String, Integer>> future : futuresList)
        {
            try
            {
                final HashMap<String, Integer> logOutputMap = future.get();
                printOutput(logOutputMap, filePathList.get(BatchCount));

            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
            BatchCount++;
        }



    }
    private void printOutput(HashMap<String, Integer> logOutputMap, String filePath){
        System.out.println("\nOutput Format:-");
        System.out.println("Log File Path : "+ filePath +"\n");
        for (Map.Entry mapElement : logOutputMap.entrySet()) {
            String key = (String)mapElement.getKey();
            int value = ((int)mapElement.getValue());
            if(key  != null ){
                String[] errorMessgae = key.split("_");
                if(errorMessgae.length > 1 ){
                    String timeStamp = errorMessgae[0];
                    String messgae = errorMessgae[1];
                    System.out.println(timeStamp +" => "+messgae +" => "+ value);

                }
            }
        }
    }

    public void shutdown(){

        if (this.executor != null && (!this.executor.isShutdown() || !this.executor.isTerminated()))
        {
            synchronized (ExecutorService.class)
            {
                if (!this.executor.isShutdown() || !this.executor.isTerminated())
                {
                    try
                    {
                        this.executor.shutdownNow();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


}



