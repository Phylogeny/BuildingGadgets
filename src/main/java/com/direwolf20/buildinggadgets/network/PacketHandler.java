package com.direwolf20.buildinggadgets.network;

import com.direwolf20.buildinggadgets.BuildingGadgets;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BuildingGadgets.MODID);
    private static int packetId = 0;

    public static void registerMessages() {

        // Server side
        registerMessage(PacketToggleMode.Handler.class, PacketToggleMode.class, Side.SERVER);
        registerMessage(PacketChangeRange.Handler.class, PacketChangeRange.class, Side.SERVER);
        registerMessage(PacketUndoKey.Handler.class, PacketUndoKey.class, Side.SERVER);
        registerMessage(PacketAnchorKey.Handler.class, PacketAnchorKey.class, Side.SERVER);
        registerMessage(PacketRequestBlockMap.Handler.class, PacketRequestBlockMap.class, Side.SERVER);
        registerMessage(PacketTemplateManagerSave.Handler.class, PacketTemplateManagerSave.class, Side.SERVER);
        registerMessage(PacketTemplateManagerLoad.Handler.class, PacketTemplateManagerLoad.class, Side.SERVER);
        registerMessage(PacketTemplateManagerPaste.Handler.class, PacketTemplateManagerPaste.class, Side.SERVER);
        registerMessage(PacketCopyCoords.Handler.class, PacketCopyCoords.class, Side.SERVER);
        registerMessage(PacketDestructionGUI.Handler.class, PacketDestructionGUI.class, Side.SERVER);

        // Client side
        registerMessage(PacketBlockMap.Handler.class, PacketBlockMap.class, Side.CLIENT);
    }

    private static void registerMessage(Class handler, Class packet, Side side) {
        INSTANCE.registerMessage(handler, packet, packetId++, side);
    }
}
