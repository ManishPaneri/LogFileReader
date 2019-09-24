package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jCustom {

    private static final Logger LOG = LogManager.getLogger(Log4jCustom.class);

    public void setLogException(Throwable throwable) {
        LOG.error(throwable.getClass().getName());
    }

}
