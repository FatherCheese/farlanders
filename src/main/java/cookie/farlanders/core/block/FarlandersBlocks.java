package cookie.farlanders.core.block;

import cookie.farlanders.core.Farlanders;
import cookie.farlanders.core.block.logic.BlockLogicSmoker;
import cookie.farlanders.extra.FarlandersConfig;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

public class FarlandersBlocks implements BlockInitEntrypoint {
	private static int baseID = FarlandersConfig.cfg.getInt("IDs.startingBlockID");

	public static final Block<?> SMOKER = new BlockBuilder(Farlanders.MOD_ID)
		.setBlockSound(BlockSounds.STONE)
		.setHardness(2.0F)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build("smoker", baseID++, b -> new BlockLogicSmoker(b));

	@Override
	public void afterBlockInit() {

	}
}
