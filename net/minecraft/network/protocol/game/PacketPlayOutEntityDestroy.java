package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutEntityDestroy implements Packet<PacketListenerPlayOut> {

    private final int entityId;

    public PacketPlayOutEntityDestroy(int i) {
        this.entityId = i;
    }

    public PacketPlayOutEntityDestroy(PacketDataSerializer packetdataserializer) {
        this.entityId = packetdataserializer.j();
    }

    @Override
    public void a(PacketDataSerializer packetdataserializer) {
        packetdataserializer.d(this.entityId);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public int b() {
        return this.entityId;
    }
}
