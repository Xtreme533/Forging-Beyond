
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
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ContentProvider {
    private static int ANVIL_LIMIT_INT;
    private static final SimpleConfig CONFIG = SimpleConfig.of("forging-beyond").provider(ContentProvider::cfgString).request();
    static final Identifier SEND_INT_TO_CLIENT_ID = new Identifier("forging-beyond", "limit-int");
    static final Identifier CLIENT_TO_SERVER_ID = new Identifier("forging-beyond", "client-to-server");
    static final Identifier SERVER_TO_CLIENT_ID = new Identifier("forging-beyond", "server-to-client");

    @Contract(pure = true)
    static @NotNull String serverCfgString(String filename) {
        return "# GENERATED WITH SIMPLECONFIG BY MAGISTERMAKS\n\n" +

                "# This is a server-side config file for the mod Forging Beyond.\n" +
                "# The boolean below determines if the server should kick players from the server that don't have this mod.\n" +
                "# While this is not exactly vital for the mod to work properly, it's recommended to leave this on true, as players that don't have the mod installed\n" +
                "# might experience visual glitches while combining items more (or less) expensive than 40 levels. Change this at your own risk.\n\n" +

                "should-kick-non-modded-clients=true";
    }

    @Contract(pure = true)
    static @NotNull String cfgString(String filename) {
        return "# GENERATED WITH SIMPLECONFIG BY MAGISTERMAKS\n\n" +

                "# This is a config file for the mod Forging Beyond.\n" +
                "# The value below is used by the mod to define at what XP level the anvil should block forging items together and display the message 'Too Expensive!'.\n" +
                "# The allowed range of values the mod allows is from 2 to 32767.\n" +
                "# If given value is outside of the range or is not a number, the mod will use value '40' instead!\n" +
                "# In case of joining a multiplayer server, this value will be ignored in favor of the server's configured value, or lack thereof.\n\n" +

                "xp-level-limit=40";
    }

    static int getCustomIntLimit() {
        final int intLimit = ContentProvider.CONFIG.getOrDefault("xp-level-limit", 40);
        if (intLimit >= Short.MAX_VALUE || intLimit <= 2) {
            logger("Forging Beyond Config").warn("The given value is invalid! Defaulting to 40...");
            return 40;
        }
        return intLimit;
    }

    static void setAnvilLimitInt(int value) {
        ANVIL_LIMIT_INT = value;
    }

    public static int getAnvilLimitInt() {
        return ANVIL_LIMIT_INT;
    }

    static Logger logger(String name) {
        return LogManager.getLogger(name);
    }

}
