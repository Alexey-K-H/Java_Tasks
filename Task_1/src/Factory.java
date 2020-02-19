import Commands.Command;
import contex_exe.Block;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Logger;

public class Factory {
    private static Factory factory;
    private Stack<Double> stack;
    private Map<String, Double> map;
    private Class factoryClass;
    public static final Properties p;
    private final static Logger log = Logger.getLogger(Factory.class.getName());

    //Инициализация (Properties p)
    static {
        try{
            InputStream resource = Factory.class.getClassLoader().getResourceAsStream("Config.properties");//Загрузить информацию о классах
            p = new Properties();

            if(resource != null)
               p.load(resource);

        }catch (IOException ex){
            throw new RuntimeException();//Если данный о классах не были загружены
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

    //For unit-tests
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
            log.severe("There is no access to field!");
            command = null;
        }
    }

    public Command makeCommand(String[] arguments){
        Command command = null;
        String className;
        /*if(arguments.length > 3){
            log.severe("Wrong amount of arguments!");
            return null;
        }*/

        try{
            if(arguments[0].equals("#")){
                className = p.getProperty("COMMENT");
            }
            else
            className = p.getProperty(arguments[0]);

            if(className == null){
                log.warning("Unexpected command. What do you mean?");
            }
            else
            {
                factoryClass = Class.forName(className);
                //Создаем экземпляр класса и проверяем что он создан на основе Command
                if(factoryClass.getDeclaredConstructor().newInstance() instanceof Command){
                    command = (Command) factoryClass.getDeclaredConstructor().newInstance();
                    fillCommand(command, arguments);//Вносим информацию в данную команду(аргументы)
                }
                else
                {
                    log.severe("Cannot create command from this object");
                }
            }
        } catch (ClassNotFoundException ex) {
            log.severe("Class not found!");
        } catch (IllegalAccessException ex){
            log.severe("Illegal access to class!");
        } catch (InstantiationException ex){
            log.severe("Fail instance of this object!");
        } catch (NoSuchMethodException | InvocationTargetException e) {
            log.severe("Fail to get constructor of this object!");
        }

        return command;
    }
}
