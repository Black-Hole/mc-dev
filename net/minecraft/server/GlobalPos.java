package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Objects;

public final class GlobalPos implements MinecraftSerializable {

    private final DimensionManager a;
    private final BlockPosition b;

    private GlobalPos(DimensionManager dimensionmanager, BlockPosition blockposition) {
        this.a = dimensionmanager;
        this.b = blockposition;
    }

    public static GlobalPos a(DimensionManager dimensionmanager, BlockPosition blockposition) {
        return new GlobalPos(dimensionmanager, blockposition);
    }

    public static GlobalPos a(Dynamic<?> dynamic) {
        return (GlobalPos) dynamic.get("dimension").map(DimensionManager::a).flatMap((dimensionmanager) -> {
            return dynamic.get("pos").map(BlockPosition::a).map((blockposition) -> {
                return new GlobalPos(dimensionmanager, blockposition);
            });
        }).orElseThrow(() -> {
            return new IllegalArgumentException("Could not parse GlobalPos");
        });
    }

    public DimensionManager a() {
        return this.a;
    }

    public BlockPosition b() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            GlobalPos globalpos = (GlobalPos) object;

            return Objects.equals(this.a, globalpos.a) && Objects.equals(this.b, globalpos.b);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[] { this.a, this.b});
    }

    @Override
    public <T> T a(DynamicOps<T> dynamicops) {
        return dynamicops.createMap(ImmutableMap.of(dynamicops.createString("dimension"), this.a.a(dynamicops), dynamicops.createString("pos"), this.b.a(dynamicops)));
    }

    public String toString() {
        return this.a.toString() + " " + this.b;
    }
}
