package com.googlecode.mp4parser.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JuliLogger extends Logger {
    Logger logger;

    public JuliLogger(String name) {
        this.logger = Logger.getLogger(name);
    }

    public void logDebug(String message) {
        this.logger.log(Level.FINE, message);
    }

    public void logWarn(String message) {
        this.logger.log(Level.WARNING, message);
    }

    public void logError(String message) {
        this.logger.log(Level.SEVERE, message);
    }
}
