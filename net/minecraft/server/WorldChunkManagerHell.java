package net.minecraft.server;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldChunkManagerHell extends WorldChunkManager {

    private final BiomeBase a;

    public WorldChunkManagerHell(BiomeBase biomebase) {
        this.a = biomebase;
    }

    public BiomeBase getBiome(BlockPosition blockposition) {
        return this.a;
    }

    public BiomeBase[] getBiomes(BiomeBase[] abiomebase, int i, int j, int k, int l) {
        if (abiomebase == null || abiomebase.length < k * l) {
            abiomebase = new BiomeBase[k * l];
        }

        Arrays.fill(abiomebase, 0, k * l, this.a);
        return abiomebase;
    }

    public BiomeBase[] getBiomeBlock(BiomeBase[] abiomebase, int i, int j, int k, int l) {
        if (abiomebase == null || abiomebase.length < k * l) {
            abiomebase = new BiomeBase[k * l];
        }

        Arrays.fill(abiomebase, 0, k * l, this.a);
        return abiomebase;
    }

    public BiomeBase[] a(BiomeBase[] abiomebase, int i, int j, int k, int l, boolean flag) {
        return this.getBiomeBlock(abiomebase, i, j, k, l);
    }

    public BlockPosition a(int i, int j, int k, List<BiomeBase> list, Random random) {
        return list.contains(this.a) ? new BlockPosition(i - k + random.nextInt(k * 2 + 1), 0, j - k + random.nextInt(k * 2 + 1)) : null;
    }

    public boolean a(int i, int j, int k, List<BiomeBase> list) {
        return list.contains(this.a);
    }
}
