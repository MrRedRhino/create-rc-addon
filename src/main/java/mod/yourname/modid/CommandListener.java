package mod.yourname.modid;

import mod.yourname.modid.network.CreateRCPacketHandler;
import mod.yourname.modid.network.packets.ControllerUpdatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatEvent;

public class CommandListener {
    public static float speed = 10;
    public static void processCommand(ClientChatEvent event) {
        final Minecraft MC = Minecraft.getInstance();
        try {
            int number = Integer.parseInt(event.getMessage());
            speed = number;
//            CreateRCPacketHandler.sendToServer(new ControllerUpdatePacket(number, testIS(), testIS()));

        } catch (Exception e) {
            Minecraft.getInstance().gui.getChat().addMessage(
                    ITextComponent.nullToEmpty("Das war kein integer, dummy!"));
        }
    }

    private static ItemStack testIS() {
        return Items.ACACIA_PLANKS.getDefaultInstance();
    }
}
