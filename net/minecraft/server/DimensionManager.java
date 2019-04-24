package net.minecraft.server;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.io.File;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class DimensionManager implements MinecraftSerializable {

    public static final DimensionManager OVERWORLD = a("overworld", new DimensionManager(1, "", "", WorldProviderNormal::new, true));
    public static final DimensionManager NETHER = a("the_nether", new DimensionManager(0, "_nether", "DIM-1", WorldProviderHell::new, false));
    public static final DimensionManager THE_END = a("the_end", new DimensionManager(2, "_end", "DIM1", WorldProviderTheEnd::new, false));
    private final int d;
    private final String e;
    private final String f;
    private final BiFunction<World, DimensionManager, ? extends WorldProvider> g;
    private final boolean h;

    private static DimensionManager a(String s, DimensionManager dimensionmanager) {
        return (DimensionManager) IRegistry.a(IRegistry.DIMENSION_TYPE, dimensionmanager.d, s, dimensionmanager);
    }

    public DimensionManager(int i, String s, String s1, BiFunction<World, DimensionManager, ? extends WorldProvider> bifunction, boolean flag) {
        this.d = i;
        this.e = s;
        this.f = s1;
        this.g = bifunction;
        this.h = flag;
    }

    public static DimensionManager a(Dynamic<?> dynamic) {
        return (DimensionManager) IRegistry.DIMENSION_TYPE.get(new MinecraftKey(dynamic.asString("")));
    }

    public static Iterable<DimensionManager> a() {
        return IRegistry.DIMENSION_TYPE;
    }

    public int getDimensionID() {
        return this.d + -1;
    }

    public String c() {
        return this.e;
    }

    public File a(File file) {
        return this.f.isEmpty() ? file : new File(file, this.f);
    }

    public WorldProvider getWorldProvider(World world) {
        return (WorldProvider) this.g.apply(world, this);
    }

    public String toString() {
        return a(this).toString();
    }

    @Nullable
    public static DimensionManager a(int i) {
        return (DimensionManager) IRegistry.DIMENSION_TYPE.fromId(i - -1);
    }

    @Nullable
    public static DimensionManager a(MinecraftKey minecraftkey) {
        return (DimensionManager) IRegistry.DIMENSION_TYPE.get(minecraftkey);
    }

    @Nullable
    public static MinecraftKey a(DimensionManager dimensionmanager) {
        return IRegistry.DIMENSION_TYPE.getKey(dimensionmanager);
    }

    public boolean hasSkyLight() {
        return this.h;
    }

    @Override
    public <T> T a(DynamicOps<T> dynamicops) {
        return dynamicops.createString(IRegistry.DIMENSION_TYPE.getKey(this).toString());
    }
}
