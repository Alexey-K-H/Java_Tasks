public class MainClient {
    public static void main(String[] args){
        try {
            var client = new TCPClient("127.0.0.1", 2048);
            ClientListener listener = new ClientListener(client);
            client.addListener(listener);

            client.start();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
