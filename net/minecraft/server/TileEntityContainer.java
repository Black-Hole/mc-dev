package net.minecraft.server;

public abstract class TileEntityContainer extends TileEntity implements ITileEntityContainer, ITileInventory {

    private ChestLock a;

    public TileEntityContainer() {
        this.a = ChestLock.a;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = ChestLock.b(nbttagcompound);
    }

    public void save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        if (this.a != null) {
            this.a.a(nbttagcompound);
        }

    }

    public boolean x_() {
        return this.a != null && !this.a.a();
    }

    public ChestLock y_() {
        return this.a;
    }

    public void a(ChestLock chestlock) {
        this.a = chestlock;
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        return (IChatBaseComponent) (this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }
}
