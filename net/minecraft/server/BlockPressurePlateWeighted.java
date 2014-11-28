package net.minecraft.server;

public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract {

    public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
    private final int b;

    protected BlockPressurePlateWeighted(String s, Material material, int i) {
        super(material);
        this.j(this.blockStateList.getBlockData().set(BlockPressurePlateWeighted.POWER, Integer.valueOf(0)));
        this.b = i;
    }

    protected int e(World world, BlockPosition blockposition) {
        int i = Math.min(world.a(Entity.class, this.a(blockposition)).size(), this.b);

        if (i > 0) {
            float f = (float) Math.min(this.b, i) / (float) this.b;

            return MathHelper.f(f * 15.0F);
        } else {
            return 0;
        }
    }

    protected int e(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockPressurePlateWeighted.POWER)).intValue();
    }

    protected IBlockData a(IBlockData iblockdata, int i) {
        return iblockdata.set(BlockPressurePlateWeighted.POWER, Integer.valueOf(i));
    }

    public int a(World world) {
        return 10;
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockPressurePlateWeighted.POWER, Integer.valueOf(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockPressurePlateWeighted.POWER)).intValue();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockPressurePlateWeighted.POWER});
    }
}
