package com.tntgun;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CustomTNTEntity extends ThrownItemEntity{
    public CustomTNTEntity(EntityType<? extends ThrownItemEntity> entityType, World world){
        super(entityType, world);
    }
    public CustomTNTEntity(World world, LivingEntity owner){
        super(TNTGun.CUSTOM_TNT, owner, world);
    }
    @Override
    protected Item getDefaultItem() {
        return Items.TNT;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(!this.getWorld().isClient){
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 4.0f, World.ExplosionSourceType.TNT);
            this.discard(); 
        }
    }
}