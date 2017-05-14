package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInRecipeDisplayed implements Packet<PacketListenerPlayIn> {

    public static int a = 1;
    public static int b = 2;
    private int c;
    private IRecipe d;
    private boolean e;
    private boolean f;

    public PacketPlayInRecipeDisplayed() {}

    public PacketPlayInRecipeDisplayed(IRecipe irecipe) {
        this.c = PacketPlayInRecipeDisplayed.a;
        this.d = irecipe;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.c = packetdataserializer.readInt();
        if (this.c == PacketPlayInRecipeDisplayed.a) {
            this.d = CraftingManager.a(packetdataserializer.readInt());
        } else if (this.c == PacketPlayInRecipeDisplayed.b) {
            this.e = packetdataserializer.readBoolean();
            this.f = packetdataserializer.readBoolean();
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeInt(this.c);
        if (this.c == PacketPlayInRecipeDisplayed.a) {
            packetdataserializer.writeInt(CraftingManager.a(this.d));
        } else if (this.c == PacketPlayInRecipeDisplayed.b) {
            packetdataserializer.writeBoolean(this.e);
            packetdataserializer.writeBoolean(this.f);
        }

    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public int a() {
        return this.c;
    }

    public IRecipe b() {
        return this.d;
    }

    public boolean c() {
        return this.e;
    }

    public boolean d() {
        return this.f;
    }
}
