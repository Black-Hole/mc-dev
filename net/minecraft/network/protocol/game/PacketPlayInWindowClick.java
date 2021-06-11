package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.inventory.InventoryClickType;
import net.minecraft.world.item.ItemStack;

public class PacketPlayInWindowClick implements Packet<PacketListenerPlayIn> {

    private final int containerId;
    private final int slotNum;
    private final int buttonNum;
    private final InventoryClickType clickType;
    private final ItemStack carriedItem;
    private final Int2ObjectMap<ItemStack> changedSlots;

    public PacketPlayInWindowClick(int i, int j, int k, InventoryClickType inventoryclicktype, ItemStack itemstack, Int2ObjectMap<ItemStack> int2objectmap) {
        this.containerId = i;
        this.slotNum = j;
        this.buttonNum = k;
        this.clickType = inventoryclicktype;
        this.carriedItem = itemstack;
        this.changedSlots = Int2ObjectMaps.unmodifiable(int2objectmap);
    }

    public PacketPlayInWindowClick(PacketDataSerializer packetdataserializer) {
        this.containerId = packetdataserializer.readByte();
        this.slotNum = packetdataserializer.readShort();
        this.buttonNum = packetdataserializer.readByte();
        this.clickType = (InventoryClickType) packetdataserializer.a(InventoryClickType.class);
        this.changedSlots = Int2ObjectMaps.unmodifiable((Int2ObjectMap) packetdataserializer.a(Int2ObjectOpenHashMap::new, (packetdataserializer1) -> {
            return Integer.valueOf(packetdataserializer1.readShort());
        }, PacketDataSerializer::o));
        this.carriedItem = packetdataserializer.o();
    }

    @Override
    public void a(PacketDataSerializer packetdataserializer) {
        packetdataserializer.writeByte(this.containerId);
        packetdataserializer.writeShort(this.slotNum);
        packetdataserializer.writeByte(this.buttonNum);
        packetdataserializer.a((Enum) this.clickType);
        packetdataserializer.a((Map) this.changedSlots, PacketDataSerializer::writeShort, PacketDataSerializer::a);
        packetdataserializer.a(this.carriedItem);
    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public int b() {
        return this.containerId;
    }

    public int c() {
        return this.slotNum;
    }

    public int d() {
        return this.buttonNum;
    }

    public ItemStack e() {
        return this.carriedItem;
    }

    public Int2ObjectMap<ItemStack> f() {
        return this.changedSlots;
    }

    public InventoryClickType g() {
        return this.clickType;
    }
}
