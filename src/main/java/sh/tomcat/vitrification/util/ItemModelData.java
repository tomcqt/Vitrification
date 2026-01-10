package sh.tomcat.vitrification.util;

import net.minecraft.text.Text;

import java.util.Map;

public record ItemModelData(int maxModel, Map<Integer, String> names, Map<Integer, String> modelData) {}
