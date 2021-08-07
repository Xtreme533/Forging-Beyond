package com.cce.forgingbeyond.mixin;

import com.cce.forgingbeyond.ContentProvider;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

    @ModifyConstant(method = "drawForeground", constant = @Constant(intValue = 40))
    private int customLimitInt(int i) {
        return ContentProvider.getAnvilLimitInt();
    }
}
