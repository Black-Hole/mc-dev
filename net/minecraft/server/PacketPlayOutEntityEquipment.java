package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutEntityEquipment implements Packet<PacketListenerPlayOut> {

    private int a;
    private EnumItemSlot b;
    private ItemStack c;

    public PacketPlayOutEntityEquipment() {}

    public PacketPlayOutEntityEquipment(int i, EnumItemSlot enumitemslot, ItemStack itemstack) {
        this.a = i;
        this.b = enumitemslot;
        this.c = itemstack == null ? null : itemstack.cloneItemStack();
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.g();
        this.b = (EnumItemSlot) packetdataserializer.a(EnumItemSlot.class);
        this.c = packetdataserializer.k();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.b(this.a);
        packetdataserializer.a((Enum) this.b);
        packetdataserializer.a(this.c);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketListener packetlistener) {
        this.a((PacketListenerPlayOut) packetlistener);
    }
}
