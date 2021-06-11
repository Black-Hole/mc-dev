package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AxisAlignedBB;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget {

    private static final PathfinderTargetCondition HURT_BY_TARGETING = PathfinderTargetCondition.a().d().e();
    private static final int ALERT_RANGE_Y = 10;
    private boolean alertSameType;
    private int timestamp;
    private final Class<?>[] toIgnoreDamage;
    private Class<?>[] toIgnoreAlert;

    public PathfinderGoalHurtByTarget(EntityCreature entitycreature, Class<?>... aclass) {
        super(entitycreature, true);
        this.toIgnoreDamage = aclass;
        this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
    }

    @Override
    public boolean a() {
        int i = this.mob.dH();
        EntityLiving entityliving = this.mob.getLastDamager();

        if (i != this.timestamp && entityliving != null) {
            if (entityliving.getEntityType() == EntityTypes.PLAYER && this.mob.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                return false;
            } else {
                Class[] aclass = this.toIgnoreDamage;
                int j = aclass.length;

                for (int k = 0; k < j; ++k) {
                    Class<?> oclass = aclass[k];

                    if (oclass.isAssignableFrom(entityliving.getClass())) {
                        return false;
                    }
                }

                return this.a(entityliving, PathfinderGoalHurtByTarget.HURT_BY_TARGETING);
            }
        } else {
            return false;
        }
    }

    public PathfinderGoalHurtByTarget a(Class<?>... aclass) {
        this.alertSameType = true;
        this.toIgnoreAlert = aclass;
        return this;
    }

    @Override
    public void c() {
        this.mob.setGoalTarget(this.mob.getLastDamager());
        this.targetMob = this.mob.getGoalTarget();
        this.timestamp = this.mob.dH();
        this.unseenMemoryTicks = 300;
        if (this.alertSameType) {
            this.g();
        }

        super.c();
    }

    protected void g() {
        double d0 = this.k();
        AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.mob.getPositionVector()).grow(d0, 10.0D, d0);
        List<? extends EntityInsentient> list = this.mob.level.a(this.mob.getClass(), axisalignedbb, IEntitySelector.NO_SPECTATORS);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityInsentient entityinsentient = (EntityInsentient) iterator.next();

            if (this.mob != entityinsentient && entityinsentient.getGoalTarget() == null && (!(this.mob instanceof EntityTameableAnimal) || ((EntityTameableAnimal) this.mob).getOwner() == ((EntityTameableAnimal) entityinsentient).getOwner()) && !entityinsentient.p(this.mob.getLastDamager())) {
                if (this.toIgnoreAlert != null) {
                    boolean flag = false;
                    Class[] aclass = this.toIgnoreAlert;
                    int i = aclass.length;

                    for (int j = 0; j < i; ++j) {
                        Class<?> oclass = aclass[j];

                        if (entityinsentient.getClass() == oclass) {
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        continue;
                    }
                }

                this.a(entityinsentient, this.mob.getLastDamager());
            }
        }

    }

    protected void a(EntityInsentient entityinsentient, EntityLiving entityliving) {
        entityinsentient.setGoalTarget(entityliving);
    }
}
