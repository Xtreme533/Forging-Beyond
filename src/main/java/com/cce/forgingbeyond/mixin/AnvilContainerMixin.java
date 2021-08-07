package com.cce.forgingbeyond.mixin;

import com.cce.forgingbeyond.ContentProvider;
import net.minecraft.container.AnvilContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilContainer.class)
public class AnvilContainerMixin {

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40))
    private int customLimitInt(int i) {
        return ContentProvider.getAnvilLimitInt();
    }
    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 39))
    private int customMaxInt(int i) {
        return ContentProvider.getAnvilLimitInt() - 1;
    }
}
