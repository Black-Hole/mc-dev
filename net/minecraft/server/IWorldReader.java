package net.minecraft.server;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface IWorldReader extends IIBlockAccess {

    default boolean isEmpty(BlockPosition blockposition) {
        return this.getType(blockposition).isAir();
    }

    default boolean v(BlockPosition blockposition) {
        if (blockposition.getY() >= this.getSeaLevel()) {
            return this.f(blockposition);
        } else {
            BlockPosition blockposition1 = new BlockPosition(blockposition.getX(), this.getSeaLevel(), blockposition.getZ());

            if (!this.f(blockposition1)) {
                return false;
            } else {
                for (blockposition1 = blockposition1.down(); blockposition1.getY() > blockposition.getY(); blockposition1 = blockposition1.down()) {
                    IBlockData iblockdata = this.getType(blockposition1);

                    if (iblockdata.b((IBlockAccess) this, blockposition1) > 0 && !iblockdata.getMaterial().isLiquid()) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    int getLightLevel(BlockPosition blockposition, int i);

    @Nullable
    IChunkAccess getChunkAt(int i, int j, ChunkStatus chunkstatus, boolean flag);

    @Deprecated
    boolean isChunkLoaded(int i, int j);

    BlockPosition getHighestBlockYAt(HeightMap.Type heightmap_type, BlockPosition blockposition);

    int a(HeightMap.Type heightmap_type, int i, int j);

    default float w(BlockPosition blockposition) {
        return this.getWorldProvider().i()[this.getLightLevel(blockposition)];
    }

    int c();

    WorldBorder getWorldBorder();

    boolean a(@Nullable Entity entity, VoxelShape voxelshape);

    int a(BlockPosition blockposition, EnumDirection enumdirection);

    boolean e();

    int getSeaLevel();

    default IChunkAccess x(BlockPosition blockposition) {
        return this.getChunkAt(blockposition.getX() >> 4, blockposition.getZ() >> 4);
    }

    default IChunkAccess getChunkAt(int i, int j) {
        return this.getChunkAt(i, j, ChunkStatus.FULL, true);
    }

    default IChunkAccess getChunkAt(int i, int j, ChunkStatus chunkstatus) {
        return this.getChunkAt(i, j, chunkstatus, true);
    }

    default ChunkStatus O() {
        return ChunkStatus.EMPTY;
    }

    default boolean a(IBlockData iblockdata, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
        VoxelShape voxelshape = iblockdata.b((IBlockAccess) this, blockposition, voxelshapecollision);

        return voxelshape.isEmpty() || this.a((Entity) null, voxelshape.a((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ()));
    }

    default boolean i(Entity entity) {
        return this.a(entity, VoxelShapes.a(entity.getBoundingBox()));
    }

    default boolean c(AxisAlignedBB axisalignedbb) {
        return this.b((Entity) null, axisalignedbb, Collections.emptySet()).allMatch(VoxelShape::isEmpty);
    }

    default boolean getCubes(Entity entity) {
        return this.b(entity, entity.getBoundingBox(), Collections.emptySet()).allMatch(VoxelShape::isEmpty);
    }

    default boolean getCubes(Entity entity, AxisAlignedBB axisalignedbb) {
        return this.b(entity, axisalignedbb, Collections.emptySet()).allMatch(VoxelShape::isEmpty);
    }

    default boolean a(Entity entity, AxisAlignedBB axisalignedbb, Set<Entity> set) {
        return this.b(entity, axisalignedbb, set).allMatch(VoxelShape::isEmpty);
    }

    default Stream<VoxelShape> a(@Nullable Entity entity, VoxelShape voxelshape, Set<Entity> set) {
        return Stream.empty();
    }

    default Stream<VoxelShape> b(@Nullable Entity entity, AxisAlignedBB axisalignedbb, Set<Entity> set) {
        VoxelShape voxelshape = VoxelShapes.a(axisalignedbb);
        VoxelShapeCollision voxelshapecollision;
        Stream stream;

        if (entity == null) {
            voxelshapecollision = VoxelShapeCollision.a();
            stream = Stream.empty();
        } else {
            voxelshapecollision = VoxelShapeCollision.a(entity);
            VoxelShape voxelshape1 = this.getWorldBorder().a();
            boolean flag = VoxelShapes.c(voxelshape1, VoxelShapes.a(entity.getBoundingBox().shrink(1.0E-7D)), OperatorBoolean.AND);
            boolean flag1 = VoxelShapes.c(voxelshape1, VoxelShapes.a(entity.getBoundingBox().g(1.0E-7D)), OperatorBoolean.AND);

            if (!flag && flag1) {
                stream = Stream.concat(Stream.of(voxelshape1), this.a(entity, voxelshape, set));
            } else {
                stream = this.a(entity, voxelshape, set);
            }
        }

        int i = MathHelper.floor(voxelshape.b(EnumDirection.EnumAxis.X)) - 1;
        int j = MathHelper.f(voxelshape.c(EnumDirection.EnumAxis.X)) + 1;
        int k = MathHelper.floor(voxelshape.b(EnumDirection.EnumAxis.Y)) - 1;
        int l = MathHelper.f(voxelshape.c(EnumDirection.EnumAxis.Y)) + 1;
        int i1 = MathHelper.floor(voxelshape.b(EnumDirection.EnumAxis.Z)) - 1;
        int j1 = MathHelper.f(voxelshape.c(EnumDirection.EnumAxis.Z)) + 1;
        VoxelShapeBitSet voxelshapebitset = new VoxelShapeBitSet(j - i, l - k, j1 - i1);
        Predicate<VoxelShape> predicate = (voxelshape2) -> {
            return !voxelshape2.isEmpty() && VoxelShapes.c(voxelshape, voxelshape2, OperatorBoolean.AND);
        };
        AtomicReference<ChunkCoordIntPair> atomicreference = new AtomicReference(new ChunkCoordIntPair(i >> 4, i1 >> 4));
        AtomicReference<IChunkAccess> atomicreference1 = new AtomicReference(this.getChunkAt(i >> 4, i1 >> 4, this.O(), false));
        Stream<VoxelShape> stream1 = BlockPosition.a(i, k, i1, j - 1, l - 1, j1 - 1).map((blockposition) -> {
            int k1 = blockposition.getX();
            int l1 = blockposition.getY();
            int i2 = blockposition.getZ();

            if (World.b(l1)) {
                return VoxelShapes.a();
            } else {
                boolean flag2 = k1 == i || k1 == j - 1;
                boolean flag3 = l1 == k || l1 == l - 1;
                boolean flag4 = i2 == i1 || i2 == j1 - 1;
                ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) atomicreference.get();
                int j2 = k1 >> 4;
                int k2 = i2 >> 4;
                IChunkAccess ichunkaccess;

                if (chunkcoordintpair.x == j2 && chunkcoordintpair.z == k2) {
                    ichunkaccess = (IChunkAccess) atomicreference1.get();
                } else {
                    ichunkaccess = this.getChunkAt(j2, k2, this.O(), false);
                    atomicreference.set(new ChunkCoordIntPair(j2, k2));
                    atomicreference1.set(ichunkaccess);
                }

                if ((!flag2 || !flag3) && (!flag3 || !flag4) && (!flag4 || !flag2) && ichunkaccess != null) {
                    VoxelShape voxelshape2 = ichunkaccess.getType(blockposition).b((IBlockAccess) this, blockposition, voxelshapecollision);
                    VoxelShape voxelshape3 = VoxelShapes.a().a((double) (-k1), (double) (-l1), (double) (-i2));

                    if (VoxelShapes.c(voxelshape3, voxelshape2, OperatorBoolean.AND)) {
                        return VoxelShapes.a();
                    } else if (voxelshape2 == VoxelShapes.b()) {
                        voxelshapebitset.a(k1 - i, l1 - k, i2 - i1, true, true);
                        return VoxelShapes.a();
                    } else {
                        return voxelshape2.a((double) k1, (double) l1, (double) i2);
                    }
                } else {
                    return VoxelShapes.a();
                }
            }
        });

        return Stream.concat(stream, Stream.concat(stream1, Stream.generate(() -> {
            return new VoxelShapeWorldRegion(voxelshapebitset, i, k, i1);
        }).limit(1L)).filter(predicate));
    }

    default boolean y(BlockPosition blockposition) {
        return this.getFluid(blockposition).a(TagsFluid.WATER);
    }

    default boolean containsLiquid(AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.f(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.f(axisalignedbb.maxY);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.f(axisalignedbb.maxZ);
        BlockPosition.PooledBlockPosition blockposition_pooledblockposition = BlockPosition.PooledBlockPosition.r();
        Throwable throwable = null;

        try {
            for (int k1 = i; k1 < j; ++k1) {
                for (int l1 = k; l1 < l; ++l1) {
                    for (int i2 = i1; i2 < j1; ++i2) {
                        IBlockData iblockdata = this.getType(blockposition_pooledblockposition.d(k1, l1, i2));

                        if (!iblockdata.p().isEmpty()) {
                            boolean flag = true;

                            return flag;
                        }
                    }
                }
            }

            return false;
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            if (blockposition_pooledblockposition != null) {
                if (throwable != null) {
                    try {
                        blockposition_pooledblockposition.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    blockposition_pooledblockposition.close();
                }
            }

        }
    }

    default int getLightLevel(BlockPosition blockposition) {
        return this.d(blockposition, this.c());
    }

    default int d(BlockPosition blockposition, int i) {
        return blockposition.getX() >= -30000000 && blockposition.getZ() >= -30000000 && blockposition.getX() < 30000000 && blockposition.getZ() < 30000000 ? this.getLightLevel(blockposition, i) : 15;
    }

    @Deprecated
    default boolean isLoaded(BlockPosition blockposition) {
        return this.isChunkLoaded(blockposition.getX() >> 4, blockposition.getZ() >> 4);
    }

    @Deprecated
    default boolean areChunksLoadedBetween(BlockPosition blockposition, BlockPosition blockposition1) {
        return this.isAreaLoaded(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
    }

    @Deprecated
    default boolean isAreaLoaded(int i, int j, int k, int l, int i1, int j1) {
        if (i1 >= 0 && j < 256) {
            i >>= 4;
            k >>= 4;
            l >>= 4;
            j1 >>= 4;

            for (int k1 = i; k1 <= l; ++k1) {
                for (int l1 = k; l1 <= j1; ++l1) {
                    if (!this.isChunkLoaded(k1, l1)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    WorldProvider getWorldProvider();
}
