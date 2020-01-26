import Commands.Command;
import contex_exe.Block;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

public class Factory {
    private static Factory factory;
    private Stack<Double> stack;
    private Map<String, Double> map;
    private Class factoryClass;
    public static final Properties p;

    //Инициализация (Properties p)
    static {
        try{
            InputStream resource = Factory.class.getClassLoader().getResourceAsStream("Config.properties");
            p = new Properties();
            p.load(resource);
        }catch (IOException ex){
            throw new RuntimeException();
        }
    }

    private Factory(){
        this.stack = new Stack<>();
        this.map = new HashMap<>();
    }

    public static Factory getInstance(){
        if(factory == null){
            factory = new Factory();
        }
        return factory;
    }

    public Stack<Double> getStack(){
        return stack;
    }

    private void fillCommand(Command command, String[] arguments){
        Block block;

        Field[] fields = factoryClass.getDeclaredFields();
        try{
            for(Field field : fields){
                block = field.getDeclaredAnnotation(Block.class);
                field.setAccessible(true);
                switch (block.arg()){
                    case STACK:
                        field.set(command, stack);
                        break;
                    case ARGUMENTS:
                        field.set(command, arguments);
                        break;
                    case VARIABLES:
                        field.set(command, map);
                        break;
                }
            }
        }catch (IllegalAccessException ex){
            command = null;
        }
    }

    public Command makeCommand(String[] arguments){
        Command command = null;
        String className;
        if(arguments.length > 3){
            return null;
        }

        try{
            if(arguments[0].equals("#")){
                className = p.getProperty("COMMENT");
            }
            else
            className = p.getProperty(arguments[0]);

            if(className == null){

            }
            else
            {
                factoryClass = Class.forName(className);
                if(factoryClass.newInstance() instanceof Command){
                    command = (Command) factoryClass.newInstance();
                    fillCommand(command, arguments);
                }
                else
                {

                }
            }
        } catch (ClassNotFoundException ex) {

        } catch (IllegalAccessException ex){

        } catch (InstantiationException ex){

        }

        return command;
    }

}
