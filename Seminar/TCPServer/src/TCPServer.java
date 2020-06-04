import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class TCPServer implements Runnable{
    private final int port;
    private ServerSocket s;
    private Map<RequestProcessor, String> clients = Collections.synchronizedMap(new HashMap<>());

    public TCPServer(int port){
        this.port = port;
    }

    public void close() {
        if(s != null){
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(Map.Entry<RequestProcessor, String> pair : clients.entrySet()){
            try {
                pair.getKey().close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void addClient(RequestProcessor client, String userName){
        clients.put(client, userName);
    }

    public void removeClient(RequestProcessor client){
        System.out.println("Пользователь:" + clients.get(client) + " покинул чат!");
        clients.remove(client);

        if(clients.size() == 0){
            this.close();
        }
    }

    public void sendMessage(Message message){
        for(Map.Entry<RequestProcessor, String> pair : clients.entrySet()){
            try {
                pair.getKey().sendMessage(message);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public boolean isUniqueUser(String userName){
        return !clients.containsValue(userName);
    }

    public void sendUserList(RequestProcessor client){
        String list = "";

        for(Map.Entry<RequestProcessor, String> pair : clients.entrySet()){
                list = list.concat("#:" + pair.getValue() + "\n");
        }

        try{
            client.sendMessage(new Message(MessageType.USER_LIST, list));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            //Создаем новый серверный Socket на порту 2048
            s = new ServerSocket(port);

            while (true)
            {
                Socket clientSocket = s.accept(); //принимаем соединение
                System.out.println("Получено соединение от:" + clientSocket.getInetAddress() +":" + clientSocket.getPort());
                //Создаем и запускаем поток для обработки запроса
                var client = new RequestProcessor(clientSocket, this);
                Thread t = new Thread(client);
                System.out.println("Запуск обработчика...");
                t.start();
            }
        }
        catch (IOException e){
            System.out.println("Server closed!");
        }
    }
}
