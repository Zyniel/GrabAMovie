package grabamovie.core;

import grabamovie.utils.LogFormatter;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCanada
 */
public final class FileCopyProcessor extends OrderableProcessor {
    private static final Logger LOG = LogFormatter.getLogger(FileCopyProcessor.class.getName());
    private OrderProcessor gam;
    
    private String destination;
    
    public FileCopyProcessor() {
        super();
    }
    
    public FileCopyProcessor(String destination) throws Exception {
        this();
        setDestination(destination);
        LOG.info("Initialized");
    }

    public void setDestination(String destination) throws Exception {
        if (destination != null) {
            this.destination = destination;
        } else {
            throw new Exception("Movie Processor physical destination can not be empty.");
        }
    }
    
    @Override
    protected void internalProcess(IOrderable item, IOrder order) throws Exception{
        Movie movie = (Movie) item;
        LOG.log(Level.INFO, "Copying file: {0}", movie.getName());
        Path filepath = new File(movie.getLocation(), movie.getName()).toPath();
        Path destPath = new File(new File(this.destination.replace("\\", "\\\\"), order.getId()), movie.getName()).toPath();
        Files.copy(filepath, destPath, REPLACE_EXISTING);   
        LOG.info("Done");
    }

    @Override
    protected void postProcess(IOrderable item, IOrder order) throws Exception{
        LOG.info("Post-processing");
    }

    @Override
    protected void preProcess(IOrderable item, IOrder order) throws Exception{
        try {
            Files.createDirectory(new File(this.destination.replace("\\", "\\\\"),order.getId()).toPath());
            LOG.info("Created Order Folder");
        } catch (FileAlreadyExistsException faee) {}
    }

    @Override
    protected void errorProcess(IOrderable item, IOrder order, Exception e) throws Exception{
        LOG.severe(e.getMessage());
    }
}
