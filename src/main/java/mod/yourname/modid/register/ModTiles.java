package mod.yourname.modid.register;

import com.simibubi.create.content.contraptions.relays.encased.SplitShaftInstance;
import com.simibubi.create.content.contraptions.relays.encased.SplitShaftRenderer;
import com.simibubi.create.repack.registrate.util.entry.TileEntityEntry;
import mod.yourname.modid.CreateAddon;
import mod.yourname.modid.blocks.servo_clutch.ServoClutchTileEntity;

public class ModTiles {
    public static final TileEntityEntry<ServoClutchTileEntity> SERVO_CLUTCH = CreateAddon.registrate()
            .tileEntity("servo_clutch", ServoClutchTileEntity::new)
            .instance(() -> SplitShaftInstance::new)
            .validBlocks(ModBlocks.SERVO_CLUTCH)
            .renderer(() -> SplitShaftRenderer::new)
            .register();


    public static void register() {

    }
}
