package net.minecraft.server;

import java.util.List;

public interface ChunkGenerator {

    Chunk getOrCreateChunk(int i, int j);

    void recreateStructures(int i, int j);

    boolean a(Chunk chunk, int i, int j);

    List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, BlockPosition blockposition);

    BlockPosition findNearestMapFeature(World world, String s, BlockPosition blockposition);

    void recreateStructures(Chunk chunk, int i, int j);
}
