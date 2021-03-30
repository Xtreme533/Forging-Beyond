package net.forgingbeyond.cce.mixin;

import net.forgingbeyond.cce.ProviderContent;
import net.minecraft.container.AnvilContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilContainer.class)
public class AnvilMixinContainer {

    @ModifyConstant(method = "updateResult()V", constant = @Constant(intValue = 40))
    private int setNewXPLvlLimit(int i) {
        return ProviderContent.INSTANCE.getLimitInteger();
    }
    @ModifyConstant(method = "updateResult()V", constant = @Constant(intValue = 39))
    private int SubtractFromXPLvlLimit(int i) {
        return ProviderContent.INSTANCE.getLimitInteger() - 1;
    }
}
