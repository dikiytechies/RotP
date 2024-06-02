package com.github.standobyte.jojo.entity;

import com.github.standobyte.jojo.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class MRFireStormEntity extends Entity implements IEntityAdditionalSpawnData {

    private LivingEntity owner;

    public MRFireStormEntity(LivingEntity owner, World world) {
        this(ModEntityTypes.MR_FIRE_STORM_ENTITY.get(), world);
        this.owner = owner;
    }

    public MRFireStormEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (owner != null && owner.isAlive()) {

        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT pCompound) {

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return null;
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {

    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {

    }

    protected float movementSpeed() {
        boolean shouldMove = distanceTo(owner) >= 3.5f;
        return shouldMove? Math.min(1.0f, distanceTo(owner) / 20) : 0.0f;
    }
}
