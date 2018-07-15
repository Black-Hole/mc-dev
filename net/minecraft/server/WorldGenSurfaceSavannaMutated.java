package net.minecraft.server;

import java.util.Random;

public class WorldGenSurfaceSavannaMutated implements WorldGenSurface<WorldGenSurfaceConfigurationBase> {

    public WorldGenSurfaceSavannaMutated() {}

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, long i1, WorldGenSurfaceConfigurationBase worldgensurfaceconfigurationbase) {
        if (d0 > 1.75D) {
            BiomeBase.av.a(random, ichunkaccess, biomebase, i, j, k, d0, iblockdata, iblockdata1, l, i1, BiomeBase.ak);
        } else if (d0 > -0.5D) {
            BiomeBase.av.a(random, ichunkaccess, biomebase, i, j, k, d0, iblockdata, iblockdata1, l, i1, BiomeBase.am);
        } else {
            BiomeBase.av.a(random, ichunkaccess, biomebase, i, j, k, d0, iblockdata, iblockdata1, l, i1, BiomeBase.aj);
        }

    }
}
