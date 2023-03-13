package io.github.thebusybiscuit.slimefun4.implementation.listeners.entity;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.blockpattern.TShapedBlockPattern;
import io.github.thebusybiscuit.slimefun4.utils.blockpattern.WitherBuildPattern;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Arrays;
import java.util.Collection;

/**
 * This {@link Listener} makes sure that iron golems, snowman (snowmen) and withers cannot be
 * built/spawned by the player if the blocks which are used to build the creature are tracked by
 * {@link BlockStorage}
 */
public class CreatureBuildListener implements Listener {

    public CreatureBuildListener(Slimefun plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // We only care about building iron golems, snowmen and withers
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM
                && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN
                && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.BUILD_WITHER) {
            return;
        }

        Location location = event.getLocation();
        Collection<Block> removedBlocks;
        switch (event.getEntityType()) {
            case IRON_GOLEM -> {
                // Add the iron blocks
                removedBlocks = TShapedBlockPattern.getMatchingBlocks(Material.IRON_BLOCK, location);
                // Add the carved pumpkin head
                removedBlocks.add(location.getBlock().getRelative(BlockFace.UP, 2));
            }
            case SNOWMAN -> {
                // Add the two snow blocks and the carved pumpkin head
                Block base = location.getBlock();
                removedBlocks = Arrays.asList(base, base.getRelative(BlockFace.UP), base.getRelative(BlockFace.UP, 2));
            }
            case WITHER ->
                // Add the soul sand and wither skulls
                removedBlocks = WitherBuildPattern.getMatchingBlocks(location);
            default -> {
                // This should not happen as we checked the SpawnReason earlier
                // This return statement is just to make the compiler happy
                return;
            }
        }
        for (Block block : removedBlocks) {
            if (BlockStorage.hasBlockInfo(block)) {
                // We have found a special block; we should deny the creature spawn
                event.setCancelled(true);
                return;
            }
        }
    }

}