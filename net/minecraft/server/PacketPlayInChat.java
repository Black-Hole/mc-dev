package net.minecraft.server;

public class PacketPlayInChat implements Packet {

    private String a;

    public PacketPlayInChat() {}

    public PacketPlayInChat(String s) {
        if (s.length() > 100) {
            s = s.substring(0, 100);
        }

        this.a = s;
    }

    public void a(PacketDataSerializer packetdataserializer) {
        this.a = packetdataserializer.c(100);
    }

    public void b(PacketDataSerializer packetdataserializer) {
        packetdataserializer.a(this.a);
    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public String a() {
        return this.a;
    }

    public void a(PacketListener packetlistener) {
        this.a((PacketListenerPlayIn) packetlistener);
    }
}
