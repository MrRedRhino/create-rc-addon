package mod.yourname.modid.blocks.servo_clutch;

import com.simibubi.create.content.contraptions.RotationPropagator;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.AbstractEncasedShaftBlock;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.utility.Iterate;
import mod.yourname.modid.register.ModTiles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ServoClutchBlock extends AbstractEncasedShaftBlock implements ITE<ServoClutchTileEntity> {
    int oldPower = 0;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public ServoClutchBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        TileEntity te = worldIn.getBlockEntity(pos);
        if (!(te instanceof KineticTileEntity)) return;

        KineticTileEntity kte = (KineticTileEntity) te;
        RotationPropagator.handleAdded(worldIn, pos, kte);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.isClientSide)
            return;

        if (hasPowerChanged(getPower(worldIn, pos))) {
            detachKinetics(worldIn, pos, true);
            worldIn.setBlock(pos, state.cycle(POWERED), 2);
        } // TODO remove block-state powered

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    protected boolean areStatesKineticallyEquivalent(BlockState oldState, BlockState newState) {
        return false;
    }

    public void detachKinetics(World worldIn, BlockPos pos, boolean reAttachNextTick) {
        TileEntity te = worldIn.getBlockEntity(pos);
        if (!(te instanceof KineticTileEntity))
            return;
        RotationPropagator.handleRemoved(worldIn, pos, (KineticTileEntity) te);

        // Re-attach next tick
        if (reAttachNextTick)
            worldIn.getBlockTicks().scheduleTick(pos, this, 0, TickPriority.EXTREMELY_HIGH);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTiles.SERVO_CLUTCH.create();
    }

    @Override
    public Class<ServoClutchTileEntity> getTileEntityClass() {
        return null;
    }

    private boolean hasPowerChanged(int newPower) {
        if (newPower != oldPower) {
            oldPower = newPower;
            return true;
        }
        return false;
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
