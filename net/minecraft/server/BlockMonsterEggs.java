package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;

public class BlockMonsterEggs extends Block {

    private final Block a;
    private static final Map<Block, Block> b = Maps.newIdentityHashMap();

    public BlockMonsterEggs(Block block, Block.Info block_info) {
        super(block_info);
        this.a = block;
        BlockMonsterEggs.b.put(block, this);
    }

    public int a(IBlockData iblockdata, Random random) {
        return 0;
    }

    public Block b() {
        return this.a;
    }

    public static boolean j(IBlockData iblockdata) {
        return BlockMonsterEggs.b.containsKey(iblockdata.getBlock());
    }

    protected ItemStack s(IBlockData iblockdata) {
        return new ItemStack(this.a);
    }

    public void dropNaturally(IBlockData iblockdata, World world, BlockPosition blockposition, float f, int i) {
        if (!world.isClientSide && world.getGameRules().getBoolean("doTileDrops")) {
            EntitySilverfish entitysilverfish = new EntitySilverfish(world);

            entitysilverfish.setPositionRotation((double) blockposition.getX() + 0.5D, (double) blockposition.getY(), (double) blockposition.getZ() + 0.5D, 0.0F, 0.0F);
            world.addEntity(entitysilverfish);
            entitysilverfish.doSpawnEffect();
        }

    }

    public static IBlockData f(Block block) {
        return ((Block) BlockMonsterEggs.b.get(block)).getBlockData();
    }
}
