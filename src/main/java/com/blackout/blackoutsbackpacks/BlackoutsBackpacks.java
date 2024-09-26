package com.blackout.blackoutsbackpacks;

import com.blackout.blackoutsbackpacks.data.BBAdvancementProvider;
import com.blackout.blackoutsbackpacks.data.BBRecipeProvider;
import com.blackout.blackoutsbackpacks.data.BBTagProvider;
import com.blackout.blackoutsbackpacks.registry.BBContainerTypes;
import com.blackout.blackoutsbackpacks.registry.BBItems;
import com.blackout.blackoutsbackpacks.registry.BBStats;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import java.util.Optional;

@Mod(BlackoutsBackpacks.MODID)
public class BlackoutsBackpacks {
	public static final String MODID = "blackoutsbackpacks";
	public static final String MODNAME = "Blackout's Backpacks";
	public static ArtifactVersion VERSION = null;
	public static final Logger LOGGER = LogManager.getLogger();

	public BlackoutsBackpacks() {
		Optional<? extends ModContainer> opt = ModList.get().getModContainerById(MODID);
		if (opt.isPresent()) {
			IModInfo modInfo = opt.get().getModInfo();
			VERSION = modInfo.getVersion();
		} else {
			LOGGER.warn("Cannot get version from mod info");
		}

		LOGGER.debug(MODNAME + " Version is: " + VERSION);
		LOGGER.debug("Mod ID for " + MODNAME + " is: " + MODID);
		if (ModList.get().isLoaded("chaosawakens")) {
			LOGGER.debug(ChaosAwakens.MODNAME + " is installed. " + ChaosAwakens.MODNAME + " Compatibility is enabled!");
			LOGGER.debug("Mod ID for " + ChaosAwakens.MODNAME + " is: " + ChaosAwakens.MODID);
			LOGGER.debug(ChaosAwakens.MODNAME + " Version is: {}", ChaosAwakens.VERSION);
		}

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		eventBus.addListener(this::gatherData);

		BBItems.ITEMS.register(eventBus);
		if (ModList.get().isLoaded("chaosawakens") || DatagenModLoader.isRunningDataGen()) BBItems.ITEMS_CHAOSAWAKENS.register(eventBus);
		BBStats.STAT_TYPES.register(eventBus);
		BBContainerTypes.CONTAINER_TYPES.register(eventBus);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
	}

	private void gatherData(final GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		final ExistingFileHelper existing = event.getExistingFileHelper();

		if (event.includeServer()) {
			dataGenerator.addProvider(new BBAdvancementProvider(dataGenerator));
			dataGenerator.addProvider(new BBRecipeProvider(dataGenerator));
			dataGenerator.addProvider(new BBTagProvider.BBItemTagProvider(dataGenerator, existing));
		}
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		BBContainerTypes.registerScreens(event);
	}
}
