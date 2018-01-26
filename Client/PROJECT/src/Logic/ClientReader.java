package Logic;

import Interface.MainPanel;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientReader extends Thread {

    Socket socket;
    InputStream in;
    OutputStream out;
    String data;
    int nickCheck = 0;
    private JTextArea resultArea;
    private MainPanel mainPanel;
    private JButton room;
    private List<JButton> roomsList;
    private Client client;

    public ClientReader(Socket socket, InputStream in, OutputStream out, String data, Client client, JTextArea resultArea, MainPanel mainPanel, List roomsList) {
        this.client = client;
        this.roomsList = roomsList;
        this.mainPanel = mainPanel;
        this.resultArea = resultArea;
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.data = data;
    }

    public int isNickCheck() {
        return nickCheck;
    }

    public void setNickCheck(int nickCheck) {
        this.nickCheck = nickCheck;
    }

    @Override
    public void run() {
        StringBuffer buf;
        StringBuffer buf2;
        StringBuffer buf3;
        StringBuffer buf4;
        StringBuffer buf5;
        int k;

        StringBuffer sb = new StringBuffer();
        while (isInterrupted() == false) {
            try {

                while ((k = in.read()) != -1 && k != ':') {
                    sb.append((char) k);
                }

                switch (sb.toString()) {
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!NICK_ERR":
                        setNickCheck(1);
                        sb = new StringBuffer();
                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         
                    case "!NICK_OK":
                        buf = new StringBuffer();
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        resultArea.append("[SERWER]: " + buf.toString() + "\r\n");
                        setNickCheck(2);
                        sb = new StringBuffer();
                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         
                    case "!CLIENT_JOIN":
                        buf = new StringBuffer();
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }

                        resultArea.append("[SERWER]: " + buf.toString() + "\r\n");
                        sb = new StringBuffer();
                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         
                    case "!ROOM_CREATED":

                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf2.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        int roomNR = Integer.parseInt(buf2.toString().substring(1, buf2.length())) + 1;
                        room = new JButton(roomNR + " :     | Gracz 1: <" + buf.toString() + ">   | Gracz 2: " + "<EMPTY>");
                        roomsList.add(room);
                        room.addMouseListener(mainPanel.RoomListener(room));
                        mainPanel.getTextArea().append("[SERWER]: Room Created\r\n");
                        mainPanel.getRoomsPanel().add(room);
                        mainPanel.getRoomsList().add(room);
                        mainPanel.repaint(1000);
                        sb = new StringBuffer();

                        if (buf.toString().equals(client.getNick())) {

                            mainPanel.StartRoom();
                            client.getGameWindow().getGamePanel().repaint(100);

                        }
                        sb = new StringBuffer();
                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                        
                    case "!ROOMS_LIST":

                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        int nr = 0;

                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        String[] rNames = new String[Integer.parseInt(buf.toString())];
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf2.append((char) k);
                            if ((char) k == '?') {
                                rNames[nr] = buf2.toString().substring(0, buf2.length() - 1);
                                nr++;
                                buf2 = new StringBuffer();
                            }

                        }

                        for (int i = 0; i < Integer.parseInt(buf.toString()); i++) {
                            room = new JButton(rNames[i].substring(1, rNames[i].length()));
                            roomsList.add(room);
                            if (rNames[i].substring(0, 1).equals("D")) {
                                room.setEnabled(false);
                            }
                            room.addMouseListener(mainPanel.RoomListener(room));

                            mainPanel.getRoomsPanel().add(room);
                            mainPanel.getRoomsList().add(room);
                            mainPanel.repaint();
                        }

                        sb = new StringBuffer();
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
                        mainPanel.getTextArea().append("[" + buf2.toString() + "]: " + buf.toString() + "\r\n");
                        mainPanel.repaint();
                        sb = new StringBuffer();
                        break;
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!CLIENTS_LIST":
                        buf = new StringBuffer();

                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        buf.replace(buf.length() - 2, buf.length() - 1, "");
                        mainPanel.getTextArea().append("[SERWER]: " + buf.toString() + "\r\n");
                        mainPanel.repaint();
                        sb = new StringBuffer();
                        break;
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!ROOM_DISABLE":
                        buf = new StringBuffer();
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        int number = Integer.parseInt(buf.toString());
                        mainPanel.getRoomsList().get(number).setEnabled(false);

                        mainPanel.repaint();
                        sb = new StringBuffer();
                        break;
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!CONFIRM_INVITE":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        buf3 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf2.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf3.append((char) k);

                        }

                        if (client.getNick().equals(buf2.toString())) {
                            int reply = JOptionPane.showConfirmDialog(null, "Gracz " + buf.toString() + " zaprasza cie do gry, czy chcesz dołączyć ?", "", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                mainPanel.getMainFrame().setVisible(false);
                                try {
                                    client.startGameWindow(mainPanel.getMainFrame(), 2);
                                } catch (HeadlessException ex) {
                                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                client.startGame();
                                client.disableRoom((Integer.parseInt(room.getText().substring(0, 1))) - 1);
                                client.joinToRoom(buf3.toString());
                            } else {

                            }
                        }

                        sb = new StringBuffer();
                        break;
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!INVITE_ERROR":
                        buf = new StringBuffer();
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        if (client.getNick().equals(buf.toString())) {
                            JOptionPane.showMessageDialog(null, "Nie ma takiego gracza");
                        }

                        sb = new StringBuffer();
                        break;
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!PLAYER_DISCONNECT":
                        buf = new StringBuffer();
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf.append((char) k);

                        }
                        mainPanel.getTextArea().append("[SERVER]: " + buf.toString() + " DISCONNECT\r\n");
                        if(buf.toString().equals(client.getNick())){

                            this.socket.close();
                            this.in.close();
                            this.out.close();
                            this.interrupt();
                            System.exit(0);
                        }
                        sb = new StringBuffer();
                        break;                        
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!DICES":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        buf3 = new StringBuffer();
                        buf4 = new StringBuffer();
                        buf5 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf2.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf3.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf4.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf5.append((char) k);

                        }
                        if (client.getNick().equals(buf3.toString()) || client.getNick().equals(buf4.toString())) {
                            if (Integer.parseInt(buf5.toString()) == 1) {
                                if (client.getNick().equals(buf3.toString())) {
                                    client.getGameWindow().getGamePanel().addRound();
                                    client.getGameWindow().getGamePanel().setToken(false);
                                    client.getGameWindow().getGamePanel().setDices(buf.toString());
                                    client.getGameWindow().getGamePanel().interpretPoints(Integer.parseInt(buf2.toString()));
                                    client.getGameWindow().getGamePanel().enableDice();
                                    client.getGameWindow().getGamePanel().removeBorders();
                                    client.getGameWindow().getGamePanel().disableGame();
                                }
                                if (client.getNick().equals(buf4.toString())) {

                                    client.getGameWindow().getGamePanel().setToken(true);
                                    client.getGameWindow().getGamePanel().setEnemyDices(buf.toString());
                                    client.getGameWindow().getGamePanel().enableDice();
                                    client.getGameWindow().getGamePanel().enableGame();
                                    client.getGameWindow().getGamePanel().removeBorders();
                                    client.getGameWindow().getGamePanel().interpretEnemyPoints(Integer.parseInt(buf2.toString()));
                                }
                            } else if (Integer.parseInt(buf5.toString()) == 2) {
                                if (client.getNick().equals(buf3.toString())) {
                                    client.getGameWindow().getGamePanel().setToken(true);
                                    client.getGameWindow().getGamePanel().setEnemyDices(buf.toString());
                                    client.getGameWindow().getGamePanel().enableDice();
                                    client.getGameWindow().getGamePanel().enableGame();
                                    client.getGameWindow().getGamePanel().activeBorders();
                                    client.getGameWindow().getGamePanel().interpretEnemyPoints(Integer.parseInt(buf2.toString()));
                                }
                                if (client.getNick().equals(buf4.toString())) {
                                    client.getGameWindow().getGamePanel().addRound();
                                    client.getGameWindow().getGamePanel().checkGame(buf3.toString(), buf4.toString());
                                    client.getGameWindow().getGamePanel().setToken(false);
                                    client.getGameWindow().getGamePanel().setDices(buf.toString());
                                    client.getGameWindow().getGamePanel().interpretPoints(Integer.parseInt(buf2.toString()));
                                    client.getGameWindow().getGamePanel().enableDice();
                                    client.getGameWindow().getGamePanel().activeBorders();
                                    client.getGameWindow().getGamePanel().disableGame();
                                }
                            }
                        }

                        sb = new StringBuffer();
                        break;
//----------------------------------------------------------------------------------------------------------------------------------------- 
                    case "!START_GAME":
                        buf = new StringBuffer();
                        buf2 = new StringBuffer();
                        buf3 = new StringBuffer();
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != ',')) {
                            buf3.append((char) k);

                        }
                        while (((k = in.read()) != -1 && k != '$')) {
                            buf2.append((char) k);

                        }
                        String s = roomsList.get(Integer.parseInt(buf3.toString())).getText();
                        s = s.substring(0, s.length() - 7);
                        s += " <" + buf.toString() + ">";
                        roomsList.get(Integer.parseInt(buf3.toString())).setText(s);
                        if (client.getNick().equals(buf2.toString())) {
                            client.getGameWindow().getGamePanel().enableGame();
                            client.setRoomNumber(Integer.parseInt(buf3.toString()));

                        }
                        if (client.getNick().equals(buf.toString())) {
                            client.getGameWindow().getGamePanel().disableGame();
                            client.setRoomNumber(Integer.parseInt(buf3.toString()));
                        }
                        sb = new StringBuffer();
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
                        if (client.getNick().equals(buf2.toString()) || client.getNick().equals(buf.toString())) {
                            if (client.getNick().equals(buf2.toString())) {
                                client.getGameWindow().getGamePanel().endGame();
                            }
                            if (client.getNick().equals(buf.toString())) {
                                client.getGameWindow().getGamePanel().endGame();
                            }
                        }
                        sb = new StringBuffer();
                        break;
//-----------------------------------------------------------------------------------------------------------------------------------------                         

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
