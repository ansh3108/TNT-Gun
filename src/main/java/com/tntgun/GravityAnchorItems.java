package com.tntgun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class GravityAnchorItems extends Item{
    public GravityAnchorItems(Item.Settings settings) { super(settings); }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected){
        if(entity instanceof PlayerEntity player && player.getOffHandStack() == stack) {
            Box area = player.getBoundingBox().expand(10.0);
            List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class, area, item -> true);

            for(ItemEntity item: items) {
                Vec3d pullVec = player.getPos().subtract(item.getPos()).normalize().multiply(0.2);

                if(player.getItemCooldownManager().isCoolingDown(this)){
                    item.setVelocity(0.0,0.5,0);
                } else {
                    item.addVelocity(pullVec.x, pullVec.y, pullVec.z);
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){
        user.getItemCooldownManager().set(this, 100);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}