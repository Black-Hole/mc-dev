package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;

public class ChunkStatus {

    private static final EnumSet<HeightMap.Type> n = EnumSet.of(HeightMap.Type.OCEAN_FLOOR_WG, HeightMap.Type.WORLD_SURFACE_WG);
    private static final EnumSet<HeightMap.Type> o = EnumSet.of(HeightMap.Type.OCEAN_FLOOR, HeightMap.Type.WORLD_SURFACE, HeightMap.Type.MOTION_BLOCKING, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
    public static final ChunkStatus EMPTY = a("empty", (ChunkStatus) null, -1, ChunkStatus.n, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
    });
    public static final ChunkStatus STRUCTURE_STARTS = a("structure_starts", ChunkStatus.EMPTY, 0, ChunkStatus.n, ChunkStatus.Type.PROTOCHUNK, (chunkstatus, worldserver, chunkgenerator, definedstructuremanager, lightenginethreaded, function, list, ichunkaccess) -> {
        if (!ichunkaccess.getChunkStatus().b(chunkstatus)) {
            if (worldserver.getWorldData().shouldGenerateMapFeatures()) {
                chunkgenerator.createStructures(ichunkaccess, chunkgenerator, definedstructuremanager);
            }

            if (ichunkaccess instanceof ProtoChunk) {
                ((ProtoChunk) ichunkaccess).a(chunkstatus);
            }
        }

        return CompletableFuture.completedFuture(Either.left(ichunkaccess));
    });
    public static final ChunkStatus STRUCTURE_REFERENCES = a("structure_references", ChunkStatus.STRUCTURE_STARTS, 8, ChunkStatus.n, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
        chunkgenerator.storeStructures(new RegionLimitedWorldAccess(worldserver, list), ichunkaccess);
    });
    public static final ChunkStatus BIOMES = a("biomes", ChunkStatus.STRUCTURE_REFERENCES, 0, ChunkStatus.n, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
        chunkgenerator.createBiomes(ichunkaccess);
    });
    public static final ChunkStatus NOISE = a("noise", ChunkStatus.BIOMES, 8, ChunkStatus.n, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
        chunkgenerator.buildNoise(new RegionLimitedWorldAccess(worldserver, list), ichunkaccess);
    });
    public static final ChunkStatus SURFACE = a("surface", ChunkStatus.NOISE, 0, ChunkStatus.n, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
        chunkgenerator.buildBase(ichunkaccess);
    });
    public static final ChunkStatus CARVERS = a("carvers", ChunkStatus.SURFACE, 0, ChunkStatus.n, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
        chunkgenerator.doCarving(ichunkaccess, WorldGenStage.Features.AIR);
    });
    public static final ChunkStatus LIQUID_CARVERS = a("liquid_carvers", ChunkStatus.CARVERS, 0, ChunkStatus.o, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
        chunkgenerator.doCarving(ichunkaccess, WorldGenStage.Features.LIQUID);
    });
    public static final ChunkStatus FEATURES = a("features", ChunkStatus.LIQUID_CARVERS, 8, ChunkStatus.o, ChunkStatus.Type.PROTOCHUNK, (chunkstatus, worldserver, chunkgenerator, definedstructuremanager, lightenginethreaded, function, list, ichunkaccess) -> {
        ichunkaccess.a((LightEngine) lightenginethreaded);
        if (!ichunkaccess.getChunkStatus().b(chunkstatus)) {
            HeightMap.a(ichunkaccess, EnumSet.of(HeightMap.Type.MOTION_BLOCKING, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, HeightMap.Type.OCEAN_FLOOR, HeightMap.Type.WORLD_SURFACE));
            chunkgenerator.addDecorations(new RegionLimitedWorldAccess(worldserver, list));
            if (ichunkaccess instanceof ProtoChunk) {
                ((ProtoChunk) ichunkaccess).a(chunkstatus);
            }
        }

        return CompletableFuture.completedFuture(Either.left(ichunkaccess));
    });
    public static final ChunkStatus LIGHT = a("light", ChunkStatus.FEATURES, 1, ChunkStatus.o, ChunkStatus.Type.PROTOCHUNK, (chunkstatus, worldserver, chunkgenerator, definedstructuremanager, lightenginethreaded, function, list, ichunkaccess) -> {
        boolean flag = ichunkaccess.getChunkStatus().b(chunkstatus) && ichunkaccess.r();

        if (!ichunkaccess.getChunkStatus().b(chunkstatus)) {
            ((ProtoChunk) ichunkaccess).a(chunkstatus);
        }

        return lightenginethreaded.a(ichunkaccess, flag).thenApply(Either::left);
    });
    public static final ChunkStatus SPAWN = a("spawn", ChunkStatus.LIGHT, 0, ChunkStatus.o, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
        chunkgenerator.addMobs(new RegionLimitedWorldAccess(worldserver, list));
    });
    public static final ChunkStatus HEIGHTMAPS = a("heightmaps", ChunkStatus.SPAWN, 0, ChunkStatus.o, ChunkStatus.Type.PROTOCHUNK, (worldserver, chunkgenerator, list, ichunkaccess) -> {
    });
    public static final ChunkStatus FULL = a("full", ChunkStatus.HEIGHTMAPS, 0, ChunkStatus.o, ChunkStatus.Type.LEVELCHUNK, (chunkstatus, worldserver, chunkgenerator, definedstructuremanager, lightenginethreaded, function, list, ichunkaccess) -> {
        return (CompletableFuture) function.apply(ichunkaccess);
    });
    private static final List<ChunkStatus> p = ImmutableList.of(ChunkStatus.FULL, ChunkStatus.FEATURES, ChunkStatus.LIQUID_CARVERS, ChunkStatus.STRUCTURE_STARTS, ChunkStatus.STRUCTURE_STARTS, ChunkStatus.STRUCTURE_STARTS, ChunkStatus.STRUCTURE_STARTS, ChunkStatus.STRUCTURE_STARTS, ChunkStatus.STRUCTURE_STARTS, ChunkStatus.STRUCTURE_STARTS, ChunkStatus.STRUCTURE_STARTS);
    private static final IntList q = (IntList) SystemUtils.a((Object) (new IntArrayList(a().size())), (intarraylist) -> {
        int i = 0;

        for (int j = a().size() - 1; j >= 0; --j) {
            while (i + 1 < ChunkStatus.p.size() && j <= ((ChunkStatus) ChunkStatus.p.get(i + 1)).c()) {
                ++i;
            }

            intarraylist.add(0, i);
        }

    });
    private final String r;
    private final int s;
    private final ChunkStatus t;
    private final ChunkStatus.a u;
    private final int v;
    private final ChunkStatus.Type w;
    private final EnumSet<HeightMap.Type> x;

    private static ChunkStatus a(String s, @Nullable ChunkStatus chunkstatus, int i, EnumSet<HeightMap.Type> enumset, ChunkStatus.Type chunkstatus_type, ChunkStatus.c chunkstatus_c) {
        return a(s, chunkstatus, i, enumset, chunkstatus_type, (ChunkStatus.a) chunkstatus_c);
    }

    private static ChunkStatus a(String s, @Nullable ChunkStatus chunkstatus, int i, EnumSet<HeightMap.Type> enumset, ChunkStatus.Type chunkstatus_type, ChunkStatus.a chunkstatus_a) {
        return (ChunkStatus) IRegistry.a((IRegistry) IRegistry.CHUNK_STATUS, s, (Object) (new ChunkStatus(s, chunkstatus, i, enumset, chunkstatus_type, chunkstatus_a)));
    }

    public static List<ChunkStatus> a() {
        List<ChunkStatus> list = Lists.newArrayList();

        ChunkStatus chunkstatus;

        for (chunkstatus = ChunkStatus.FULL; chunkstatus.e() != chunkstatus; chunkstatus = chunkstatus.e()) {
            list.add(chunkstatus);
        }

        list.add(chunkstatus);
        Collections.reverse(list);
        return list;
    }

    public static ChunkStatus a(int i) {
        return i >= ChunkStatus.p.size() ? ChunkStatus.EMPTY : (i < 0 ? ChunkStatus.FULL : (ChunkStatus) ChunkStatus.p.get(i));
    }

    public static int b() {
        return ChunkStatus.p.size();
    }

    public static int a(ChunkStatus chunkstatus) {
        return ChunkStatus.q.getInt(chunkstatus.c());
    }

    ChunkStatus(String s, @Nullable ChunkStatus chunkstatus, int i, EnumSet<HeightMap.Type> enumset, ChunkStatus.Type chunkstatus_type, ChunkStatus.a chunkstatus_a) {
        this.r = s;
        this.t = chunkstatus == null ? this : chunkstatus;
        this.u = chunkstatus_a;
        this.v = i;
        this.w = chunkstatus_type;
        this.x = enumset;
        this.s = chunkstatus == null ? 0 : chunkstatus.c() + 1;
    }

    public int c() {
        return this.s;
    }

    public String d() {
        return this.r;
    }

    public ChunkStatus e() {
        return this.t;
    }

    public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(WorldServer worldserver, ChunkGenerator<?> chunkgenerator, DefinedStructureManager definedstructuremanager, LightEngineThreaded lightenginethreaded, Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> function, List<IChunkAccess> list) {
        return this.u.doWork(this, worldserver, chunkgenerator, definedstructuremanager, lightenginethreaded, function, list, (IChunkAccess) list.get(list.size() / 2));
    }

    public int f() {
        return this.v;
    }

    public ChunkStatus.Type getType() {
        return this.w;
    }

    public static ChunkStatus a(String s) {
        return (ChunkStatus) IRegistry.CHUNK_STATUS.get(MinecraftKey.a(s));
    }

    public EnumSet<HeightMap.Type> h() {
        return this.x;
    }

    public boolean b(ChunkStatus chunkstatus) {
        return this.c() >= chunkstatus.c();
    }

    public String toString() {
        return IRegistry.CHUNK_STATUS.getKey(this).toString();
    }

    public static enum Type {

        PROTOCHUNK, LEVELCHUNK;

        private Type() {}
    }

    interface c extends ChunkStatus.a {

        @Override
        default CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> doWork(ChunkStatus chunkstatus, WorldServer worldserver, ChunkGenerator<?> chunkgenerator, DefinedStructureManager definedstructuremanager, LightEngineThreaded lightenginethreaded, Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> function, List<IChunkAccess> list, IChunkAccess ichunkaccess) {
            if (!ichunkaccess.getChunkStatus().b(chunkstatus)) {
                this.doWork(worldserver, chunkgenerator, list, ichunkaccess);
                if (ichunkaccess instanceof ProtoChunk) {
                    ((ProtoChunk) ichunkaccess).a(chunkstatus);
                }
            }

            return CompletableFuture.completedFuture(Either.left(ichunkaccess));
        }

        void doWork(WorldServer worldserver, ChunkGenerator<?> chunkgenerator, List<IChunkAccess> list, IChunkAccess ichunkaccess);
    }

    interface a {

        CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> doWork(ChunkStatus chunkstatus, WorldServer worldserver, ChunkGenerator<?> chunkgenerator, DefinedStructureManager definedstructuremanager, LightEngineThreaded lightenginethreaded, Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> function, List<IChunkAccess> list, IChunkAccess ichunkaccess);
    }
}
