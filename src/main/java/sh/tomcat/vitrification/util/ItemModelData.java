package sh.tomcat.vitrification.util;

import java.util.Map;

public record ItemModelData(int maxModel, Map<Integer, String> names, Map<Integer, String> modelData, Map<Integer, String> owners) {}
