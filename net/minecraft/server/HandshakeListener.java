package net.minecraft.server;

public class HandshakeListener implements PacketHandshakingInListener {

    private final MinecraftServer a;
    private final NetworkManager b;

    public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
        this.a = minecraftserver;
        this.b = networkmanager;
    }

    public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
        switch (HandshakeListener.SyntheticClass_1.a[packethandshakinginsetprotocol.a().ordinal()]) {
        case 1:
            this.b.setProtocol(EnumProtocol.LOGIN);
            ChatComponentText chatcomponenttext;

            if (packethandshakinginsetprotocol.b() > 107) {
                chatcomponenttext = new ChatComponentText("Outdated server! I\'m still on 1.9");
                this.b.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext));
                this.b.close(chatcomponenttext);
            } else if (packethandshakinginsetprotocol.b() < 107) {
                chatcomponenttext = new ChatComponentText("Outdated client! Please use 1.9");
                this.b.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext));
                this.b.close(chatcomponenttext);
            } else {
                this.b.setPacketListener(new LoginListener(this.a, this.b));
            }
            break;

        case 2:
            this.b.setProtocol(EnumProtocol.STATUS);
            this.b.setPacketListener(new PacketStatusListener(this.a, this.b));
            break;

        default:
            throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
        }

    }

    public void a(IChatBaseComponent ichatbasecomponent) {}

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumProtocol.values().length];

        static {
            try {
                HandshakeListener.SyntheticClass_1.a[EnumProtocol.LOGIN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                HandshakeListener.SyntheticClass_1.a[EnumProtocol.STATUS.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

        }
    }
}
