package mod.yourname.modid.blocks.servo_clutch;

import com.simibubi.create.content.contraptions.relays.encased.SplitShaftTileEntity;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ServoClutchTileEntity extends SplitShaftTileEntity {
    public ServoClutchTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        if (isVirtual() || !hasSource() || face == getSourceFacing()) return 1;

        int pwr = getPower(level, worldPosition);
        return pwr == 0 ? 0 : pwr / 15f;
    }

    private int getPower(World worldIn, BlockPos pos) {
        int power = 0;
        for (Direction direction : Iterate.directions)
            power = Math.max(worldIn.getSignal(pos.relative(direction), direction), power);
        for (Direction direction : Iterate.directions)
            power = Math.max(worldIn.getSignal(pos.relative(direction), Direction.UP), power);
        return power;
    }
}
