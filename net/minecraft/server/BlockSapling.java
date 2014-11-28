package net.minecraft.server;

import java.util.Random;

public class BlockSapling extends BlockPlant implements IBlockFragilePlantElement {

    public static final BlockStateEnum TYPE = BlockStateEnum.of("type", EnumLogVariant.class);
    public static final BlockStateInteger STAGE = BlockStateInteger.of("stage", 0, 1);

    protected BlockSapling() {
        this.j(this.blockStateList.getBlockData().set(BlockSapling.TYPE, EnumLogVariant.OAK).set(BlockSapling.STAGE, Integer.valueOf(0)));
        float f = 0.4F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.a(CreativeModeTab.c);
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (!world.isStatic) {
            super.b(world, blockposition, iblockdata, random);
            if (world.getLightLevel(blockposition.up()) >= 9 && random.nextInt(7) == 0) {
                this.grow(world, blockposition, iblockdata, random);
            }

        }
    }

    public void grow(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (((Integer) iblockdata.get(BlockSapling.STAGE)).intValue() == 0) {
            world.setTypeAndData(blockposition, iblockdata.a(BlockSapling.STAGE), 4);
        } else {
            this.e(world, blockposition, iblockdata, random);
        }

    }

    public void e(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        Object object = random.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        int i = 0;
        int j = 0;
        boolean flag = false;

        switch (SwitchHelperLogVariant.a[((EnumLogVariant) iblockdata.get(BlockSapling.TYPE)).ordinal()]) {
        case 1:
            label78:
            for (i = 0; i >= -1; --i) {
                for (j = 0; j >= -1; --j) {
                    if (this.a(world, blockposition.a(i, 0, j), EnumLogVariant.SPRUCE) && this.a(world, blockposition.a(i + 1, 0, j), EnumLogVariant.SPRUCE) && this.a(world, blockposition.a(i, 0, j + 1), EnumLogVariant.SPRUCE) && this.a(world, blockposition.a(i + 1, 0, j + 1), EnumLogVariant.SPRUCE)) {
                        object = new WorldGenMegaTree(false, random.nextBoolean());
                        flag = true;
                        break label78;
                    }
                }
            }

            if (!flag) {
                j = 0;
                i = 0;
                object = new WorldGenTaiga2(true);
            }
            break;

        case 2:
            object = new WorldGenForest(true, false);
            break;

        case 3:
            label93:
            for (i = 0; i >= -1; --i) {
                for (j = 0; j >= -1; --j) {
                    if (this.a(world, blockposition.a(i, 0, j), EnumLogVariant.JUNGLE) && this.a(world, blockposition.a(i + 1, 0, j), EnumLogVariant.JUNGLE) && this.a(world, blockposition.a(i, 0, j + 1), EnumLogVariant.JUNGLE) && this.a(world, blockposition.a(i + 1, 0, j + 1), EnumLogVariant.JUNGLE)) {
                        object = new WorldGenJungleTree(true, 10, 20, EnumLogVariant.JUNGLE.a(), EnumLogVariant.JUNGLE.a());
                        flag = true;
                        break label93;
                    }
                }
            }

            if (!flag) {
                j = 0;
                i = 0;
                object = new WorldGenTrees(true, 4 + random.nextInt(7), EnumLogVariant.JUNGLE.a(), EnumLogVariant.JUNGLE.a(), false);
            }
            break;

        case 4:
            object = new WorldGenAcaciaTree(true);
            break;

        case 5:
            label108:
            for (i = 0; i >= -1; --i) {
                for (j = 0; j >= -1; --j) {
                    if (this.a(world, blockposition.a(i, 0, j), EnumLogVariant.DARK_OAK) && this.a(world, blockposition.a(i + 1, 0, j), EnumLogVariant.DARK_OAK) && this.a(world, blockposition.a(i, 0, j + 1), EnumLogVariant.DARK_OAK) && this.a(world, blockposition.a(i + 1, 0, j + 1), EnumLogVariant.DARK_OAK)) {
                        object = new WorldGenForestTree(true);
                        flag = true;
                        break label108;
                    }
                }
            }

            if (!flag) {
                return;
            }

        case 6:
        }

        IBlockData iblockdata1 = Blocks.AIR.getBlockData();

        if (flag) {
            world.setTypeAndData(blockposition.a(i, 0, j), iblockdata1, 4);
            world.setTypeAndData(blockposition.a(i + 1, 0, j), iblockdata1, 4);
            world.setTypeAndData(blockposition.a(i, 0, j + 1), iblockdata1, 4);
            world.setTypeAndData(blockposition.a(i + 1, 0, j + 1), iblockdata1, 4);
        } else {
            world.setTypeAndData(blockposition, iblockdata1, 4);
        }

        if (!((WorldGenerator) object).generate(world, random, blockposition.a(i, 0, j))) {
            if (flag) {
                world.setTypeAndData(blockposition.a(i, 0, j), iblockdata, 4);
                world.setTypeAndData(blockposition.a(i + 1, 0, j), iblockdata, 4);
                world.setTypeAndData(blockposition.a(i, 0, j + 1), iblockdata, 4);
                world.setTypeAndData(blockposition.a(i + 1, 0, j + 1), iblockdata, 4);
            } else {
                world.setTypeAndData(blockposition, iblockdata, 4);
            }
        }

    }

    public boolean a(World world, BlockPosition blockposition, EnumLogVariant enumlogvariant) {
        IBlockData iblockdata = world.getType(blockposition);

        return iblockdata.getBlock() == this && iblockdata.get(BlockSapling.TYPE) == enumlogvariant;
    }

    public int getDropData(IBlockData iblockdata) {
        return ((EnumLogVariant) iblockdata.get(BlockSapling.TYPE)).a();
    }

    public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
        return true;
    }

    public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        return (double) world.random.nextFloat() < 0.45D;
    }

    public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        this.grow(world, blockposition, iblockdata, random);
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockSapling.TYPE, EnumLogVariant.a(i & 7)).set(BlockSapling.STAGE, Integer.valueOf((i & 8) >> 3));
    }

    public int toLegacyData(IBlockData iblockdata) {
        byte b0 = 0;
        int i = b0 | ((EnumLogVariant) iblockdata.get(BlockSapling.TYPE)).a();

        i |= ((Integer) iblockdata.get(BlockSapling.STAGE)).intValue() << 3;
        return i;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockSapling.TYPE, BlockSapling.STAGE});
    }
}
