package net.minecraft.server;

import com.mojang.datafixers.util.Pair;
import java.util.Set;

public abstract class Behavior<E extends EntityLiving> {

    private Behavior.Status a;
    private long b;
    private final int c;
    private final int d;

    public Behavior() {
        this(60);
    }

    public Behavior(int i) {
        this(i, i);
    }

    public Behavior(int i, int j) {
        this.a = Behavior.Status.STOPPED;
        this.c = i;
        this.d = j;
    }

    public Behavior.Status b() {
        return this.a;
    }

    public final boolean b(WorldServer worldserver, E e0, long i) {
        if (this.a(e0) && this.a(worldserver, e0)) {
            this.a = Behavior.Status.RUNNING;
            int j = this.c + worldserver.getRandom().nextInt(this.d + 1 - this.c);

            this.b = i + (long) j;
            this.a(worldserver, e0, i);
            return true;
        } else {
            return false;
        }
    }

    protected void a(WorldServer worldserver, E e0, long i) {}

    public final void c(WorldServer worldserver, E e0, long i) {
        if (!this.a(i) && this.g(worldserver, e0, i)) {
            this.d(worldserver, e0, i);
        } else {
            this.e(worldserver, e0, i);
        }

    }

    protected void d(WorldServer worldserver, E e0, long i) {}

    public final void e(WorldServer worldserver, E e0, long i) {
        this.a = Behavior.Status.STOPPED;
        this.f(worldserver, e0, i);
    }

    protected void f(WorldServer worldserver, E e0, long i) {}

    protected boolean g(WorldServer worldserver, E e0, long i) {
        return false;
    }

    protected boolean a(long i) {
        return i > this.b;
    }

    protected boolean a(WorldServer worldserver, E e0) {
        return true;
    }

    protected abstract Set<Pair<MemoryModuleType<?>, MemoryStatus>> a();

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private boolean a(E e0) {
        return this.a().stream().allMatch((pair) -> {
            MemoryModuleType<?> memorymoduletype = (MemoryModuleType) pair.getFirst();
            MemoryStatus memorystatus = (MemoryStatus) pair.getSecond();

            return e0.getBehaviorController().a(memorymoduletype, memorystatus);
        });
    }

    public static enum Status {

        STOPPED, RUNNING;

        private Status() {}
    }
}
