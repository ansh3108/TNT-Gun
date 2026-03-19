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
import net.minecraft.util.math.Box; 
import net.minecraft.util.math.Vec3d;

public class NitroCandyItem extends Item {
    public NitroCandyItem(Settings settings) { super(settings); }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            if(player.hasStatusEffect(StatusEffects.RESISTANCE) && 
            player.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() >= 250) {
            player.fallDistance = 0;
        }

        if(player.hasStatusEffect(StatusEffects.SPEED)) {
            if(world.isClient && player.getVelocity().horizontalLengthSquared() > 0.01) {
                world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), 0, 0.1, 0);
            }

            if(!world.isClient) {
                Vec3d velocity = player.getVelocity();
                if(velocity.horizontalLengthSquared() > 0.01) {
                    Box sensorBox = player.getBoundingBox().stretch(velocity.x * 3, 0, velocity.z * 3).expand(0.1);
                    
                     boolean isHittingWall = world.getBlockCollisions(player, sensorBox).iterator().hasNext();

                    if(isHittingWall) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 254, false, false, true));
                        world.createExplosion(null, player.getX(), player.getY(), player.getZ(), 2.0f, false, World.ExplosionSourceType.NONE);
                        player.addVelocity(0, 1.6, 0);
                        player.velocityModified = true;
                        if (player instanceof ServerPlayerEntity serverPlayer) {
                            serverPlayer.velocityDirty = true;
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
}}

