package grabamovie.utils;


import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class LogFormatter {
    public static Logger getLogger(String className) {
        Logger LOG =  Logger.getLogger(className);
        
        Handler consoleHandler = new ConsoleHandler();
        Formatter fmt = new LogCustomFormatter();
        consoleHandler.setFormatter(fmt);
        
        LOG.addHandler(consoleHandler);
        
        return LOG;
    }
}
