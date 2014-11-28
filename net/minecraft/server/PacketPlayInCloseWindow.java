package net.minecraft.server;

public class PacketPlayInCloseWindow implements Packet {

    private int id;

    public PacketPlayInCloseWindow() {}

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) {
        this.id = packetdataserializer.readByte();
    }

    public void b(PacketDataSerializer packetdataserializer) {
        packetdataserializer.writeByte(this.id);
    }

    public void a(PacketListener packetlistener) {
        this.a((PacketListenerPlayIn) packetlistener);
    }
}
