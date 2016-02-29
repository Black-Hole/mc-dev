package net.minecraft.server;

public class TileEntityFlowerPot extends TileEntity {

    private Item a;
    private int f;

    public TileEntityFlowerPot() {}

    public TileEntityFlowerPot(Item item, int i) {
        this.a = item;
        this.f = i;
    }

    public void save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        MinecraftKey minecraftkey = (MinecraftKey) Item.REGISTRY.b(this.a);

        nbttagcompound.setString("Item", minecraftkey == null ? "" : minecraftkey.toString());
        nbttagcompound.setInt("Data", this.f);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKeyOfType("Item", 8)) {
            this.a = Item.d(nbttagcompound.getString("Item"));
        } else {
            this.a = Item.getById(nbttagcompound.getInt("Item"));
        }

        this.f = nbttagcompound.getInt("Data");
    }

    public Packet<?> getUpdatePacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.save(nbttagcompound);
        nbttagcompound.remove("Item");
        nbttagcompound.setInt("Item", Item.getId(this.a));
        return new PacketPlayOutTileEntityData(this.position, 5, nbttagcompound);
    }

    public void a(Item item, int i) {
        this.a = item;
        this.f = i;
    }

    public ItemStack b() {
        return this.a == null ? null : new ItemStack(this.a, 1, this.f);
    }

    public Item c() {
        return this.a;
    }

    public int d() {
        return this.f;
    }
}
