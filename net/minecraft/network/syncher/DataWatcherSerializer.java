package net.minecraft.network.syncher;

import net.minecraft.network.PacketDataSerializer;

public interface DataWatcherSerializer<T> {

    void a(PacketDataSerializer packetdataserializer, T t0);

    T a(PacketDataSerializer packetdataserializer);

    default DataWatcherObject<T> a(int i) {
        return new DataWatcherObject<>(i, this);
    }

    T a(T t0);
}
