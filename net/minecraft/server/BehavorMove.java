package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;

public class BehavorMove extends Behavior<EntityInsentient> {

    @Nullable
    private PathEntity a;
    @Nullable
    private BlockPosition b;
    private float c;
    private int d;

    public BehavorMove(int i) {
        super(i);
    }

    @Override
    protected Set<Pair<MemoryModuleType<?>, MemoryStatus>> a() {
        return ImmutableSet.of(Pair.of(MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT));
    }

    protected boolean a(WorldServer worldserver, EntityInsentient entityinsentient) {
        BehaviorController<?> behaviorcontroller = entityinsentient.getBehaviorController();
        MemoryTarget memorytarget = (MemoryTarget) behaviorcontroller.c(MemoryModuleType.WALK_TARGET).get();

        if (!this.b(entityinsentient, memorytarget) && this.a(entityinsentient, memorytarget)) {
            this.b = memorytarget.a().a();
            return true;
        } else {
            behaviorcontroller.b(MemoryModuleType.WALK_TARGET);
            return false;
        }
    }

    protected boolean g(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        if (this.a != null && this.b != null) {
            Optional<MemoryTarget> optional = entityinsentient.getBehaviorController().c(MemoryModuleType.WALK_TARGET);
            NavigationAbstract navigationabstract = entityinsentient.getNavigation();

            return !navigationabstract.n() && optional.isPresent() && !this.b(entityinsentient, (MemoryTarget) optional.get());
        } else {
            return false;
        }
    }

    protected void f(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        entityinsentient.getNavigation().o();
        entityinsentient.getBehaviorController().b(MemoryModuleType.WALK_TARGET);
        entityinsentient.getBehaviorController().b(MemoryModuleType.PATH);
        this.a = null;
    }

    protected void a(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        entityinsentient.getBehaviorController().a(MemoryModuleType.PATH, (Object) this.a);
        entityinsentient.getNavigation().a(this.a, (double) this.c);
        this.d = worldserver.getRandom().nextInt(10);
    }

    protected void d(WorldServer worldserver, EntityInsentient entityinsentient, long i) {
        --this.d;
        if (this.d <= 0) {
            PathEntity pathentity = entityinsentient.getNavigation().l();

            if (this.a != pathentity) {
                this.a = pathentity;
                entityinsentient.getBehaviorController().a(MemoryModuleType.PATH, (Object) pathentity);
            }

            if (pathentity != null && this.b != null) {
                MemoryTarget memorytarget = (MemoryTarget) entityinsentient.getBehaviorController().c(MemoryModuleType.WALK_TARGET).get();

                if (memorytarget.a().a().m(this.b) > 4.0D && this.a(entityinsentient, memorytarget)) {
                    this.b = memorytarget.a().a();
                    this.a(worldserver, entityinsentient, i);
                }

            }
        }
    }

    private boolean a(EntityInsentient entityinsentient, MemoryTarget memorytarget) {
        BlockPosition blockposition = memorytarget.a().a();

        this.a = entityinsentient.getNavigation().b(blockposition);
        this.c = memorytarget.b();
        if (!this.b(entityinsentient, memorytarget)) {
            if (this.a != null) {
                return true;
            }

            Vec3D vec3d = RandomPositionGenerator.a((EntityCreature) entityinsentient, 10, 7, new Vec3D(blockposition));

            if (vec3d != null) {
                this.a = entityinsentient.getNavigation().a(vec3d.x, vec3d.y, vec3d.z);
                return this.a != null;
            }
        }

        return false;
    }

    private boolean b(EntityInsentient entityinsentient, MemoryTarget memorytarget) {
        return memorytarget.a().a().n(new BlockPosition(entityinsentient)) <= memorytarget.c();
    }
}
