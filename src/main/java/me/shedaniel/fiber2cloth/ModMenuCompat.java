package me.shedaniel.fiber2cloth;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.fiber2cloth.api.Fiber2Cloth;
import me.zeroeightsix.fiber.exception.FiberException;
import me.zeroeightsix.fiber.tree.ConfigNode;
import me.zeroeightsix.fiber.tree.ConfigValue;
import me.zeroeightsix.fiber.tree.Node;
import net.minecraft.client.gui.screen.Screen;

import java.util.function.Function;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public String getModId() {
        return "fiber2cloth";
    }
    
    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        ConfigNode node = new ConfigNode();
        try {
            ConfigValue<Integer> basicIntField = ConfigValue.builder(Integer.class)
                    .withName("basicIntField")
                    .withComment("This field will accept 0 - 100.")
                    .withParent(node)
                    .withDefaultValue(100)
                    .constraints()
                    .minNumerical(0)
                    .maxNumerical(100)
                    .finish()
                    .build();
            Node secondCategory = node.fork("second.category");
            ConfigValue<String> nestedExample = ConfigValue.builder(String.class)
                    .withName("nestedExample")
                    .withParent(secondCategory)
                    .withDefaultValue("Hi")
                    .build();
            Node iAmInside = secondCategory.fork("i.am.inside");
            ConfigValue<Boolean> nestedNestedExample = ConfigValue.builder(Boolean.class)
                    .withName("nestedNestedExample")
                    .withParent(iAmInside)
                    .withComment("I am inside lol wot u doing")
                    .withDefaultValue(false)
                    .build();
            ConfigValue<String[]> nestedNestedList = ConfigValue.builder(String[].class)
                    .withName("nestedNestedList")
                    .withParent(iAmInside)
                    .withDefaultValue(new String[]{"hi", "no"})
                    .build();
            Node lol = iAmInside.fork("lol");
            ConfigValue<Boolean> exampleBool = ConfigValue.builder(Boolean.class)
                    .withName("exampleBool")
                    .withParent(lol)
                    .withDefaultValue(false)
                    .build();
        } catch (FiberException e) {
            e.printStackTrace();
        }
        return screen -> Fiber2Cloth.create(screen, getModId(), node, "Fiber2Cloth Example Config").setSaveRunnable(() -> {
            // Here you should serialise the node into the config file.
        }).build().getScreen();
    }
}