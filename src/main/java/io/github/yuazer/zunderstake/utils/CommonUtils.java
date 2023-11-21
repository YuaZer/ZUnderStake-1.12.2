package io.github.yuazer.zunderstake.utils;

import io.github.yuazer.zunderstake.Main;
import org.bukkit.Bukkit;

import java.util.List;

public class CommonUtils {
    public static void checkAllStakeSettingAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), CommonUtils::checkAllStakeSettingSync);
    }

    public static void checkAllStakeSettingSync() {
        for (String stakeID : Main.getInstance().getConfig().getConfigurationSection("UnderStakeSetting").getKeys(false)) {
            if (!checkUnderStakeSetting(stakeID)) {
                Main.getErrorStakeID().add(stakeID);
            }
        }
    }

    //检测配置文件
    public static boolean checkUnderStakeSetting(String stakeID) {
        if (!Main.getInstance().getConfig().getConfigurationSection("UnderStakeSetting").getKeys(false).contains(stakeID)) {
            LoggerUtils.warnLog(YamlUtils.getConfigMessage("Message.errorExist"));
            return false;
        }
        if (getRewardBrickAmount(stakeID) + getDefaultBrickAmount(stakeID) > getBrickAmount(stakeID)) {
            LoggerUtils.warnLog(YamlUtils.getConfigMessage("Message.errorAmount"));
            return false;
        }
        LoggerUtils.log(YamlUtils.getConfigMessage("Message.checkSuccess").replace("%stakeID%",stakeID));
        return true;
    }

    //获取奖励砖块的数量
    public static int getRewardBrickAmount(String stakeID) {
        return YamlUtils.getConfigInt("UnderStakeSetting." + stakeID + ".rewardBrick");
    }

    //获取安慰砖块的数量
    public static int getDefaultBrickAmount(String stakeID) {
        return YamlUtils.getConfigInt("UnderStakeSetting." + stakeID + ".defaultBrick");
    }

    //获取总砖块数量
    public static int getBrickAmount(String stakeID) {
        return YamlUtils.getConfigInt("UnderStakeSetting." + stakeID + ".brickAmount");
    }

    //获取砖块宝藏奖励
    public static List<String> getBrickRewardCommands(String stakeID) {
        return YamlUtils.getConfigStringList("UnderStakeSetting." + stakeID + ".rewardCommands");
    }
    //获取砖块安慰奖励
    public static List<String> getBrickDefaultCommands(String stakeID) {
        return YamlUtils.getConfigStringList("UnderStakeSetting." + stakeID + ".defaultCommands");
    }
    //获取砖块轮空指令
    public static List<String> getBrickNullCommands(String stakeID) {
        return YamlUtils.getConfigStringList("UnderStakeSetting." + stakeID + ".nullCommands");
    }
    //获取砖块返回指令
    public static List<String> getBrickBackCommands(String stakeID) {
        return YamlUtils.getConfigStringList("UnderStakeSetting." + stakeID + ".backCommands");
    }
}
