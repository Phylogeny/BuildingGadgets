package com.direwolf20.buildinggadgets.eventhandlers;

import com.direwolf20.buildinggadgets.items.CopyPasteTool;
import com.direwolf20.buildinggadgets.items.ITemplate;
import com.direwolf20.buildinggadgets.network.PacketHandler;
import com.direwolf20.buildinggadgets.network.PacketRequestBlockMap;
import com.direwolf20.buildinggadgets.tools.PasteToolBufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ClientTickEvent {

    private static int counter = 0;
    private static boolean joinedWorld;

    @SubscribeEvent
    public static void onClientTick(@SuppressWarnings("unused") TickEvent.ClientTickEvent event) {
        counter++;
        if (counter > 600 || !joinedWorld) {
            if (!joinedWorld && counter > 200)
                joinedWorld = true;

            counter = 0;
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player == null) return;

            for (int i = 0; i < 36; ++i) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (!(stack.getItem() instanceof ITemplate)) continue;

                ITemplate template = (ITemplate) stack.getItem();
                String UUID = template.getUUID(stack);
                if (UUID != null && PasteToolBufferBuilder.isUpdateNeeded(UUID, stack)) {
                    //System.out.println("BlockMap Update Needed for UUID: " + UUID + " in slot " + i);
                    PacketHandler.INSTANCE.sendToServer(new PacketRequestBlockMap(template.getUUID(stack), !(template instanceof CopyPasteTool)));
                    joinedWorld = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onJoinWorld(@SuppressWarnings("unused") ClientConnectedToServerEvent event) {
        joinedWorld = false;
    }
}
