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
    public void put(String key1, UnderStakeRunnable runnable) {
        runnables.put(key1, runnable);
    }

    public HashMap<String, UnderStakeRunnable> getRunnables() {
        return runnables;
    }

    //创建一个方法，用来移除一个String和BukkitRunnable的对应关系
    public void remove(String key1) {
        //从HashMap中移除name对应的runnable
        runnables.remove(key1);
    }


    public void startRunnable(String key1, long delay, long period) {
        //从HashMap中获取name对应的runnable
        UnderStakeRunnable runnable = runnables.get(key1);
        //判断runnable是否存在
        if (runnable != null) {
            runningStake.add(key1);
            //如果存在，调用runTaskTimer方法，传入插件实例，延迟时间和周期时间
            runnable.runTaskTimerAsynchronously(plugin, delay, period);
        }
    }

    public UnderStakeRunnable getRunnable(String key1) {
        return runnables.get(key1);
    }

    public void stopRunnable(String key1) {
        //从HashMap中获取name对应的runnable
        UnderStakeRunnable runnable = runnables.get(key1);
        //判断runnable是否存在
        if (runnable != null) {
            runningStake.remove(key1);
            //如果存在，调用cancel方法，取消任务
            runnable.cancel();
            Main.loadStakeRunnableByID(key1);
        }
    }

    public Set<String> getAllRunnableID() {
        return runnables.keySet();
    }
}
