import java.io.*;
import java.net.Socket;

class RequestProcessor implements Runnable {
    private final TCPServer server;
    private ObjectOutputStream writer;
    Socket s; //Точка установленного соединения
    String userName;

    RequestProcessor(Socket s, TCPServer server) {
        this.s = s;
        this.server = server;
    }

    public void close() throws IOException {
        s.close();
    }

    public void sendMessage(Message message) throws IOException {
        if(writer != null){
            writer.writeObject(message);
            writer.flush();
        }
    }

    public void run() {
        try {
            writer = new ObjectOutputStream(s.getOutputStream());
            InputStream input = s.getInputStream();
            ObjectInputStream reader = new ObjectInputStream(input);

            //Проверка имени пользователя
            Message message = (Message) reader.readObject();
            System.out.println("Проверка имени пользователя...");
            while (!server.isUniqueUser(message.getMessage())){
                writer.writeObject(new Message(MessageType.BAD_NAME, "Это имя уже используется!" + "\n"
                        + "Попробуйте другое имя:"));
                writer.flush();
                message = (Message) reader.readObject();
            }
            userName = message.getMessage();
            writer.writeObject(new Message(MessageType.TEXT_RESPONSE, "Добро пожаловать!" + "\n"));
            writer.flush();
            System.out.println("Идентификация прошла успешно!");
            server.addClient(this, message.getMessage());

            while (true) {
                System.out.println("Ожидаем сообщение...");
                message = (Message) reader.readObject();

                if(message.getType() == MessageType.GET_USER_LIST){
                    System.out.println("List");
                    server.sendUserList(this);
                }
                else {
                    System.out.println("Получено сообщение:" + message.getMessage());
                    if(!message.getMessage().equalsIgnoreCase("exit"))
                    server.sendMessage(new Message(MessageType.TEXT_RESPONSE, "[" + userName + "]:" + message.getMessage()));
                    else
                        sendMessage(new Message(MessageType.LOGOUT, "exit"));
                }
            }
        }
        catch (Exception ex) {
            server.removeClient(this);
        }
    }
}
