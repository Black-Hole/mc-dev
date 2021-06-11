package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class PacketDecompressor extends ByteToMessageDecoder {

    public static final int MAXIMUM_DECOMPRESSED_LENGTH = 2097152;
    private final Inflater inflater;
    private int threshold;

    public PacketDecompressor(int i) {
        this.threshold = i;
        this.inflater = new Inflater();
    }

    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List<Object> list) throws Exception {
        if (bytebuf.readableBytes() != 0) {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf);
            int i = packetdataserializer.j();

            if (i == 0) {
                list.add(packetdataserializer.readBytes(packetdataserializer.readableBytes()));
            } else {
                if (i < this.threshold) {
                    throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.threshold);
                }

                if (i > 2097152) {
                    throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of 2097152");
                }

                byte[] abyte = new byte[packetdataserializer.readableBytes()];

                packetdataserializer.readBytes(abyte);
                this.inflater.setInput(abyte);
                byte[] abyte1 = new byte[i];

                this.inflater.inflate(abyte1);
                list.add(Unpooled.wrappedBuffer(abyte1));
                this.inflater.reset();
            }

        }
    }

    public int a() {
        return this.threshold;
    }

    public void a(int i) {
        this.threshold = i;
    }
}
