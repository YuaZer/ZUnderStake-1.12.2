package io.github.yuazer.zunderstake.utils;

import io.github.yuazer.zunderstake.Main;
import io.github.yuazer.zunderstake.object.Brick;
import io.github.yuazer.zunderstake.object.UnderStake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UnderStakeUtils {
    //通过地下城ID获取地下城对象
    public static UnderStake getUnderStakeFormID(String stakeID) {
        return Main.getRunnableManager().getRunnable(stakeID).getUnderStake();
    }
    //强制结束该地下城回合
    public static boolean stopUnderStake(String stakeID) {
        if (!Main.getRunnableManager().getRunningStake().contains(stakeID)) {
            return false;
        }
        getUnderStakeFormID(stakeID).getBricks().forEach(brick -> {
            if (brick.isHasPlayer()) {
                brick.runBackCommands();
            }
        });
        Main.getRunnableManager().stopRunnable(stakeID);
        return true;
    }
    //正常结束地下城场次并进行结算
    public static void endUnderStake(String stakeID) {
        if (!Main.getRunnableManager().getRunningStake().contains(stakeID)) {
            return;
        }
        List<Brick> bricks = getUnderStakeFormID(stakeID).getBricks();
        List<Brick> rewardBricks = getRandomBricks(CommonUtils.getRewardBrickAmount(stakeID), getUnderStakeFormID(stakeID).getBricks());
        List<Brick> defaultBricks = getRandomBricks(CommonUtils.getDefaultBrickAmount(stakeID), getOtherBricks(bricks, rewardBricks));
        List<Brick> nullBricks = getOtherBricks(getOtherBricks(bricks, rewardBricks), defaultBricks);
        rewardBricks.forEach(Brick::runRewardCommands);
        defaultBricks.forEach(Brick::runDefaultCommands);
        if (!nullBricks.isEmpty()) {
            nullBricks.forEach(Brick::runNullCommands);
        }
        Main.getRunnableManager().stopRunnable(stakeID);
    }

    public static List<Brick> getOtherBricks(List<Brick> list1, List<Brick> list2) {
        // 使用 Java 8 的 Stream API 进行列表操作
        return list1.stream()
                .filter(element -> !list2.contains(element))
                .collect(Collectors.toList());
    }

    public static List<Brick> getRandomBricks(int n, List<Brick> brickList) {
        // 创建一个副本以防修改原始列表
        List<Brick> copyList = new ArrayList<>(brickList);
        // 使用Collections.shuffle()打乱列表顺序
        Collections.shuffle(copyList);
        // 截取前n个元素作为结果
        return copyList.subList(0, Math.min(n, copyList.size()));
    }
}
