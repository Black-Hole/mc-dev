package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorldUpgraderIterator {

    private static final Pattern a = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
    private final File b;
    private final Map<DimensionManager, List<ChunkCoordIntPair>> c;

    public WorldUpgraderIterator(File file) {
        this.b = file;
        Builder builder = ImmutableMap.builder();
        Iterator iterator = DimensionManager.b().iterator();

        while (iterator.hasNext()) {
            DimensionManager dimensionmanager = (DimensionManager) iterator.next();

            builder.put(dimensionmanager, this.b(dimensionmanager));
        }

        this.c = builder.build();
    }

    private List<ChunkCoordIntPair> b(DimensionManager dimensionmanager) {
        ArrayList arraylist = Lists.newArrayList();
        File file = dimensionmanager.a(this.b);
        List list = this.b(file);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            File file1 = (File) iterator.next();

            arraylist.addAll(this.a(file1));
        }

        list.sort(File::compareTo);
        return arraylist;
    }

    private List<ChunkCoordIntPair> a(File file) {
        ArrayList arraylist = Lists.newArrayList();
        RegionFile regionfile = null;

        ArrayList arraylist1;

        try {
            Matcher matcher = WorldUpgraderIterator.a.matcher(file.getName());

            if (!matcher.matches()) {
                arraylist1 = arraylist;
                return arraylist1;
            }

            int i = Integer.parseInt(matcher.group(1)) << 5;
            int j = Integer.parseInt(matcher.group(2)) << 5;

            regionfile = new RegionFile(file);

            for (int k = 0; k < 32; ++k) {
                for (int l = 0; l < 32; ++l) {
                    if (regionfile.b(k, l)) {
                        arraylist.add(new ChunkCoordIntPair(k + i, l + j));
                    }
                }
            }

            return arraylist;
        } catch (Throwable throwable) {
            arraylist1 = Lists.newArrayList();
        } finally {
            if (regionfile != null) {
                try {
                    regionfile.close();
                } catch (IOException ioexception) {
                    ;
                }
            }

        }

        return arraylist1;
    }

    private List<File> b(File file) {
        File file1 = new File(file, "region");
        File[] afile = file1.listFiles((file, s) -> {
            return s.endsWith(".mca");
        });

        return afile != null ? Lists.newArrayList(afile) : Lists.newArrayList();
    }

    public List<ChunkCoordIntPair> a(DimensionManager dimensionmanager) {
        return (List) this.c.get(dimensionmanager);
    }
}
