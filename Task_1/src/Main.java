import Commands.Command;

import java.io.*;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    //private final static

    public static void main(String []args) throws IOException {
        Logger log = Logger.getLogger(Main.class.getName());
        FileHandler fileHandler;

        try{
            fileHandler = new FileHandler("Log_file.log");
            log.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.setUseParentHandlers(false);


        Factory factory = Factory.getInstance();
        String arguments;
        Command command;

        if(args.length == 1) {
            try {
                log.info("Open input file...");
                File file = new File(args[0]);
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while (true) {
                    try {
                        if ((arguments = bufferedReader.readLine()) == null) {
                            break;
                        } else {
                            log.info("Create command..." + Arrays.toString(arguments.split(" ", 1)));
                            command = factory.makeCommand(arguments.split(" "));
                            if (command != null)
                                command.do_command();
                        }
                    } catch (IOException ex) {
                        log.severe("Error during reading file!");
                    }
                }
            } catch (Exception ex) {
                log.severe("File cannot be open!");
            }
        }
        else {
            BufferedReader Reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (true){
                    arguments = Reader.readLine();
                    if(arguments.equals("")){
                        break;
                    }
                    else{
                        log.info("Create command...");
                        command = factory.makeCommand(arguments.split(" "));
                        if(command != null)
                            command.do_command();
                    }
                }
            }catch (IOException ex){
                log.severe("Error during reading console!");
            }
        }
    }
}
