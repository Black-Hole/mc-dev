package net.minecraft.server;

import java.util.Random;

public class BlockPlant extends Block {

    protected static final AxisAlignedBB b = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);

    protected BlockPlant() {
        this(Material.PLANT);
    }

    protected BlockPlant(Material material) {
        this(material, material.r());
    }

    protected BlockPlant(Material material, MaterialMapColor materialmapcolor) {
        super(material, materialmapcolor);
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return super.canPlace(world, blockposition) && this.i(world.getType(blockposition.down()));
    }

    protected boolean i(IBlockData iblockdata) {
        return iblockdata.getBlock() == Blocks.GRASS || iblockdata.getBlock() == Blocks.DIRT || iblockdata.getBlock() == Blocks.FARMLAND;
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        super.doPhysics(world, blockposition, iblockdata, block);
        this.e(world, blockposition, iblockdata);
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        this.e(world, blockposition, iblockdata);
    }

    protected void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.f(world, blockposition, iblockdata)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
        }

    }

    public boolean f(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return this.i(world.getType(blockposition.down()));
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockPlant.b;
    }

    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockPlant.k;
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }
}
