package io.github.yuazer.zunderstake.object;


import io.github.yuazer.zunderstake.Main;
import org.bukkit.Bukkit;
import java.util.List;

public class Brick {
    private  String ownerPlayerName;
    private final List<String> rewardCommands;
    private final List<String> backCommands;
    private final List<String> defaultCommands;
    private final List<String> nullCommands;
    private boolean hasPlayer;
    public Brick(String ownerPlayerName,
                 List<String> rewardCommands,
                 List<String> defaultCommands,
                 List<String> nullCommands,
                 List<String> backCommands,
                 boolean hasPlayer) {
        this.ownerPlayerName = ownerPlayerName;
        this.rewardCommands = rewardCommands;
        this.backCommands = backCommands;
        this.defaultCommands = defaultCommands;
        this.nullCommands = nullCommands;
        this.hasPlayer = hasPlayer;
    }

    public List<String> getDefaultCommands() {
        return defaultCommands;
    }

    public List<String> getNullCommands() {
        return nullCommands;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public List<String> getBackCommands() {
        return backCommands;
    }

    public void setOwnerPlayerName(String ownerPlayerName) {
        this.ownerPlayerName = ownerPlayerName;
    }

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public List<String> getRewardCommands() {
        return rewardCommands;
    }

    public String getOwnerPlayerName() {
        if (!isHasPlayer()){
            return "无";
        }
        return ownerPlayerName;
    }

    public void runRewardCommands() {
        Bukkit.getScheduler().runTask(Main.getInstance(),()->{
            for (String cmd : rewardCommands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", ownerPlayerName));
            }
        });
    }
    public void runBackCommands() {
        Bukkit.getScheduler().runTask(Main.getInstance(),()->{
            for (String cmd : backCommands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", ownerPlayerName));
            }
        });
    }
    public void runDefaultCommands(){
        Bukkit.getScheduler().runTask(Main.getInstance(),()->{
            for (String cmd : defaultCommands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", ownerPlayerName));
            }
        });
    }
    public void runNullCommands(){
        Bukkit.getScheduler().runTask(Main.getInstance(),()->{
            for (String cmd : nullCommands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", ownerPlayerName));
            }
        });
    }
}
