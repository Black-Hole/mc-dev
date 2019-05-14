package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PathfinderGoalRaid<T extends EntityRaider> extends PathfinderGoal {

    private final T a;

    public PathfinderGoalRaid(T t0) {
        this.a = t0;
        this.a(EnumSet.of(PathfinderGoal.Type.MOVE));
    }

    @Override
    public boolean a() {
        return this.a.getGoalTarget() == null && !this.a.isVehicle() && this.a.el() && !this.a.ek().a() && !((WorldServer) this.a.world).b_(new BlockPosition(this.a));
    }

    @Override
    public boolean b() {
        return this.a.el() && !this.a.ek().a() && this.a.world instanceof WorldServer && !((WorldServer) this.a.world).b_(new BlockPosition(this.a));
    }

    @Override
    public void e() {
        if (this.a.el()) {
            Raid raid = this.a.ek();

            if (this.a.ticksLived % 20 == 0) {
                this.a(raid);
            }

            if (!this.a.dT()) {
                Vec3D vec3d = new Vec3D(raid.s());
                Vec3D vec3d1 = new Vec3D(this.a.locX, this.a.locY, this.a.locZ);
                Vec3D vec3d2 = vec3d1.d(vec3d);

                vec3d = vec3d2.a(0.4D).e(vec3d);
                Vec3D vec3d3 = vec3d.d(vec3d1).d().a(10.0D).e(vec3d1);
                BlockPosition blockposition = new BlockPosition(vec3d3);

                blockposition = this.a.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, blockposition);
                if (!this.a.getNavigation().a((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ(), 1.0D)) {
                    this.g();
                }
            }
        }

    }

    private void a(Raid raid) {
        if (raid.u()) {
            Set<EntityRaider> set = Sets.newHashSet();
            List<EntityRaider> list = this.a.world.a(EntityRaider.class, this.a.getBoundingBox().g(16.0D), (entityraider) -> {
                return !entityraider.el() && PersistentRaid.a(entityraider, raid);
            });

            set.addAll(list);
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                EntityRaider entityraider = (EntityRaider) iterator.next();

                raid.a(raid.k(), entityraider, (BlockPosition) null, true);
            }
        }

    }

    private void g() {
        Random random = this.a.getRandom();
        BlockPosition blockposition = this.a.world.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, (new BlockPosition(this.a)).b(-8 + random.nextInt(16), 0, -8 + random.nextInt(16)));

        this.a.getNavigation().a((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ(), 1.0D);
    }
}
