package net.minecraft.world.item.context;

import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class BlockActionContextDirectional extends BlockActionContext {

    private final EnumDirection direction;

    public BlockActionContextDirectional(World world, BlockPosition blockposition, EnumDirection enumdirection, ItemStack itemstack, EnumDirection enumdirection1) {
        super(world, (EntityHuman) null, EnumHand.MAIN_HAND, itemstack, new MovingObjectPositionBlock(Vec3D.c((BaseBlockPosition) blockposition), enumdirection1, blockposition, false));
        this.direction = enumdirection;
    }

    @Override
    public BlockPosition getClickPosition() {
        return this.j().getBlockPosition();
    }

    @Override
    public boolean b() {
        return this.getWorld().getType(this.j().getBlockPosition()).a((BlockActionContext) this);
    }

    @Override
    public boolean c() {
        return this.b();
    }

    @Override
    public EnumDirection d() {
        return EnumDirection.DOWN;
    }

    @Override
    public EnumDirection[] f() {
        switch (this.direction) {
            case DOWN:
            default:
                return new EnumDirection[]{EnumDirection.DOWN, EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.WEST, EnumDirection.UP};
            case UP:
                return new EnumDirection[]{EnumDirection.DOWN, EnumDirection.UP, EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.WEST};
            case NORTH:
                return new EnumDirection[]{EnumDirection.DOWN, EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.WEST, EnumDirection.UP, EnumDirection.SOUTH};
            case SOUTH:
                return new EnumDirection[]{EnumDirection.DOWN, EnumDirection.SOUTH, EnumDirection.EAST, EnumDirection.WEST, EnumDirection.UP, EnumDirection.NORTH};
            case WEST:
                return new EnumDirection[]{EnumDirection.DOWN, EnumDirection.WEST, EnumDirection.SOUTH, EnumDirection.UP, EnumDirection.NORTH, EnumDirection.EAST};
            case EAST:
                return new EnumDirection[]{EnumDirection.DOWN, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.UP, EnumDirection.NORTH, EnumDirection.WEST};
        }
    }

    @Override
    public EnumDirection g() {
        return this.direction.n() == EnumDirection.EnumAxis.Y ? EnumDirection.NORTH : this.direction;
    }

    @Override
    public boolean isSneaking() {
        return false;
    }

    @Override
    public float i() {
        return (float) (this.direction.get2DRotationValue() * 90);
    }
}
