package net.minecraft.server;

import java.util.EnumSet;

public class PathfinderGoalDefendVillage extends PathfinderGoalTarget {

    private final EntityIronGolem a;
    private EntityLiving b;

    public PathfinderGoalDefendVillage(EntityIronGolem entityirongolem) {
        super(entityirongolem, false, true);
        this.a = entityirongolem;
        this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public void c() {
        this.a.setGoalTarget(this.b);
        super.c();
    }
}
