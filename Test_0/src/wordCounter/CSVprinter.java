package wordCounter;
import java.io.*;
import java.util.*;

public class CSVprinter {
    private int count;
    private Map<String, Integer> _map_freq;

    //Method for sorting the TreeMap based on values
    public static <String, Integer extends Comparable<Integer>> Map<String, Integer>
        sortByVal(final Map<String, Integer> map){
        Comparator<String> valueComp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int compare = map.get(o1).compareTo(map.get(o2));
                if(compare == 0)
                    return 1;
                else
                    return compare;
            }
        };

        Map<String, Integer> sort_mapByVal = new TreeMap<String, Integer>(valueComp.reversed());
        sort_mapByVal.putAll(map);
        return sort_mapByVal;
    }

    public void set_count(int value){
        count = value;
    }

    public void set_map(Map<String, Integer> pars_map){
        _map_freq = pars_map;
    }

    public void _print_freq() throws IOException {
        File file = new File("result.csv");

        if(!file.exists()){
            file.createNewFile();
        }

        PrintWriter out = new PrintWriter(file.getAbsoluteFile());

        Map sortedByVal = sortByVal(_map_freq);

        Set set = sortedByVal.entrySet();

        Iterator i = set.iterator();

        while (i.hasNext()){
            Map.Entry me = (Map.Entry)i.next();
            out.print(me.getKey() + ";");
            out.print(me.getValue() + ";");
            float freq = (float)((int) me.getValue());
            out.println(freq/count + "%;");
        }
        out.close();
    }
}
