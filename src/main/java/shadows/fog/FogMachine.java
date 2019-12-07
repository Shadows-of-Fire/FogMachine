package shadows.fog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = FogMachine.MODID, name = FogMachine.MODNAME, version = FogMachine.VERSION)
public class FogMachine {

	public static final String MODID = "fogmachine";
	public static final String MODNAME = "Fog Machine";
	public static final String VERSION = "1.0.0";

	public static final Logger LOG = LogManager.getLogger(MODID);

	@Config(modid = MODID)
	public static class FogConfig {

		@Config.Name("Fog Density")
		@Config.Comment("How powerful the fog is.")
		public static float density = 0.1F;

	}

	public FogMachine() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void density(FogDensity e) {
		if (isFogEnabled()) {
			GlStateManager.setFog(GlStateManager.FogMode.EXP);
			e.setDensity(getFogDensity());
			e.setCanceled(true);
		}
	}

	public static float getFogDensity() {
		return FogConfig.density;
	}

	public static boolean isFogEnabled() {
		return FogConfig.density > 0;
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.getModID().equals(MODID)) {
			ConfigManager.sync(MODID, Config.Type.INSTANCE);
			LOG.info("Fog machine config reloaded.");
		}
	}

}
