package net.minecraft.server;

import javax.annotation.Nullable;

public class BiomeStorage implements BiomeManager.Provider {

    private static final int d = (int) Math.round(Math.log(16.0D) / Math.log(2.0D)) - 2;
    private static final int e = (int) Math.round(Math.log(256.0D) / Math.log(2.0D)) - 2;
    public static final int a = 1 << BiomeStorage.d + BiomeStorage.d + BiomeStorage.e;
    public static final int b = (1 << BiomeStorage.d) - 1;
    public static final int c = (1 << BiomeStorage.e) - 1;
    private final BiomeBase[] f;

    public BiomeStorage(BiomeBase[] abiomebase) {
        this.f = abiomebase;
    }

    private BiomeStorage() {
        this(new BiomeBase[BiomeStorage.a]);
    }

    public BiomeStorage(PacketDataSerializer packetdataserializer) {
        this();

        for (int i = 0; i < this.f.length; ++i) {
            this.f[i] = (BiomeBase) IRegistry.BIOME.fromId(packetdataserializer.readInt());
        }

    }

    public BiomeStorage(ChunkCoordIntPair chunkcoordintpair, WorldChunkManager worldchunkmanager) {
        this();
        int i = chunkcoordintpair.d() >> 2;
        int j = chunkcoordintpair.e() >> 2;

        for (int k = 0; k < this.f.length; ++k) {
            int l = k & BiomeStorage.b;
            int i1 = k >> BiomeStorage.d + BiomeStorage.d & BiomeStorage.c;
            int j1 = k >> BiomeStorage.d & BiomeStorage.b;

            this.f[k] = worldchunkmanager.getBiome(i + l, i1, j + j1);
        }

    }

    public BiomeStorage(ChunkCoordIntPair chunkcoordintpair, WorldChunkManager worldchunkmanager, @Nullable int[] aint) {
        this();
        int i = chunkcoordintpair.d() >> 2;
        int j = chunkcoordintpair.e() >> 2;
        int k;
        int l;
        int i1;
        int j1;

        if (aint != null) {
            for (k = 0; k < aint.length; ++k) {
                this.f[k] = (BiomeBase) IRegistry.BIOME.fromId(aint[k]);
                if (this.f[k] == null) {
                    l = k & BiomeStorage.b;
                    i1 = k >> BiomeStorage.d + BiomeStorage.d & BiomeStorage.c;
                    j1 = k >> BiomeStorage.d & BiomeStorage.b;
                    this.f[k] = worldchunkmanager.getBiome(i + l, i1, j + j1);
                }
            }
        } else {
            for (k = 0; k < this.f.length; ++k) {
                l = k & BiomeStorage.b;
                i1 = k >> BiomeStorage.d + BiomeStorage.d & BiomeStorage.c;
                j1 = k >> BiomeStorage.d & BiomeStorage.b;
                this.f[k] = worldchunkmanager.getBiome(i + l, i1, j + j1);
            }
        }

    }

    public int[] a() {
        int[] aint = new int[this.f.length];

        for (int i = 0; i < this.f.length; ++i) {
            aint[i] = IRegistry.BIOME.a((Object) this.f[i]);
        }

        return aint;
    }

    public void a(PacketDataSerializer packetdataserializer) {
        BiomeBase[] abiomebase = this.f;
        int i = abiomebase.length;

        for (int j = 0; j < i; ++j) {
            BiomeBase biomebase = abiomebase[j];

            packetdataserializer.writeInt(IRegistry.BIOME.a((Object) biomebase));
        }

    }

    public BiomeStorage b() {
        return new BiomeStorage((BiomeBase[]) this.f.clone());
    }

    @Override
    public BiomeBase getBiome(int i, int j, int k) {
        int l = i & BiomeStorage.b;
        int i1 = MathHelper.clamp(j, 0, BiomeStorage.c);
        int j1 = k & BiomeStorage.b;

        return this.f[i1 << BiomeStorage.d + BiomeStorage.d | j1 << BiomeStorage.d | l];
    }
}
