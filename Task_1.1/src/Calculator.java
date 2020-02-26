import Commands.Command;
import Context.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {
    private static Logger logger = Logger.getLogger(Calculator.class.getName());
    private InputStream in;


    public Calculator(InputStream input){
        this.in = input;
    }

    public void ran_calc(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String arguments;
        Command command;
        Factory factory = Factory.getInstance();
        Context context = new Context();

        logger.log(Level.INFO, "Start process...");
        while (true){
            try{
                if((arguments = bufferedReader.readLine()) == null || arguments.equals("")){
                    break;
                }else{
                    logger.log(Level.INFO, "Create command..." + Arrays.toString(arguments.split(" ", 1)));
                    command = factory.make_command(arguments.split(" "));
                    if(command != null){
                        command.do_command(context, arguments.split(" "));
                    }
                }
            }
            catch (IOException ex){
                logger.log(Level.SEVERE, "Error during reading input!");
            }
        }
    }

}
