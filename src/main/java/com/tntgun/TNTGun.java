package com.tntgun;

import net.minecraft.registry.Registry; 
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
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

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "tnt_launcher"), TNT_LAUNCHER);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.add(TNT_LAUNCHER);
		});
	}
}