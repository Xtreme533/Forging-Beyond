package net.forgingbeyond.cce.mixin;

import net.forgingbeyond.cce.ProviderContent;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public class AnvilMixinScreen {

    @ModifyConstant(method = "drawForeground", constant = @Constant(intValue = 40))
    private int TooExpensiveValue(int i) {
        return ProviderContent.INSTANCE.getLimitInteger();
    }
}
