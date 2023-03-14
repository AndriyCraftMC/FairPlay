package FairPlay.utils;

import net.minecraft.server.v1_12_R1.*;

import java.util.Random;

public class NPC {

    public static void updateArmor(PlayerConnection connection, EntityPlayer npc) {
        Random random = new Random();

        npc.setSlot(EnumItemSlot.HEAD, new ItemStack(Armor.helments[random.nextInt(Armor.helments.length)]));
        npc.setSlot(EnumItemSlot.CHEST, new ItemStack(Armor.chestplates[random.nextInt(Armor.chestplates.length)]));
        npc.setSlot(EnumItemSlot.LEGS, new ItemStack(Armor.leggins[random.nextInt(Armor.leggins.length)]));
        npc.setSlot(EnumItemSlot.FEET, new ItemStack(Armor.boots[random.nextInt(Armor.boots.length)]));

        connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.HEAD, npc.getEquipment(EnumItemSlot.HEAD)));
        connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.CHEST, npc.getEquipment(EnumItemSlot.CHEST)));
        connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.LEGS, npc.getEquipment(EnumItemSlot.LEGS)));
        connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.FEET, npc.getEquipment(EnumItemSlot.FEET)));
    }

}
