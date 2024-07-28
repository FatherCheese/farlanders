package cookie.farlanders.core;

import cookie.farlanders.Farlanders;
import cookie.farlanders.FarlandersConfig;
import cookie.farlanders.core.block.SmokerBlock;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import org.useless.dragonfly.model.block.DFBlockModelBuilder;
import turniplabs.halplibe.helper.BlockBuilder;

public class FarlandersBlocks {
	private static int startingID = FarlandersConfig.cfg.getInt("IDs.startingBlockID");
	private static int nextID() {
		return ++startingID;
	}

	public static Block SMOKER;

	public static void initializeBlocks() {
		SMOKER = new BlockBuilder(Farlanders.MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(2.0F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockModel(block -> new DFBlockModelBuilder(
				Farlanders.MOD_ID)
				.setBlockModel("block/smoker.json")
				.build(block))
			.build(new SmokerBlock("smoker", nextID()));
	};
}
