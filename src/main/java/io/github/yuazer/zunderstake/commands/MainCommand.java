package io.github.yuazer.zunderstake.commands;

import io.github.yuazer.zunderstake.Main;
import io.github.yuazer.zunderstake.utils.CommonUtils;
import io.github.yuazer.zunderstake.utils.LoggerUtils;
import io.github.yuazer.zunderstake.utils.UnderStakeUtils;
import io.github.yuazer.zunderstake.utils.YamlUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String abcde, String[] args) {
        if (command.getName().equalsIgnoreCase("zunderstake")) {
            if (args.length == 0 && sender.isOp()) {
                return true;
            }
            if (args[0].equalsIgnoreCase("help") && sender.isOp()) {
                sender.sendMessage("§a/zunderstake -> /zus");
                sender.sendMessage("§a/zunderstake start 地下城ID §7- §e开启一场地下城");
                sender.sendMessage("§a/zunderstake stop 地下城ID §7- §e强制结束一场地下城");
                sender.sendMessage("§a/zunderstake stake 地下城ID 砖块ID §7- §e选取指定砖块");
                sender.sendMessage("§a/zunderstake check 地下城ID §7- §e检查该地下城ID的配置内容");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
                Main.getInstance().reloadConfig();
                CommonUtils.checkAllStakeSettingAsync();
                Main.loadStakeRunnable();
                sender.sendMessage(YamlUtils.getConfigMessage("Message.reload"));
                return true;
            }
            if (args[0].equalsIgnoreCase("start") && sender.isOp()) {
                String stakeID = args[1];
                if (Main.getErrorStakeID().contains(stakeID)) {
                    sender.sendMessage(YamlUtils.getConfigMessage("Message.startErrorStakeID")
                            .replace("%stakeID%", stakeID));
                    return true;
                }
                if (Main.getRunnableManager().getRunningStake().contains(stakeID)) {
                    sender.sendMessage(YamlUtils.getConfigMessage("Message.alreadyRunning"));
                    return true;
                }
                //TODO 开启地下城方法
                Main.getRunnableManager().startRunnable(stakeID, 0L, 20L);
                sender.sendMessage(YamlUtils.getConfigMessage("Message.successStart").replace("%stakeID%", stakeID));
                return true;
            }
            if (args[0].equalsIgnoreCase("stake") && sender.isOp() && (sender instanceof Player)) {
                //TODO 下注方法
                Player player = (Player) sender;
                String stakeID = args[1];
                int brickID = Integer.parseInt(args[2]);
                int brickAmount = YamlUtils.getConfigInt("UnderStakeSetting." + stakeID + ".brickAmount");
                if (!Main.getRunnableManager().getRunningStake().contains(stakeID)) {
                    sender.sendMessage(YamlUtils.getConfigMessage("Message.notRunning"));
                    return true;
                }
                if (brickID >= brickAmount) {
                    sender.sendMessage(YamlUtils.getConfigMessage("Message.errorBrickID"));
                    return true;
                }
                if (!UnderStakeUtils.getUnderStakeFormID(stakeID).getBricks().get(brickID).isHasPlayer()) {
                    UnderStakeUtils.getUnderStakeFormID(stakeID).getBricks().get(brickID).setHasPlayer(true);
                    UnderStakeUtils.getUnderStakeFormID(stakeID).getBricks().get(brickID).setOwnerPlayerName(player.getName());
                    player.sendMessage(YamlUtils.getConfigMessage("Message.successChooseBrick"));
                } else {
                    player.sendMessage(YamlUtils.getConfigMessage("Message.brickHasPlayer"));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("stop") && sender.isOp()) {
                //TODO 结束地下城方法
                String stakeID = args[1];
                if (!UnderStakeUtils.stopUnderStake(stakeID)) {
                    sender.sendMessage(YamlUtils.getConfigMessage("Message.notRunning"));
                } else {
                    sender.sendMessage(YamlUtils.getConfigMessage("Message.successStop"));
                }
                return true;
            }
            //检查地下城配置
            if (args[0].equalsIgnoreCase("check") && sender.isOp()) {
                String stakeID = args[1];
                CommonUtils.checkUnderStakeSetting(stakeID);
                sender.sendMessage(YamlUtils.getConfigMessage("Message.executeCheck"));
                return true;
            }
            if (args[0].equalsIgnoreCase("debug") && sender.isOp()) {
                String stakeID = args[1];
                int value = Integer.parseInt(args[2]);
                LoggerUtils.log(UnderStakeUtils.getUnderStakeFormID(stakeID).getBricks().get(value).getOwnerPlayerName());
            }
            return true;
        }
        return false;
    }
}
