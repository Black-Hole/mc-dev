package net.minecraft.server;

public class DataInspectorItemList extends DataInspectorTagged {

    private final String[] a;

    public DataInspectorItemList(String s, String... astring) {
        super("id", s);
        this.a = astring;
    }

    NBTTagCompound b(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
        String[] astring = this.a;
        int j = astring.length;

        for (int k = 0; k < j; ++k) {
            String s = astring[k];

            nbttagcompound = DataConverterRegistry.b(dataconverter, nbttagcompound, i, s);
        }

        return nbttagcompound;
    }
}
