import java.io.*;
import java.net.Socket;

public class TCPClient{
    private final String ip;
    private final int port;
    private Socket s;
    private ClientListener listener;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public TCPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void close() throws IOException {
        if(s != null){
            s.close();
        }
    }

    public void addListener(ClientListener listener){
        this.listener = listener;
    }

    public void notifyListener(Message message){
        listener.update(message);
    }

    public void connectClient() throws Exception {
        Message message = new Message(MessageType.LOGIN, "Вход в систему...");
        notifyListener(message);

        while (true){
            message = (Message) reader.readObject();
            if(message.getType() != MessageType.BAD_NAME){
                notifyListener(message);
                listener.freeListenerBuffer();
                break;
            }else {
                notifyListener(message);
                listener.freeListenerBuffer();
            }
        }
    }

    public void start() {
        try {
            s = new Socket(ip, port);
            writer = new ObjectOutputStream(s.getOutputStream());
            reader = new ObjectInputStream(s.getInputStream());

            listener.start();
            connectClient();
            Message message;

            while (true){
                message = (Message) reader.readObject();
                if(message.getType() != MessageType.LOGOUT){
                    if(message.getType() == MessageType.USER_LIST){
                        notifyListener(message);
                    }

                    if(message.getType() == MessageType.TEXT_RESPONSE){
                        notifyListener(message);
                    }
                }
                else{
                    break;
                }
            }

            listener.join();
            writer.close();
            this.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
