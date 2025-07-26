package cookie.farlanders.core.block.state;

import cookie.farlanders.core.block.logic.BlockLogicSmoker;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import org.useless.dragonfly.data.block.mojang.state.MetaStateInterpreter;

import java.util.HashMap;

public class SmokerMetaState extends MetaStateInterpreter {
	@Override
	public HashMap<String, String> getStateMap(WorldSource worldSource, int x, int y, int z, Block<?> block, int meta) {
		HashMap<String, String> result = new HashMap<>();
		Side side = Side.getSideById(meta & ~BlockLogicSmoker.MASK_ACTIVE);

		result.put("side", side.name().toLowerCase());

		return result;
	}
}
