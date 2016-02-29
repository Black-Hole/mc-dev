package net.minecraft.server;

public class BlockWoodButton extends BlockButtonAbstract {

    protected BlockWoodButton() {
        super(true);
    }

    protected void a(EntityHuman entityhuman, World world, BlockPosition blockposition) {
        world.a(entityhuman, blockposition, SoundEffects.gZ, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

    protected void b(World world, BlockPosition blockposition) {
        world.a((EntityHuman) null, blockposition, SoundEffects.gY, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }
}
