package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInTeleportAccept implements Packet<PacketListenerPlayIn> {

    private int a;

    public PacketPlayInTeleportAccept() {}

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.g();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.b(this.a);
    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public int a() {
        return this.a;
    }

    public void a(PacketListener packetlistener) {
        this.a((PacketListenerPlayIn) packetlistener);
    }
}
