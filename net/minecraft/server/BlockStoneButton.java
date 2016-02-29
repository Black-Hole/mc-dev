package net.minecraft.server;

public class BlockStoneButton extends BlockButtonAbstract {

    protected BlockStoneButton() {
        super(false);
    }

    protected void a(EntityHuman entityhuman, World world, BlockPosition blockposition) {
        world.a(entityhuman, blockposition, SoundEffects.gb, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

    protected void b(World world, BlockPosition blockposition) {
        world.a((EntityHuman) null, blockposition, SoundEffects.ga, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }
}
