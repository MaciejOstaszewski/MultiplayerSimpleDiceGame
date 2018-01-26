package Interface;

import Logic.Client;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainWindow extends JFrame {

    MainPanel mainPanel;
    private Client client;
    private int index;
    private List<JButton> roomsList;
    
    public MainWindow() throws InterruptedException {

        setTitle("Dice Game");
        setSize(1200, 800);
        roomsList = new ArrayList<>();
        mainPanel = new MainPanel(roomsList,this);
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);


    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }



    public static void main(String[] args) throws InterruptedException {
        MainWindow mainWindow = new MainWindow();

    }

}
