package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInTabComplete implements Packet<PacketListenerPlayIn> {

    private int a;
    private String b;

    public PacketPlayInTabComplete() {}

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.g();
        this.b = packetdataserializer.e(256);
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a);
        packetdataserializer.a(this.b);
    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public int b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }
}
