package com.github.standobyte.jojo.entity.stand.stands;

import java.util.function.Supplier;

import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.damaging.projectile.MRFlameEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagiciansRedEntity extends StandEntity {

    private static final DataParameter<Boolean> CASTING_FIRESTORM = EntityDataManager.defineId(MagiciansRedEntity.class, DataSerializers.BOOLEAN);
    
    public MagiciansRedEntity(StandEntityType<MagiciansRedEntity> type, World world) {
        super(type, world);
    }
    
    @Override
    public boolean attackEntity(Supplier<Boolean> doAttack, StandEntityPunch punch, StandEntityTask task) {
        return DamageUtil.dealDamageAndSetOnFire(punch.target, 
                entity -> super.attackEntity(doAttack, punch, task), 1, true);
    }
    
    @Override
    public void playStandSummonSound() {
        if (!isArmsOnlyMode()) {
            super.playStandSummonSound();
        }
    }
    
    @Deprecated
    public static void removeFireUnderPlayer(LivingEntity user, IStandPower power) {
        World world = user.level;
        if (!world.isClientSide() && user.isAlive()
                && power.isActive() && power.getStandManifestation() instanceof MagiciansRedEntity) {
            AxisAlignedBB userHitbox = user.getBoundingBox();
            BlockPos pos1 = new BlockPos(userHitbox.minX + 0.001D, userHitbox.minY + 0.001D, userHitbox.minZ + 0.001D);
            BlockPos pos2 = new BlockPos(userHitbox.maxX - 0.001D, userHitbox.maxY - 0.001D, userHitbox.maxZ - 0.001D);
            BlockPos.Mutable blockPos = new BlockPos.Mutable();
            if (world.hasChunksAt(pos1, pos2)) {
                for(int x = pos1.getX(); x <= pos2.getX(); ++x) {
                    for(int y = pos1.getY(); y <= pos2.getY(); ++y) {
                        for(int z = pos1.getZ(); z <= pos2.getZ(); ++z) {
                            blockPos.set(x, y, z);
                            BlockState blockState = world.getBlockState(blockPos);
                            if (!blockState.isAir(world, blockPos) && blockState.getBlock() instanceof AbstractFireBlock) {
                                world.destroyBlock(blockPos, false);
                            }
                        }
                    }
                }
            }
        }
    }

    @Deprecated
    public static void firestormCast(LivingEntity user, IStandPower power) {
        World world = user.level;
        if (power.getStandManifestation() instanceof MagiciansRedEntity) {
            StandEntity standEntity = ((StandEntity) power.getStandManifestation());
            if (!world.isClientSide()) {
                for (float i = 0.0f; i < 6.28f; i+=1.57) {
                    for (double j = 0.25; j < 4.404; j += 0.125) {
                        MRFlameEntity flame = new MRFlameEntity(standEntity, world, true, i, j);
                        selectFlamePosition(flame, i, j);
                        standEntity.addProjectileWithSetDamageMultipliedByStandStats(flame, 0.02f);
                        if (j > 3 && j < 4.279) {
                            flame = new MRFlameEntity(standEntity, world, true, i, j + 0.0625);
                            selectFlamePosition(flame, i, j + 0.0625);
                            standEntity.addProjectileWithSetDamageMultipliedByStandStats(flame, 0.02f);
                        }
                    }
                }
            }
        }
    }

    private static void selectFlamePosition(MRFlameEntity flame, float positionIndex, double j) {
        flame.moveTo(flame.getX() + flame.getYOffsetMultiplier() * flame.getRadius() * Math.cos(positionIndex), flame.getY() + j - 1.75, flame.getZ() + flame.getYOffsetMultiplier() * flame.getRadius() * Math.sin(positionIndex));
    }

    public boolean isCastingFirestorm() {
        return entityData.get(CASTING_FIRESTORM);
    }
    public void setCastingFirestorm(boolean cast) {
        entityData.set(CASTING_FIRESTORM, cast);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CASTING_FIRESTORM, false);
    }
}
