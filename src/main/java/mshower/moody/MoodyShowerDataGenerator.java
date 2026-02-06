package mshower.moody;

import mshower.moody.datagen.MoodyShowerEnUsProvider;
import mshower.moody.datagen.MoodyShowerZhCnProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MoodyShowerDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
            FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

            pack.addProvider(MoodyShowerEnUsProvider::new);
            pack.addProvider(MoodyShowerZhCnProvider::new);

	}
}
