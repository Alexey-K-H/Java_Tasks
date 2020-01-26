import Commands.Command;

import java.io.*;

public class Main {
    public static void main(String []args){
        Factory factory = Factory.getInstance();
        String arguments;
        Command command;

        try{
            File file = new File("input.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while (true){
                try{
                    if((arguments = bufferedReader.readLine()) == null){
                        break;
                    }else {
                        command = factory.makeCommand(arguments.split(" "));
                        if(command != null)
                            command.do_command();
                    }
                }catch (IOException ex){

                }
            }
        }catch (Exception ex){

        }
    }
}
