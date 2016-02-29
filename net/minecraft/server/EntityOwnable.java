package net.minecraft.server;

import java.util.UUID;

public interface EntityOwnable {

    UUID getOwnerUUID();

    Entity getOwner();
}
