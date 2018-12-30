package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockDragonNest extends BlockMidnightPlant {

    public BlockDragonNest() {
        super(true);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityLivingBase && !entity.world.isRemote && entity.ticksExisted % 20 == 0) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModEffects.DRAGON_GUARD, 100, 0, false, true));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return canBlockStay(world, pos, state) && state.getBlock().isReplaceable(world, pos);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        return blockUp == ModBlocks.SHADOWROOT_LEAVES || blockUp == ModBlocks.DARK_WILLOW_LEAVES;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        super.randomDisplayTick(state, world, pos, rand);
        if (rand.nextInt(10) == 0) {
            Vec3d offset = getOffset(state, world, pos);
            double distX = rand.nextFloat() * 0.6 - 0.3d;
            MidnightParticles.DRIP.spawn(world, pos.getX() + 0.5d + offset.x + distX, pos.getY() + offset.y + Math.abs(distX), pos.getZ() + 0.5d + offset.z + (rand.nextBoolean() ? distX : -distX), 0d, 0d, 0d,191, 70, 82);
        }
    }
}
