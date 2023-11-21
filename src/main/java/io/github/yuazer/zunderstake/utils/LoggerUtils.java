package io.github.yuazer.zunderstake.utils;

import io.github.yuazer.zunderstake.Main;

import java.util.logging.Level;

public class LoggerUtils {
    public static void warnLog(String content){
        Main.getInstance().getLogger().warning(content);
    }
    public static void log(String content){
        Main.getInstance().getLogger().log(Level.INFO,content);
    }
}
