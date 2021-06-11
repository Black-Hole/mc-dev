package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class PacketPlayOutSetSlot implements Packet<PacketListenerPlayOut> {

    public static final int CARRIED_ITEM = -1;
    public static final int PLAYER_INVENTORY = -2;
    private final int containerId;
    private final int slot;
    private final ItemStack itemStack;

    public PacketPlayOutSetSlot(int i, int j, ItemStack itemstack) {
        this.containerId = i;
        this.slot = j;
        this.itemStack = itemstack.cloneItemStack();
    }

    public PacketPlayOutSetSlot(PacketDataSerializer packetdataserializer) {
        this.containerId = packetdataserializer.readByte();
        this.slot = packetdataserializer.readShort();
        this.itemStack = packetdataserializer.o();
    }

    @Override
    public void a(PacketDataSerializer packetdataserializer) {
        packetdataserializer.writeByte(this.containerId);
        packetdataserializer.writeShort(this.slot);
        packetdataserializer.a(this.itemStack);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public int b() {
        return this.containerId;
    }

    public int c() {
        return this.slot;
    }

    public ItemStack d() {
        return this.itemStack;
    }
}
