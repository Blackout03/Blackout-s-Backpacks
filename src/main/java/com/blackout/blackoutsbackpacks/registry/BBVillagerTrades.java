package com.blackout.blackoutsbackpacks.registry;

import com.blackout.blackoutsbackpacks.BlackoutsBackpacks;
import com.blackout.blackoutsbackpacks.util.TradeUtil;
import com.blackout.blackoutsbackpacks.util.TradeUtil.BBDyedBackpackForEmeraldsTrade;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlackoutsBackpacks.MODID)
public class BBVillagerTrades {
    @SubscribeEvent
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        TradeUtil.addVillagerTrades(event, VillagerProfession.LEATHERWORKER, TradeUtil.APPRENTICE,
                new BBDyedBackpackForEmeraldsTrade(BBItems.LEATHER_BACKPACK.get(), 2, 12, 10)
        );
    }
}
