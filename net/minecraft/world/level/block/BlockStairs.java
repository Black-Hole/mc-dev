package net.minecraft.world.level.block;

import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyHalf;
import net.minecraft.world.level.block.state.properties.BlockPropertyStairsShape;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockStairs extends Block implements IBlockWaterlogged {

    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateEnum<BlockPropertyHalf> HALF = BlockProperties.HALF;
    public static final BlockStateEnum<BlockPropertyStairsShape> SHAPE = BlockProperties.STAIRS_SHAPE;
    public static final BlockStateBoolean WATERLOGGED = BlockProperties.WATERLOGGED;
    protected static final VoxelShape TOP_AABB = BlockStepAbstract.TOP_AABB;
    protected static final VoxelShape BOTTOM_AABB = BlockStepAbstract.BOTTOM_AABB;
    protected static final VoxelShape OCTET_NNN = Block.a(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
    protected static final VoxelShape OCTET_NNP = Block.a(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
    protected static final VoxelShape OCTET_NPN = Block.a(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
    protected static final VoxelShape OCTET_NPP = Block.a(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape OCTET_PNN = Block.a(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
    protected static final VoxelShape OCTET_PNP = Block.a(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape OCTET_PPN = Block.a(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape OCTET_PPP = Block.a(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape[] TOP_SHAPES = a(BlockStairs.TOP_AABB, BlockStairs.OCTET_NNN, BlockStairs.OCTET_PNN, BlockStairs.OCTET_NNP, BlockStairs.OCTET_PNP);
    protected static final VoxelShape[] BOTTOM_SHAPES = a(BlockStairs.BOTTOM_AABB, BlockStairs.OCTET_NPN, BlockStairs.OCTET_PPN, BlockStairs.OCTET_NPP, BlockStairs.OCTET_PPP);
    private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    private final Block base;
    private final IBlockData baseState;

    private static VoxelShape[] a(VoxelShape voxelshape, VoxelShape voxelshape1, VoxelShape voxelshape2, VoxelShape voxelshape3, VoxelShape voxelshape4) {
        return (VoxelShape[]) IntStream.range(0, 16).mapToObj((i) -> {
            return a(i, voxelshape, voxelshape1, voxelshape2, voxelshape3, voxelshape4);
        }).toArray((i) -> {
            return new VoxelShape[i];
        });
    }

    private static VoxelShape a(int i, VoxelShape voxelshape, VoxelShape voxelshape1, VoxelShape voxelshape2, VoxelShape voxelshape3, VoxelShape voxelshape4) {
        VoxelShape voxelshape5 = voxelshape;

        if ((i & 1) != 0) {
            voxelshape5 = VoxelShapes.a(voxelshape, voxelshape1);
        }

        if ((i & 2) != 0) {
            voxelshape5 = VoxelShapes.a(voxelshape5, voxelshape2);
        }

        if ((i & 4) != 0) {
            voxelshape5 = VoxelShapes.a(voxelshape5, voxelshape3);
        }

        if ((i & 8) != 0) {
            voxelshape5 = VoxelShapes.a(voxelshape5, voxelshape4);
        }

        return voxelshape5;
    }

    protected BlockStairs(IBlockData iblockdata, BlockBase.Info blockbase_info) {
        super(blockbase_info);
        this.k((IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) this.stateDefinition.getBlockData()).set(BlockStairs.FACING, EnumDirection.NORTH)).set(BlockStairs.HALF, BlockPropertyHalf.BOTTOM)).set(BlockStairs.SHAPE, BlockPropertyStairsShape.STRAIGHT)).set(BlockStairs.WATERLOGGED, false));
        this.base = iblockdata.getBlock();
        this.baseState = iblockdata;
    }

    @Override
    public boolean g_(IBlockData iblockdata) {
        return true;
    }

    @Override
    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
        return (iblockdata.get(BlockStairs.HALF) == BlockPropertyHalf.TOP ? BlockStairs.TOP_SHAPES : BlockStairs.BOTTOM_SHAPES)[BlockStairs.SHAPE_BY_STATE[this.n(iblockdata)]];
    }

    private int n(IBlockData iblockdata) {
        return ((BlockPropertyStairsShape) iblockdata.get(BlockStairs.SHAPE)).ordinal() * 4 + ((EnumDirection) iblockdata.get(BlockStairs.FACING)).get2DRotationValue();
    }

    @Override
    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        this.base.a(iblockdata, world, blockposition, random);
    }

    @Override
    public void attack(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
        this.baseState.attack(world, blockposition, entityhuman);
    }

    @Override
    public void postBreak(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        this.base.postBreak(generatoraccess, blockposition, iblockdata);
    }

    @Override
    public float getDurability() {
        return this.base.getDurability();
    }

    @Override
    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!iblockdata.a(iblockdata.getBlock())) {
            this.baseState.doPhysics(world, blockposition, Blocks.AIR, blockposition, false);
            this.base.onPlace(this.baseState, world, blockposition, iblockdata1, false);
        }
    }

    @Override
    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!iblockdata.a(iblockdata1.getBlock())) {
            this.baseState.remove(world, blockposition, iblockdata1, flag);
        }
    }

    @Override
    public void stepOn(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
        this.base.stepOn(world, blockposition, iblockdata, entity);
    }

    @Override
    public boolean isTicking(IBlockData iblockdata) {
        return this.base.isTicking(iblockdata);
    }

    @Override
    public void tick(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, Random random) {
        this.base.tick(iblockdata, worldserver, blockposition, random);
    }

    @Override
    public void tickAlways(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, Random random) {
        this.base.tickAlways(iblockdata, worldserver, blockposition, random);
    }

    @Override
    public EnumInteractionResult interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand enumhand, MovingObjectPositionBlock movingobjectpositionblock) {
        return this.baseState.interact(world, entityhuman, enumhand, movingobjectpositionblock);
    }

    @Override
    public void wasExploded(World world, BlockPosition blockposition, Explosion explosion) {
        this.base.wasExploded(world, blockposition, explosion);
    }

    @Override
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        EnumDirection enumdirection = blockactioncontext.getClickedFace();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        Fluid fluid = blockactioncontext.getWorld().getFluid(blockposition);
        IBlockData iblockdata = (IBlockData) ((IBlockData) ((IBlockData) this.getBlockData().set(BlockStairs.FACING, blockactioncontext.g())).set(BlockStairs.HALF, enumdirection != EnumDirection.DOWN && (enumdirection == EnumDirection.UP || blockactioncontext.getPos().y - (double) blockposition.getY() <= 0.5D) ? BlockPropertyHalf.BOTTOM : BlockPropertyHalf.TOP)).set(BlockStairs.WATERLOGGED, fluid.getType() == FluidTypes.WATER);

        return (IBlockData) iblockdata.set(BlockStairs.SHAPE, h(iblockdata, blockactioncontext.getWorld(), blockposition));
    }

    @Override
    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if ((Boolean) iblockdata.get(BlockStairs.WATERLOGGED)) {
            generatoraccess.getFluidTickList().a(blockposition, FluidTypes.WATER, FluidTypes.WATER.a((IWorldReader) generatoraccess));
        }

        return enumdirection.n().d() ? (IBlockData) iblockdata.set(BlockStairs.SHAPE, h(iblockdata, generatoraccess, blockposition)) : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    private static BlockPropertyStairsShape h(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        EnumDirection enumdirection = (EnumDirection) iblockdata.get(BlockStairs.FACING);
        IBlockData iblockdata1 = iblockaccess.getType(blockposition.shift(enumdirection));

        if (h(iblockdata1) && iblockdata.get(BlockStairs.HALF) == iblockdata1.get(BlockStairs.HALF)) {
            EnumDirection enumdirection1 = (EnumDirection) iblockdata1.get(BlockStairs.FACING);

            if (enumdirection1.n() != ((EnumDirection) iblockdata.get(BlockStairs.FACING)).n() && c(iblockdata, iblockaccess, blockposition, enumdirection1.opposite())) {
                if (enumdirection1 == enumdirection.h()) {
                    return BlockPropertyStairsShape.OUTER_LEFT;
                }

                return BlockPropertyStairsShape.OUTER_RIGHT;
            }
        }

        IBlockData iblockdata2 = iblockaccess.getType(blockposition.shift(enumdirection.opposite()));

        if (h(iblockdata2) && iblockdata.get(BlockStairs.HALF) == iblockdata2.get(BlockStairs.HALF)) {
            EnumDirection enumdirection2 = (EnumDirection) iblockdata2.get(BlockStairs.FACING);

            if (enumdirection2.n() != ((EnumDirection) iblockdata.get(BlockStairs.FACING)).n() && c(iblockdata, iblockaccess, blockposition, enumdirection2)) {
                if (enumdirection2 == enumdirection.h()) {
                    return BlockPropertyStairsShape.INNER_LEFT;
                }

                return BlockPropertyStairsShape.INNER_RIGHT;
            }
        }

        return BlockPropertyStairsShape.STRAIGHT;
    }

    private static boolean c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        IBlockData iblockdata1 = iblockaccess.getType(blockposition.shift(enumdirection));

        return !h(iblockdata1) || iblockdata1.get(BlockStairs.FACING) != iblockdata.get(BlockStairs.FACING) || iblockdata1.get(BlockStairs.HALF) != iblockdata.get(BlockStairs.HALF);
    }

    public static boolean h(IBlockData iblockdata) {
        return iblockdata.getBlock() instanceof BlockStairs;
    }

    @Override
    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData) iblockdata.set(BlockStairs.FACING, enumblockrotation.a((EnumDirection) iblockdata.get(BlockStairs.FACING)));
    }

    @Override
    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        EnumDirection enumdirection = (EnumDirection) iblockdata.get(BlockStairs.FACING);
        BlockPropertyStairsShape blockpropertystairsshape = (BlockPropertyStairsShape) iblockdata.get(BlockStairs.SHAPE);

        switch (enumblockmirror) {
            case LEFT_RIGHT:
                if (enumdirection.n() == EnumDirection.EnumAxis.Z) {
                    switch (blockpropertystairsshape) {
                        case INNER_LEFT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.OUTER_LEFT);
                        default:
                            return iblockdata.a(EnumBlockRotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if (enumdirection.n() == EnumDirection.EnumAxis.X) {
                    switch (blockpropertystairsshape) {
                        case INNER_LEFT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return (IBlockData) iblockdata.a(EnumBlockRotation.CLOCKWISE_180).set(BlockStairs.SHAPE, BlockPropertyStairsShape.OUTER_LEFT);
                        case STRAIGHT:
                            return iblockdata.a(EnumBlockRotation.CLOCKWISE_180);
                    }
                }
        }

        return super.a(iblockdata, enumblockmirror);
    }

    @Override
    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
        blockstatelist_a.a(BlockStairs.FACING, BlockStairs.HALF, BlockStairs.SHAPE, BlockStairs.WATERLOGGED);
    }

    @Override
    public Fluid c_(IBlockData iblockdata) {
        return (Boolean) iblockdata.get(BlockStairs.WATERLOGGED) ? FluidTypes.WATER.a(false) : super.c_(iblockdata);
    }

    @Override
    public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
        return false;
    }
}
