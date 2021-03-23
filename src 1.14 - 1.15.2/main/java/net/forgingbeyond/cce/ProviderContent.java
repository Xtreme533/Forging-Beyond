package net.forgingbeyond.cce;

import net.forgingbeyond.cce.SimpleConfig.SimpleConfig;

public class ProviderContent {

    public static final ProviderContent INSTANCE = new ProviderContent();
    private String provider(String filename) {
        return "# GENERATED WITH SIMPLECONFIG BY MAGISTERMAKS\n\n" +
                "# This is a config file for the mod Forging Beyond.\n" +
                "#The value below is used by the mod to define at what XP level the anvil should block forging and display the message 'Too Expensive!'.\n" +
                "#The allowed range of values the mod supports is from 2 to 32,767.\n" +
                "#If given value is outside of the range or is not a number, the mod will use value '40' instead!\n\n" +
                "xp-level-limit=40";
    }
    SimpleConfig CONFIG = SimpleConfig.of("forging-beyond").provider(this::provider).request();
    private final int limitInteger = CONFIG.getOrDefault( "xp-level-limit", 40 );


    public boolean checkLimit() {
        try {
            return limitInteger <= Short.MAX_VALUE && limitInteger >= 2;
        } catch(NumberFormatException nfe) {
            return false;
        }
    }
    public int getLimitInteger() {

        if(checkLimit()) {
            return limitInteger;

        }
        else {
            return 40;
        }

    }
}
