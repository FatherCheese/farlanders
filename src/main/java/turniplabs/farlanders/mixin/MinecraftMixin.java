package turniplabs.farlanders.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {

    @Shadow private static Minecraft theMinecraft;

    @Inject(method = "getMinecraft(Ljava/lang/Object;)Lnet/minecraft/client/Minecraft;", at = @At("HEAD"), cancellable = true)
    private static void farlanders_minecraftFix(Object caller, CallbackInfoReturnable<Minecraft> cir) {
        cir.setReturnValue(theMinecraft);
    }
}
