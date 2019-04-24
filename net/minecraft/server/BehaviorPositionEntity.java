package net.minecraft.server;

import java.util.List;
import java.util.Optional;

public class BehaviorPositionEntity implements BehaviorPosition {

    private final EntityLiving a;

    public BehaviorPositionEntity(EntityLiving entityliving) {
        this.a = entityliving;
    }

    @Override
    public BlockPosition a() {
        return new BlockPosition(this.a.locX, this.a.locY, this.a.locZ);
    }

    @Override
    public Vec3D b() {
        return new Vec3D(this.a.locX, this.a.locY + (double) this.a.getHeadHeight(), this.a.locZ);
    }

    @Override
    public boolean a(EntityLiving entityliving) {
        Optional<List<EntityLiving>> optional = entityliving.getBehaviorController().c(MemoryModuleType.VISIBLE_MOBS);

        return this.a.isAlive() && optional.isPresent() && ((List) optional.get()).contains(this.a);
    }

    public String toString() {
        return "EntityPosWrapper for " + this.a;
    }
}
