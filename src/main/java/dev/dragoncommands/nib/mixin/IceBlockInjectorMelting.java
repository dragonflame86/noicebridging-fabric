package dev.dragoncommands.nib.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class IceBlockInjectorMelting {

	@Shadow
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {}

	@Inject(at = @At("HEAD"), method = "randomTick")
	private void init(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
		if(!world.getDimension().ultrawarm() && (!state.isOf(Blocks.BLUE_ICE) || !state.isOf(Blocks.PACKED_ICE)))
			scheduledTick(state, world, pos, random);
		else {
			if(state.isOf(Blocks.BLUE_ICE)) {
				world.setBlockState(pos, Blocks.PACKED_ICE.getDefaultState());
			} else if(state.isOf(Blocks.PACKED_ICE)) {
				world.setBlockState(pos, Blocks.ICE.getDefaultState());
			}
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 10.0f, 1.0f, true);
			world.spawnParticles(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, random.nextBetween(5,10),0,0.1,0,0.05);
		}
	}
}