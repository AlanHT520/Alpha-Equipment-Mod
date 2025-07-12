package net.alan.ae.entity.entities;

import net.alan.ae.entity.AlphaEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.alan.ae.entity.ai.WildShootFireballGoal;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;

public class WildFireEntity extends HostileEntity {
    public float eyeOffset = 0.5F;
    public int eyeOffsetCooldown;
    public static final TrackedData<Byte> WILD_FIRE_FLAGS;

    public final AnimationState idleAnimationState = new AnimationState();

    private final ServerBossBar bossBar;

    public void tick() {
        super.tick();

    }

    public WildFireEntity(EntityType<? extends WildFireEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.experiencePoints = 10;

        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), BossBar.Color.RED, BossBar.Style.PROGRESS)).setDarkenSky(true);

        this.getAttributeInstance(EntityAttributes.FALL_DAMAGE_MULTIPLIER)
                .setBaseValue(0.0);
    }

    protected void initGoals() {
        this.goalSelector.add(4, new WildShootFireballGoal(this));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createWildFireAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.ATTACK_DAMAGE, (double) 6.0F)
                .add(EntityAttributes.MOVEMENT_SPEED, (double) 0.23F)
                .add(EntityAttributes.FOLLOW_RANGE, (double) 48.0F)
                .add(EntityAttributes.MAX_HEALTH, (double) 100F);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(WILD_FIRE_FLAGS, (byte) 0);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }

    public float getBrightnessAtEyes() {
        return 1.0F;
    }

    public void tickMovement() {
        if (!this.isOnGround() && this.getVelocity().y < (double) 0.0F) {
            this.setVelocity(this.getVelocity().multiply((double) 1.0F, 0.6, (double) 1.0F));
        }

        if (this.getWorld().isClient) {
            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                this.getWorld().playSoundClient(this.getX() + (double) 0.5F, this.getY() + (double) 0.5F, this.getZ() + (double) 0.5F, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
            }

            for (int i = 0; i < 2; ++i) {
                this.getWorld().addParticleClient(ParticleTypes.LARGE_SMOKE, this.getParticleX((double) 0.5F), this.getRandomBodyY(), this.getParticleZ((double) 0.5F), (double) 0.0F, (double) 0.0F, (double) 0.0F);
            }
        }

        super.tickMovement();
    }

    public boolean hurtByWater() {
        return true;
    }

    public boolean isOnFire() {
        return this.isFireActive();
    }

    public boolean isFireActive() {
        return ((Byte) this.dataTracker.get(WILD_FIRE_FLAGS) & 1) != 0;
    }

    public void setFireActive(boolean fireActive) {
        byte b = (Byte) this.dataTracker.get(WILD_FIRE_FLAGS);
        if (fireActive) {
            b = (byte) (b | 1);
        } else {
            b = (byte) (b & -2);
        }

        this.dataTracker.set(WILD_FIRE_FLAGS, b);
    }

    static {
        WILD_FIRE_FLAGS = DataTracker.registerData(WildFireEntity.class, TrackedDataHandlerRegistry.BYTE);



        BiomeModifications.addSpawn(

                BiomeSelectors.includeByKey( // 仅保留玄武岩三角洲和荒地
                        BiomeKeys.BASALT_DELTAS,
                        BiomeKeys.NETHER_WASTES
                ),
                SpawnGroup.MONSTER,
                AlphaEntityType.WILD_FIRE,
                10,
                0,
                1
        );


        SpawnRestriction.register(
                AlphaEntityType.WILD_FIRE,       // 实体类型
                SpawnLocationTypes.ON_GROUND,    // 生成位置（地面）
                Heightmap.Type.MOTION_BLOCKING,  // 高度映射类型
                (entityType, world, spawnReason, pos, random) -> {
                    // 条件：亮度≤7、非液体方块、非灵魂沙/土
                    return world.getLightLevel(pos) <= 7
                            && !world.getFluidState(pos).isIn(FluidTags.LAVA)
                            && !world.getBlockState(pos.down()).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS);
                }
        );

    }
    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }
    @Override
    protected void mobTick(ServerWorld world) {
        --this.eyeOffsetCooldown;
        if (this.eyeOffsetCooldown <= 0) {
            this.eyeOffsetCooldown = 100;
            this.eyeOffset = (float) this.random.nextTriangular((double) 0.5F, 6.891);
        }

        LivingEntity livingEntity = this.getTarget();
        if (livingEntity != null && livingEntity.getEyeY() > this.getEyeY() + (double) this.eyeOffset && this.canTarget(livingEntity)) {
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(this.getVelocity().add((double) 0.0F, ((double) 0.3F - vec3d.y) * (double) 0.3F, (double) 0.0F));
            this.velocityDirty = true;
        }

        super.mobTick(world);
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }
}