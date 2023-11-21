package io.github.yuazer.zunderstake.runnable;

import io.github.yuazer.zunderstake.object.Brick;
import io.github.yuazer.zunderstake.object.UnderStake;
import io.github.yuazer.zunderstake.utils.UnderStakeUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class UnderStakeRunnable extends BukkitRunnable {
    private String stakeID;
    private int overTime;
    private List<Brick> bricks;
    private UnderStake underStake;
    private static int nowTime ;

    public  int getNowTime() {
        return nowTime;
    }

    public  void setNowTime(int nowTime) {
        UnderStakeRunnable.nowTime = nowTime;
    }

    public UnderStakeRunnable(UnderStake underStake) {
        this.underStake = underStake;
        this.stakeID = underStake.getStakeID();
        this.overTime = underStake.getOverTime();
        this.bricks = underStake.getBricks();
        nowTime = overTime;
    }

    public UnderStake getUnderStake() {
        return underStake;
    }

    @Override
    public void run() {
        if (nowTime<=0){
            //TODO 结束并返还
            UnderStakeUtils.stopUnderStake(stakeID);
            return;
        }
        if (hasNullBrick()){
            setNowTime(nowTime-1);
        }else {
            //TODO 开奖
            UnderStakeUtils.endUnderStake(stakeID);
        }
    }

    private boolean hasNullBrick() {
        for (Brick brick : bricks) {
            if (!brick.isHasPlayer()) {
                return true;
            }
        }
        return false;
    }
}
