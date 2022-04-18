package mod.yourname.modid.network;

import mod.yourname.modid.network.packets.ControllerUpdatePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;


public class CreateRCPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int packetID = 0;
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("create_rc", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        CHANNEL.registerMessage(packetID++, ControllerUpdatePacket.class, ControllerUpdatePacket::encode,
                ControllerUpdatePacket::new, ControllerUpdatePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    public static <T> void sendToServer(T t) {
        CHANNEL.sendToServer(t);
    }
}
