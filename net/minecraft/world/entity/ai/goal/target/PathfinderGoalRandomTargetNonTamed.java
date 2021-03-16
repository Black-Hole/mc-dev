package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;

public class PathfinderGoalRandomTargetNonTamed<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {

    private final EntityTameableAnimal i;

    public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal entitytameableanimal, Class<T> oclass, boolean flag, @Nullable Predicate<EntityLiving> predicate) {
        super(entitytameableanimal, oclass, 10, flag, false, predicate);
        this.i = entitytameableanimal;
    }

    @Override
    public boolean a() {
        return !this.i.isTamed() && super.a();
    }

    @Override
    public boolean b() {
        return this.d != null ? this.d.a(this.e, this.c) : super.b();
    }
}
