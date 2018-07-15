package net.minecraft.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class BlockVine extends Block {

    public static final BlockStateBoolean UP = BlockSprawling.q;
    public static final BlockStateBoolean NORTH = BlockSprawling.a;
    public static final BlockStateBoolean EAST = BlockSprawling.b;
    public static final BlockStateBoolean SOUTH = BlockSprawling.c;
    public static final BlockStateBoolean WEST = BlockSprawling.p;
    public static final Map<EnumDirection, BlockStateBoolean> r = (Map) BlockSprawling.s.entrySet().stream().filter((entry) -> {
        return entry.getKey() != EnumDirection.DOWN;
    }).collect(SystemUtils.a());
    protected static final VoxelShape s = Block.a(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape t = Block.a(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    protected static final VoxelShape u = Block.a(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape v = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    protected static final VoxelShape w = Block.a(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);

    public BlockVine(Block.Info block_info) {
        super(block_info);
        this.v((IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) this.blockStateList.getBlockData()).set(BlockVine.UP, Boolean.valueOf(false))).set(BlockVine.NORTH, Boolean.valueOf(false))).set(BlockVine.EAST, Boolean.valueOf(false))).set(BlockVine.SOUTH, Boolean.valueOf(false))).set(BlockVine.WEST, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        VoxelShape voxelshape = VoxelShapes.a();

        if (((Boolean) iblockdata.get(BlockVine.UP)).booleanValue()) {
            voxelshape = VoxelShapes.a(voxelshape, BlockVine.s);
        }

        if (((Boolean) iblockdata.get(BlockVine.NORTH)).booleanValue()) {
            voxelshape = VoxelShapes.a(voxelshape, BlockVine.v);
        }

        if (((Boolean) iblockdata.get(BlockVine.EAST)).booleanValue()) {
            voxelshape = VoxelShapes.a(voxelshape, BlockVine.u);
        }

        if (((Boolean) iblockdata.get(BlockVine.SOUTH)).booleanValue()) {
            voxelshape = VoxelShapes.a(voxelshape, BlockVine.w);
        }

        if (((Boolean) iblockdata.get(BlockVine.WEST)).booleanValue()) {
            voxelshape = VoxelShapes.a(voxelshape, BlockVine.t);
        }

        return voxelshape;
    }

    public boolean a(IBlockData iblockdata) {
        return false;
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        return this.j(this.m(iblockdata, iworldreader, blockposition));
    }

    private boolean j(IBlockData iblockdata) {
        return this.w(iblockdata) > 0;
    }

    private int w(IBlockData iblockdata) {
        int i = 0;
        Iterator iterator = BlockVine.r.values().iterator();

        while (iterator.hasNext()) {
            BlockStateBoolean blockstateboolean = (BlockStateBoolean) iterator.next();

            if (((Boolean) iblockdata.get(blockstateboolean)).booleanValue()) {
                ++i;
            }
        }

        return i;
    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        if (enumdirection == EnumDirection.DOWN) {
            return false;
        } else {
            BlockPosition blockposition1 = blockposition.shift(enumdirection);

            if (this.b(iblockaccess, blockposition1, enumdirection)) {
                return true;
            } else if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
                return false;
            } else {
                BlockStateBoolean blockstateboolean = (BlockStateBoolean) BlockVine.r.get(enumdirection);
                IBlockData iblockdata = iblockaccess.getType(blockposition.up());

                return iblockdata.getBlock() == this && ((Boolean) iblockdata.get(blockstateboolean)).booleanValue();
            }
        }
    }

    private boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);

        return iblockdata.c(iblockaccess, blockposition, enumdirection.opposite()) == EnumBlockFaceShape.SOLID && !f(iblockdata.getBlock());
    }

    protected static boolean f(Block block) {
        return block instanceof BlockShulkerBox || block instanceof BlockStainedGlass || block == Blocks.BEACON || block == Blocks.CAULDRON || block == Blocks.GLASS || block == Blocks.PISTON || block == Blocks.STICKY_PISTON || block == Blocks.PISTON_HEAD || block == Blocks.OAK_TRAPDOOR;
    }

    private IBlockData m(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.up();

        if (((Boolean) iblockdata.get(BlockVine.UP)).booleanValue()) {
            iblockdata = (IBlockData) iblockdata.set(BlockVine.UP, Boolean.valueOf(this.b(iblockaccess, blockposition1, EnumDirection.DOWN)));
        }

        IBlockData iblockdata1 = null;
        Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumDirection enumdirection = (EnumDirection) iterator.next();
            BlockStateBoolean blockstateboolean = getDirection(enumdirection);

            if (((Boolean) iblockdata.get(blockstateboolean)).booleanValue()) {
                boolean flag = this.a(iblockaccess, blockposition, enumdirection);

                if (!flag) {
                    if (iblockdata1 == null) {
                        iblockdata1 = iblockaccess.getType(blockposition1);
                    }

                    flag = iblockdata1.getBlock() == this && ((Boolean) iblockdata1.get(blockstateboolean)).booleanValue();
                }

                iblockdata = (IBlockData) iblockdata.set(blockstateboolean, Boolean.valueOf(flag));
            }
        }

        return iblockdata;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection == EnumDirection.DOWN) {
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        } else {
            IBlockData iblockdata2 = this.m(iblockdata, generatoraccess, blockposition);

            return !this.j(iblockdata2) ? Blocks.AIR.getBlockData() : iblockdata2;
        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        if (!world.isClientSide) {
            IBlockData iblockdata1 = this.m(iblockdata, world, blockposition);

            if (iblockdata1 != iblockdata) {
                if (this.j(iblockdata1)) {
                    world.setTypeAndData(blockposition, iblockdata1, 2);
                } else {
                    iblockdata.a(world, blockposition, 0);
                    world.setAir(blockposition);
                }

            } else if (world.random.nextInt(4) == 0) {
                EnumDirection enumdirection = EnumDirection.a(random);
                BlockPosition blockposition1 = blockposition.up();
                BlockPosition blockposition2;
                IBlockData iblockdata2;
                EnumDirection enumdirection1;

                if (enumdirection.k().c() && !((Boolean) iblockdata.get(getDirection(enumdirection))).booleanValue()) {
                    if (this.a((IBlockAccess) world, blockposition)) {
                        blockposition2 = blockposition.shift(enumdirection);
                        iblockdata2 = world.getType(blockposition2);
                        if (iblockdata2.isAir()) {
                            enumdirection1 = enumdirection.e();
                            EnumDirection enumdirection2 = enumdirection.f();
                            boolean flag = ((Boolean) iblockdata.get(getDirection(enumdirection1))).booleanValue();
                            boolean flag1 = ((Boolean) iblockdata.get(getDirection(enumdirection2))).booleanValue();
                            BlockPosition blockposition3 = blockposition2.shift(enumdirection1);
                            BlockPosition blockposition4 = blockposition2.shift(enumdirection2);

                            if (flag && this.b(world, blockposition3, enumdirection1)) {
                                world.setTypeAndData(blockposition2, (IBlockData) this.getBlockData().set(getDirection(enumdirection1), Boolean.valueOf(true)), 2);
                            } else if (flag1 && this.b(world, blockposition4, enumdirection2)) {
                                world.setTypeAndData(blockposition2, (IBlockData) this.getBlockData().set(getDirection(enumdirection2), Boolean.valueOf(true)), 2);
                            } else {
                                EnumDirection enumdirection3 = enumdirection.opposite();

                                if (flag && world.isEmpty(blockposition3) && this.b(world, blockposition.shift(enumdirection1), enumdirection3)) {
                                    world.setTypeAndData(blockposition3, (IBlockData) this.getBlockData().set(getDirection(enumdirection3), Boolean.valueOf(true)), 2);
                                } else if (flag1 && world.isEmpty(blockposition4) && this.b(world, blockposition.shift(enumdirection2), enumdirection3)) {
                                    world.setTypeAndData(blockposition4, (IBlockData) this.getBlockData().set(getDirection(enumdirection3), Boolean.valueOf(true)), 2);
                                } else if ((double) world.random.nextFloat() < 0.05D && this.b(world, blockposition2.up(), EnumDirection.UP)) {
                                    world.setTypeAndData(blockposition2, (IBlockData) this.getBlockData().set(BlockVine.UP, Boolean.valueOf(true)), 2);
                                }
                            }
                        } else if (this.b(world, blockposition2, enumdirection)) {
                            world.setTypeAndData(blockposition, (IBlockData) iblockdata.set(getDirection(enumdirection), Boolean.valueOf(true)), 2);
                        }

                    }
                } else {
                    if (enumdirection == EnumDirection.UP && blockposition.getY() < 255) {
                        if (this.a((IBlockAccess) world, blockposition, enumdirection)) {
                            world.setTypeAndData(blockposition, (IBlockData) iblockdata.set(BlockVine.UP, Boolean.valueOf(true)), 2);
                            return;
                        }

                        if (world.isEmpty(blockposition1)) {
                            if (!this.a((IBlockAccess) world, blockposition)) {
                                return;
                            }

                            IBlockData iblockdata3 = iblockdata;
                            Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

                            while (iterator.hasNext()) {
                                enumdirection1 = (EnumDirection) iterator.next();
                                if (random.nextBoolean() || !this.b(world, blockposition1.shift(enumdirection1), EnumDirection.UP)) {
                                    iblockdata3 = (IBlockData) iblockdata3.set(getDirection(enumdirection1), Boolean.valueOf(false));
                                }
                            }

                            if (this.x(iblockdata3)) {
                                world.setTypeAndData(blockposition1, iblockdata3, 2);
                            }

                            return;
                        }
                    }

                    if (blockposition.getY() > 0) {
                        blockposition2 = blockposition.down();
                        iblockdata2 = world.getType(blockposition2);
                        if (iblockdata2.isAir() || iblockdata2.getBlock() == this) {
                            IBlockData iblockdata4 = iblockdata2.isAir() ? this.getBlockData() : iblockdata2;
                            IBlockData iblockdata5 = this.a(iblockdata, iblockdata4, random);

                            if (iblockdata4 != iblockdata5 && this.x(iblockdata5)) {
                                world.setTypeAndData(blockposition2, iblockdata5, 2);
                            }
                        }
                    }

                }
            }
        }
    }

    private IBlockData a(IBlockData iblockdata, IBlockData iblockdata1, Random random) {
        Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumDirection enumdirection = (EnumDirection) iterator.next();

            if (random.nextBoolean()) {
                BlockStateBoolean blockstateboolean = getDirection(enumdirection);

                if (((Boolean) iblockdata.get(blockstateboolean)).booleanValue()) {
                    iblockdata1 = (IBlockData) iblockdata1.set(blockstateboolean, Boolean.valueOf(true));
                }
            }
        }

        return iblockdata1;
    }

    private boolean x(IBlockData iblockdata) {
        return ((Boolean) iblockdata.get(BlockVine.NORTH)).booleanValue() || ((Boolean) iblockdata.get(BlockVine.EAST)).booleanValue() || ((Boolean) iblockdata.get(BlockVine.SOUTH)).booleanValue() || ((Boolean) iblockdata.get(BlockVine.WEST)).booleanValue();
    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        boolean flag = true;
        Iterable iterable = BlockPosition.MutableBlockPosition.b(blockposition.getX() - 4, blockposition.getY() - 1, blockposition.getZ() - 4, blockposition.getX() + 4, blockposition.getY() + 1, blockposition.getZ() + 4);
        int i = 5;
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            BlockPosition blockposition1 = (BlockPosition) iterator.next();

            if (iblockaccess.getType(blockposition1).getBlock() == this) {
                --i;
                if (i <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
        IBlockData iblockdata1 = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());

        return iblockdata1.getBlock() == this ? this.w(iblockdata1) < BlockVine.r.size() : super.a(iblockdata, blockactioncontext);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());
        boolean flag = iblockdata.getBlock() == this;
        IBlockData iblockdata1 = flag ? iblockdata : this.getBlockData();
        EnumDirection[] aenumdirection = blockactioncontext.e();
        int i = aenumdirection.length;

        for (int j = 0; j < i; ++j) {
            EnumDirection enumdirection = aenumdirection[j];

            if (enumdirection != EnumDirection.DOWN) {
                BlockStateBoolean blockstateboolean = getDirection(enumdirection);
                boolean flag1 = flag && ((Boolean) iblockdata.get(blockstateboolean)).booleanValue();

                if (!flag1 && this.a((IBlockAccess) blockactioncontext.getWorld(), blockactioncontext.getClickPosition(), enumdirection)) {
                    return (IBlockData) iblockdata1.set(blockstateboolean, Boolean.valueOf(true));
                }
            }
        }

        return flag ? iblockdata1 : null;
    }

    public IMaterial getDropType(IBlockData iblockdata, World world, BlockPosition blockposition, int i) {
        return Items.AIR;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        if (!world.isClientSide && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
            entityhuman.applyExhaustion(0.005F);
            a(world, blockposition, new ItemStack(Blocks.VINE));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }

    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
        blockstatelist_a.a(new IBlockState[] { BlockVine.UP, BlockVine.NORTH, BlockVine.EAST, BlockVine.SOUTH, BlockVine.WEST});
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (enumblockrotation) {
        case CLOCKWISE_180:
            return (IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.SOUTH))).set(BlockVine.EAST, iblockdata.get(BlockVine.WEST))).set(BlockVine.SOUTH, iblockdata.get(BlockVine.NORTH))).set(BlockVine.WEST, iblockdata.get(BlockVine.EAST));

        case COUNTERCLOCKWISE_90:
            return (IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.EAST))).set(BlockVine.EAST, iblockdata.get(BlockVine.SOUTH))).set(BlockVine.SOUTH, iblockdata.get(BlockVine.WEST))).set(BlockVine.WEST, iblockdata.get(BlockVine.NORTH));

        case CLOCKWISE_90:
            return (IBlockData) ((IBlockData) ((IBlockData) ((IBlockData) iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.WEST))).set(BlockVine.EAST, iblockdata.get(BlockVine.NORTH))).set(BlockVine.SOUTH, iblockdata.get(BlockVine.EAST))).set(BlockVine.WEST, iblockdata.get(BlockVine.SOUTH));

        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        switch (enumblockmirror) {
        case LEFT_RIGHT:
            return (IBlockData) ((IBlockData) iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.SOUTH))).set(BlockVine.SOUTH, iblockdata.get(BlockVine.NORTH));

        case FRONT_BACK:
            return (IBlockData) ((IBlockData) iblockdata.set(BlockVine.EAST, iblockdata.get(BlockVine.WEST))).set(BlockVine.WEST, iblockdata.get(BlockVine.EAST));

        default:
            return super.a(iblockdata, enumblockmirror);
        }
    }

    public static BlockStateBoolean getDirection(EnumDirection enumdirection) {
        return (BlockStateBoolean) BlockVine.r.get(enumdirection);
    }

    public EnumBlockFaceShape a(IBlockAccess iblockaccess, IBlockData iblockdata, BlockPosition blockposition, EnumDirection enumdirection) {
        return EnumBlockFaceShape.UNDEFINED;
    }
}
