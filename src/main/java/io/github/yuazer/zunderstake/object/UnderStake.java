package io.github.yuazer.zunderstake.object;

import java.util.List;

public class UnderStake {
    private List<Brick> bricks;
    private String stakeID;
    private int overTime;

    public int getOverTime() {
        return overTime;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public String getStakeID() {
        return stakeID;
    }

    public UnderStake(String stakeID, List<Brick> bricks, int overTime) {
        this.bricks = bricks;
        this.stakeID = stakeID;
        this.overTime = overTime;
    }
}
