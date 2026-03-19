package com.tntgun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;

public class NitroCandyItem extends Item {
    public NitroCandyItem(Settings settings) { super(settings); }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {            
            if (!player.isOnGround() && !player.hasStatusEffect(StatusEffects.SPEED) && player.velocityModified) {
                player.fallDistance = 0; 
            }

            if (player.hasStatusEffect(StatusEffects.SPEED)) {
                if (world.isClient && player.getVelocity().horizontalLengthSquared() > 0.01) {
                    world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), 0, 0.1, 0);
                }

                if (!world.isClient) {
                    Vec3d lookDir = player.getRotationVec(1.0F).multiply(1.2);
                    BlockPos frontPos = BlockPos.ofFloored(player.getX() + lookDir.x, player.getY() + 0.8, player.getZ() + lookDir.z);

                    if (world.getBlockState(frontPos).isFullCube(world, frontPos) && player.getVelocity().horizontalLengthSquared() > 0.02) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 40, 5, false, false));
                        world.createExplosion(null, player.getX(), player.getY(), player.getZ(), 2.0f, false, ExplosionSourceType.NONE);
                        player.addVelocity(0, 1.5, 0);
                        player.velocityModified = true;
                        if (player instanceof ServerPlayerEntity) {
                            player.velocityDirty = true;
                        }
                        player.removeStatusEffect(StatusEffects.SPEED);
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), 
                            net.minecraft.sound.SoundEvents.ENTITY_GENERIC_EXPLODE, 
                            net.minecraft.sound.SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }
                }
            }
        }
    }
}