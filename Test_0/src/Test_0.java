import wordCounter.*;
import java.io.*;
import java.util.*;

public class Test_0 {
    public static void main(String []args) throws IOException {
        if (args.length == 0) {
            System.err.print("No files given");
        }
        else
        {
            Parser _parser = new Parser(args[0]);
            _parser._parse_file();

            int count_words = _parser.get_count_words();
            Map<String, Integer> word_freq = _parser.get_words_freq();

            CSVprinter _printer = new CSVprinter();
            _printer.set_count(count_words);
            _printer.set_map(word_freq);
            _printer._print_freq();
            System.out.println("Success...");
        }
    }
}
