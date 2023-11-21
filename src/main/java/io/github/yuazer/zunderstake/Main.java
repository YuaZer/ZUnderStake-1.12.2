package io.github.yuazer.zunderstake;

import io.github.yuazer.zunderstake.commands.MainCommand;
import io.github.yuazer.zunderstake.hook.UnderStakeHook;
import io.github.yuazer.zunderstake.manager.BukkitRunnableManager;
import io.github.yuazer.zunderstake.object.Brick;
import io.github.yuazer.zunderstake.object.UnderStake;
import io.github.yuazer.zunderstake.runnable.UnderStakeRunnable;
import io.github.yuazer.zunderstake.utils.CommonUtils;
import io.github.yuazer.zunderstake.utils.YamlUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private static Set<String> errorStakeID;

    public static Set<String> getErrorStakeID() {
        return errorStakeID;
    }

    private static BukkitRunnableManager runnableManager;

    public static BukkitRunnableManager getRunnableManager() {
        return runnableManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        errorStakeID = new HashSet<>();
        runnableManager = new BukkitRunnableManager(this);
        saveDefaultConfig();
        reloadConfig();
        CommonUtils.checkAllStakeSettingAsync();
        loadStakeRunnable();
        Bukkit.getPluginCommand("zunderstake").setExecutor(new MainCommand());
        UnderStakeHook hook = new UnderStakeHook();
        hook.register();
        logLoaded(this);
    }

    @Override
    public void onDisable() {
        logDisable(this);
    }

    //加载地下城配置
    public static void loadStakeRunnable() {
        for (String stakeID : Main.getInstance().getConfig().getConfigurationSection("UnderStakeSetting").getKeys(false)) {
            if (errorStakeID.contains(stakeID)) {
                continue;
            }
            loadStakeRunnableByID(stakeID);
        }
    }

    public static void loadStakeRunnableByID(String stakeID) {
        List<Brick> bricks = new ArrayList<>();
        for (int i = 0; i < YamlUtils.getConfigInt("UnderStakeSetting." + stakeID + ".brickAmount"); i++) {
            bricks.add(new Brick(null, CommonUtils.getBrickRewardCommands(stakeID),
                    CommonUtils.getBrickDefaultCommands(stakeID),
                    CommonUtils.getBrickNullCommands(stakeID), CommonUtils.getBrickBackCommands(stakeID),
                    false));
        }
        int overTime = YamlUtils.getConfigInt("UnderStakeSetting." + stakeID + ".overTime");
        runnableManager.put(stakeID, new UnderStakeRunnable(new UnderStake(stakeID, bricks, overTime)));
    }

    public static void logLoaded(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §f已加载", plugin.getName()));
        Bukkit.getLogger().info("§b作者:§eZ菌");
        Bukkit.getLogger().info("§b版本:§e" + plugin.getDescription().getVersion());
    }

    public static void logDisable(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §c已卸载", plugin.getName()));
    }
}
