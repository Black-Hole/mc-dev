package net.minecraft.server;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRegionLoader {

    private static final Logger LOGGER = LogManager.getLogger();

    public static ProtoChunk loadChunk(World world, DefinedStructureManager definedstructuremanager, VillagePlace villageplace, ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound) {
        ChunkGenerator<?> chunkgenerator = world.getChunkProvider().getChunkGenerator();
        WorldChunkManager worldchunkmanager = chunkgenerator.getWorldChunkManager();
        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Level");
        ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(nbttagcompound1.getInt("xPos"), nbttagcompound1.getInt("zPos"));

        if (!Objects.equals(chunkcoordintpair, chunkcoordintpair1)) {
            ChunkRegionLoader.LOGGER.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", chunkcoordintpair, chunkcoordintpair, chunkcoordintpair1);
        }

        BiomeBase[] abiomebase = new BiomeBase[256];
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

        if (nbttagcompound1.hasKeyOfType("Biomes", 11)) {
            int[] aint = nbttagcompound1.getIntArray("Biomes");

            for (int i = 0; i < aint.length; ++i) {
                abiomebase[i] = (BiomeBase) IRegistry.BIOME.fromId(aint[i]);
                if (abiomebase[i] == null) {
                    abiomebase[i] = worldchunkmanager.getBiome(blockposition_mutableblockposition.d((i & 15) + chunkcoordintpair.d(), 0, (i >> 4 & 15) + chunkcoordintpair.e()));
                }
            }
        } else {
            for (int j = 0; j < abiomebase.length; ++j) {
                abiomebase[j] = worldchunkmanager.getBiome(blockposition_mutableblockposition.d((j & 15) + chunkcoordintpair.d(), 0, (j >> 4 & 15) + chunkcoordintpair.e()));
            }
        }

        ChunkConverter chunkconverter = nbttagcompound1.hasKeyOfType("UpgradeData", 10) ? new ChunkConverter(nbttagcompound1.getCompound("UpgradeData")) : ChunkConverter.a;
        ProtoChunkTickList<Block> protochunkticklist = new ProtoChunkTickList<>((block) -> {
            return block == null || block.getBlockData().isAir();
        }, IRegistry.BLOCK::getKey, IRegistry.BLOCK::get, chunkcoordintpair, nbttagcompound1.getList("ToBeTicked", 9));
        ProtoChunkTickList<FluidType> protochunkticklist1 = new ProtoChunkTickList<>((fluidtype) -> {
            return fluidtype == null || fluidtype == FluidTypes.EMPTY;
        }, IRegistry.FLUID::getKey, IRegistry.FLUID::get, chunkcoordintpair, nbttagcompound1.getList("LiquidsToBeTicked", 9));
        boolean flag = nbttagcompound1.getBoolean("isLightOn");
        NBTTagList nbttaglist = nbttagcompound1.getList("Sections", 10);
        boolean flag1 = true;
        ChunkSection[] achunksection = new ChunkSection[16];
        boolean flag2 = world.getWorldProvider().g();
        IChunkProvider ichunkprovider = world.getChunkProvider();
        LightEngine lightengine = ichunkprovider.getLightEngine();

        for (int k = 0; k < nbttaglist.size(); ++k) {
            NBTTagCompound nbttagcompound2 = nbttaglist.getCompound(k);
            byte b0 = nbttagcompound2.getByte("Y");

            if (nbttagcompound2.hasKeyOfType("Palette", 9) && nbttagcompound2.hasKeyOfType("BlockStates", 12)) {
                ChunkSection chunksection = new ChunkSection(b0 << 4);

                chunksection.getBlocks().a(nbttagcompound2.getList("Palette", 10), nbttagcompound2.getLongArray("BlockStates"));
                chunksection.recalcBlockCounts();
                if (!chunksection.c()) {
                    achunksection[b0] = chunksection;
                }

                villageplace.a(chunkcoordintpair, chunksection);
            }

            if (flag) {
                if (nbttagcompound2.hasKeyOfType("BlockLight", 7)) {
                    lightengine.a(EnumSkyBlock.BLOCK, SectionPosition.a(chunkcoordintpair, b0), new NibbleArray(nbttagcompound2.getByteArray("BlockLight")));
                }

                if (flag2 && nbttagcompound2.hasKeyOfType("SkyLight", 7)) {
                    lightengine.a(EnumSkyBlock.SKY, SectionPosition.a(chunkcoordintpair, b0), new NibbleArray(nbttagcompound2.getByteArray("SkyLight")));
                }
            }
        }

        long l = nbttagcompound1.getLong("InhabitedTime");
        ChunkStatus.Type chunkstatus_type = a(nbttagcompound);
        Object object;

        if (chunkstatus_type == ChunkStatus.Type.LEVELCHUNK) {
            object = new Chunk(world.getMinecraftWorld(), chunkcoordintpair, abiomebase, chunkconverter, protochunkticklist, protochunkticklist1, l, achunksection, (chunk) -> {
                loadEntities(nbttagcompound1, chunk);
            });
        } else {
            ProtoChunk protochunk = new ProtoChunk(chunkcoordintpair, chunkconverter, achunksection, protochunkticklist, protochunkticklist1);

            object = protochunk;
            protochunk.a(abiomebase);
            protochunk.b(l);
            protochunk.a(ChunkStatus.a(nbttagcompound1.getString("Status")));
            if (protochunk.getChunkStatus().b(ChunkStatus.FEATURES)) {
                protochunk.a(lightengine);
            }

            if (!flag && protochunk.getChunkStatus().b(ChunkStatus.LIGHT)) {
                Iterator iterator = BlockPosition.b(chunkcoordintpair.d(), 0, chunkcoordintpair.e(), chunkcoordintpair.f(), 255, chunkcoordintpair.g()).iterator();

                while (iterator.hasNext()) {
                    BlockPosition blockposition = (BlockPosition) iterator.next();

                    if (((IChunkAccess) object).getType(blockposition).h() != 0) {
                        protochunk.j(blockposition);
                    }
                }
            }
        }

        ((IChunkAccess) object).b(flag);
        NBTTagCompound nbttagcompound3 = nbttagcompound1.getCompound("Heightmaps");
        EnumSet<HeightMap.Type> enumset = EnumSet.noneOf(HeightMap.Type.class);
        Iterator iterator1 = ((IChunkAccess) object).getChunkStatus().h().iterator();

        while (iterator1.hasNext()) {
            HeightMap.Type heightmap_type = (HeightMap.Type) iterator1.next();
            String s = heightmap_type.a();

            if (nbttagcompound3.hasKeyOfType(s, 12)) {
                ((IChunkAccess) object).a(heightmap_type, nbttagcompound3.getLongArray(s));
            } else {
                enumset.add(heightmap_type);
            }
        }

        HeightMap.a((IChunkAccess) object, enumset);
        NBTTagCompound nbttagcompound4 = nbttagcompound1.getCompound("Structures");

        ((IChunkAccess) object).a(a(chunkgenerator, definedstructuremanager, worldchunkmanager, nbttagcompound4));
        ((IChunkAccess) object).b(b(nbttagcompound4));
        if (nbttagcompound1.getBoolean("shouldSave")) {
            ((IChunkAccess) object).setNeedsSaving(true);
        }

        NBTTagList nbttaglist1 = nbttagcompound1.getList("PostProcessing", 9);

        NBTTagList nbttaglist2;
        int i1;

        for (int j1 = 0; j1 < nbttaglist1.size(); ++j1) {
            nbttaglist2 = nbttaglist1.b(j1);

            for (i1 = 0; i1 < nbttaglist2.size(); ++i1) {
                ((IChunkAccess) object).a(nbttaglist2.d(i1), j1);
            }
        }

        if (chunkstatus_type == ChunkStatus.Type.LEVELCHUNK) {
            return new ProtoChunkExtension((Chunk) object);
        } else {
            ProtoChunk protochunk1 = (ProtoChunk) object;

            nbttaglist2 = nbttagcompound1.getList("Entities", 10);

            for (i1 = 0; i1 < nbttaglist2.size(); ++i1) {
                protochunk1.b(nbttaglist2.getCompound(i1));
            }

            NBTTagList nbttaglist3 = nbttagcompound1.getList("TileEntities", 10);

            NBTTagCompound nbttagcompound5;

            for (int k1 = 0; k1 < nbttaglist3.size(); ++k1) {
                nbttagcompound5 = nbttaglist3.getCompound(k1);
                ((IChunkAccess) object).a(nbttagcompound5);
            }

            NBTTagList nbttaglist4 = nbttagcompound1.getList("Lights", 9);

            for (int l1 = 0; l1 < nbttaglist4.size(); ++l1) {
                NBTTagList nbttaglist5 = nbttaglist4.b(l1);

                for (int i2 = 0; i2 < nbttaglist5.size(); ++i2) {
                    protochunk1.b(nbttaglist5.d(i2), l1);
                }
            }

            nbttagcompound5 = nbttagcompound1.getCompound("CarvingMasks");
            Iterator iterator2 = nbttagcompound5.getKeys().iterator();

            while (iterator2.hasNext()) {
                String s1 = (String) iterator2.next();
                WorldGenStage.Features worldgenstage_features = WorldGenStage.Features.valueOf(s1);

                protochunk1.a(worldgenstage_features, BitSet.valueOf(nbttagcompound5.getByteArray(s1)));
            }

            return protochunk1;
        }
    }

    public static NBTTagCompound saveChunk(World world, IChunkAccess ichunkaccess) {
        ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        nbttagcompound.setInt("DataVersion", SharedConstants.a().getWorldVersion());
        nbttagcompound.set("Level", nbttagcompound1);
        nbttagcompound1.setInt("xPos", chunkcoordintpair.x);
        nbttagcompound1.setInt("zPos", chunkcoordintpair.z);
        nbttagcompound1.setLong("LastUpdate", world.getTime());
        nbttagcompound1.setLong("InhabitedTime", ichunkaccess.q());
        nbttagcompound1.setString("Status", ichunkaccess.getChunkStatus().d());
        ChunkConverter chunkconverter = ichunkaccess.p();

        if (!chunkconverter.a()) {
            nbttagcompound1.set("UpgradeData", chunkconverter.b());
        }

        ChunkSection[] achunksection = ichunkaccess.getSections();
        NBTTagList nbttaglist = new NBTTagList();
        LightEngine lightengine = world.getChunkProvider().getLightEngine();

        NBTTagCompound nbttagcompound2;

        for (int i = -1; i < 17; ++i) {
            ChunkSection chunksection = (ChunkSection) Arrays.stream(achunksection).filter((chunksection1) -> {
                return chunksection1 != null && chunksection1.getYPosition() >> 4 == i;
            }).findFirst().orElse(Chunk.a);
            NibbleArray nibblearray = lightengine.a(EnumSkyBlock.BLOCK).a(SectionPosition.a(chunkcoordintpair, i));
            NibbleArray nibblearray1 = lightengine.a(EnumSkyBlock.SKY).a(SectionPosition.a(chunkcoordintpair, i));

            if (chunksection != Chunk.a || nibblearray != null || nibblearray1 != null) {
                nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Y", (byte) (i & 255));
                if (chunksection != Chunk.a) {
                    chunksection.getBlocks().a(nbttagcompound2, "Palette", "BlockStates");
                }

                if (nibblearray != null && !nibblearray.c()) {
                    nbttagcompound2.setByteArray("BlockLight", nibblearray.asBytes());
                }

                if (nibblearray1 != null && !nibblearray1.c()) {
                    nbttagcompound2.setByteArray("SkyLight", nibblearray1.asBytes());
                }

                nbttaglist.add(nbttagcompound2);
            }
        }

        nbttagcompound1.set("Sections", nbttaglist);
        if (ichunkaccess.r()) {
            nbttagcompound1.setBoolean("isLightOn", true);
        }

        BiomeBase[] abiomebase = ichunkaccess.getBiomeIndex();
        int[] aint = abiomebase != null ? new int[abiomebase.length] : new int[0];

        if (abiomebase != null) {
            for (int j = 0; j < abiomebase.length; ++j) {
                aint[j] = IRegistry.BIOME.a((Object) abiomebase[j]);
            }
        }

        nbttagcompound1.setIntArray("Biomes", aint);
        NBTTagList nbttaglist1 = new NBTTagList();
        Iterator iterator = ichunkaccess.c().iterator();

        while (iterator.hasNext()) {
            BlockPosition blockposition = (BlockPosition) iterator.next();
            TileEntity tileentity = ichunkaccess.getTileEntity(blockposition);
            NBTTagCompound nbttagcompound3;

            if (tileentity != null) {
                nbttagcompound3 = new NBTTagCompound();
                tileentity.save(nbttagcompound3);
                if (ichunkaccess.getChunkStatus().getType() == ChunkStatus.Type.LEVELCHUNK) {
                    nbttagcompound3.setBoolean("keepPacked", false);
                }

                nbttaglist1.add(nbttagcompound3);
            } else {
                nbttagcompound3 = ichunkaccess.i(blockposition);
                if (nbttagcompound3 != null) {
                    if (ichunkaccess.getChunkStatus().getType() == ChunkStatus.Type.LEVELCHUNK) {
                        nbttagcompound3.setBoolean("keepPacked", true);
                    }

                    nbttaglist1.add(nbttagcompound3);
                }
            }
        }

        nbttagcompound1.set("TileEntities", nbttaglist1);
        NBTTagList nbttaglist2 = new NBTTagList();
        NBTTagCompound nbttagcompound4;
        Iterator iterator1;

        if (ichunkaccess.getChunkStatus().getType() == ChunkStatus.Type.LEVELCHUNK) {
            Chunk chunk = (Chunk) ichunkaccess;

            chunk.d(false);

            for (int k = 0; k < chunk.getEntitySlices().length; ++k) {
                Iterator iterator2 = chunk.getEntitySlices()[k].iterator();

                while (iterator2.hasNext()) {
                    Entity entity = (Entity) iterator2.next();

                    nbttagcompound4 = new NBTTagCompound();
                    if (entity.d(nbttagcompound4)) {
                        chunk.d(true);
                        nbttaglist2.add(nbttagcompound4);
                    }
                }
            }
        } else {
            ProtoChunk protochunk = (ProtoChunk) ichunkaccess;

            nbttaglist2.addAll(protochunk.y());
            iterator1 = ichunkaccess.c().iterator();

            while (iterator1.hasNext()) {
                BlockPosition blockposition1 = (BlockPosition) iterator1.next();
                TileEntity tileentity1 = ichunkaccess.getTileEntity(blockposition1);

                if (tileentity1 != null) {
                    nbttagcompound4 = new NBTTagCompound();
                    tileentity1.save(nbttagcompound4);
                    nbttaglist1.add(nbttagcompound4);
                } else {
                    nbttaglist1.add(ichunkaccess.i(blockposition1));
                }
            }

            nbttagcompound1.set("Lights", a(protochunk.w()));
            nbttagcompound2 = new NBTTagCompound();
            WorldGenStage.Features[] aworldgenstage_features = WorldGenStage.Features.values();
            int l = aworldgenstage_features.length;

            for (int i1 = 0; i1 < l; ++i1) {
                WorldGenStage.Features worldgenstage_features = aworldgenstage_features[i1];

                nbttagcompound2.setByteArray(worldgenstage_features.toString(), ichunkaccess.a(worldgenstage_features).toByteArray());
            }

            nbttagcompound1.set("CarvingMasks", nbttagcompound2);
        }

        nbttagcompound1.set("Entities", nbttaglist2);
        if (world.getBlockTickList() instanceof TickListServer) {
            nbttagcompound1.set("TileTicks", ((TickListServer) world.getBlockTickList()).a(chunkcoordintpair));
        }

        if (ichunkaccess.n() instanceof ProtoChunkTickList) {
            nbttagcompound1.set("ToBeTicked", ((ProtoChunkTickList) ichunkaccess.n()).a());
        }

        if (ichunkaccess.n() instanceof TickListChunk) {
            nbttagcompound1.set("TileTicks", ((TickListChunk) ichunkaccess.n()).a(world.getTime()));
        }

        if (world.getFluidTickList() instanceof TickListServer) {
            nbttagcompound1.set("LiquidTicks", ((TickListServer) world.getFluidTickList()).a(chunkcoordintpair));
        }

        if (ichunkaccess.o() instanceof ProtoChunkTickList) {
            nbttagcompound1.set("LiquidsToBeTicked", ((ProtoChunkTickList) ichunkaccess.o()).a());
        }

        if (ichunkaccess.o() instanceof TickListChunk) {
            nbttagcompound1.set("LiquidTicks", ((TickListChunk) ichunkaccess.o()).a(world.getTime()));
        }

        nbttagcompound1.set("PostProcessing", a(ichunkaccess.l()));
        NBTTagCompound nbttagcompound5 = new NBTTagCompound();

        iterator1 = ichunkaccess.f().iterator();

        while (iterator1.hasNext()) {
            Entry<HeightMap.Type, HeightMap> entry = (Entry) iterator1.next();

            if (ichunkaccess.getChunkStatus().h().contains(entry.getKey())) {
                nbttagcompound5.set(((HeightMap.Type) entry.getKey()).a(), new NBTTagLongArray(((HeightMap) entry.getValue()).a()));
            }
        }

        nbttagcompound1.set("Heightmaps", nbttagcompound5);
        nbttagcompound1.set("Structures", a(chunkcoordintpair, ichunkaccess.h(), ichunkaccess.v()));
        return nbttagcompound;
    }

    public static ChunkStatus.Type a(@Nullable NBTTagCompound nbttagcompound) {
        if (nbttagcompound != null) {
            ChunkStatus chunkstatus = ChunkStatus.a(nbttagcompound.getCompound("Level").getString("Status"));

            if (chunkstatus != null) {
                return chunkstatus.getType();
            }
        }

        return ChunkStatus.Type.PROTOCHUNK;
    }

    private static void loadEntities(NBTTagCompound nbttagcompound, Chunk chunk) {
        NBTTagList nbttaglist = nbttagcompound.getList("Entities", 10);
        World world = chunk.getWorld();

        for (int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompound(i);

            EntityTypes.a(nbttagcompound1, world, (entity) -> {
                chunk.a(entity);
                return entity;
            });
            chunk.d(true);
        }

        NBTTagList nbttaglist1 = nbttagcompound.getList("TileEntities", 10);

        for (int j = 0; j < nbttaglist1.size(); ++j) {
            NBTTagCompound nbttagcompound2 = nbttaglist1.getCompound(j);
            boolean flag = nbttagcompound2.getBoolean("keepPacked");

            if (flag) {
                chunk.a(nbttagcompound2);
            } else {
                TileEntity tileentity = TileEntity.create(nbttagcompound2);

                if (tileentity != null) {
                    chunk.a(tileentity);
                }
            }
        }

        if (nbttagcompound.hasKeyOfType("TileTicks", 9) && world.getBlockTickList() instanceof TickListServer) {
            ((TickListServer) world.getBlockTickList()).a(nbttagcompound.getList("TileTicks", 10));
        }

        if (nbttagcompound.hasKeyOfType("LiquidTicks", 9) && world.getFluidTickList() instanceof TickListServer) {
            ((TickListServer) world.getFluidTickList()).a(nbttagcompound.getList("LiquidTicks", 10));
        }

    }

    private static NBTTagCompound a(ChunkCoordIntPair chunkcoordintpair, Map<String, StructureStart> map, Map<String, LongSet> map1) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, StructureStart> entry = (Entry) iterator.next();

            nbttagcompound1.set((String) entry.getKey(), ((StructureStart) entry.getValue()).a(chunkcoordintpair.x, chunkcoordintpair.z));
        }

        nbttagcompound.set("Starts", nbttagcompound1);
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        Iterator iterator1 = map1.entrySet().iterator();

        while (iterator1.hasNext()) {
            Entry<String, LongSet> entry1 = (Entry) iterator1.next();

            nbttagcompound2.set((String) entry1.getKey(), new NBTTagLongArray((LongSet) entry1.getValue()));
        }

        nbttagcompound.set("References", nbttagcompound2);
        return nbttagcompound;
    }

    private static Map<String, StructureStart> a(ChunkGenerator<?> chunkgenerator, DefinedStructureManager definedstructuremanager, WorldChunkManager worldchunkmanager, NBTTagCompound nbttagcompound) {
        Map<String, StructureStart> map = Maps.newHashMap();
        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Starts");
        Iterator iterator = nbttagcompound1.getKeys().iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            map.put(s, WorldGenFactory.a(chunkgenerator, definedstructuremanager, worldchunkmanager, nbttagcompound1.getCompound(s)));
        }

        return map;
    }

    private static Map<String, LongSet> b(NBTTagCompound nbttagcompound) {
        Map<String, LongSet> map = Maps.newHashMap();
        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("References");
        Iterator iterator = nbttagcompound1.getKeys().iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            map.put(s, new LongOpenHashSet(nbttagcompound1.getLongArray(s)));
        }

        return map;
    }

    public static NBTTagList a(ShortList[] ashortlist) {
        NBTTagList nbttaglist = new NBTTagList();
        ShortList[] ashortlist1 = ashortlist;
        int i = ashortlist.length;

        for (int j = 0; j < i; ++j) {
            ShortList shortlist = ashortlist1[j];
            NBTTagList nbttaglist1 = new NBTTagList();

            if (shortlist != null) {
                ShortListIterator shortlistiterator = shortlist.iterator();

                while (shortlistiterator.hasNext()) {
                    Short oshort = (Short) shortlistiterator.next();

                    nbttaglist1.add(new NBTTagShort(oshort));
                }
            }

            nbttaglist.add(nbttaglist1);
        }

        return nbttaglist;
    }
}
