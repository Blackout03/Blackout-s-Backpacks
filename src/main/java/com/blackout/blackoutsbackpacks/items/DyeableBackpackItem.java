package com.blackout.blackoutsbackpacks.items;

import com.blackout.blackoutsbackpacks.BlackoutsBackpacks;
import com.blackout.blackoutsbackpacks.registry.BBStats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
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

public class DyeableBackpackItem extends BackpackItem implements IDyeableArmorItem {
	public DyeableBackpackItem(Properties properties) {
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
				tag.putInt("height", 1);

				playerIn.getItemInHand(handIn).setTag(tag);
			}

			if (tag.getInt("width") != 12) {
				tag.putInt("width", 12);
			}

			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerIn;
			if (playerIn.getItemInHand(handIn).getItem() instanceof BackpackItem) {
				NetworkHooks.openGui(serverPlayerEntity, new BackpackItemContainerProvider(handIn, playerIn.getItemInHand(handIn), 12, 1), (buf) -> buf.writeInt(handIn == Hand.MAIN_HAND ? 0 : 1));
				playerIn.awardStat(BBStats.OPEN_BACKPACK);
			}
		}

		return ActionResult.pass(playerIn.getItemInHand(handIn));
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		int width = 12;
		int height = 1;

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
