package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerChunkMap extends IChunkLoader implements PlayerChunk.d {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final int GOLDEN_TICKET = 33 + ChunkStatus.b();
    public final Long2ObjectLinkedOpenHashMap<PlayerChunk> updatingChunks = new Long2ObjectLinkedOpenHashMap();
    public volatile Long2ObjectLinkedOpenHashMap<PlayerChunk> visibleChunks;
    private final Long2ObjectLinkedOpenHashMap<PlayerChunk> g;
    private final LongSet h;
    public final WorldServer world;
    private final LightEngineThreaded lightEngine;
    private final IAsyncTaskHandler<Runnable> executor;
    public final ChunkGenerator<?> chunkGenerator;
    private final Supplier<WorldPersistentData> m;
    private final VillagePlace n;
    public final LongSet unloadQueue;
    private boolean updatingChunksModified;
    private final ChunkTaskQueueSorter q;
    private final Mailbox<ChunkTaskQueueSorter.a<Runnable>> mailboxWorldGen;
    private final Mailbox<ChunkTaskQueueSorter.a<Runnable>> mailboxMain;
    public final WorldLoadListener worldLoadListener;
    private final PlayerChunkMap.a u;
    private final AtomicInteger v;
    private final DefinedStructureManager definedStructureManager;
    private final File x;
    private final PlayerMap y;
    public final Int2ObjectMap<PlayerChunkMap.EntityTracker> trackedEntities;
    private int A;
    private int B;

    public PlayerChunkMap(WorldServer worldserver, File file, DataFixer datafixer, DefinedStructureManager definedstructuremanager, Executor executor, IAsyncTaskHandler<Runnable> iasynctaskhandler, ILightAccess ilightaccess, ChunkGenerator<?> chunkgenerator, WorldLoadListener worldloadlistener, Supplier<WorldPersistentData> supplier, int i, int j) {
        super(new File(worldserver.getWorldProvider().getDimensionManager().a(file), "region"), datafixer);
        this.visibleChunks = this.updatingChunks.clone();
        this.g = new Long2ObjectLinkedOpenHashMap();
        this.h = new LongOpenHashSet();
        this.unloadQueue = new LongOpenHashSet();
        this.v = new AtomicInteger();
        this.y = new PlayerMap();
        this.trackedEntities = new Int2ObjectOpenHashMap();
        this.definedStructureManager = definedstructuremanager;
        this.x = worldserver.getWorldProvider().getDimensionManager().a(file);
        this.world = worldserver;
        this.chunkGenerator = chunkgenerator;
        this.executor = iasynctaskhandler;
        ThreadedMailbox<Runnable> threadedmailbox = ThreadedMailbox.a(executor, "worldgen");
        ThreadedMailbox<Runnable> threadedmailbox1 = ThreadedMailbox.a((Executor) iasynctaskhandler, "main");

        this.worldLoadListener = worldloadlistener;
        ThreadedMailbox<Runnable> threadedmailbox2 = ThreadedMailbox.a(executor, "light");

        this.q = new ChunkTaskQueueSorter(ImmutableList.of(threadedmailbox, threadedmailbox1, threadedmailbox2), executor, Integer.MAX_VALUE);
        this.mailboxWorldGen = this.q.a(threadedmailbox, false);
        this.mailboxMain = this.q.a(threadedmailbox1, false);
        this.lightEngine = new LightEngineThreaded(ilightaccess, this, this.world.getWorldProvider().g(), threadedmailbox2, this.q.a(threadedmailbox2, false));
        this.u = new PlayerChunkMap.a(executor, iasynctaskhandler);
        this.m = supplier;
        this.n = new VillagePlace(new File(this.x, "poi"), datafixer);
        this.setViewDistance(i, j);
    }

    private static double a(ChunkCoordIntPair chunkcoordintpair, Entity entity) {
        double d0 = (double) (chunkcoordintpair.x * 16 + 8);
        double d1 = (double) (chunkcoordintpair.z * 16 + 8);
        double d2 = d0 - entity.locX;
        double d3 = d1 - entity.locZ;

        return d2 * d2 + d3 * d3;
    }

    private static int b(ChunkCoordIntPair chunkcoordintpair, EntityPlayer entityplayer, boolean flag) {
        int i;
        int j;

        if (flag) {
            SectionPosition sectionposition = entityplayer.M();

            i = sectionposition.a();
            j = sectionposition.c();
        } else {
            i = MathHelper.floor(entityplayer.locX / 16.0D);
            j = MathHelper.floor(entityplayer.locZ / 16.0D);
        }

        return a(chunkcoordintpair, i, j);
    }

    private static int a(ChunkCoordIntPair chunkcoordintpair, int i, int j) {
        int k = chunkcoordintpair.x - i;
        int l = chunkcoordintpair.z - j;

        return Math.max(Math.abs(k), Math.abs(l));
    }

    protected LightEngineThreaded a() {
        return this.lightEngine;
    }

    @Nullable
    protected PlayerChunk getUpdatingChunk(long i) {
        return (PlayerChunk) this.updatingChunks.get(i);
    }

    @Nullable
    protected PlayerChunk getVisibleChunk(long i) {
        return (PlayerChunk) this.visibleChunks.get(i);
    }

    protected IntSupplier c(long i) {
        return () -> {
            PlayerChunk playerchunk = this.getVisibleChunk(i);

            return playerchunk == null ? ChunkTaskQueue.a - 1 : Math.min(playerchunk.j(), ChunkTaskQueue.a - 1);
        };
    }

    private CompletableFuture<Either<List<IChunkAccess>, PlayerChunk.Failure>> a(ChunkCoordIntPair chunkcoordintpair, int i, IntFunction<ChunkStatus> intfunction) {
        List<CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> list = Lists.newArrayList();
        int j = chunkcoordintpair.x;
        int k = chunkcoordintpair.z;

        for (int l = -i; l <= i; ++l) {
            for (int i1 = -i; i1 <= i; ++i1) {
                int j1 = Math.max(Math.abs(i1), Math.abs(l));
                final ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(j + i1, k + l);
                long k1 = chunkcoordintpair1.pair();
                PlayerChunk playerchunk = this.getUpdatingChunk(k1);

                if (playerchunk == null) {
                    return CompletableFuture.completedFuture(Either.right(new PlayerChunk.Failure() {
                        public String toString() {
                            return "Unloaded " + chunkcoordintpair1.toString();
                        }
                    }));
                }

                ChunkStatus chunkstatus = (ChunkStatus) intfunction.apply(j1);
                CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = playerchunk.a(chunkstatus, this);

                list.add(completablefuture);
            }
        }

        CompletableFuture<List<Either<IChunkAccess, PlayerChunk.Failure>>> completablefuture1 = SystemUtils.b(list);

        return completablefuture1.thenApply((list1) -> {
            List<IChunkAccess> list2 = Lists.newArrayList();
            final int l1 = 0;

            for (Iterator iterator = list1.iterator(); iterator.hasNext(); ++l1) {
                final Either<IChunkAccess, PlayerChunk.Failure> either = (Either) iterator.next();
                Optional<IChunkAccess> optional = either.left();

                if (!optional.isPresent()) {
                    return Either.right(new PlayerChunk.Failure() {
                        public String toString() {
                            return "Unloaded " + new ChunkCoordIntPair(j + l1 % (i * 2 + 1), k + l1 / (i * 2 + 1)) + " " + ((PlayerChunk.Failure) either.right().get()).toString();
                        }
                    });
                }

                list2.add(optional.get());
            }

            return Either.left(list2);
        });
    }

    public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> b(ChunkCoordIntPair chunkcoordintpair) {
        return this.a(chunkcoordintpair, 2, (i) -> {
            return ChunkStatus.FULL;
        }).thenApplyAsync((either) -> {
            return either.mapLeft((list) -> {
                return (Chunk) list.get(list.size() / 2);
            });
        }, this.executor);
    }

    @Nullable
    private PlayerChunk a(long i, int j, @Nullable PlayerChunk playerchunk, int k) {
        if (k > PlayerChunkMap.GOLDEN_TICKET && j > PlayerChunkMap.GOLDEN_TICKET) {
            return playerchunk;
        } else {
            if (playerchunk != null) {
                playerchunk.a(j);
            }

            if (playerchunk != null) {
                if (j > PlayerChunkMap.GOLDEN_TICKET) {
                    this.unloadQueue.add(i);
                } else {
                    this.unloadQueue.remove(i);
                }
            }

            if (j <= PlayerChunkMap.GOLDEN_TICKET && playerchunk == null) {
                playerchunk = (PlayerChunk) this.g.remove(i);
                if (playerchunk != null) {
                    playerchunk.a(j);
                } else {
                    playerchunk = new PlayerChunk(new ChunkCoordIntPair(i), j, this.lightEngine, this.q, this);
                }

                this.updatingChunks.put(i, playerchunk);
                this.updatingChunksModified = true;
            }

            return playerchunk;
        }
    }

    @Override
    public void close() throws IOException {
        this.q.close();
        this.save(true);
        this.n.close();
        super.close();
    }

    protected void save(boolean flag) {
        this.visibleChunks.values().stream().filter(PlayerChunk::k).peek(PlayerChunk::l).map((playerchunk) -> {
            return playerchunk.a(ChunkStatus.FULL);
        }).forEach((completablefuture) -> {
            if (flag) {
                this.executor.awaitTasks(completablefuture::isDone);
                ((Either) completablefuture.join()).ifLeft(this::saveChunk);
            } else {
                ((Either) completablefuture.getNow(PlayerChunk.UNLOADED_CHUNK_ACCESS)).ifLeft(this::saveChunk);
            }

        });
        if (flag) {
            PlayerChunkMap.LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", this.x.getName());
        }

    }

    protected void unloadChunks(BooleanSupplier booleansupplier) {
        GameProfilerFiller gameprofilerfiller = this.world.getMethodProfiler();

        gameprofilerfiller.enter("poi");
        this.n.a(booleansupplier);
        gameprofilerfiller.exitEnter("chunk_unload");
        if (!this.world.isSavingDisabled()) {
            LongIterator longiterator = this.unloadQueue.iterator();

            for (int i = 0; longiterator.hasNext() && (booleansupplier.getAsBoolean() || i < 200 || this.unloadQueue.size() > 2000); longiterator.remove()) {
                long j = longiterator.nextLong();
                PlayerChunk playerchunk = (PlayerChunk) this.updatingChunks.remove(j);

                if (playerchunk != null) {
                    this.g.put(j, playerchunk);
                    this.updatingChunksModified = true;
                    ++i;
                    this.a(j, playerchunk);
                }
            }
        }

        gameprofilerfiller.exit();
    }

    private void a(long i, PlayerChunk playerchunk) {
        CompletableFuture<IChunkAccess> completablefuture = playerchunk.getChunkSave();

        completablefuture.thenAcceptAsync((ichunkaccess) -> {
            CompletableFuture<IChunkAccess> completablefuture1 = playerchunk.getChunkSave();

            if (completablefuture1 != completablefuture) {
                this.a(i, playerchunk);
            } else {
                if (this.g.remove(i, playerchunk) && ichunkaccess != null) {
                    this.saveChunk(ichunkaccess);
                    if (this.h.remove(i) && ichunkaccess instanceof Chunk) {
                        Chunk chunk = (Chunk) ichunkaccess;

                        chunk.c(false);
                        this.world.unloadChunk(chunk);
                    }

                    this.lightEngine.a(ichunkaccess.getPos());
                    this.lightEngine.queueUpdate();
                    this.worldLoadListener.a(ichunkaccess.getPos(), (ChunkStatus) null);
                }

            }
        }, this.executor);
    }

    protected void b() {
        if (this.updatingChunksModified) {
            this.visibleChunks = this.updatingChunks.clone();
            this.updatingChunksModified = false;
        }
    }

    public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(PlayerChunk playerchunk, ChunkStatus chunkstatus) {
        ChunkCoordIntPair chunkcoordintpair = playerchunk.h();

        if (chunkstatus == ChunkStatus.EMPTY) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    NBTTagCompound nbttagcompound = this.f(chunkcoordintpair);

                    if (nbttagcompound != null) {
                        boolean flag = nbttagcompound.hasKeyOfType("Level", 10) && nbttagcompound.getCompound("Level").hasKeyOfType("Status", 8);

                        if (flag) {
                            ProtoChunk protochunk = ChunkRegionLoader.loadChunk(this.world, this.definedStructureManager, this.n, chunkcoordintpair, nbttagcompound);

                            protochunk.setLastSaved(this.world.getTime());
                            return Either.left(protochunk);
                        }

                        PlayerChunkMap.LOGGER.error("Chunk file at {} is missing level data, skipping", chunkcoordintpair);
                    }
                } catch (ReportedException reportedexception) {
                    Throwable throwable = reportedexception.getCause();

                    if (!(throwable instanceof IOException)) {
                        throw reportedexception;
                    }

                    PlayerChunkMap.LOGGER.error("Couldn't load chunk {}", chunkcoordintpair, throwable);
                } catch (Exception exception) {
                    PlayerChunkMap.LOGGER.error("Couldn't load chunk {}", chunkcoordintpair, exception);
                }

                return Either.left(new ProtoChunk(chunkcoordintpair, ChunkConverter.a));
            }, this.executor);
        } else {
            if (chunkstatus == ChunkStatus.LIGHT) {
                this.u.a(TicketType.LIGHT, chunkcoordintpair, 33 + ChunkStatus.a(ChunkStatus.FEATURES), chunkcoordintpair);
            }

            CompletableFuture<Either<List<IChunkAccess>, PlayerChunk.Failure>> completablefuture = this.a(chunkcoordintpair, chunkstatus.f(), (i) -> {
                return this.a(chunkstatus, i);
            });

            return completablefuture.thenComposeAsync((either) -> {
                return (CompletableFuture) either.map((list) -> {
                    try {
                        CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture1 = chunkstatus.a(this.world, this.chunkGenerator, this.definedStructureManager, this.lightEngine, (ichunkaccess) -> {
                            return this.b(playerchunk);
                        }, list);

                        this.worldLoadListener.a(chunkcoordintpair, chunkstatus);
                        return completablefuture1;
                    } catch (Exception exception) {
                        CrashReport crashreport = CrashReport.a(exception, "Exception generating new chunk");
                        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");

                        crashreportsystemdetails.a("Location", (Object) String.format("%d,%d", chunkcoordintpair.x, chunkcoordintpair.z));
                        crashreportsystemdetails.a("Position hash", (Object) ChunkCoordIntPair.pair(chunkcoordintpair.x, chunkcoordintpair.z));
                        crashreportsystemdetails.a("Generator", (Object) this.chunkGenerator);
                        throw new ReportedException(crashreport);
                    }
                }, (playerchunk_failure) -> {
                    this.c(chunkcoordintpair);
                    return CompletableFuture.completedFuture(Either.right(playerchunk_failure));
                });
            }, (runnable) -> {
                this.mailboxWorldGen.a((Object) ChunkTaskQueueSorter.a(playerchunk, runnable));
            });
        }
    }

    protected void c(ChunkCoordIntPair chunkcoordintpair) {
        this.executor.a(SystemUtils.a(() -> {
            this.u.b(TicketType.LIGHT, chunkcoordintpair, 33 + ChunkStatus.a(ChunkStatus.FEATURES), chunkcoordintpair);
        }, () -> {
            return "release light ticket " + chunkcoordintpair;
        }));
    }

    private ChunkStatus a(ChunkStatus chunkstatus, int i) {
        ChunkStatus chunkstatus1;

        if (i == 0) {
            chunkstatus1 = chunkstatus.e();
        } else {
            chunkstatus1 = ChunkStatus.a(ChunkStatus.a(chunkstatus) + i);
        }

        return chunkstatus1;
    }

    private CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> b(PlayerChunk playerchunk) {
        CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = playerchunk.a(ChunkStatus.FULL.e());

        return completablefuture.thenApplyAsync((either) -> {
            ChunkStatus chunkstatus = PlayerChunk.b(playerchunk.i());

            return !chunkstatus.b(ChunkStatus.FULL) ? PlayerChunk.UNLOADED_CHUNK_ACCESS : either.mapLeft((ichunkaccess) -> {
                ChunkCoordIntPair chunkcoordintpair = playerchunk.h();
                Chunk chunk;

                if (ichunkaccess instanceof ProtoChunkExtension) {
                    chunk = ((ProtoChunkExtension) ichunkaccess).u();
                } else {
                    chunk = new Chunk(this.world, (ProtoChunk) ichunkaccess);
                    playerchunk.a(new ProtoChunkExtension(chunk));
                }

                chunk.a(() -> {
                    return PlayerChunk.c(playerchunk.i());
                });
                chunk.addEntities();
                if (this.h.add(chunkcoordintpair.pair())) {
                    chunk.c(true);
                    this.world.a(chunk.getTileEntities().values());
                    List<Entity> list = null;
                    EntitySlice[] aentityslice = chunk.getEntitySlices();
                    int i = aentityslice.length;

                    for (int j = 0; j < i; ++j) {
                        EntitySlice<Entity> entityslice = aentityslice[j];
                        Iterator iterator = entityslice.iterator();

                        while (iterator.hasNext()) {
                            Entity entity = (Entity) iterator.next();

                            if (!(entity instanceof EntityHuman) && !this.world.addEntityChunk(entity)) {
                                if (list == null) {
                                    list = Lists.newArrayList(new Entity[] { entity});
                                } else {
                                    list.add(entity);
                                }
                            }
                        }
                    }

                    if (list != null) {
                        list.forEach(chunk::b);
                    }
                }

                return chunk;
            });
        }, (runnable) -> {
            Mailbox mailbox = this.mailboxMain;
            long i = playerchunk.h().pair();

            playerchunk.getClass();
            mailbox.a((Object) ChunkTaskQueueSorter.a(runnable, i, playerchunk::i));
        });
    }

    public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> a(PlayerChunk playerchunk) {
        ChunkCoordIntPair chunkcoordintpair = playerchunk.h();
        CompletableFuture<Either<List<IChunkAccess>, PlayerChunk.Failure>> completablefuture = this.a(chunkcoordintpair, 1, (i) -> {
            return ChunkStatus.FULL;
        });
        CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture1 = completablefuture.thenApplyAsync((either) -> {
            return either.flatMap((list) -> {
                Chunk chunk = (Chunk) list.get(list.size() / 2);

                chunk.A();
                return Either.left(chunk);
            });
        }, (runnable) -> {
            this.mailboxMain.a((Object) ChunkTaskQueueSorter.a(playerchunk, runnable));
        });

        completablefuture1.thenAcceptAsync((either) -> {
            either.mapLeft((chunk) -> {
                this.v.getAndIncrement();
                Packet<?>[] apacket = new Packet[2];

                this.a(chunkcoordintpair, false).forEach((entityplayer) -> {
                    this.a(entityplayer, apacket, chunk);
                });
                return Either.left(chunk);
            });
        }, (runnable) -> {
            this.mailboxMain.a((Object) ChunkTaskQueueSorter.a(playerchunk, runnable));
        });
        return completablefuture1;
    }

    public int c() {
        return this.v.get();
    }

    public void saveChunk(IChunkAccess ichunkaccess) {
        this.n.a(ichunkaccess.getPos());
        if (ichunkaccess.isNeedsSaving()) {
            try {
                this.world.checkSession();
            } catch (ExceptionWorldConflict exceptionworldconflict) {
                PlayerChunkMap.LOGGER.error("Couldn't save chunk; already in use by another instance of Minecraft?", exceptionworldconflict);
                return;
            }

            ichunkaccess.setLastSaved(this.world.getTime());
            ichunkaccess.setNeedsSaving(false);
            ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();

            try {
                ChunkStatus chunkstatus = ichunkaccess.getChunkStatus();
                NBTTagCompound nbttagcompound;

                if (chunkstatus.getType() != ChunkStatus.Type.LEVELCHUNK) {
                    nbttagcompound = this.f(chunkcoordintpair);
                    if (nbttagcompound != null && ChunkRegionLoader.a(nbttagcompound) == ChunkStatus.Type.LEVELCHUNK) {
                        return;
                    }

                    if (chunkstatus == ChunkStatus.EMPTY && ichunkaccess.h().values().stream().noneMatch(StructureStart::e)) {
                        return;
                    }
                }

                nbttagcompound = ChunkRegionLoader.saveChunk(this.world, ichunkaccess);
                this.write(chunkcoordintpair, nbttagcompound);
            } catch (Exception exception) {
                PlayerChunkMap.LOGGER.error("Failed to save chunk {},{}", chunkcoordintpair.x, chunkcoordintpair.z, exception);
            }

        }
    }

    protected void setViewDistance(int i, int j) {
        int k = MathHelper.clamp(i + 1, 3, 33);
        int l;

        if (k != this.A) {
            l = this.A;
            this.A = k;
            this.u.b(this.A);
            ObjectIterator objectiterator = this.updatingChunks.values().iterator();

            while (objectiterator.hasNext()) {
                PlayerChunk playerchunk = (PlayerChunk) objectiterator.next();
                ChunkCoordIntPair chunkcoordintpair = playerchunk.h();
                Packet<?>[] apacket = new Packet[2];

                this.a(chunkcoordintpair, false).forEach((entityplayer) -> {
                    int i1 = b(chunkcoordintpair, entityplayer, true);
                    boolean flag = i1 <= l;
                    boolean flag1 = i1 <= this.A;

                    this.sendChunk(entityplayer, chunkcoordintpair, apacket, flag, flag1);
                });
            }
        }

        l = MathHelper.clamp(j + 1, 1, 16);
        if (l != this.B) {
            this.B = l;
            this.u.setEntityDistance(this.B);
        }

    }

    protected void sendChunk(EntityPlayer entityplayer, ChunkCoordIntPair chunkcoordintpair, Packet<?>[] apacket, boolean flag, boolean flag1) {
        if (entityplayer.world == this.world) {
            if (flag1 && !flag) {
                PlayerChunk playerchunk = this.getVisibleChunk(chunkcoordintpair.pair());

                if (playerchunk != null) {
                    Chunk chunk = playerchunk.getChunk();

                    if (chunk != null) {
                        this.a(entityplayer, apacket, chunk);
                    }

                    PacketDebug.a(this.world, chunkcoordintpair);
                }
            }

            if (!flag1 && flag) {
                entityplayer.a(chunkcoordintpair);
            }

        }
    }

    public int d() {
        return this.visibleChunks.size();
    }

    protected PlayerChunkMap.a e() {
        return this.u;
    }

    protected ObjectBidirectionalIterator<Entry<PlayerChunk>> f() {
        return this.visibleChunks.long2ObjectEntrySet().fastIterator();
    }

    @Nullable
    private NBTTagCompound f(ChunkCoordIntPair chunkcoordintpair) throws IOException {
        NBTTagCompound nbttagcompound = this.read(chunkcoordintpair);

        return nbttagcompound == null ? null : this.getChunkData(this.world.getWorldProvider().getDimensionManager(), this.m, nbttagcompound);
    }

    boolean d(ChunkCoordIntPair chunkcoordintpair) {
        return this.y.a(chunkcoordintpair.pair()).noneMatch((entityplayer) -> {
            return !entityplayer.isSpectator() && a(chunkcoordintpair, (Entity) entityplayer) < 16384.0D;
        });
    }

    private boolean b(EntityPlayer entityplayer) {
        return entityplayer.isSpectator() && !this.world.getGameRules().getBoolean("spectatorsGenerateChunks");
    }

    void a(EntityPlayer entityplayer, boolean flag) {
        boolean flag1 = this.b(entityplayer);
        boolean flag2 = this.y.c(entityplayer);
        int i = MathHelper.floor(entityplayer.locX) >> 4;
        int j = MathHelper.floor(entityplayer.locZ) >> 4;

        if (flag) {
            this.y.a(ChunkCoordIntPair.pair(i, j), entityplayer, flag1);
            if (!flag1) {
                this.u.a(SectionPosition.a((Entity) entityplayer), entityplayer);
            }
        } else {
            SectionPosition sectionposition = entityplayer.M();

            this.y.a(sectionposition.u().pair(), entityplayer);
            if (!flag1) {
                this.u.b(sectionposition, entityplayer);
            }
        }

        for (int k = i - this.A; k <= i + this.A; ++k) {
            for (int l = j - this.A; l <= j + this.A; ++l) {
                ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(k, l);

                this.sendChunk(entityplayer, chunkcoordintpair, new Packet[2], !flag && !flag2, flag && !flag1);
            }
        }

    }

    public void movePlayer(EntityPlayer entityplayer) {
        ObjectIterator objectiterator = this.trackedEntities.values().iterator();

        while (objectiterator.hasNext()) {
            PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker) objectiterator.next();

            if (playerchunkmap_entitytracker.tracker == entityplayer) {
                playerchunkmap_entitytracker.track(this.world.getPlayers());
            } else {
                playerchunkmap_entitytracker.updatePlayer(entityplayer);
            }
        }

        int i = MathHelper.floor(entityplayer.locX) >> 4;
        int j = MathHelper.floor(entityplayer.locZ) >> 4;
        SectionPosition sectionposition = entityplayer.M();
        SectionPosition sectionposition1 = SectionPosition.a((Entity) entityplayer);
        long k = sectionposition.u().pair();
        long l = sectionposition1.u().pair();
        boolean flag = this.y.c(entityplayer);
        boolean flag1 = this.b(entityplayer);
        boolean flag2 = sectionposition.v() != sectionposition1.v();

        if (flag2 || flag != flag1) {
            if (!flag && flag1 || flag2) {
                this.u.b(sectionposition, entityplayer);
            }

            if (flag && !flag1 || flag2) {
                this.u.a(sectionposition1, entityplayer);
            }

            if (!flag && flag1) {
                this.y.a(entityplayer);
            }

            if (flag && !flag1) {
                this.y.b(entityplayer);
            }

            if (k != l) {
                this.y.a(k, l, entityplayer);
            }

            int i1 = sectionposition.a();
            int j1 = sectionposition.c();
            int k1;
            int l1;

            if (Math.abs(i1 - i) <= this.A * 2 && Math.abs(j1 - j) <= this.A * 2) {
                k1 = Math.min(i, i1) - this.A;
                l1 = Math.min(j, j1) - this.A;
                int i2 = Math.max(i, i1) + this.A;
                int j2 = Math.max(j, j1) + this.A;

                for (int k2 = k1; k2 <= i2; ++k2) {
                    for (int l2 = l1; l2 <= j2; ++l2) {
                        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(k2, l2);
                        boolean flag3 = !flag && a(chunkcoordintpair, i1, j1) <= this.A;
                        boolean flag4 = !flag1 && a(chunkcoordintpair, i, j) <= this.A;

                        this.sendChunk(entityplayer, chunkcoordintpair, new Packet[2], flag3, flag4);
                    }
                }
            } else {
                ChunkCoordIntPair chunkcoordintpair1;
                boolean flag5;
                boolean flag6;

                for (k1 = i1 - this.A; k1 <= i1 + this.A; ++k1) {
                    for (l1 = j1 - this.A; l1 <= j1 + this.A; ++l1) {
                        chunkcoordintpair1 = new ChunkCoordIntPair(k1, l1);
                        flag5 = !flag;
                        flag6 = false;
                        this.sendChunk(entityplayer, chunkcoordintpair1, new Packet[2], flag5, false);
                    }
                }

                for (k1 = i - this.A; k1 <= i + this.A; ++k1) {
                    for (l1 = j - this.A; l1 <= j + this.A; ++l1) {
                        chunkcoordintpair1 = new ChunkCoordIntPair(k1, l1);
                        flag5 = false;
                        flag6 = !flag1;
                        this.sendChunk(entityplayer, chunkcoordintpair1, new Packet[2], false, flag6);
                    }
                }
            }

        }
    }

    @Override
    public Stream<EntityPlayer> a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
        return this.y.a(chunkcoordintpair.pair()).filter((entityplayer) -> {
            int i = b(chunkcoordintpair, entityplayer, true);

            return i > this.A ? false : !flag || i == this.A;
        });
    }

    protected void addEntity(Entity entity) {
        if (!(entity instanceof EntityComplexPart)) {
            if (!(entity instanceof EntityLightning)) {
                EntityTypes<?> entitytypes = entity.getEntityType();
                int i = entitytypes.getChunkRange() * 16;
                int j = entitytypes.getUpdateInterval();

                if (this.trackedEntities.containsKey(entity.getId())) {
                    throw new IllegalStateException("Entity is already tracked!");
                } else {
                    PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = new PlayerChunkMap.EntityTracker(entity, i, j, entitytypes.isDeltaTracking());

                    this.trackedEntities.put(entity.getId(), playerchunkmap_entitytracker);
                    playerchunkmap_entitytracker.track(this.world.getPlayers());
                    if (entity instanceof EntityPlayer) {
                        EntityPlayer entityplayer = (EntityPlayer) entity;

                        this.a(entityplayer, true);
                        ObjectIterator objectiterator = this.trackedEntities.values().iterator();

                        while (objectiterator.hasNext()) {
                            PlayerChunkMap.EntityTracker playerchunkmap_entitytracker1 = (PlayerChunkMap.EntityTracker) objectiterator.next();

                            if (playerchunkmap_entitytracker1.tracker != entityplayer) {
                                playerchunkmap_entitytracker1.updatePlayer(entityplayer);
                            }
                        }
                    }

                }
            }
        }
    }

    protected void removeEntity(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entity;

            this.a(entityplayer, false);
            ObjectIterator objectiterator = this.trackedEntities.values().iterator();

            while (objectiterator.hasNext()) {
                PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker) objectiterator.next();

                playerchunkmap_entitytracker.clear(entityplayer);
            }
        }

        PlayerChunkMap.EntityTracker playerchunkmap_entitytracker1 = (PlayerChunkMap.EntityTracker) this.trackedEntities.remove(entity.getId());

        if (playerchunkmap_entitytracker1 != null) {
            playerchunkmap_entitytracker1.a();
        }

    }

    protected void g() {
        List<EntityPlayer> list = Lists.newArrayList();
        List<EntityPlayer> list1 = this.world.getPlayers();

        PlayerChunkMap.EntityTracker playerchunkmap_entitytracker;
        ObjectIterator objectiterator;

        for (objectiterator = this.trackedEntities.values().iterator(); objectiterator.hasNext(); playerchunkmap_entitytracker.trackerEntry.a()) {
            playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker) objectiterator.next();
            SectionPosition sectionposition = playerchunkmap_entitytracker.e;
            SectionPosition sectionposition1 = SectionPosition.a(playerchunkmap_entitytracker.tracker);

            if (!Objects.equals(sectionposition, sectionposition1)) {
                playerchunkmap_entitytracker.track(list1);
                Entity entity = playerchunkmap_entitytracker.tracker;

                if (entity instanceof EntityPlayer) {
                    list.add((EntityPlayer) entity);
                }

                playerchunkmap_entitytracker.e = sectionposition1;
            }
        }

        objectiterator = this.trackedEntities.values().iterator();

        while (objectiterator.hasNext()) {
            playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker) objectiterator.next();
            playerchunkmap_entitytracker.track(list);
        }

    }

    protected void broadcast(Entity entity, Packet<?> packet) {
        PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker) this.trackedEntities.get(entity.getId());

        if (playerchunkmap_entitytracker != null) {
            playerchunkmap_entitytracker.broadcast(packet);
        }

    }

    protected void broadcastIncludingSelf(Entity entity, Packet<?> packet) {
        PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker) this.trackedEntities.get(entity.getId());

        if (playerchunkmap_entitytracker != null) {
            playerchunkmap_entitytracker.broadcastIncludingSelf(packet);
        }

    }

    private void a(EntityPlayer entityplayer, Packet<?>[] apacket, Chunk chunk) {
        if (apacket[0] == null) {
            apacket[0] = new PacketPlayOutMapChunk(chunk, 65535);
            apacket[1] = new PacketPlayOutLightUpdate(chunk.getPos(), this.lightEngine);
        }

        entityplayer.a(chunk.getPos(), apacket[0], apacket[1]);
        PacketDebug.a(this.world, chunk.getPos());
        List<Entity> list = Lists.newArrayList();
        List<Entity> list1 = Lists.newArrayList();
        ObjectIterator objectiterator = this.trackedEntities.values().iterator();

        while (objectiterator.hasNext()) {
            PlayerChunkMap.EntityTracker playerchunkmap_entitytracker = (PlayerChunkMap.EntityTracker) objectiterator.next();
            Entity entity = playerchunkmap_entitytracker.tracker;

            if (entity != entityplayer && entity.chunkX == chunk.getPos().x && entity.chunkZ == chunk.getPos().z) {
                playerchunkmap_entitytracker.updatePlayer(entityplayer);
                if (entity instanceof EntityInsentient && ((EntityInsentient) entity).getLeashHolder() != null) {
                    list.add(entity);
                }

                if (!entity.getPassengers().isEmpty()) {
                    list1.add(entity);
                }
            }
        }

        Iterator iterator;
        Entity entity1;

        if (!list.isEmpty()) {
            iterator = list.iterator();

            while (iterator.hasNext()) {
                entity1 = (Entity) iterator.next();
                entityplayer.playerConnection.sendPacket(new PacketPlayOutAttachEntity(entity1, ((EntityInsentient) entity1).getLeashHolder()));
            }
        }

        if (!list1.isEmpty()) {
            iterator = list1.iterator();

            while (iterator.hasNext()) {
                entity1 = (Entity) iterator.next();
                entityplayer.playerConnection.sendPacket(new PacketPlayOutMount(entity1));
            }
        }

    }

    protected VillagePlace h() {
        return this.n;
    }

    public void a(Chunk chunk) {
        this.executor.a(() -> {
            chunk.a(this.world);
        });
    }

    public class EntityTracker {

        private final EntityTrackerEntry trackerEntry;
        private final Entity tracker;
        private final int trackingDistance;
        private SectionPosition e;
        public final Set<EntityPlayer> trackedPlayers = Sets.newHashSet();

        public EntityTracker(Entity entity, int i, int j, boolean flag) {
            this.trackerEntry = new EntityTrackerEntry(PlayerChunkMap.this.world, entity, j, flag, this::broadcast);
            this.tracker = entity;
            this.trackingDistance = i;
            this.e = SectionPosition.a(entity);
        }

        public boolean equals(Object object) {
            return object instanceof PlayerChunkMap.EntityTracker ? ((PlayerChunkMap.EntityTracker) object).tracker.getId() == this.tracker.getId() : false;
        }

        public int hashCode() {
            return this.tracker.getId();
        }

        public void broadcast(Packet<?> packet) {
            Iterator iterator = this.trackedPlayers.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                entityplayer.playerConnection.sendPacket(packet);
            }

        }

        public void broadcastIncludingSelf(Packet<?> packet) {
            this.broadcast(packet);
            if (this.tracker instanceof EntityPlayer) {
                ((EntityPlayer) this.tracker).playerConnection.sendPacket(packet);
            }

        }

        public void a() {
            Iterator iterator = this.trackedPlayers.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                this.trackerEntry.a(entityplayer);
            }

        }

        public void clear(EntityPlayer entityplayer) {
            if (this.trackedPlayers.remove(entityplayer)) {
                this.trackerEntry.a(entityplayer);
            }

        }

        public void updatePlayer(EntityPlayer entityplayer) {
            if (entityplayer != this.tracker) {
                Vec3D vec3d = (new Vec3D(entityplayer.locX, entityplayer.locY, entityplayer.locZ)).d(this.trackerEntry.b());
                int i = Math.min(this.trackingDistance, (PlayerChunkMap.this.A - 1) * 16);
                boolean flag = vec3d.x >= (double) (-i) && vec3d.x <= (double) i && vec3d.z >= (double) (-i) && vec3d.z <= (double) i && this.tracker.a(entityplayer);

                if (flag) {
                    boolean flag1 = this.tracker.attachedToPlayer;

                    if (!flag1) {
                        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(this.tracker.chunkX, this.tracker.chunkZ);
                        PlayerChunk playerchunk = PlayerChunkMap.this.getVisibleChunk(chunkcoordintpair.pair());

                        if (playerchunk != null && playerchunk.getChunk() != null) {
                            flag1 = PlayerChunkMap.b(chunkcoordintpair, entityplayer, false) <= PlayerChunkMap.this.A;
                        }
                    }

                    if (flag1 && this.trackedPlayers.add(entityplayer)) {
                        this.trackerEntry.b(entityplayer);
                    }
                } else if (this.trackedPlayers.remove(entityplayer)) {
                    this.trackerEntry.a(entityplayer);
                }

            }
        }

        public void track(List<EntityPlayer> list) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                this.updatePlayer(entityplayer);
            }

        }
    }

    class a extends ChunkMapDistance {

        protected a(Executor executor, Executor executor1) {
            super(executor, executor1);
        }

        @Override
        protected boolean a(long i) {
            return PlayerChunkMap.this.unloadQueue.contains(i);
        }

        @Nullable
        @Override
        protected PlayerChunk b(long i) {
            return PlayerChunkMap.this.getUpdatingChunk(i);
        }

        @Nullable
        @Override
        protected PlayerChunk a(long i, int j, @Nullable PlayerChunk playerchunk, int k) {
            return PlayerChunkMap.this.a(i, j, playerchunk, k);
        }
    }
}
