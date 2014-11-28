package net.minecraft.server;

public class PacketStatusListener implements PacketStatusInListener {

    private final MinecraftServer minecraftServer;
    private final NetworkManager networkManager;

    public PacketStatusListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
        this.minecraftServer = minecraftserver;
        this.networkManager = networkmanager;
    }

    public void a(IChatBaseComponent ichatbasecomponent) {}

    public void a(PacketStatusInStart packetstatusinstart) {
        this.networkManager.handle(new PacketStatusOutServerInfo(this.minecraftServer.aE()));
    }

    public void a(PacketStatusInPing packetstatusinping) {
        this.networkManager.handle(new PacketStatusOutPong(packetstatusinping.a()));
    }
}
