package org.example.smartcabs.utilities;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@NoArgsConstructor
public class AppLogger {
    private static Logger logger;
    public static Logger getLogger(){
        if(logger == null){
            logger =  LoggerFactory.getLogger(AppLogger.class);
            return  logger;
        }
        return logger;
    }


}
