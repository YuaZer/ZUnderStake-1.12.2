package io.github.yuazer.zunderstake.hook;

import io.github.yuazer.zunderstake.Main;
import io.github.yuazer.zunderstake.utils.UnderStakeUtils;
import io.github.yuazer.zunderstake.utils.YamlUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class UnderStakeHook extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "zunderstake";
    }

    @Override
    public String getAuthor() {
        return "Z菌";
    }

    @Override
    public String getVersion() {
        return Main.getInstance().getDescription().getVersion();
    }
    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        String key = indentifier.split("_")[0];
        String value = indentifier.split("_")[1];
        if (key.equalsIgnoreCase("time")){
            return String.valueOf(Main.getRunnableManager().getRunnable(value).getNowTime());
        }
        if (key.equalsIgnoreCase("isOpen")){
            return Main.getRunnableManager().getRunningStake().contains(value) ? YamlUtils.getConfigMessage("PapiHook.isOpen.true") : YamlUtils.getConfigMessage("PapiHook.isOpen.false");
        }
        if (Main.getRunnableManager().getRunningStake().contains(key)){
            return UnderStakeUtils.getUnderStakeFormID(key).getBricks().get(Integer.parseInt(value)).getOwnerPlayerName();
        }
        return "error";
    }
}
