package mod.yourname.modid.network.packets;

import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.IRedstoneLinkable;
import com.simibubi.create.content.logistics.RedstoneLinkNetworkHandler.Frequency;
import com.simibubi.create.foundation.tileEntity.behaviour.linked.LinkBehaviour;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ControllerUpdatePacket {
    int power;
    ItemStack f1;
    ItemStack f2;

    public ControllerUpdatePacket(int power, ItemStack f1, ItemStack f2) {
        this.power = power;
        this.f1 = f1;
        this.f2 = f2;
    }

    public ControllerUpdatePacket(PacketBuffer buf) {
        this(buf.readInt(), buf.readItem(), buf.readItem());
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(power);
        buf.writeItem(f1);
        buf.writeItem(f2);
    }

    public static void handle(ControllerUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            if (sender == null) return;

            updateLinks(sender.getLevel(), Frequency.of(msg.f1), Frequency.of(msg.f2), msg.power);

        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public String toString() {
        return "ControllerUpdatePacket{" + "power=" + power + ", f1=" + f1 + ", f2=" + f2 + '}';
    }

    public static void updateLinks(World w, Frequency f1, Frequency f2, int power) {

        for (Map.Entry<Pair<Frequency, Frequency>,
                Set<IRedstoneLinkable>> e : Create.REDSTONE_LINK_NETWORK_HANDLER.networksIn(w).entrySet()) {

            if (e.getKey().getLeft().equals(f1) && e.getKey().getRight().equals(f2)) {
                for (IRedstoneLinkable rl : e.getValue()) {

                    if (rl instanceof LinkBehaviour) {
                        LinkBehaviour linkBehaviour = (LinkBehaviour) rl;
                        if (linkBehaviour.isListening()) {
                            linkBehaviour.newPosition = true;
                            linkBehaviour.setReceivedStrength(power);
                        }
                    }
                }
            }
        }
    }
}
