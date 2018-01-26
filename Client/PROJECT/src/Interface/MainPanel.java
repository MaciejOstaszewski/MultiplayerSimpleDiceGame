package Interface;

import Logic.Client;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainPanel extends JPanel {

    private JTextArea textArea;
    private JTextArea writeArea;
    private JTextArea userArea;
    private JButton submit;

    private JButton createRoom;

    private JButton submitUsers;
    private JButton getUsers;
    private List<JButton> roomsList;
    private MouseListener mouseListener;

    private MainPanel mainPanel;
    private JFrame mainFrame;
    private JPanel roomsPanel;
    private GameWindow gameWindow;
    String nick = "";
    boolean close = false;
    public Client client;
    int index = 0;

    public MainPanel(List roomsList, JFrame mainFrame) throws InterruptedException {
        mainPanel = this;
        this.roomsList = roomsList;
        this.mainFrame = mainFrame;
        createRoom = new JButton();
        textArea = new JTextArea();
        userArea = new JTextArea();
        getUsers = new JButton("Pobierz graczy");
        submitUsers = new JButton("Zaproś");
        roomsPanel = new JPanel();
        client = new Client(textArea, this, roomsList);
        setNick(this.client);
        newClient(this.client);
        this.index++;
        setSize(1200, 800);
        setLayout(null);

        writeArea = new JTextArea();
        submit = new JButton();
        GridLayout experimentLayout = new GridLayout(12, 1);
        roomsPanel.setLayout(experimentLayout);
        add(getUsers);
        add(submitUsers);
        add(createRoom);
        add(submit);
        add(textArea);
        add(writeArea);
        add(userArea);
        add(roomsPanel);
        createRoom.setText("Twórz nowy pokój");
        submit.setText("Wyślij");

        textArea.setEditable(false);
        writeArea.setEditable(true);
        setBackground(Color.black);
        textArea.setBackground(Color.LIGHT_GRAY);
        roomsPanel.setBackground(Color.BLACK);

        roomsPanel.setBounds(5, 85, 1184, 345);
        submit.setBounds(1084, 742, 105, 27);
        textArea.setBounds(5, 440, 1184, 274);
        userArea.setBounds(5, 717, 1084, 25);
        writeArea.setBounds(5, 743, 1084, 25);
        createRoom.setBounds(5, 5, 1184, 80);
        getUsers.setBounds(879, 716, 155, 27);
        submitUsers.setBounds(1034, 716, 155, 27);

        Listeners();
        closureProcedure(mainFrame);

    }

    public void closureProcedure(JFrame mainFrame) {
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                client.disconnect();

            }
        });
    }
//------------------------------------------------------------------------------------------------------------------------------  

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public List<JButton> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(List<JButton> roomsList) {
        this.roomsList = roomsList;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JPanel getRoomsPanel() {
        return roomsPanel;
    }

    public void setRoomsPanel(JPanel roomsPanel) {
        this.roomsPanel = roomsPanel;
    }

//------------------------------------------------------------------------------------------------------------------------------   
    public void StartRoom() throws HeadlessException, IOException {
        mainFrame.setVisible(false);

        client.startGameWindow(mainFrame, 1);
        client.startGame();
    }

    public MouseListener RoomListener(JButton room) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                mainFrame.setVisible(false);
                try {
                    client.startGameWindow(mainFrame, 2);
                } catch (HeadlessException ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                client.startGame();
                client.disableRoom((Integer.parseInt(room.getText().substring(0, 1))) - 1);
                client.joinToRoom(room.getText());

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public void setIndex() {
        index++;
    }

    public void setNick(Client client) {

        while (nick.length() == 0) {
            nick = JOptionPane.showInputDialog("Podaj nick");
            if (nick == null) {
                client.disconnect();

            }
            if (nick.length() == 0) {
                JOptionPane.showMessageDialog(null, "Nie poprawny nick");
            }

        }
        client.setNick(nick);

    }

    public boolean checkNick(Client client) throws InterruptedException {
        return client.checkNick();
    }

    public void newClient(Client client) throws InterruptedException {
        while (checkNick(client)) {

            if (checkNick(client)) {
                JOptionPane.showMessageDialog(null, "Istnieje klient o podanym nicku");
                this.nick = "";
                setNick(client);

            }
        }
    }

    public void Listeners() {
        createRoom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                client.addRoom();

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        getUsers.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                client.sendClientsList();

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        submitUsers.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                client.addRoom();
                client.inviteToRoom(userArea.getText());

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        submit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                client.SendMessage(writeArea.getText());
                writeArea.setText("");
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

}
