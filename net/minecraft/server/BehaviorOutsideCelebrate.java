package net.minecraft.server;

public class BehaviorOutsideCelebrate extends BehaviorOutside {

    public BehaviorOutsideCelebrate(float f) {
        super(f);
    }

    @Override
    protected boolean shouldExecute(WorldServer worldserver, EntityLiving entityliving) {
        Raid raid = worldserver.c_(new BlockPosition(entityliving));

        return raid != null && raid.e() && super.shouldExecute(worldserver, entityliving);
    }
}
