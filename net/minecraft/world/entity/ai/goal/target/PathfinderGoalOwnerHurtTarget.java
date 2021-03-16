package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;

public class PathfinderGoalOwnerHurtTarget extends PathfinderGoalTarget {

    private final EntityTameableAnimal a;
    private EntityLiving b;
    private int c;

    public PathfinderGoalOwnerHurtTarget(EntityTameableAnimal entitytameableanimal) {
        super(entitytameableanimal, false);
        this.a = entitytameableanimal;
        this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
    }

    @Override
    public boolean a() {
        if (this.a.isTamed() && !this.a.isWillSit()) {
            EntityLiving entityliving = this.a.getOwner();

            if (entityliving == null) {
                return false;
            } else {
                this.b = entityliving.db();
                int i = entityliving.dc();

                return i != this.c && this.a(this.b, PathfinderTargetCondition.a) && this.a.a(this.b, entityliving);
            }
        } else {
            return false;
        }
    }

    @Override
    public void c() {
        this.e.setGoalTarget(this.b);
        EntityLiving entityliving = this.a.getOwner();

        if (entityliving != null) {
            this.c = entityliving.dc();
        }

        super.c();
    }
}
