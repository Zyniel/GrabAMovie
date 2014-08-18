/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.*;
import java.util.logging.Logger;

/**
 *
 * @author OCanada
 */
public final class HDDMovieProcessor extends MovieProcessor {
    private static final Logger LOG = Logger.getLogger(HDDMovieProcessor.class.getName());
    
    private String destination;
    
    public HDDMovieProcessor() {
        processorName = "HDDMovieProcessor";
    }
    
    public HDDMovieProcessor(String destination) throws Exception {
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
    protected void internalProcess(Movie movie, Order order) throws Exception{
        LOG.info("Copying file: " + movie.getName());
        Path filepath = new File(movie.getLocation(), movie.getName()).toPath();
        Path destPath = new File(new File(this.destination.replace("\\", "\\\\"), order.getId()), movie.getName()).toPath();
        Files.copy(filepath, destPath, REPLACE_EXISTING);   
        LOG.info("Done");
    }

    @Override
    protected void postProcess(Movie movie, Order order) throws Exception{
        LOG.info("Post-processing");
    }

    @Override
    protected void preProcess(Movie movie, Order order) throws Exception{
        try {
            Files.createDirectory(new File(this.destination.replace("\\", "\\\\"),order.getId()).toPath());
            LOG.info("Created Order Folder");
        } catch (FileAlreadyExistsException faee) {}
    }

    @Override
    protected void errorProcess(Movie movie, Order order, Exception e) throws Exception{
        throw e;
    }
}
