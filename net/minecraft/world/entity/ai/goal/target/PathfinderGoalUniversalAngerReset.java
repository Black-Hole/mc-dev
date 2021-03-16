package net.minecraft.world.entity.ai.goal.target;

import java.util.List;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AxisAlignedBB;

public class PathfinderGoalUniversalAngerReset<T extends EntityInsentient & IEntityAngerable> extends PathfinderGoal {

    private final T a;
    private final boolean b;
    private int c;

    public PathfinderGoalUniversalAngerReset(T t0, boolean flag) {
        this.a = t0;
        this.b = flag;
    }

    @Override
    public boolean a() {
        return this.a.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER) && this.g();
    }

    private boolean g() {
        return this.a.getLastDamager() != null && this.a.getLastDamager().getEntityType() == EntityTypes.PLAYER && this.a.da() > this.c;
    }

    @Override
    public void c() {
        this.c = this.a.da();
        ((IEntityAngerable) this.a).I_();
        if (this.b) {
            this.h().stream().filter((entityinsentient) -> {
                return entityinsentient != this.a;
            }).map((entityinsentient) -> {
                return (IEntityAngerable) entityinsentient;
            }).forEach(IEntityAngerable::I_);
        }

        super.c();
    }

    private List<EntityInsentient> h() {
        double d0 = this.a.b(GenericAttributes.FOLLOW_RANGE);
        AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.a.getPositionVector()).grow(d0, 10.0D, d0);

        return this.a.world.b(this.a.getClass(), axisalignedbb);
    }
}
