package com.blackout.blackoutsbackpacks.items;

import com.blackout.blackoutsbackpacks.registry.BBStats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class AmethystBackpackItem extends BackpackItem {
	public AmethystBackpackItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		//make sure we're server side
		if (!worldIn.isClientSide) {
			CompoundNBT tag = new CompoundNBT();
			//if it doesn't have a tag - make one to stop crashes
			if (!playerIn.getItemInHand(handIn).hasTag()) {
				tag.putInt("width", 12);
				tag.putInt("height", 8);

				playerIn.getItemInHand(handIn).setTag(tag);
			}

			if (tag.getInt("width") != 12) {
				tag.putInt("width", 12);
			}

			if (tag.getInt("height") != 8) {
				tag.putInt("height", 8);
			}

			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerIn;
			NetworkHooks.openGui(serverPlayerEntity, new BackpackItemContainerProvider(handIn, playerIn.getItemInHand(handIn), 12, 8), (buf) -> buf.writeInt(handIn == Hand.MAIN_HAND ? 0 : 1));
			serverPlayerEntity.closeContainer();
			NetworkHooks.openGui(serverPlayerEntity, new BackpackItemContainerProvider(handIn, playerIn.getItemInHand(handIn), 12, 8), (buf) -> buf.writeInt(handIn == Hand.MAIN_HAND ? 0 : 1));
			playerIn.awardStat(BBStats.OPEN_BACKPACK);
		}

		return ActionResult.pass(playerIn.getItemInHand(handIn));
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		int width = 12;
		int height = 8;

		CompoundNBT tag = new CompoundNBT();
		if (stack.hasTag()) {
			assert stack.getTag() != null;
			if (stack.getTag().contains("width")) {
				width = stack.getTag().getInt("width");
				height = stack.getTag().getInt("height");
			}

			if (tag.getInt("width") != 12) {
				tag.putInt("width", 12);
			}

			if (tag.getInt("height") != 8) {
				tag.putInt("height", 8);
			}
		}

		StringTextComponent widthComponent = new StringTextComponent("Width: ");
		widthComponent.append(new StringTextComponent(width + "").withStyle(TextFormatting.LIGHT_PURPLE));

		StringTextComponent heightComponent = new StringTextComponent("Height: ");
		heightComponent.append(new StringTextComponent(height + "").withStyle(TextFormatting.LIGHT_PURPLE));

		tooltip.add(widthComponent);
		tooltip.add(heightComponent);

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
}
