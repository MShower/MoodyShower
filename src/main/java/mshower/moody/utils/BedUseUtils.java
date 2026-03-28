package mshower.moody.utils;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static mshower.moody.MoodyShower.config;

public class BedUseUtils {
    public static void listenInTheEnd() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient || !config.toggleBedUseInTheEnd) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (world.getRegistryKey() == World.END && state.getBlock() instanceof BedBlock) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
    public static void listenInTheNether() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient || !config.toggleBedUseInTheNether) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (world.getRegistryKey() == World.NETHER && state.getBlock() instanceof BedBlock) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
}
