package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class PacketPlayInBEdit implements Packet<PacketListenerPlayIn> {

    private final ItemStack book;
    private final boolean signing;
    private final int slot;

    public PacketPlayInBEdit(ItemStack itemstack, boolean flag, int i) {
        this.book = itemstack.cloneItemStack();
        this.signing = flag;
        this.slot = i;
    }

    public PacketPlayInBEdit(PacketDataSerializer packetdataserializer) {
        this.book = packetdataserializer.o();
        this.signing = packetdataserializer.readBoolean();
        this.slot = packetdataserializer.j();
    }

    @Override
    public void a(PacketDataSerializer packetdataserializer) {
        packetdataserializer.a(this.book);
        packetdataserializer.writeBoolean(this.signing);
        packetdataserializer.d(this.slot);
    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public ItemStack b() {
        return this.book;
    }

    public boolean c() {
        return this.signing;
    }

    public int d() {
        return this.slot;
    }
}
