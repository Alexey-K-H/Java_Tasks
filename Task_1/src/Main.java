import Commands.Command;

import java.io.*;
import java.util.logging.Logger;

public class Main {
    private final static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String []args){
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
