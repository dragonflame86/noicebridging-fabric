package dev.dragoncommands.nib.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public abstract class NormalIceInject extends TransparentBlock {

    public NormalIceInject(Settings settings) {
        super(settings);
    }

    @Shadow
    protected void melt(BlockState state, World world, BlockPos pos) {}

    @Inject(at=@At("HEAD"), method = "randomTick")
    public void pleaseMelt(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if(world.getDimension().ultrawarm())
            melt(state, world, pos);
    }
}
