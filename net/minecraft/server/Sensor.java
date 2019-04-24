package net.minecraft.server;

import java.util.Set;

public abstract class Sensor<E extends EntityLiving> {

    private final int b;
    protected long a;

    public Sensor(int i) {
        this.b = i;
    }

    public Sensor() {
        this(20);
    }

    public final void b(WorldServer worldserver, E e0) {
        if (worldserver.getTime() - this.a >= (long) this.b) {
            this.a = worldserver.getTime();
            this.a(worldserver, e0);
        }

    }

    protected abstract void a(WorldServer worldserver, E e0);

    public abstract Set<MemoryModuleType<?>> a();
}
