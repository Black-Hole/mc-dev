package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInRecipeDisplayed implements Packet<PacketListenerPlayIn> {

    private PacketPlayInRecipeDisplayed.a a;
    private IRecipe b;
    private boolean c;
    private boolean d;

    public PacketPlayInRecipeDisplayed() {}

    public PacketPlayInRecipeDisplayed(IRecipe irecipe) {
        this.a = PacketPlayInRecipeDisplayed.a.a;
        this.b = irecipe;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = (PacketPlayInRecipeDisplayed.a) packetdataserializer.a(PacketPlayInRecipeDisplayed.a.class);
        if (this.a == PacketPlayInRecipeDisplayed.a.a) {
            this.b = CraftingManager.a(packetdataserializer.readInt());
        } else if (this.a == PacketPlayInRecipeDisplayed.a.b) {
            this.c = packetdataserializer.readBoolean();
            this.d = packetdataserializer.readBoolean();
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a((Enum) this.a);
        if (this.a == PacketPlayInRecipeDisplayed.a.a) {
            packetdataserializer.writeInt(CraftingManager.a(this.b));
        } else if (this.a == PacketPlayInRecipeDisplayed.a.b) {
            packetdataserializer.writeBoolean(this.c);
            packetdataserializer.writeBoolean(this.d);
        }

    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public PacketPlayInRecipeDisplayed.a a() {
        return this.a;
    }

    public IRecipe b() {
        return this.b;
    }

    public boolean c() {
        return this.c;
    }

    public boolean d() {
        return this.d;
    }

    public static enum a {

        a, b;

        private a() {}
    }
}
