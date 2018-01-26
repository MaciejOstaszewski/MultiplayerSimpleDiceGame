package Logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private String nick;
    boolean nickCheck = false;
    private List<ClientThread> threadList;
    private int index;
    private StringBuffer userList;
    private List<RoomPlayers> playerList;
    private Game game;
    private Server server;
    
    public ClientThread(Socket socket, List threadList, int index, StringBuffer userList, List<RoomPlayers> playerList, Server server) throws IOException {
        this.socket = socket;
        this.index = index;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.threadList = threadList;
        this.userList = userList;
        this.playerList = playerList;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (isInterrupted() == false) {
                int k;

                StringBuffer sb = new StringBuffer();
                StringBuffer buf;
                StringBuffer buf2;
                StringBuffer buf3;
                StringBuffer buf4;
                while ((k = in.read()) != -1 && k != ':') {
                    sb.append((char) k);

                }

                switch (sb.toString()) {

//-----------------------------------------------------------------------------------------------------------------------------------------                     
                    case "!CHECK_NICK":
                        buf = new StringBuffer();
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        nick = buf.toString();

                        if (CheckNicks(buf.toString())) {
                            SendToMe("!NICK_ERR:".getBytes(), index);

                        } else {
                            this.userList.append(nick + ", ");
                            SendToMe(("!NICK_OK: Witaj " + nick + "$").getBytes(), index);
                            SendToEveryoneExceptMe(("!CLIENT_JOIN: " + nick + " JOIN$").getBytes());

                        }

                        break;

                       
//-----------------------------------------------------------------------------------------------------------------------------------------                         
                    case "!CREATE_ROOM":
                        buf = new StringBuffer();

                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        String roomName = (playerList.size() + 1) + " :     | Gracz 1: <" + this.nick + ">   | Gracz 2: " + "EMPTY";
                        playerList.add(new RoomPlayers(playerList.size(), buf.toString(), "EMPTY", 0, 0, roomName));
                        SendToEveryone(("!ROOM_CREATED: " + (playerList.size() - 1) + "," + this.nick + "$").getBytes());


                        break;

//-----------------------------------------------------------------------------------------------------------------------------------------                         
                    case "!SEND_ROOMS_LIST":
                        if (playerList.size() > 0) {
                            StringBuffer rNames = new StringBuffer();
                            for (RoomPlayers r : playerList) {
                                if (r.isOpen()) {
                                    rNames.append("E" + r.getRoomName() + "?");
                                } else {
                                    rNames.append("D" + r.getRoomName() + "?");
                                }
                            }

                            SendToMe(("!ROOMS_LIST:" + playerList.size() + "," + rNames + "$").getBytes(), this.index);
                        }

                        break;

//-----------------------------------------------------------------------------------------------------------------------------------------                         
                    case "!MSG":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf2.append((char) k);

                        }
                        SendToEveryone(("!MSG:" + buf.toString() + "," + buf2.toString() + "$").getBytes());

                        break;

//-----------------------------------------------------------------------------------------------------------------------------------------                         
                    case "!GET_CLIENTS_LIST":
                        SendToMe(("!CLIENTS_LIST:" + userList.toString() + "$").getBytes(), this.index);
                        break;


//-----------------------------------------------------------------------------------------------------------------------------------------                         

                    case "!ROOM_FULL":
                        buf = new StringBuffer();
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        SendToEveryone(("!ROOM_DISABLE:" + buf.toString() + "$").getBytes());

                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         

                    case "!ROLL_DICE":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        buf3 = new StringBuffer();
                        buf4 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf2.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf3.append((char) k);

                        }

                        while (((k = in.read()) != -1 && k != '$')) {
                            buf4.append((char) k);

                        }
                        if (this.nick.equals(buf3.toString())) {

                            game.Play(buf.toString());
                            String host = playerList.get(Integer.parseInt(buf2.toString())).getPlayer1();
                            String guest = playerList.get(Integer.parseInt(buf2.toString())).getPlayer2();
                            SendToEveryone(("!DICES:" + game.getDiceList() + "," + game.CheeckDice() + "," + host + "," + guest + "," + buf4.toString() + "$").getBytes());
                        }
                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         

                    case "!CLIENT_JOIN_TO_ROOM":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf2.append((char) k);

                        }
                        String name = playerList.get(Integer.parseInt(buf.toString())).getRoomName();

                        name = name.substring(0, name.length() - 7);
                        name += " <" + this.nick + ">";
                        playerList.get(Integer.parseInt(buf.toString())).setRoomName(name);
                        playerList.get(Integer.parseInt(buf.toString())).setOpen(false);
                        String nickH = playerList.get(Integer.parseInt(buf.toString())).getPlayer1();
                        playerList.get(Integer.parseInt(buf.toString())).setPlayer2(buf2.toString());
                        SendToEveryone(("!START_GAME:" + this.nick + "," + buf.toString() + "," + nickH + "$").getBytes());

                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         

                    case "!NEW_GAME":
                        buf = new StringBuffer();

                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }

                        if (this.nick.equals(buf.toString())) {
                            game = new Game();
                        }

                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         

                    case "!END_GAME":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf2.append((char) k);

                        }

                        SendToEveryone(("!END_GAME:" + buf.toString() + "," + buf2.toString() + "$").getBytes());

                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         

                    case "!DISCONNECT":
                        buf = new StringBuffer();

                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }

                        SendToEveryone(("!PLAYER_DISCONNECT:" + buf.toString() + "$").getBytes());

                        for (int i = 0; i < threadList.size(); i++) {
                            if (buf.toString().equals(this.nick)) {

                                threadList.get(i).interrupt();
                                this.socket.close();
                                this.in.close();
                                this.out.close();
                                server.removeClient(i);

                            }
                        }

                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         

                    case "!INVITE_PLAYER":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf2.append((char) k);

                        }
                        String rname = "";
                        for (int i = 0; i < playerList.size(); i++) {
                            if (buf.toString().equals(playerList.get(i).getPlayer1())) {
                                rname = playerList.get(i).getRoomName();
                            }
                        }

                        if (!CheckNicks(buf2.toString())) {
                            SendToEveryone(("!INVITE_ERROR:" + buf.toString() + "$").getBytes());
                        } else {
                            SendToEveryone(("!CONFIRM_INVITE:" + buf.toString() + "," + buf2.toString() + "," + rname + "$").getBytes());
                        }

                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(byte[] data) {
        try {

            out.write(data);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean CheckNicks(String nick) {
        for (int i = 0, n = threadList.size(); i < n; i++) {
            if (i != index) {
                if (nick.equals(threadList.get(i).nick)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void SendToMe(byte[] data, int index) {
        threadList.get(index).send(data);
    }

    public void SendToEveryoneExceptMe(byte[] data) {
        for (int i = 0, n = threadList.size(); i < n; i++) {
            if (i != this.index) {
                threadList.get(i).send(data);
            }
        }
    }

    public void SendToEveryone(byte[] data) {
        for (int i = 0, n = threadList.size(); i < n; i++) {
            threadList.get(i).send(data);
        }
    }

}
