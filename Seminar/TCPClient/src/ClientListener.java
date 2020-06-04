import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientListener extends Thread{
    private final TCPClient client;
    private String text = "";
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public ClientListener(TCPClient client){
        this.client = client;
    }

    public void freeListenerBuffer(){
        this.text = "";
    }

    public void update(Message notifyMessage){
        if(notifyMessage.getType() == MessageType.LOGIN){
            System.out.println(notifyMessage.getMessage());
            System.out.print("Введите имя:");
        }

        if(notifyMessage.getType() == MessageType.BAD_NAME){
            System.out.println(notifyMessage.getMessage());
        }

        if(notifyMessage.getType() == MessageType.USER_LIST){
            System.out.println("<Список пользователей>");
            System.out.println(notifyMessage.getMessage());
        }

        if(notifyMessage.getType() == MessageType.TEXT_RESPONSE){
            System.out.println(notifyMessage.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                text = consoleReader.readLine();
                if(!text.equalsIgnoreCase("")){

                    if(text.equalsIgnoreCase("user list")){
                        client.getWriter().writeObject(new Message(MessageType.GET_USER_LIST, text));
                    }
                    else if(text.equalsIgnoreCase("exit")){
                        client.getWriter().writeObject(new Message(MessageType.LOGOUT, text));
                        break;
                    }
                    else{
                        client.getWriter().writeObject(new Message(MessageType.TEXT_RESPONSE, text));
                    }
                    client.getWriter().flush();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
