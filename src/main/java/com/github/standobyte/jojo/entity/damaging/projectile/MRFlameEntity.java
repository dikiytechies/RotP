package com.github.standobyte.jojo.entity.damaging.projectile;

import java.util.Collections;
import java.util.Optional;

import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.action.stand.CrazyDiamondRestoreTerrain;
import com.github.standobyte.jojo.capability.entity.LivingUtilCapProvider;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.ModBlocks;
import com.github.standobyte.jojo.init.ModEntityTypes;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.network.NetworkUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

public class MRFlameEntity extends ModdedProjectileEntity {
    private Vector3d startingPos = null;
    Entity epicenter = null;
    float positionIndex = 0.0f;
    float radius = 5.5f;
    float interval = 2.0f; //value * 6
    double yOffset = 0.0;
    double yOffsetMultiplier = 0.0;

    public MRFlameEntity(LivingEntity shooter, World world) {
        super(ModEntityTypes.MR_FLAME.get(), shooter, world);
    }

    public MRFlameEntity(LivingEntity shooter, World world, boolean isShooterEpicenter) {
        super(ModEntityTypes.MR_FLAME.get(), shooter, world);
        if (isShooterEpicenter) epicenter = shooter;
        if (shooter instanceof StandEntity) radius += 10 * ((StandEntity) shooter).getUserPower().getResolve() / ((StandEntity) shooter).getUserPower().getMaxResolve();
    }

    public MRFlameEntity(LivingEntity shooter, World world, Entity epicenter) {
        super(ModEntityTypes.MR_FLAME.get(), shooter, world);
        this.epicenter = epicenter;
        if (shooter instanceof StandEntity) radius += 10 * ((StandEntity) shooter).getUserPower().getResolve() / ((StandEntity) shooter).getUserPower().getMaxResolve();
    }

    public MRFlameEntity(LivingEntity shooter, World world, boolean isShooterEpicenter, float positionIndex) {
        super(ModEntityTypes.MR_FLAME.get(), shooter, world);
        if (isShooterEpicenter) epicenter = shooter;
        this.positionIndex = positionIndex;
        if (shooter instanceof StandEntity) radius += 10 * ((StandEntity) shooter).getUserPower().getResolve() / ((StandEntity) shooter).getUserPower().getMaxResolve();
    }

    public MRFlameEntity(LivingEntity shooter, World world, Entity epicenter, float positionIndex) {
        super(ModEntityTypes.MR_FLAME.get(), shooter, world);
        this.epicenter = epicenter;
        this.positionIndex = positionIndex;
        if (shooter instanceof StandEntity) radius += 10 * ((StandEntity) shooter).getUserPower().getResolve() / ((StandEntity) shooter).getUserPower().getMaxResolve();
    }

    public MRFlameEntity(LivingEntity shooter, World world, boolean isShooterEpicenter, float positionIndex, double yOffset) {
        super(ModEntityTypes.MR_FLAME.get(), shooter, world);
        if (isShooterEpicenter) epicenter = shooter;
        this.positionIndex = positionIndex;
        this.yOffset = yOffset;
        if (shooter instanceof StandEntity) radius += 10 * ((StandEntity) shooter).getUserPower().getResolve() / ((StandEntity) shooter).getUserPower().getMaxResolve();
    }

    public MRFlameEntity(LivingEntity shooter, World world, Entity epicenter, float positionIndex, double yOffset) {
        super(ModEntityTypes.MR_FLAME.get(), shooter, world);
        this.epicenter = epicenter;
        this.positionIndex = positionIndex;
        this.yOffset = yOffset;
        if (shooter instanceof StandEntity) radius += 10 * ((StandEntity) shooter).getUserPower().getResolve() / ((StandEntity) shooter).getUserPower().getMaxResolve();
    }

    protected MRFlameEntity(EntityType<? extends MRFlameEntity> type, LivingEntity shooter, World world) {
        super(type, shooter, world);
    }

    public MRFlameEntity(EntityType<? extends MRFlameEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean standDamage() {
        return true;
    }

    @Override
    public float getBaseDamage() {
        return 1.0F;
    }

    @Override
    protected float knockbackMultiplier() {
        return 0.02F;
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        startingPos = position();
    }

    @Override
    protected boolean hurtTarget(Entity target, LivingEntity owner) {
        if (hasEpicenter() && target instanceof LivingEntity) {
            if (((LivingEntity) target).getEffect(ModStatusEffects.SLOWBURN.get()) == null) {
                ((LivingEntity) target).addEffect(new EffectInstance(ModStatusEffects.SLOWBURN.get(), 61, 1, false, false, true));
            } else {
                ((LivingEntity) target).addEffect(new EffectInstance(ModStatusEffects.SLOWBURN.get(), 61, ((LivingEntity) target).getEffect(ModStatusEffects.SLOWBURN.get()).getAmplifier() + 1, false, false, true));
            }
        } else if (target instanceof LivingEntity) {
            int burnAmplifier = (int) Math.floor((double) target.getCapability(LivingUtilCapProvider.CAPABILITY).map(cap -> cap.getBurnRate()).orElse(0.0f));
            ((LivingEntity) target).addEffect(new EffectInstance(ModStatusEffects.SLOWBURN.get(), 61, burnAmplifier, false, false, true));
            target.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.addBurnRate(0.2f));
        }
        return DamageUtil.dealDamageAndSetOnFire(target,
                entity -> super.hurtTarget(entity, owner), 3, true);
    }

    @Override
    protected RayTraceResult[] rayTrace() {
        return new RayTraceResult[]{JojoModUtil.getHitResult(this, this::canHitEntity, RayTraceContext.BlockMode.OUTLINE)};
    }

    @Override
    protected void afterBlockHit(BlockRayTraceResult blockRayTraceResult, boolean blockDestroyed) {
        if (!level.isClientSide) {
            if (ForgeEventFactory.getMobGriefingEvent(level, getEntity())) {
                BlockPos blockPos = blockRayTraceResult.getBlockPos();
                BlockState blockState = level.getBlockState(blockPos);
                if (!meltIceAndSnow(level, blockState, blockPos) &&
                        blockState.getCollisionShape(level, blockPos) != VoxelShapes.empty()) {
                    blockPos = blockPos.relative(blockRayTraceResult.getDirection());
                    if (level.isEmptyBlock(blockPos)) {
                        level.setBlockAndUpdate(blockPos, ModBlocks.MAGICIANS_RED_FIRE.get().getStateForPlacement(level, blockPos));
                    }
                }
            }
        }
    }

    public static boolean meltIceAndSnow(World world, BlockState blockState, BlockPos blockPos) {
        if (world.isClientSide()) return false;
        if (blockState.getMaterial() == Material.SNOW || blockState.getMaterial() == Material.TOP_SNOW
                || blockState.getMaterial() == Material.ICE || blockState.getMaterial() == Material.ICE_SOLID) {
            if (world.dimensionType().ultraWarm() || !blockState.isCollisionShapeFullBlock(world, blockPos)) {
                CrazyDiamondRestoreTerrain.rememberBrokenBlock(world, blockPos, blockState,
                        Optional.ofNullable(world.getBlockEntity(blockPos)), Collections.emptyList());
                world.removeBlock(blockPos, false);
            } else {
                world.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
                world.neighborChanged(blockPos, Blocks.WATER, blockPos);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void breakProjectile(TargetType targetType, RayTraceResult hitTarget) {
        if (targetType == TargetType.BLOCK) {
            BlockRayTraceResult blockHit = (BlockRayTraceResult) hitTarget;
            BlockPos blockPos = blockHit.getBlockPos();
            BlockState blockState = level.getBlockState(blockPos);
            if (!blockState.isCollisionShapeFullBlock(level, blockPos)) return;
        }
        super.breakProjectile(targetType, hitTarget);
    }

    @Override
    protected boolean canBreakBlock(BlockPos blockPos, BlockState blockState) {
        return super.canBreakBlock(blockPos, blockState) && !(blockState.getBlock() instanceof AbstractFireBlock);
    }

    @Override
    protected DamageSource getDamageSource(LivingEntity owner) {
        return super.getDamageSource(owner).setIsFire();
    }

    @Override
    public void tick() {
        if (isInWaterOrRain()) {
            clearFire();
        } else {
            if (hasEpicenter()) {
                if (!epicenter.isAlive()) this.remove();
                this.setDeltaMovement(nextPositionVec());
            }
            super.tick();
        }
    }

    @Override
    public void clearFire() {
        super.clearFire();
        if (!level.isClientSide()) {
            JojoModUtil.extinguishFieryStandEntity(this, (ServerWorld) level);
        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isFiery() {
        return true;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0;
    }

    @Override
    public int ticksLifespan() {
        return hasEpicenter() ? Math.round(interval * 6) / 4 : 8;
    }

    @Override
    protected Vector3d getOwnerRelativeOffset() {
        return Vector3d.ZERO;
    }

    private static final Vector3d OFFSET_XROT = new Vector3d(0, 0.2, 0.0);

    @Override
    protected Vector3d getXRotOffset() {
        return OFFSET_XROT;
    }

    public Vector3d getStartingPos() {
        return startingPos;
    }

    private Vector3d nextPositionVec() {
            positionIndex = (positionIndex + 1  / interval) % (float) (2 * Math.PI);
            Vector3d ownerDistance = epicenter.position().subtract(this.position());
            Vector3d vec = new Vector3d(getYOffsetMultiplier() * radius * Math.cos(positionIndex), yOffset, getYOffsetMultiplier() * radius * Math.sin(positionIndex));
            return vec.add(ownerDistance);
    }

    public double getYOffsetMultiplier() {
        return -0.005 * Math.pow(yOffset, 4) + 0.2 * yOffset + 1;
    }


    public boolean hasEpicenter() {
        return epicenter != null;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
        boolean hasStartingPos = startingPos != null;
        buffer.writeBoolean(hasStartingPos);
        if (hasStartingPos) {
            buffer.writeDouble(startingPos.x);
            buffer.writeDouble(startingPos.y);
            buffer.writeDouble(startingPos.z);
        }
        if (yOffset != 0.0) buffer.writeDouble(yOffset);
        buffer.writeFloat(radius);
        buffer.writeFloat(positionIndex);
        NetworkUtil.writeOptional(buffer, Optional.ofNullable(epicenter).map(Entity::getId),
                id -> buffer.writeInt(id));
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
        if (additionalData.readBoolean()) {
            startingPos = new Vector3d(additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble());
        }
        else {
            startingPos = position();
        }
        yOffset = additionalData.readDouble();
        radius = additionalData.readFloat();
        positionIndex = additionalData.readFloat();
        NetworkUtil.readOptional(additionalData, () -> additionalData.readInt())
                .ifPresent(id -> epicenter = level.getEntity(id));
    }
}
