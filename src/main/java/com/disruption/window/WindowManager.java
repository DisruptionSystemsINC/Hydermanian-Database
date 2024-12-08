package com.disruption.window;

public class WindowManager {

    static DisruptionWindow[] windows;


    public WindowManager addWindow(DisruptionWindow window) {
        windows = new DisruptionWindow[windows.length+1];
        windows[windows.length-1] = window;
        return this;
    }

    public DisruptionWindow[] getWindows() {
        return windows;
    }
}
