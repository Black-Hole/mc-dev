package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutOpenWindowMerchant implements Packet<PacketListenerPlayOut> {

    private int a;
    private MerchantRecipeList b;
    private int c;
    private int d;
    private boolean e;

    public PacketPlayOutOpenWindowMerchant() {}

    public PacketPlayOutOpenWindowMerchant(int i, MerchantRecipeList merchantrecipelist, int j, int k, boolean flag) {
        this.a = i;
        this.b = merchantrecipelist;
        this.c = j;
        this.d = k;
        this.e = flag;
    }

    @Override
    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.i();
        this.b = MerchantRecipeList.b(packetdataserializer);
        this.c = packetdataserializer.i();
        this.d = packetdataserializer.i();
        this.e = packetdataserializer.readBoolean();
    }

    @Override
    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a);
        this.b.a(packetdataserializer);
        packetdataserializer.d(this.c);
        packetdataserializer.d(this.d);
        packetdataserializer.writeBoolean(this.e);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
