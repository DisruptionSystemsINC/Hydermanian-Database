package com.disruption.window;

import com.disruption.HyDB;
import com.disruptionsystems.DragonLog;
import com.disruptionsystems.logging.LogLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DisruptionWindow {
    private String uuid;
    private JFrame frame;
    private DragonLog logger = HyDB.getLogger();

    public DisruptionWindow(String uuid, JFrame frame, DragonLog logger) {
        this.uuid = uuid;
        this.frame = frame;
        this.logger = logger;
    }



}

class DisruptionWindowBuilder {
    private String uuid;
    private JFrame frame;
    private DragonLog logger = HyDB.getLogger();

    public DisruptionWindow build(){
        if (frame == null){
            frame = new JFrame();
            logger.printToLog(LogLevel.WARNING, "JFrame was empty. Adding an empty frame.");
        }
        return new DisruptionWindow(uuid, frame, logger);
    }

    public DisruptionWindowBuilder setFrame(JFrame frame){
        this.frame = frame;
        return this;
    }

    public DisruptionWindowBuilder setUuid(String uuid){
        this.uuid = uuid;
        return this;
    }
}
