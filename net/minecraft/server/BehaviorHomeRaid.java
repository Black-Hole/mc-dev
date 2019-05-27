package net.minecraft.server;

public class BehaviorHomeRaid extends BehaviorHome {

    public BehaviorHomeRaid(int i, float f) {
        super(i, f, 1);
    }

    @Override
    protected boolean shouldExecute(WorldServer worldserver, EntityLiving entityliving) {
        Raid raid = worldserver.c_(new BlockPosition(entityliving));

        return super.shouldExecute(worldserver, entityliving) && raid != null && raid.u() && !raid.e() && !raid.f();
    }
}
