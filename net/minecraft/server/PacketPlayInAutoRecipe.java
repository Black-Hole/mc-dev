package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PacketPlayInAutoRecipe implements Packet<PacketListenerPlayIn> {

    private int a;
    private short b;
    private List<PacketPlayInAutoRecipe.a> c;
    private List<PacketPlayInAutoRecipe.a> d;

    public PacketPlayInAutoRecipe() {}

    public int a() {
        return this.a;
    }

    public short b() {
        return this.b;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readByte();
        this.b = packetdataserializer.readShort();
        this.c = this.c(packetdataserializer);
        this.d = this.c(packetdataserializer);
    }

    private List<PacketPlayInAutoRecipe.a> c(PacketDataSerializer packetdataserializer) {
        short short0 = packetdataserializer.readShort();
        ArrayList arraylist = Lists.newArrayListWithCapacity(short0);

        for (int i = 0; i < short0; ++i) {
            ItemStack itemstack = packetdataserializer.k();
            byte b0 = packetdataserializer.readByte();
            byte b1 = packetdataserializer.readByte();

            arraylist.add(new PacketPlayInAutoRecipe.a(itemstack, b0, b1));
        }

        return arraylist;
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeByte(this.a);
        packetdataserializer.writeShort(this.b);
        this.a(packetdataserializer, this.c);
        this.a(packetdataserializer, this.d);
    }

    private void a(PacketDataSerializer packetdataserializer, List<PacketPlayInAutoRecipe.a> list) {
        packetdataserializer.writeShort(list.size());
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            PacketPlayInAutoRecipe.a packetplayinautorecipe_a = (PacketPlayInAutoRecipe.a) iterator.next();

            packetdataserializer.a(packetplayinautorecipe_a.a);
            packetdataserializer.writeByte(packetplayinautorecipe_a.b);
            packetdataserializer.writeByte(packetplayinautorecipe_a.c);
        }

    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public List<PacketPlayInAutoRecipe.a> c() {
        return this.d;
    }

    public List<PacketPlayInAutoRecipe.a> d() {
        return this.c;
    }

    public static class a {

        public ItemStack a;
        public int b;
        public int c;

        public a(ItemStack itemstack, int i, int j) {
            this.a = itemstack.cloneItemStack();
            this.b = i;
            this.c = j;
        }
    }
}
