package net.minecraft.server;

import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class PlayerChunk {

    public static final Either<IChunkAccess, PlayerChunk.Failure> UNLOADED_CHUNK_ACCESS = Either.right(PlayerChunk.Failure.b);
    public static final CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> UNLOADED_CHUNK_ACCESS_FUTURE = CompletableFuture.completedFuture(PlayerChunk.UNLOADED_CHUNK_ACCESS);
    public static final Either<Chunk, PlayerChunk.Failure> UNLOADED_CHUNK = Either.right(PlayerChunk.Failure.b);
    private static final CompletableFuture<Either<Chunk, PlayerChunk.Failure>> UNLOADED_CHUNK_FUTURE = CompletableFuture.completedFuture(PlayerChunk.UNLOADED_CHUNK);
    private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.a();
    private static final PlayerChunk.State[] CHUNK_STATES = PlayerChunk.State.values();
    private final AtomicReferenceArray<CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> statusFutures;
    private volatile CompletableFuture<Either<Chunk, PlayerChunk.Failure>> tickingFuture;
    private volatile CompletableFuture<Either<Chunk, PlayerChunk.Failure>> entityTickingFuture;
    private CompletableFuture<IChunkAccess> chunkSave;
    private int k;
    private int l;
    private int m;
    private final ChunkCoordIntPair location;
    private final short[] dirtyBlocks;
    private int dirtyCount;
    private int q;
    private int r;
    private int s;
    private int t;
    private final LightEngine lightEngine;
    private final PlayerChunk.c v;
    public final PlayerChunk.d players;
    private boolean x;

    public PlayerChunk(ChunkCoordIntPair chunkcoordintpair, int i, LightEngine lightengine, PlayerChunk.c playerchunk_c, PlayerChunk.d playerchunk_d) {
        this.statusFutures = new AtomicReferenceArray(PlayerChunk.CHUNK_STATUSES.size());
        this.tickingFuture = PlayerChunk.UNLOADED_CHUNK_FUTURE;
        this.entityTickingFuture = PlayerChunk.UNLOADED_CHUNK_FUTURE;
        this.chunkSave = CompletableFuture.completedFuture((Object) null);
        this.dirtyBlocks = new short[64];
        this.location = chunkcoordintpair;
        this.lightEngine = lightengine;
        this.v = playerchunk_c;
        this.players = playerchunk_d;
        this.k = PlayerChunkMap.GOLDEN_TICKET + 1;
        this.l = this.k;
        this.m = this.k;
        this.a(i);
    }

    public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(ChunkStatus chunkstatus) {
        CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = (CompletableFuture) this.statusFutures.get(chunkstatus.c());

        return completablefuture == null ? PlayerChunk.UNLOADED_CHUNK_ACCESS_FUTURE : completablefuture;
    }

    public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> b(ChunkStatus chunkstatus) {
        return b(this.l).b(chunkstatus) ? this.a(chunkstatus) : PlayerChunk.UNLOADED_CHUNK_ACCESS_FUTURE;
    }

    public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> a() {
        return this.tickingFuture;
    }

    public CompletableFuture<Either<Chunk, PlayerChunk.Failure>> b() {
        return this.entityTickingFuture;
    }

    @Nullable
    public Chunk getChunk() {
        CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture = this.a();
        Either<Chunk, PlayerChunk.Failure> either = (Either) completablefuture.getNow((Object) null);

        return either == null ? null : (Chunk) either.left().orElse((Object) null);
    }

    public CompletableFuture<IChunkAccess> getChunkSave() {
        return this.chunkSave;
    }

    public void a(int i, int j, int k) {
        Chunk chunk = this.getChunk();

        if (chunk != null) {
            this.q |= 1 << (j >> 4);
            if (this.dirtyCount < 64) {
                short short0 = (short) (i << 12 | k << 8 | j);

                for (int l = 0; l < this.dirtyCount; ++l) {
                    if (this.dirtyBlocks[l] == short0) {
                        return;
                    }
                }

                this.dirtyBlocks[this.dirtyCount++] = short0;
            }

        }
    }

    public void a(EnumSkyBlock enumskyblock, int i) {
        Chunk chunk = this.getChunk();

        if (chunk != null) {
            chunk.setNeedsSaving(true);
            if (enumskyblock == EnumSkyBlock.SKY) {
                this.t |= 1 << i - -1;
            } else {
                this.s |= 1 << i - -1;
            }

        }
    }

    public void a(Chunk chunk) {
        if (this.dirtyCount != 0 || this.t != 0 || this.s != 0) {
            World world = chunk.getWorld();

            if (this.dirtyCount == 64) {
                this.r = -1;
            }

            int i;
            int j;

            if (this.t != 0 || this.s != 0) {
                this.a(new PacketPlayOutLightUpdate(chunk.getPos(), this.lightEngine, this.t & ~this.r, this.s & ~this.r), true);
                i = this.t & this.r;
                j = this.s & this.r;
                if (i != 0 || j != 0) {
                    this.a(new PacketPlayOutLightUpdate(chunk.getPos(), this.lightEngine, i, j), false);
                }

                this.t = 0;
                this.s = 0;
                this.r &= ~(this.t & this.s);
            }

            int k;

            if (this.dirtyCount == 1) {
                i = (this.dirtyBlocks[0] >> 12 & 15) + this.location.x * 16;
                j = this.dirtyBlocks[0] & 255;
                k = (this.dirtyBlocks[0] >> 8 & 15) + this.location.z * 16;
                BlockPosition blockposition = new BlockPosition(i, j, k);

                this.a(new PacketPlayOutBlockChange(world, blockposition), false);
                if (world.getType(blockposition).getBlock().isTileEntity()) {
                    this.a(world, blockposition);
                }
            } else if (this.dirtyCount == 64) {
                this.a(new PacketPlayOutMapChunk(chunk, this.q), false);
            } else if (this.dirtyCount != 0) {
                this.a(new PacketPlayOutMultiBlockChange(this.dirtyCount, this.dirtyBlocks, chunk), false);

                for (i = 0; i < this.dirtyCount; ++i) {
                    j = (this.dirtyBlocks[i] >> 12 & 15) + this.location.x * 16;
                    k = this.dirtyBlocks[i] & 255;
                    int l = (this.dirtyBlocks[i] >> 8 & 15) + this.location.z * 16;
                    BlockPosition blockposition1 = new BlockPosition(j, k, l);

                    if (world.getType(blockposition1).getBlock().isTileEntity()) {
                        this.a(world, blockposition1);
                    }
                }
            }

            this.dirtyCount = 0;
            this.q = 0;
        }
    }

    private void a(World world, BlockPosition blockposition) {
        TileEntity tileentity = world.getTileEntity(blockposition);

        if (tileentity != null) {
            PacketPlayOutTileEntityData packetplayouttileentitydata = tileentity.getUpdatePacket();

            if (packetplayouttileentitydata != null) {
                this.a(packetplayouttileentitydata, false);
            }
        }

    }

    private void a(Packet<?> packet, boolean flag) {
        this.players.a(this.location, flag).forEach((entityplayer) -> {
            entityplayer.playerConnection.sendPacket(packet);
        });
    }

    public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(ChunkStatus chunkstatus, PlayerChunkMap playerchunkmap) {
        int i = chunkstatus.c();
        CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = (CompletableFuture) this.statusFutures.get(i);

        if (completablefuture != null) {
            Either<IChunkAccess, PlayerChunk.Failure> either = (Either) completablefuture.getNow((Object) null);

            if (either == null || either.left().isPresent()) {
                return completablefuture;
            }
        }

        if (b(this.l).b(chunkstatus)) {
            CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture1 = playerchunkmap.a(this, chunkstatus);

            this.a(completablefuture1);
            this.statusFutures.set(i, completablefuture1);
            return completablefuture1;
        } else {
            return completablefuture == null ? PlayerChunk.UNLOADED_CHUNK_ACCESS_FUTURE : completablefuture;
        }
    }

    private void a(CompletableFuture<? extends Either<? extends IChunkAccess, PlayerChunk.Failure>> completablefuture) {
        this.chunkSave = this.chunkSave.thenCombine(completablefuture, (ichunkaccess, either) -> {
            return (IChunkAccess) either.map((ichunkaccess1) -> {
                return ichunkaccess1;
            }, (playerchunk_failure) -> {
                return ichunkaccess;
            });
        });
    }

    public ChunkCoordIntPair h() {
        return this.location;
    }

    public int i() {
        return this.l;
    }

    public int j() {
        return this.m;
    }

    private void d(int i) {
        this.m = i;
    }

    public void a(int i) {
        this.l = i;
    }

    protected void a(PlayerChunkMap playerchunkmap) {
        ChunkStatus chunkstatus = b(this.k);
        ChunkStatus chunkstatus1 = b(this.l);

        this.x |= chunkstatus1.b(ChunkStatus.FULL);
        boolean flag = this.k <= PlayerChunkMap.GOLDEN_TICKET;
        boolean flag1 = this.l <= PlayerChunkMap.GOLDEN_TICKET;
        PlayerChunk.State playerchunk_state = c(this.k);
        PlayerChunk.State playerchunk_state1 = c(this.l);
        boolean flag2 = playerchunk_state.a(PlayerChunk.State.TICKING);
        boolean flag3 = playerchunk_state1.a(PlayerChunk.State.TICKING);

        if (flag1) {
            for (int i = flag ? chunkstatus.c() + 1 : 0; i <= chunkstatus1.c(); ++i) {
                this.a((ChunkStatus) PlayerChunk.CHUNK_STATUSES.get(i), playerchunkmap);
            }
        }

        if (flag) {
            Either<IChunkAccess, PlayerChunk.Failure> either = Either.right(new PlayerChunk.Failure() {
                public String toString() {
                    return "Unloaded ticket level " + PlayerChunk.this.location.toString();
                }
            });

            for (int j = flag1 ? chunkstatus1.c() + 1 : 0; j <= chunkstatus.c(); ++j) {
                CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = (CompletableFuture) this.statusFutures.get(j);

                if (completablefuture != null) {
                    completablefuture.complete(either);
                } else {
                    this.statusFutures.set(j, CompletableFuture.completedFuture(either));
                }
            }
        }

        if (!flag2 && flag3) {
            this.tickingFuture = playerchunkmap.a(this);
            this.a(this.tickingFuture);
        }

        if (flag2 && !flag3) {
            CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture1 = this.tickingFuture;

            this.tickingFuture = PlayerChunk.UNLOADED_CHUNK_FUTURE;
            completablefuture1.thenAccept((either1) -> {
                either1.ifLeft(playerchunkmap::a);
            });
        }

        boolean flag4 = playerchunk_state.a(PlayerChunk.State.ENTITY_TICKING);
        boolean flag5 = playerchunk_state1.a(PlayerChunk.State.ENTITY_TICKING);

        if (!flag4 && flag5) {
            if (this.entityTickingFuture != PlayerChunk.UNLOADED_CHUNK_FUTURE) {
                throw new IllegalStateException();
            }

            this.entityTickingFuture = playerchunkmap.b(this.location);
            this.a(this.entityTickingFuture);
        }

        if (flag4 && !flag5) {
            this.entityTickingFuture.complete(PlayerChunk.UNLOADED_CHUNK);
            this.entityTickingFuture = PlayerChunk.UNLOADED_CHUNK_FUTURE;
        }

        this.v.a(this.location, this::j, this.l, this::d);
        this.k = this.l;
    }

    public static ChunkStatus b(int i) {
        return i <= 33 ? ChunkStatus.FULL : ChunkStatus.a(i - 33 - 1);
    }

    public static PlayerChunk.State c(int i) {
        return PlayerChunk.CHUNK_STATES[MathHelper.clamp(33 - i, 0, PlayerChunk.CHUNK_STATES.length - 1)];
    }

    public boolean k() {
        return this.x;
    }

    public void l() {
        this.x = b(this.l).b(ChunkStatus.FULL);
    }

    public void a(ProtoChunkExtension protochunkextension) {
        for (int i = 0; i < this.statusFutures.length(); ++i) {
            CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = (CompletableFuture) this.statusFutures.get(i);

            if (completablefuture != null) {
                Optional<IChunkAccess> optional = ((Either) completablefuture.getNow(PlayerChunk.UNLOADED_CHUNK_ACCESS)).left();

                if (optional.isPresent() && optional.get() instanceof ProtoChunk) {
                    this.statusFutures.set(i, CompletableFuture.completedFuture(Either.left(protochunkextension)));
                }
            }
        }

        this.a(CompletableFuture.completedFuture(Either.left(protochunkextension.u())));
    }

    public interface d {

        Stream<EntityPlayer> a(ChunkCoordIntPair chunkcoordintpair, boolean flag);
    }

    public interface c {

        void a(ChunkCoordIntPair chunkcoordintpair, IntSupplier intsupplier, int i, IntConsumer intconsumer);
    }

    public interface Failure {

        PlayerChunk.Failure b = new PlayerChunk.Failure() {
            public String toString() {
                return "UNLOADED";
            }
        };
    }

    public static enum State {

        BORDER, TICKING, ENTITY_TICKING;

        private State() {}

        public boolean a(PlayerChunk.State playerchunk_state) {
            return this.ordinal() >= playerchunk_state.ordinal();
        }
    }
}
