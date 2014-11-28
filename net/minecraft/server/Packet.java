package net.minecraft.server;

public interface Packet {

    void a(PacketDataSerializer packetdataserializer);

    void b(PacketDataSerializer packetdataserializer);

    void a(PacketListener packetlistener);
}
