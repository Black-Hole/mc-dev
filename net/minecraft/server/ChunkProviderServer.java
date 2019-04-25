package net.minecraft.server;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class ChunkProviderServer extends IChunkProvider {

    private static final int b = (int) Math.pow(17.0D, 2.0D);
    private static final List<ChunkStatus> c = ChunkStatus.a();
    private final ChunkMapDistance chunkMapDistance;
    public final ChunkGenerator<?> chunkGenerator;
    private final WorldServer world;
    private final Thread serverThread;
    private final LightEngineThreaded lightEngine;
    private final ChunkProviderServer.a serverThreadQueue;
    public final PlayerChunkMap playerChunkMap;
    private final WorldPersistentData worldPersistentData;
    private long lastTickTime;
    public boolean allowMonsters = true;
    public boolean allowAnimals = true;

    public ChunkProviderServer(WorldServer worldserver, File file, DataFixer datafixer, DefinedStructureManager definedstructuremanager, Executor executor, ChunkGenerator<?> chunkgenerator, int i, int j, WorldLoadListener worldloadlistener, Supplier<WorldPersistentData> supplier) {
        this.world = worldserver;
        this.serverThreadQueue = new ChunkProviderServer.a(worldserver);
        this.chunkGenerator = chunkgenerator;
        this.serverThread = Thread.currentThread();
        File file1 = worldserver.getWorldProvider().getDimensionManager().a(file);
        File file2 = new File(file1, "data");

        file2.mkdirs();
        this.worldPersistentData = new WorldPersistentData(file2, datafixer);
        this.playerChunkMap = new PlayerChunkMap(worldserver, file, datafixer, definedstructuremanager, executor, this.serverThreadQueue, this, this.getChunkGenerator(), worldloadlistener, supplier, i, j);
        this.lightEngine = this.playerChunkMap.a();
        this.chunkMapDistance = this.playerChunkMap.e();
    }

    @Override
    public LightEngineThreaded getLightEngine() {
        return this.lightEngine;
    }

    @Nullable
    private PlayerChunk getChunk(long i) {
        return this.playerChunkMap.getVisibleChunk(i);
    }

    public int b() {
        return this.playerChunkMap.c();
    }

    @Nullable
    @Override
    public IChunkAccess getChunkAt(int i, int j, ChunkStatus chunkstatus, boolean flag) {
        CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.getChunkFuture(i, j, chunkstatus, flag);

        return (IChunkAccess) ((Either) completablefuture.join()).map((ichunkaccess) -> {
            return ichunkaccess;
        }, (playerchunk_failure) -> {
            if (flag) {
                throw new IllegalStateException("Chunk not there when requested: " + playerchunk_failure);
            } else {
                return null;
            }
        });
    }

    public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> getChunkFuture(int i, int j, ChunkStatus chunkstatus, boolean flag) {
        boolean flag1 = Thread.currentThread() == this.serverThread;
        CompletableFuture completablefuture;

        if (flag1) {
            completablefuture = this.getChunkFutureMainThread(i, j, chunkstatus, flag);
            this.serverThreadQueue.awaitTasks(completablefuture::isDone);
        } else {
            completablefuture = CompletableFuture.supplyAsync(() -> {
                return this.getChunkFutureMainThread(i, j, chunkstatus, flag);
            }, this.serverThreadQueue).thenCompose((completablefuture1) -> {
                return completablefuture1;
            });
        }

        return completablefuture;
    }

    private CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> getChunkFutureMainThread(int i, int j, ChunkStatus chunkstatus, boolean flag) {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
        long k = chunkcoordintpair.pair();
        int l = 33 + ChunkStatus.a(chunkstatus);
        PlayerChunk playerchunk = this.getChunk(k);

        if (flag) {
            this.chunkMapDistance.a(TicketType.UNKNOWN, chunkcoordintpair, l, chunkcoordintpair);
            if (this.a(playerchunk, l)) {
                this.tickDistanceManager();
                playerchunk = this.getChunk(k);
                if (this.a(playerchunk, l)) {
                    throw new IllegalStateException("No chunk holder after ticket has been added");
                }
            }
        }

        return this.a(playerchunk, l) ? PlayerChunk.UNLOADED_CHUNK_ACCESS_FUTURE : playerchunk.b(chunkstatus);
    }

    private boolean a(@Nullable PlayerChunk playerchunk, int i) {
        return playerchunk == null || playerchunk.i() > i;
    }

    public boolean isLoaded(int i, int j) {
        PlayerChunk playerchunk = this.getChunk((new ChunkCoordIntPair(i, j)).pair());
        int k = 33 + ChunkStatus.a(ChunkStatus.FULL);

        return playerchunk != null && playerchunk.i() <= k ? ((Either) playerchunk.b(ChunkStatus.FULL).getNow(PlayerChunk.UNLOADED_CHUNK_ACCESS)).left().isPresent() : false;
    }

    @Override
    public IBlockAccess b(int i, int j) {
        long k = ChunkCoordIntPair.pair(i, j);
        PlayerChunk playerchunk = this.getChunk(k);

        if (playerchunk == null) {
            return null;
        } else {
            int l = ChunkProviderServer.c.size() - 1;

            while (true) {
                ChunkStatus chunkstatus = (ChunkStatus) ChunkProviderServer.c.get(l);
                Optional<IChunkAccess> optional = ((Either) playerchunk.a(chunkstatus).getNow(PlayerChunk.UNLOADED_CHUNK_ACCESS)).left();

                if (optional.isPresent()) {
                    return (IBlockAccess) optional.get();
                }

                if (chunkstatus == ChunkStatus.LIGHT.e()) {
                    return null;
                }

                --l;
            }
        }
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    public boolean runTasks() {
        return this.serverThreadQueue.executeNext();
    }

    private boolean tickDistanceManager() {
        if (this.chunkMapDistance.a(this.playerChunkMap)) {
            this.playerChunkMap.b();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean a(Entity entity) {
        PlayerChunk playerchunk = this.getChunk(ChunkCoordIntPair.pair(MathHelper.floor(entity.locX) >> 4, MathHelper.floor(entity.locZ) >> 4));

        if (playerchunk == null) {
            return false;
        } else {
            Either<Chunk, PlayerChunk.Failure> either = (Either) playerchunk.b().getNow(PlayerChunk.UNLOADED_CHUNK);

            return either.left().isPresent();
        }
    }

    public void save(boolean flag) {
        this.playerChunkMap.save(flag);
    }

    @Override
    public void close() throws IOException {
        this.lightEngine.close();
        this.playerChunkMap.close();
    }

    public void tick(BooleanSupplier booleansupplier) {
        this.world.getMethodProfiler().enter("purge");
        this.chunkMapDistance.purgeTickets();
        this.tickDistanceManager();
        this.world.getMethodProfiler().exitEnter("chunks");
        this.tickChunks();
        this.world.getMethodProfiler().exitEnter("unload");
        this.playerChunkMap.unloadChunks(booleansupplier);
        this.world.getMethodProfiler().exit();
    }

    private void tickChunks() {
        long i = this.world.getTime();
        long j = i - this.lastTickTime;

        this.lastTickTime = i;
        WorldData worlddata = this.world.getWorldData();
        boolean flag = worlddata.getType() == WorldType.DEBUG_ALL_BLOCK_STATES;
        boolean flag1 = this.world.getGameRules().getBoolean("doMobSpawning");

        if (!flag) {
            this.world.getMethodProfiler().enter("pollingChunks");
            int k = this.chunkMapDistance.b();
            int l = this.world.getGameRules().c("randomTickSpeed");
            BlockPosition blockposition = this.world.getSpawn();
            boolean flag2 = worlddata.getTime() % 400L == 0L;
            EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
            Object2IntMap<EnumCreatureType> object2intmap = this.world.l();
            ObjectBidirectionalIterator objectbidirectionaliterator = this.playerChunkMap.f();

            while (objectbidirectionaliterator.hasNext()) {
                Entry<PlayerChunk> entry = (Entry) objectbidirectionaliterator.next();
                PlayerChunk playerchunk = (PlayerChunk) entry.getValue();
                Optional<Chunk> optional = ((Either) playerchunk.b().getNow(PlayerChunk.UNLOADED_CHUNK)).left();

                if (optional.isPresent()) {
                    Chunk chunk = (Chunk) optional.get();

                    playerchunk.a(chunk);
                    ChunkCoordIntPair chunkcoordintpair = playerchunk.h();

                    if (!this.playerChunkMap.d(chunkcoordintpair)) {
                        chunk.b(chunk.q() + j);
                        if (flag1 && (this.allowMonsters || this.allowAnimals) && this.world.getWorldBorder().isInBounds(chunk.getPos())) {
                            this.world.getMethodProfiler().enter("spawner");
                            EnumCreatureType[] aenumcreaturetype1 = aenumcreaturetype;
                            int i1 = aenumcreaturetype.length;

                            for (int j1 = 0; j1 < i1; ++j1) {
                                EnumCreatureType enumcreaturetype = aenumcreaturetype1[j1];

                                if (enumcreaturetype != EnumCreatureType.MISC && (!enumcreaturetype.c() || this.allowAnimals) && (enumcreaturetype.c() || this.allowMonsters) && (!enumcreaturetype.d() || flag2)) {
                                    int k1 = enumcreaturetype.b() * k / ChunkProviderServer.b;

                                    if (object2intmap.getInt(enumcreaturetype) <= k1) {
                                        SpawnerCreature.a(enumcreaturetype, (World) this.world, chunk, blockposition);
                                    }
                                }
                            }

                            this.world.getMethodProfiler().exit();
                        }

                        this.world.a(chunk, l);
                    }
                }
            }

            this.world.getMethodProfiler().exit();
            if (flag1) {
                this.chunkGenerator.doMobSpawning(this.world, this.allowMonsters, this.allowAnimals);
            }
        }

        this.playerChunkMap.g();
    }

    @Override
    public String getName() {
        return "ServerChunkCache: " + this.g();
    }

    @Override
    public ChunkGenerator<?> getChunkGenerator() {
        return this.chunkGenerator;
    }

    public int g() {
        return this.playerChunkMap.d();
    }

    public void flagDirty(BlockPosition blockposition) {
        int i = blockposition.getX() >> 4;
        int j = blockposition.getZ() >> 4;
        PlayerChunk playerchunk = this.getChunk(ChunkCoordIntPair.pair(i, j));

        if (playerchunk != null) {
            playerchunk.a(blockposition.getX() & 15, blockposition.getY(), blockposition.getZ() & 15);
        }

    }

    @Override
    public void a(EnumSkyBlock enumskyblock, SectionPosition sectionposition) {
        this.serverThreadQueue.execute(() -> {
            PlayerChunk playerchunk = this.getChunk(sectionposition.u().pair());

            if (playerchunk != null) {
                playerchunk.a(enumskyblock, sectionposition.b());
            }

        });
    }

    public <T> void addTicket(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
        this.chunkMapDistance.addTicket(tickettype, chunkcoordintpair, i, t0);
    }

    public <T> void removeTicket(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
        this.chunkMapDistance.removeTicket(tickettype, chunkcoordintpair, i, t0);
    }

    @Override
    public void a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
        this.chunkMapDistance.a(chunkcoordintpair, flag);
    }

    public void movePlayer(EntityPlayer entityplayer) {
        this.playerChunkMap.movePlayer(entityplayer);
    }

    public void removeEntity(Entity entity) {
        this.playerChunkMap.removeEntity(entity);
    }

    public void addEntity(Entity entity) {
        this.playerChunkMap.addEntity(entity);
    }

    public void broadcastIncludingSelf(Entity entity, Packet<?> packet) {
        this.playerChunkMap.broadcastIncludingSelf(entity, packet);
    }

    public void broadcast(Entity entity, Packet<?> packet) {
        this.playerChunkMap.broadcast(entity, packet);
    }

    public void setViewDistance(int i, int j) {
        this.playerChunkMap.setViewDistance(i, j);
    }

    @Override
    public void a(boolean flag, boolean flag1) {
        this.allowMonsters = flag;
        this.allowAnimals = flag1;
    }

    public WorldPersistentData getWorldPersistentData() {
        return this.worldPersistentData;
    }

    public VillagePlace i() {
        return this.playerChunkMap.h();
    }

    final class a extends IAsyncTaskHandler<Runnable> {

        private a(World world) {
            super("Chunk source main thread executor for " + IRegistry.DIMENSION_TYPE.getKey(world.getWorldProvider().getDimensionManager()));
        }

        @Override
        protected Runnable postToMainThread(Runnable runnable) {
            return runnable;
        }

        @Override
        protected boolean canExecute(Runnable runnable) {
            return true;
        }

        @Override
        protected boolean isNotMainThread() {
            return true;
        }

        @Override
        protected Thread getThread() {
            return ChunkProviderServer.this.serverThread;
        }

        @Override
        protected boolean executeNext() {
            if (ChunkProviderServer.this.tickDistanceManager()) {
                return true;
            } else {
                ChunkProviderServer.this.lightEngine.queueUpdate();
                return super.executeNext();
            }
        }
    }
}
