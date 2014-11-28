package net.minecraft.server;

public class WorldMapHumanTracker {

    public final EntityHuman trackee;
    private boolean d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    public int b;
    final WorldMap worldMap;

    public WorldMapHumanTracker(WorldMap worldmap, EntityHuman entityhuman) {
        this.worldMap = worldmap;
        this.d = true;
        this.e = 0;
        this.f = 0;
        this.g = 127;
        this.h = 127;
        this.trackee = entityhuman;
    }

    public Packet a(ItemStack itemstack) {
        if (this.d) {
            this.d = false;
            return new PacketPlayOutMap(itemstack.getData(), this.worldMap.scale, this.worldMap.decorations.values(), this.worldMap.colors, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f);
        } else {
            return this.i++ % 5 == 0 ? new PacketPlayOutMap(itemstack.getData(), this.worldMap.scale, this.worldMap.decorations.values(), this.worldMap.colors, 0, 0, 0, 0) : null;
        }
    }

    public void a(int i, int j) {
        if (this.d) {
            this.e = Math.min(this.e, i);
            this.f = Math.min(this.f, j);
            this.g = Math.max(this.g, i);
            this.h = Math.max(this.h, j);
        } else {
            this.d = true;
            this.e = i;
            this.f = j;
            this.g = i;
            this.h = j;
        }

    }
}
