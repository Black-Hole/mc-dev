package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider {

    private static final Logger a = LogManager.getLogger();
    private final Set<Long> unloadQueue = Collections.newSetFromMap(new ConcurrentHashMap());
    private final ChunkGenerator chunkGenerator;
    private final IChunkLoader chunkLoader;
    private final LongHashMap<Chunk> chunks = new LongHashMap();
    private final List<Chunk> chunkList = Lists.newArrayList();
    public final WorldServer world;

    public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, ChunkGenerator chunkgenerator) {
        this.world = worldserver;
        this.chunkLoader = ichunkloader;
        this.chunkGenerator = chunkgenerator;
    }

    public List<Chunk> a() {
        return this.chunkList;
    }

    public void queueUnload(int i, int j) {
        if (this.world.worldProvider.c(i, j)) {
            this.unloadQueue.add(Long.valueOf(ChunkCoordIntPair.a(i, j)));
        }

    }

    public void b() {
        Iterator iterator = this.chunkList.iterator();

        while (iterator.hasNext()) {
            Chunk chunk = (Chunk) iterator.next();

            this.queueUnload(chunk.locX, chunk.locZ);
        }

    }

    public Chunk getLoadedChunkAt(int i, int j) {
        long k = ChunkCoordIntPair.a(i, j);
        Chunk chunk = (Chunk) this.chunks.getEntry(k);

        this.unloadQueue.remove(Long.valueOf(k));
        return chunk;
    }

    public Chunk getOrLoadChunkAt(int i, int j) {
        Chunk chunk = this.getLoadedChunkAt(i, j);

        if (chunk == null) {
            chunk = this.loadChunk(i, j);
            if (chunk != null) {
                this.chunks.put(ChunkCoordIntPair.a(i, j), chunk);
                this.chunkList.add(chunk);
                chunk.addEntities();
                chunk.loadNearby(this, this.chunkGenerator);
            }
        }

        return chunk;
    }

    public Chunk getChunkAt(int i, int j) {
        Chunk chunk = this.getOrLoadChunkAt(i, j);

        if (chunk == null) {
            long k = ChunkCoordIntPair.a(i, j);

            chunk = this.loadChunk(i, j);
            if (chunk == null) {
                try {
                    chunk = this.chunkGenerator.getOrCreateChunk(i, j);
                } catch (Throwable throwable) {
                    CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
                    CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");

                    crashreportsystemdetails.a("Location", (Object) String.format("%d,%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j)}));
                    crashreportsystemdetails.a("Position hash", (Object) Long.valueOf(k));
                    crashreportsystemdetails.a("Generator", (Object) this.chunkGenerator);
                    throw new ReportedException(crashreport);
                }
            }

            this.chunks.put(k, chunk);
            this.chunkList.add(chunk);
            chunk.addEntities();
            chunk.loadNearby(this, this.chunkGenerator);
        }

        return chunk;
    }

    public Chunk loadChunk(int i, int j) {
        try {
            Chunk chunk = this.chunkLoader.a(this.world, i, j);

            if (chunk != null) {
                chunk.setLastSaved(this.world.getTime());
                this.chunkGenerator.recreateStructures(chunk, i, j);
            }

            return chunk;
        } catch (Exception exception) {
            ChunkProviderServer.a.error("Couldn\'t load chunk", exception);
            return null;
        }
    }

    public void saveChunkNOP(Chunk chunk) {
        try {
            this.chunkLoader.b(this.world, chunk);
        } catch (Exception exception) {
            ChunkProviderServer.a.error("Couldn\'t save entities", exception);
        }

    }

    public void saveChunk(Chunk chunk) {
        try {
            chunk.setLastSaved(this.world.getTime());
            this.chunkLoader.a(this.world, chunk);
        } catch (IOException ioexception) {
            ChunkProviderServer.a.error("Couldn\'t save chunk", ioexception);
        } catch (ExceptionWorldConflict exceptionworldconflict) {
            ChunkProviderServer.a.error("Couldn\'t save chunk; already in use by another instance of Minecraft?", exceptionworldconflict);
        }

    }

    public boolean a(boolean flag) {
        int i = 0;
        ArrayList arraylist = Lists.newArrayList(this.chunkList);

        for (int j = 0; j < arraylist.size(); ++j) {
            Chunk chunk = (Chunk) arraylist.get(j);

            if (flag) {
                this.saveChunkNOP(chunk);
            }

            if (chunk.a(flag)) {
                this.saveChunk(chunk);
                chunk.f(false);
                ++i;
                if (i == 24 && !flag) {
                    return false;
                }
            }
        }

        return true;
    }

    public void c() {
        this.chunkLoader.b();
    }

    public boolean unloadChunks() {
        if (!this.world.savingDisabled) {
            for (int i = 0; i < 100; ++i) {
                if (!this.unloadQueue.isEmpty()) {
                    Long olong = (Long) this.unloadQueue.iterator().next();
                    Chunk chunk = (Chunk) this.chunks.getEntry(olong.longValue());

                    if (chunk != null) {
                        chunk.removeEntities();
                        this.saveChunk(chunk);
                        this.saveChunkNOP(chunk);
                        this.chunks.remove(olong.longValue());
                        this.chunkList.remove(chunk);
                    }

                    this.unloadQueue.remove(olong);
                }
            }

            this.chunkLoader.a();
        }

        return false;
    }

    public boolean e() {
        return !this.world.savingDisabled;
    }

    public String getName() {
        return "ServerChunkCache: " + this.chunks.count() + " Drop: " + this.unloadQueue.size();
    }

    public List<BiomeBase.BiomeMeta> a(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        return this.chunkGenerator.getMobsFor(enumcreaturetype, blockposition);
    }

    public BlockPosition a(World world, String s, BlockPosition blockposition) {
        return this.chunkGenerator.findNearestMapFeature(world, s, blockposition);
    }

    public int g() {
        return this.chunks.count();
    }

    public boolean e(int i, int j) {
        return this.chunks.contains(ChunkCoordIntPair.a(i, j));
    }
}
