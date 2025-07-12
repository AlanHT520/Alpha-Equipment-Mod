package net.alan.ae.entity.ai;

import net.alan.ae.entity.entities.WildFireEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class WildShootFireballGoal extends Goal {
    public final WildFireEntity WildFire;
    public int fireballsFired;
    public int fireballCooldown;
    public int targetNotVisibleTicks;

    public WildShootFireballGoal(WildFireEntity WildFire) {
        this.WildFire = WildFire;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.WildFire.getTarget();
        return livingEntity != null && livingEntity.isAlive() && this.WildFire.canTarget(livingEntity);
    }

    public void start() {
        this.fireballsFired = 0;
    }

    public void stop() {
        this.WildFire.setFireActive(false);
        this.targetNotVisibleTicks = 0;
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        --this.fireballCooldown;
        LivingEntity livingEntity = this.WildFire.getTarget();
        if (livingEntity != null) {
            boolean bl = this.WildFire.getVisibilityCache().canSee(livingEntity);
            if (bl) {
                this.targetNotVisibleTicks = 0;
            } else {
                ++this.targetNotVisibleTicks;
            }

            double d = this.WildFire.squaredDistanceTo(livingEntity);
            if (d < 4.0) {
                if (!bl) {
                    return;
                }

                if (this.fireballCooldown <= 0) {
                    this.fireballCooldown = 20;
                    this.WildFire.tryAttack(getServerWorld(this.WildFire), livingEntity);
                }

                this.WildFire.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
            } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                double e = livingEntity.getX() - this.WildFire.getX();
                double f = livingEntity.getBodyY(0.5) - this.WildFire.getBodyY(0.5);
                double g = livingEntity.getZ() - this.WildFire.getZ();
                if (this.fireballCooldown <= 0 && this.fireballsFired < 5) {
                    ++this.fireballsFired;
                    if (this.fireballsFired == 1) {
                        this.fireballCooldown = 10; // 首次发射后0.5秒间隔
                        this.WildFire.setFireActive(true);
                    } else {
                        this.fireballCooldown = 10; // 后续回合保持0.5秒间隔
                    }

                    if (this.fireballsFired > 0) {
                        double h = Math.sqrt(Math.sqrt(d)) * 0.5;
                        if (!this.WildFire.isSilent()) {
                            this.WildFire.getWorld().syncWorldEvent(null, 1018, this.WildFire.getBlockPos(), 0);
                        }

                        // 计算90度扇形内的15个发射角度
                        double baseAngle = Math.atan2(g, e);
                        double startAngle = baseAngle - Math.PI / 4; // -45度
                        double endAngle = baseAngle + Math.PI / 4;   // +45度
                        double angleStep = (endAngle - startAngle) / 14; // 14个间隔产生15个角度

                        for (int i = 0; i < 15; ++i) {
                            double angle = startAngle + angleStep * i;
                            double spread = this.WildFire.getRandom().nextGaussian() * 0.02;
                            double dirX = Math.cos(angle + spread) * Math.sqrt(e * e + g * g);
                            double dirZ = Math.sin(angle + spread) * Math.sqrt(e * e + g * g);
                            Vec3d vec3d = new Vec3d(dirX, f, dirZ).normalize().multiply(0.8 + this.WildFire.getRandom().nextDouble() * 0.4);
                            SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.WildFire.getWorld(), this.WildFire, vec3d);
                            smallFireballEntity.setPosition(smallFireballEntity.getX(), this.WildFire.getBodyY(0.5) + 0.5, smallFireballEntity.getZ());
                            this.WildFire.getWorld().spawnEntity(smallFireballEntity);
                        }

                        if (this.fireballsFired == 5) {
                            this.fireballCooldown = 60; // 5回合后3秒冷却
                            this.fireballsFired = 0;
                            this.WildFire.setFireActive(false);
                        }
                    }
                }

                this.WildFire.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
            } else if (this.targetNotVisibleTicks < 5) {
                this.WildFire.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), (double) 1.0F);
            }

            super.tick();
        }
    }

    public double getFollowRange() {
        return this.WildFire.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
    }
}
