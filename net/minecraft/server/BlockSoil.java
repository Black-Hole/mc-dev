package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;

public class BlockSoil extends Block {

    public static final BlockStateInteger MOISTURE = BlockStateInteger.of("moisture", 0, 7);
    protected static final AxisAlignedBB b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    protected BlockSoil() {
        super(Material.EARTH);
        this.w(this.blockStateList.getBlockData().set(BlockSoil.MOISTURE, Integer.valueOf(0)));
        this.a(true);
        this.d(255);
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockSoil.b;
    }

    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockSoil.j;
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        int i = ((Integer) iblockdata.get(BlockSoil.MOISTURE)).intValue();

        if (!this.c(world, blockposition) && !world.isRainingAt(blockposition.up())) {
            if (i > 0) {
                world.setTypeAndData(blockposition, iblockdata.set(BlockSoil.MOISTURE, Integer.valueOf(i - 1)), 2);
            } else if (!this.b(world, blockposition)) {
                world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
            }
        } else if (i < 7) {
            world.setTypeAndData(blockposition, iblockdata.set(BlockSoil.MOISTURE, Integer.valueOf(7)), 2);
        }

    }

    public void fallOn(World world, BlockPosition blockposition, Entity entity, float f) {
        if (!world.isClientSide && world.random.nextFloat() < f - 0.5F && entity instanceof EntityLiving && (entity instanceof EntityHuman || world.getGameRules().getBoolean("mobGriefing")) && entity.width * entity.width * entity.length > 0.512F) {
            world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
        }

        super.fallOn(world, blockposition, entity, f);
    }

    private boolean b(World world, BlockPosition blockposition) {
        Block block = world.getType(blockposition.up()).getBlock();

        return block instanceof BlockCrops || block instanceof BlockStem;
    }

    private boolean c(World world, BlockPosition blockposition) {
        Iterator iterator = BlockPosition.b(blockposition.a(-4, 0, -4), blockposition.a(4, 1, 4)).iterator();

        BlockPosition.MutableBlockPosition blockposition_mutableblockposition;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            blockposition_mutableblockposition = (BlockPosition.MutableBlockPosition) iterator.next();
        } while (world.getType(blockposition_mutableblockposition).getMaterial() != Material.WATER);

        return true;
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        super.doPhysics(world, blockposition, iblockdata, block);
        if (world.getType(blockposition.up()).getMaterial().isBuildable()) {
            world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
        }

    }

    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), random, i);
    }

    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return new ItemStack(Blocks.DIRT);
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockSoil.MOISTURE, Integer.valueOf(i & 7));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockSoil.MOISTURE)).intValue();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockSoil.MOISTURE});
    }
}
