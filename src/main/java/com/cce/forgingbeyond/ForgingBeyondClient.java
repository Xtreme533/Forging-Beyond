
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

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ForgingBeyondClient implements ClientModInitializer {
    private static final String MOD_NAME_CLIENT = "Forging Beyond Client";

    @Override
    public void onInitializeClient() {
        ClientEntityEvents.ENTITY_LOAD.register(((entity, world) -> {
            if (entity instanceof ClientPlayerEntity) {
                if (!MinecraftClient.getInstance().isInSingleplayer()) {
                    if (ClientPlayNetworking.canSend(ContentProvider.CLIENT_TO_SERVER_ID)) {
                        ClientPlayNetworking.send(ContentProvider.CLIENT_TO_SERVER_ID, PacketByteBufs.empty());
                    }
                    else {
                        ContentProvider.setAnvilLimitInt(40);
                        ContentProvider.logger(MOD_NAME_CLIENT).warn("Could not send custom packet to the server! Using Minecraft's vanilla value of 40...");

                    }
                }
                else {
                    ContentProvider.setAnvilLimitInt(ContentProvider.getCustomIntLimit());
                }
            }
        }));
        ClientPlayNetworking.registerGlobalReceiver(ContentProvider.SERVER_TO_CLIENT_ID, ((client, handler, buf, responseSender) -> {}));
        ClientPlayNetworking.registerGlobalReceiver(ContentProvider.SEND_INT_TO_CLIENT_ID, (client, handler, buf, responseSender) -> ContentProvider.setAnvilLimitInt(buf.readInt()));
    }
}
