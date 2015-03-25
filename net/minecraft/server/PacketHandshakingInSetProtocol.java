package net.minecraft.server;

import java.io.IOException;

public class PacketHandshakingInSetProtocol implements Packet<PacketHandshakingInListener> {

    private int a;
    private String b;
    private int c;
    private EnumProtocol d;

    public PacketHandshakingInSetProtocol() {}

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.e();
        this.b = packetdataserializer.c(255);
        this.c = packetdataserializer.readUnsignedShort();
        this.d = EnumProtocol.a(packetdataserializer.e());
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.b(this.a);
        packetdataserializer.a(this.b);
        packetdataserializer.writeShort(this.c);
        packetdataserializer.b(this.d.a());
    }

    public void a(PacketHandshakingInListener packethandshakinginlistener) {
        packethandshakinginlistener.a(this);
    }

    public EnumProtocol a() {
        return this.d;
    }

    public int b() {
        return this.a;
    }

    public void a(PacketListener packetlistener) {
        this.a((PacketHandshakingInListener) packetlistener);
    }
}
