package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;

public class PathfinderGoalOwnerHurtTarget extends PathfinderGoalTarget {

    private final EntityTameableAnimal tameAnimal;
    private EntityLiving ownerLastHurt;
    private int timestamp;

    public PathfinderGoalOwnerHurtTarget(EntityTameableAnimal entitytameableanimal) {
        super(entitytameableanimal, false);
        this.tameAnimal = entitytameableanimal;
        this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
    }

    @Override
    public boolean a() {
        if (this.tameAnimal.isTamed() && !this.tameAnimal.isWillSit()) {
            EntityLiving entityliving = this.tameAnimal.getOwner();

            if (entityliving == null) {
                return false;
            } else {
                this.ownerLastHurt = entityliving.dI();
                int i = entityliving.dJ();

                return i != this.timestamp && this.a(this.ownerLastHurt, PathfinderTargetCondition.DEFAULT) && this.tameAnimal.a(this.ownerLastHurt, entityliving);
            }
        } else {
            return false;
        }
    }

    @Override
    public void c() {
        this.mob.setGoalTarget(this.ownerLastHurt);
        EntityLiving entityliving = this.tameAnimal.getOwner();

        if (entityliving != null) {
            this.timestamp = entityliving.dJ();
        }

        super.c();
    }
}
