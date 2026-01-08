package sh.tomcat.vitrification.util;

import net.minecraft.text.Text;

import java.util.Map;

/**
 * @param modelPaths model path for predicate override
 */
public record ItemModelData(int maxModel, Map<Integer, Text> names, Map<Integer, String> modelPaths) {}
