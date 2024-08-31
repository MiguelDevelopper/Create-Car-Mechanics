package com.Miguel_dev.Create_Vehicular_Works;

import static com.Miguel_dev.Create_Vehicular_Works.CVW_main.REGISTRATE;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.world.item.Item;

public class CVW_Items {

        public static final ItemEntry<Item> PISTON_BASE_TRIM =
                REGISTRATE.item("piston_base_trim", Item::new)
                        .register();
        public static final ItemEntry<Item> PISTON_HEAD_TRIM =
                REGISTRATE.item("piston_head_trim", Item::new)
                        .register();
        public static final ItemEntry<Item> PISTON_BASE =
                REGISTRATE.item("piston_base", Item::new)
                        .register();
        public static final ItemEntry<Item> PISTON_BODY =
                REGISTRATE.item("piston_body", Item::new)
                        .register();
        public static final ItemEntry<Item> PISTON_HEAD =
                REGISTRATE.item("piston_head", Item::new)
                        .register();  
        public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_PISTON =
                REGISTRATE.item("incomplete_piston", SequencedAssemblyItem::new)
                        .register();
        public static final ItemEntry<Item> PISTON =
                REGISTRATE.item("piston", Item::new)
                        .register();               


        public static void register() { 
                CVW_main.LOGGER.info("Registering items for " + CVW_main.ID);
        }
}
