package cc.abbie.mixin;

import net.minecraft.client.options.GameOptions;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.File;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static File global_options$globalGameDir(File orig) {
        String s;
        switch (Util.getOperatingSystem()) {
            case WINDOWS:
                s = System.getenv("APPDATA") + "/.minecraft";
                break;
            case OSX:
                s = System.getProperty("user.home") + "/Library/Application Support/minecraft";
                break;
            default:
                s = System.getProperty("user.home") + "/.minecraft";
                break;
        }
        File f = new File(s);
        f.mkdirs();
        return f;
    }
}