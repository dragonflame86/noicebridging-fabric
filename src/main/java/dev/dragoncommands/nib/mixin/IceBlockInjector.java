package dev.dragoncommands.nib.mixin;

import net.minecraft.block.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class IceBlockInjector extends AbstractBlock {

	public IceBlockInjector(Settings settings) {
		super(settings);
	}

	@Inject(at = @At("RETURN"), method = "hasRandomTicks", cancellable = true)
	private void init(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if(state.isOf(Blocks.BLUE_ICE) || state.isOf(Blocks.PACKED_ICE)) {
			cir.setReturnValue(true);
		}
	}
}