package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.List;

class PlayerChunk {

    private final List b;
    private final ChunkCoordIntPair location;
    private short[] dirtyBlocks;
    private int dirtyCount;
    private int f;
    private long g;
    final PlayerChunkMap playerChunkMap;

    public PlayerChunk(PlayerChunkMap playerchunkmap, int i, int j) {
        this.playerChunkMap = playerchunkmap;
        this.b = Lists.newArrayList();
        this.dirtyBlocks = new short[64];
        this.location = new ChunkCoordIntPair(i, j);
        playerchunkmap.a().chunkProviderServer.getChunkAt(i, j);
    }

    public void a(EntityPlayer entityplayer) {
        if (this.b.contains(entityplayer)) {
            PlayerChunkMap.c().debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { entityplayer, Integer.valueOf(this.location.x), Integer.valueOf(this.location.z)});
        } else {
            if (this.b.isEmpty()) {
                this.g = PlayerChunkMap.a(this.playerChunkMap).getTime();
            }

            this.b.add(entityplayer);
            entityplayer.chunkCoordIntPairQueue.add(this.location);
        }
    }

    public void b(EntityPlayer entityplayer) {
        if (this.b.contains(entityplayer)) {
            Chunk chunk = PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z);

            if (chunk.isReady()) {
                entityplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, true, 0));
            }

            this.b.remove(entityplayer);
            entityplayer.chunkCoordIntPairQueue.remove(this.location);
            if (this.b.isEmpty()) {
                long i = (long) this.location.x + 2147483647L | (long) this.location.z + 2147483647L << 32;

                this.a(chunk);
                PlayerChunkMap.b(this.playerChunkMap).remove(i);
                PlayerChunkMap.c(this.playerChunkMap).remove(this);
                if (this.dirtyCount > 0) {
                    PlayerChunkMap.d(this.playerChunkMap).remove(this);
                }

                this.playerChunkMap.a().chunkProviderServer.queueUnload(this.location.x, this.location.z);
            }

        }
    }

    public void a() {
        this.a(PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z));
    }

    private void a(Chunk chunk) {
        chunk.c(chunk.w() + PlayerChunkMap.a(this.playerChunkMap).getTime() - this.g);
        this.g = PlayerChunkMap.a(this.playerChunkMap).getTime();
    }

    public void a(int i, int j, int k) {
        if (this.dirtyCount == 0) {
            PlayerChunkMap.d(this.playerChunkMap).add(this);
        }

        this.f |= 1 << (j >> 4);
        if (this.dirtyCount < 64) {
            short short0 = (short) (i << 12 | k << 8 | j);

            for (int l = 0; l < this.dirtyCount; ++l) {
                if (this.dirtyBlocks[l] == short0) {
                    return;
                }
            }

            this.dirtyBlocks[this.dirtyCount++] = short0;
        }

    }

    public void a(Packet packet) {
        for (int i = 0; i < this.b.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) this.b.get(i);

            if (!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }

    }

    public void b() {
        if (this.dirtyCount != 0) {
            int i;
            int j;
            int k;

            if (this.dirtyCount == 1) {
                i = (this.dirtyBlocks[0] >> 12 & 15) + this.location.x * 16;
                j = this.dirtyBlocks[0] & 255;
                k = (this.dirtyBlocks[0] >> 8 & 15) + this.location.z * 16;
                BlockPosition blockposition = new BlockPosition(i, j, k);

                this.a((Packet) (new PacketPlayOutBlockChange(PlayerChunkMap.a(this.playerChunkMap), blockposition)));
                if (PlayerChunkMap.a(this.playerChunkMap).getType(blockposition).getBlock().isTileEntity()) {
                    this.a(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(blockposition));
                }
            } else {
                int l;

                if (this.dirtyCount == 64) {
                    i = this.location.x * 16;
                    j = this.location.z * 16;
                    this.a((Packet) (new PacketPlayOutMapChunk(PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z), false, this.f)));

                    for (k = 0; k < 16; ++k) {
                        if ((this.f & 1 << k) != 0) {
                            l = k << 4;
                            List list = PlayerChunkMap.a(this.playerChunkMap).getTileEntities(i, l, j, i + 16, l + 16, j + 16);

                            for (int i1 = 0; i1 < list.size(); ++i1) {
                                this.a((TileEntity) list.get(i1));
                            }
                        }
                    }
                } else {
                    this.a((Packet) (new PacketPlayOutMultiBlockChange(this.dirtyCount, this.dirtyBlocks, PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z))));

                    for (i = 0; i < this.dirtyCount; ++i) {
                        j = (this.dirtyBlocks[i] >> 12 & 15) + this.location.x * 16;
                        k = this.dirtyBlocks[i] & 255;
                        l = (this.dirtyBlocks[i] >> 8 & 15) + this.location.z * 16;
                        BlockPosition blockposition1 = new BlockPosition(j, k, l);

                        if (PlayerChunkMap.a(this.playerChunkMap).getType(blockposition1).getBlock().isTileEntity()) {
                            this.a(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(blockposition1));
                        }
                    }
                }
            }

            this.dirtyCount = 0;
            this.f = 0;
        }
    }

    private void a(TileEntity tileentity) {
        if (tileentity != null) {
            Packet packet = tileentity.getUpdatePacket();

            if (packet != null) {
                this.a(packet);
            }
        }

    }

    static ChunkCoordIntPair a(PlayerChunk playerchunk) {
        return playerchunk.location;
    }

    static List b(PlayerChunk playerchunk) {
        return playerchunk.b;
    }
}
