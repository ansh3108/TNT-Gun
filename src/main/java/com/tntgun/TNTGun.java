package com.tntgun;

import net.minecraft.registry.Registry; 
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class TNTGun implements ModInitializer {
	public static final String MOD_ID = "tntgun";

	public static final Item TNT_LAUNCHER = new TNTLauncherItem(new Item.Settings().maxCount(1));

	public static final EntityType<CustomTNTEntity> CUSTOM_TNT = Registry.register(
		Registries.ENTITY_TYPE,
		Identifier.of(MOD_ID, "custom_tnt"),
		EntityType.Builder.<CustomTNTEntity>create(CustomTNTEntity::new, SpawnGroup.MISC).dimensions(0.5f, 0.5f).build()
	);

	public static final Item NITRO_CANDY = Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "nitro_candy"), new NitroCandyItem(new Item.Settings().food(new FoodComponent.Builder().snack().statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 4), 1.0f).build())));

		public static final Item GRAVITY_ANCHOR = Registry.register(Registries.ITEM, 
        Identifier.of(MOD_ID, "gravity_anchor"), 
        new GravityAnchorItems(new Item.Settings().maxCount(1)));

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "tnt_launcher"), TNT_LAUNCHER);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.add(TNT_LAUNCHER);
			content.add(GRAVITY_ANCHOR);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
			content.add(NITRO_CANDY);
		});
	}

		

		

}

