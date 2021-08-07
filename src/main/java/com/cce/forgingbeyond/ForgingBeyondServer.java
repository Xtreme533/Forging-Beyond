
/*
 * Copyright (c) 2021 cce
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


package com.cce.forgingbeyond;

import com.cce.forgingbeyond.simpleconfig.SimpleConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class ForgingBeyondServer implements DedicatedServerModInitializer {
    private static final String SERVER_MOD_NAME = "Forging Beyond Server";

    @Override
    public void onInitializeServer() {
        final SimpleConfig serverConfig = SimpleConfig.of("forging-beyond-server").provider(ContentProvider::serverCfgString).request();
        final String disconnectMessage = "This server requires mod named 'Forging Beyond' to be able to join it. You can find the mod here:\n" +
                "https://www.curseforge.com/minecraft/mc-mods/forging-beyond";

        ServerPlayNetworking.registerGlobalReceiver(ContentProvider.CLIENT_TO_SERVER_ID, ((server, player, handler, buf, responseSender) -> {
            PacketByteBuf sBuf = PacketByteBufs.create();
            sBuf.writeInt(ContentProvider.getCustomIntLimit());
            if (ServerPlayNetworking.canSend(player, ContentProvider.SEND_INT_TO_CLIENT_ID)) {
                ServerPlayNetworking.send(player, ContentProvider.SEND_INT_TO_CLIENT_ID, sBuf);
            }
            else {
                player.networkHandler.disconnect(new LiteralText(disconnectMessage));
                ContentProvider.logger(SERVER_MOD_NAME).warn(player.getEntityName() + " has been kicked from the server, could not send back a packet response for an unknown reason!");
            }
        }));
        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                if (!ServerPlayNetworking.canSend(player, ContentProvider.SERVER_TO_CLIENT_ID)) {
                    ContentProvider.logger(SERVER_MOD_NAME).warn("Could not send custom packet to player " + player.getEntityName() + " !");
                    if (serverConfig.getOrDefault("should-kick-non-modded-clients", true)) {
                        player.networkHandler.disconnect(new LiteralText(disconnectMessage));
                    }

                }
            }
        }));
    }
}
