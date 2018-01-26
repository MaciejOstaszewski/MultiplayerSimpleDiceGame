package Logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private boolean nextClient = false;
    List<ClientThread> clients;
    int index = 0;
    private StringBuffer userList;
    private List<RoomPlayers> playersList;

    public Server() {
        playersList = new ArrayList();
        clients = new ArrayList<ClientThread>();
        userList = new StringBuffer();
    }

    private void RunSerwer() throws IOException {
        ServerSocket server = new ServerSocket(2000);
        System.out.println("Server Run, PORT:2000");
        while (true) {
            Socket socket = server.accept();
            System.out.println("Client accepted");

            ClientThread clientThread = new ClientThread(socket, clients, index, userList, playersList, this);
            clientThread.start();
            clients.add(clientThread);
            index++;

        }

    }

    public void removeClient(int i) {
        index--;
        clients.remove(i);

    }

    public static void main(String[] args) {
        try {
            new Server().RunSerwer();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}
