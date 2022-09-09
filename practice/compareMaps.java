import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/How-to-compare-two-maps-a0762ee0b1f84a2d938a518fe219234f
 */
class compareMaps{ 
    /**
     * Compare two maps
     * 
     * @param m1
     * @param m2
     * @return
     */
	public static boolean compMaps(Map<?, ?> m1, Map<?, ?> m2){
	    if (m1.size() != m2.size()) {
            return false;
        }

        for (Object k1 : m1.keySet()) {
            if (m2.containsKey(k1)) {
                Object value1 = m1.get(k1);
                Object value2 = m2.get(k1);
                if (!value1.equals(value2)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
	}
}