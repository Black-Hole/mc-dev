package net.minecraft.server;

public interface IChunkProvider {

    Chunk getLoadedChunkAt(int i, int j);

    Chunk getChunkAt(int i, int j);

    boolean unloadChunks();

    String getName();
}
