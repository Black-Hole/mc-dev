package net.minecraft.server;

public class SecondaryWorldServer extends WorldServer {

    private WorldServer a;

    public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, int i, WorldServer worldserver, MethodProfiler methodprofiler) {
        super(minecraftserver, idatamanager, new SecondaryWorldData(worldserver.getWorldData()), i, methodprofiler);
        this.a = worldserver;
        worldserver.af().a((IWorldBorderListener) (new SecondaryWorldServerInnerClass1(this)));
    }

    protected void a() {}

    public World b() {
        this.worldMaps = this.a.T();
        this.scoreboard = this.a.getScoreboard();
        String s = PersistentVillage.a(this.worldProvider);
        PersistentVillage persistentvillage = (PersistentVillage) this.worldMaps.get(PersistentVillage.class, s);

        if (persistentvillage == null) {
            this.villages = new PersistentVillage(this);
            this.worldMaps.a(s, this.villages);
        } else {
            this.villages = persistentvillage;
            this.villages.a((World) this);
        }

        return this;
    }
}
