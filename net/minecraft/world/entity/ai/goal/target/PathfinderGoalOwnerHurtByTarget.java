package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;

public class PathfinderGoalOwnerHurtByTarget extends PathfinderGoalTarget {

    private final EntityTameableAnimal tameAnimal;
    private EntityLiving ownerLastHurtBy;
    private int timestamp;

    public PathfinderGoalOwnerHurtByTarget(EntityTameableAnimal entitytameableanimal) {
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
                this.ownerLastHurtBy = entityliving.getLastDamager();
                int i = entityliving.dH();

                return i != this.timestamp && this.a(this.ownerLastHurtBy, PathfinderTargetCondition.DEFAULT) && this.tameAnimal.a(this.ownerLastHurtBy, entityliving);
            }
        } else {
            return false;
        }
    }

    @Override
    public void c() {
        this.mob.setGoalTarget(this.ownerLastHurtBy);
        EntityLiving entityliving = this.tameAnimal.getOwner();

        if (entityliving != null) {
            this.timestamp = entityliving.dH();
        }

        super.c();
    }
}
