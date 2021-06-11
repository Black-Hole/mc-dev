package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.AxisAlignedBB;

public class PathfinderGoalNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalTarget {

    protected final Class<T> targetType;
    protected final int randomInterval;
    protected EntityLiving target;
    protected PathfinderTargetCondition targetConditions;

    public PathfinderGoalNearestAttackableTarget(EntityInsentient entityinsentient, Class<T> oclass, boolean flag) {
        this(entityinsentient, oclass, flag, false);
    }

    public PathfinderGoalNearestAttackableTarget(EntityInsentient entityinsentient, Class<T> oclass, boolean flag, boolean flag1) {
        this(entityinsentient, oclass, 10, flag, flag1, (Predicate) null);
    }

    public PathfinderGoalNearestAttackableTarget(EntityInsentient entityinsentient, Class<T> oclass, int i, boolean flag, boolean flag1, @Nullable Predicate<EntityLiving> predicate) {
        super(entityinsentient, flag, flag1);
        this.targetType = oclass;
        this.randomInterval = i;
        this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
        this.targetConditions = PathfinderTargetCondition.a().a(this.k()).a(predicate);
    }

    @Override
    public boolean a() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.g();
            return this.target != null;
        }
    }

    protected AxisAlignedBB a(double d0) {
        return this.mob.getBoundingBox().grow(d0, 4.0D, d0);
    }

    protected void g() {
        if (this.targetType != EntityHuman.class && this.targetType != EntityPlayer.class) {
            this.target = this.mob.level.a(this.mob.level.a(this.targetType, this.a(this.k()), (entityliving) -> {
                return true;
            }), this.targetConditions, (EntityLiving) this.mob, this.mob.locX(), this.mob.getHeadY(), this.mob.locZ());
        } else {
            this.target = this.mob.level.a(this.targetConditions, this.mob, this.mob.locX(), this.mob.getHeadY(), this.mob.locZ());
        }

    }

    @Override
    public void c() {
        this.mob.setGoalTarget(this.target);
        super.c();
    }

    public void a(@Nullable EntityLiving entityliving) {
        this.target = entityliving;
    }
}
