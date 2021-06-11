package net.minecraft.network.protocol.game;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class PacketPlayOutWindowItems implements Packet<PacketListenerPlayOut> {

    private final int containerId;
    private final List<ItemStack> items;

    public PacketPlayOutWindowItems(int i, NonNullList<ItemStack> nonnulllist) {
        this.containerId = i;
        this.items = NonNullList.a(nonnulllist.size(), ItemStack.EMPTY);

        for (int j = 0; j < this.items.size(); ++j) {
            this.items.set(j, ((ItemStack) nonnulllist.get(j)).cloneItemStack());
        }

    }

    public PacketPlayOutWindowItems(PacketDataSerializer packetdataserializer) {
        this.containerId = packetdataserializer.readUnsignedByte();
        short short0 = packetdataserializer.readShort();

        this.items = NonNullList.a(short0, ItemStack.EMPTY);

        for (int i = 0; i < short0; ++i) {
            this.items.set(i, packetdataserializer.o());
        }

    }

    @Override
    public void a(PacketDataSerializer packetdataserializer) {
        packetdataserializer.writeByte(this.containerId);
        packetdataserializer.writeShort(this.items.size());
        Iterator iterator = this.items.iterator();

        while (iterator.hasNext()) {
            ItemStack itemstack = (ItemStack) iterator.next();

            packetdataserializer.a(itemstack);
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public int b() {
        return this.containerId;
    }

    public List<ItemStack> c() {
        return this.items;
    }
}
