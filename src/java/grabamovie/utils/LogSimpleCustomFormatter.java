/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author OCanada
 */
class LogSimpleCustomFormatter extends Formatter {
    //
    // Create a DateFormat to format the logger timestamp.
    //
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
 
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(df.format(new Date(record.getMillis()))).append(" - ");
        String s = record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".")+1);
        builder.append("[").append(s).append("] - ");
        builder.append("[").append(record.getLevel()).append("] - ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }
 
    @Override
    public String getHead(Handler h) {
        return super.getHead(h);
    }
 
    @Override
    public String getTail(Handler h) {
        return super.getTail(h);
    }
}