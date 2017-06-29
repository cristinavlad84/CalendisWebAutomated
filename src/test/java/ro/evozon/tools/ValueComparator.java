package ro.evozon.tools;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Object> {

    private Map<Object, String> map;

    public ValueComparator(Map<Object, String> map) {
        this.map = map;
    }

    public int compare(Object a, Object b) {
        return map.get(a).compareTo(map.get(b));
    }

	
}
