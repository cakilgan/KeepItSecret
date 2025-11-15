package com.github.cakilgan;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(value = Dist.CLIENT, modid = Reference.MODID)
public class KISEvents {
    @SubscribeEvent
    public static void RenderNametagSubscribe(RenderNameTagEvent event){
        if(event.getEntity() instanceof Player player){
            Player localPlayer = Minecraft.getInstance().player;

            if(localPlayer == null){
                Reference.LOGGER.warn("LocalPlayer is NULL.");
                return;
            }

            Vec3 start = localPlayer.getEyePosition(1);
            Vec3 end = player.getEyePosition(1);

            BlockHitResult hit_result = localPlayer.level()
                    .clip(
                            new ClipContext(
                                    start,
                                    end,
                                    ClipContext.Block.COLLIDER,
                                    ClipContext.Fluid.NONE,
                                    localPlayer
                            )
                    );

            if(hit_result.getType() == HitResult.Type.BLOCK){
                event.setCanRender(TriState.FALSE);
            }
        }
    }
}
