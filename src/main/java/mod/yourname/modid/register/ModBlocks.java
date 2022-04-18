package mod.yourname.modid.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.relays.encased.ClutchBlock;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.repack.registrate.util.entry.BlockEntry;
import mod.yourname.modid.CreateAddon;
import mod.yourname.modid.blocks.servo_clutch.ServoClutchBlock;
import mod.yourname.modid.blocks.servo_clutch.ServoClutchTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class ModBlocks {
    private static final CreateRegistrate REGISTRATE = CreateAddon.registrate()
            .itemGroup(() -> new ItemGroup("create_rc") {
                @Override
                public ItemStack makeIcon() {
                    return AllBlocks.CLUTCH.asStack();
                }
            });

    public static final BlockEntry<ServoClutchBlock> SERVO_CLUTCH = REGISTRATE.block("servo_clutch", ServoClutchBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(AbstractBlock.Properties::noOcclusion)
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {
    }
}
