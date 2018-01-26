package Logic;

import Interface.GameWindow;
import Interface.MainPanel;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Client {

    private short points;
    private String nick;
    private String data;
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    private ClientReader clientReader;
    private JTextArea resultArea;
    private MainPanel mainPanel;
    private List<JButton> roomsList;
    private GameWindow gameWindow;
    private int roomNumber = 0;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getNick() {
        return nick;
    }

    public void setResultArea(JTextArea resultArea) {
        this.resultArea = resultArea;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public Client(String nick, JTextArea resultArea) {

        this.nick = nick;
        this.resultArea = resultArea;

    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public Client(JTextArea resultArea, MainPanel mainPanel, List roomsList) {
        try {
            this.resultArea = resultArea;
            this.mainPanel = mainPanel;
            this.roomsList = roomsList;
            socket = new Socket("127.0.0.1", 2000);
            System.out.println("Connect");
            in = socket.getInputStream();
            out = socket.getOutputStream();
            clientReader = new ClientReader(socket, in, out, data, this, resultArea, mainPanel, roomsList);
            clientReader.start();
            sendRooms();
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public void startGameWindow(JFrame mainPanel, int q) throws HeadlessException, IOException {
        gameWindow = new GameWindow(mainPanel, this.mainPanel, this);
        gameWindow.getGamePanel().setQ(q);
    }

//--------------------------------------------------------------------------------------------------------------------------------------------    
    public void sendRooms() {
        try {
            out.write(("!SEND_ROOMS_LIST:").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }

    }

    public void SendMessage(String text) {
        try {
            out.write(("!MSG:" + text + "," + nick + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public void addRoom() {
        try {

            out.write(("!CREATE_ROOM:" + nick + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public void sendClientsList() {
        try {
            out.write(("!GET_CLIENTS_LIST:").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }



    public void joinToRoom(String name) {

        try {
            out.write(("!CLIENT_JOIN_TO_ROOM:" + (Integer.parseInt(name.substring(0, 1)) - 1) + "," + this.nick + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public void disableRoom(int number) {
        try {
            System.out.println(number);
            out.write(("!ROOM_FULL:" + number + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public void rollTheDiceHost(String t) {
        try {
            out.write(("!ROLL_DICE:" + t + "," + roomNumber + "," + this.nick + "," + gameWindow.getGamePanel().getQ() + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }



    public void startGame() {
        try {
            out.write(("!NEW_GAME:" + this.nick + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public void endGame(String gracz1, String gracz2) {
        try {
            out.write(("!END_GAME:" + gracz1 + "," + gracz2 + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }

    }

    public void inviteToRoom(String nickName) {
        try {
            out.write(("!INVITE_PLAYER:" + this.nick + "," + nickName + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            out.write(("!DISCONNECT:" + this.nick + "$").getBytes());
        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------    

    public boolean checkNick() throws InterruptedException {
        String bufor = "!CHECK_NICK:";
        bufor += nick + '$';

        try {

            out.write(bufor.getBytes());
            Thread.sleep(500);
            do {

                if (clientReader.isNickCheck() == 1) {
                    return true;

                }
                if (clientReader.isNickCheck() == 2) {
                    return false;
                }

            } while (clientReader.isInterrupted() == false);

        } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        }
        return false;
    }

}
