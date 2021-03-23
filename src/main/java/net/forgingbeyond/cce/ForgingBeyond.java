package net.forgingbeyond.cce;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;

public class ForgingBeyond implements ModInitializer {

    public static final String MOD_NAME = "Forging Beyond";
    private Object ConfigInitializer() {
        if(!ProviderContent.INSTANCE.checkLimit()) {
            LogManager.getLogger("Forging Beyond's Verifier").warn("A value retrieved from the config is invalid! Defaulting to 40...");
        }
        return ProviderContent.INSTANCE.CONFIG;
    }
    @Override
    public void onInitialize() {
        LogManager.getLogger(MOD_NAME).info("Starting " + MOD_NAME + " mod!");
        LogManager.getLogger(MOD_NAME).info("This mod uses SimpleConfig by magistermaks on GitHub!");
        ConfigInitializer();
    }

}