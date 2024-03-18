package io.github.yuazer.zunderstake.manager;


import io.github.yuazer.zunderstake.Main;
import io.github.yuazer.zunderstake.runnable.UnderStakeRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BukkitRunnableManager {

    //创建一个HashMap对象，用来存储String和BukkitRunnable的对应关系
    private HashMap<String, UnderStakeRunnable> runnables;
    private Set<String> runningStake;
    //创建一个JavaPlugin对象，用来获取插件实例
    private JavaPlugin plugin;

    public Set<String> getRunningStake() {
        return runningStake;
    }

    //创建一个构造方法，传入插件实例
    public BukkitRunnableManager(JavaPlugin plugin) {
        //初始化HashMap对象
        runnables = new HashMap<>();
        runningStake = new HashSet<>();
        //赋值插件实例
        this.plugin = plugin;
    }

    //创建一个方法，用来添加一个String和BukkitRunnable的对应关系
    public void put(String key, UnderStakeRunnable runnable) {
        runnables.put(key, runnable);
    }

    public HashMap<String, UnderStakeRunnable> getRunnables() {
        return runnables;
    }

    //创建一个方法，用来移除一个String和BukkitRunnable的对应关系
    public void remove(String key) {
        //从HashMap中移除name对应的runnable
        runnables.remove(key);
    }


    public void startRunnable(String key, long delay, long period) {
        //从HashMap中获取name对应的runnable
        UnderStakeRunnable runnable = runnables.get(key);
        //判断runnable是否存在
        if (runnable != null) {
            runningStake.add(key);
            //如果存在，调用runTaskTimer方法，传入插件实例，延迟时间和周期时间
            runnable.runTaskTimerAsynchronously(plugin, delay, period);
        }
    }

    public UnderStakeRunnable getRunnable(String key) {
        return runnables.get(key);
    }

    public void stopRunnable(String key) {
        //从HashMap中获取name对应的runnable
        UnderStakeRunnable runnable = runnables.get(key);
        //判断runnable是否存在
        if (runnable != null) {
            runningStake.remove(key);
            //如果存在，调用cancel方法，取消任务
            runnable.cancel();
            Main.loadStakeRunnableByID(key);
        }
    }

    public Set<String> getAllRunnableID() {
        return runnables.keySet();
    }
}
