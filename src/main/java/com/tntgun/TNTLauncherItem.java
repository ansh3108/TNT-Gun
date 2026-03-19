package com.tntgun;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TNTLauncherItem extends Item {
	public TNTLauncherItem(Settings settings) { super(settings); }

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE.value(), SoundCategory.NEUTRAL, 0.5F, 1.4F);

		if(!world.isClient){
			CustomTNTEntity tntEntity = new CustomTNTEntity(world, user);
			tntEntity.setItem(new ItemStack(net.minecraft.item.Items.TNT));

			tntEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
			world.spawnEntity(tntEntity);
		}

		user.getItemCooldownManager().set(this, 20);

		return TypedActionResult.success(itemStack, world.isClient());
	}
}
 
