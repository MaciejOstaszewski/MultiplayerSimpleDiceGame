package Interface;

import Logic.Client;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private Client client;
    

    private GamePanel gamePanel;
    public GameWindow(JFrame mainFrame, JPanel mainPanel, Client client) throws HeadlessException, IOException {
        this.mainFrame = mainFrame;
        this.mainPanel = mainPanel;
        this.client = client;
        
        setTitle("Dice Game");
        setSize(1200, 800);
       
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        gamePanel = new GamePanel(this.client, this);
        add(gamePanel);

    }


    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


}
