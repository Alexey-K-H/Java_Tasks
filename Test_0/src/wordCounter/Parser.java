package wordCounter;
import java.io.*;
import java.util.*;

public class Parser {
    private Reader _input_reader;
    private StringBuilder _str_builder;
    private int _count_words;
    private Map<String, Integer>_treeMap;

    public Map<String, Integer> get_words_freq(){
        return _treeMap;
    }

    public int get_count_words(){
        return _count_words;
    }

    public Parser(String _name_in) throws FileNotFoundException {
        _count_words = 0;
        _input_reader = null;
        _treeMap = new TreeMap<>(Comparator.reverseOrder());

        this._input_reader = new InputStreamReader(new FileInputStream(_name_in));
        this._str_builder = new StringBuilder();
    }

    public void _parse_file() throws IOException {
        int curr_symbol;
        while ((curr_symbol = _input_reader.read()) != -1){
            if(Character.isLetterOrDigit(curr_symbol)){
                _str_builder.append((char)curr_symbol);
            }
            else if(_str_builder.length() != 0){
                _count_words++;
                _treeMap.merge(_str_builder.toString().toLowerCase(), 1, Integer::sum);
                _str_builder.setLength(0);
            }
        }
        //The last word
        if(_str_builder.length() != 0)
        {
            _count_words++;
            _treeMap.merge(_str_builder.toString().toLowerCase(), 1, Integer::sum);
            _str_builder.setLength(0);
        }

        _input_reader.close();
    }
}
