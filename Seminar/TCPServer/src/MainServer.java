public class MainServer {
    public static void main(String[] args){

        try {
            var server = new TCPServer(2048);
            Thread serverThread = new Thread(server);
            serverThread.start();

            serverThread.join();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
