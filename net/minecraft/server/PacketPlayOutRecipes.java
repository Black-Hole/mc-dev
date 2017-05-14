package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class PacketPlayOutRecipes implements Packet<PacketListenerPlayOut> {

    private int a;
    private List<IRecipe> b;
    private List<IRecipe> c;
    private boolean d;
    private boolean e;

    public PacketPlayOutRecipes() {}

    public PacketPlayOutRecipes(List<IRecipe> list, List<IRecipe> list1, boolean flag, boolean flag1, int i) {
        this.a = i;
        this.b = list;
        this.c = list1;
        this.d = flag;
        this.e = flag1;
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readShort();
        this.d = packetdataserializer.readBoolean();
        this.e = packetdataserializer.readBoolean();
        int i = packetdataserializer.g();

        this.b = Lists.newArrayList();

        int j;

        for (j = 0; j < i; ++j) {
            this.b.add(CraftingManager.a(packetdataserializer.readInt()));
        }

        i = packetdataserializer.g();
        this.c = Lists.newArrayList();

        for (j = 0; j < i; ++j) {
            this.c.add(CraftingManager.a(packetdataserializer.readInt()));
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeShort(this.a);
        packetdataserializer.writeBoolean(this.d);
        packetdataserializer.writeBoolean(this.e);
        packetdataserializer.d(this.b.size());
        Iterator iterator = this.b.iterator();

        IRecipe irecipe;

        while (iterator.hasNext()) {
            irecipe = (IRecipe) iterator.next();
            packetdataserializer.writeInt(CraftingManager.a(irecipe));
        }

        packetdataserializer.d(this.c.size());
        iterator = this.c.iterator();

        while (iterator.hasNext()) {
            irecipe = (IRecipe) iterator.next();
            packetdataserializer.writeInt(CraftingManager.a(irecipe));
        }

    }
}
