package Interface;

import Logic.Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {

    private Client client;
    private int q;

    BufferedImage buttonIcon;
    BufferedImage buttonIcon1;
    BufferedImage buttonIcon2;
    BufferedImage buttonIcon3;
    BufferedImage buttonIcon4;
    BufferedImage buttonIcon5;
    BufferedImage buttonIcon6;
    ImageIcon icon;
    ImageIcon icon1;
    ImageIcon icon2;
    ImageIcon icon3;
    ImageIcon icon4;
    ImageIcon icon5;
    ImageIcon icon6;

    private JLabel startLabel;
    private JLabel result1;
    private JLabel result2;

    private JButton g1d1;
    private JButton g1d2;
    private JButton g1d3;
    private JButton g1d4;
    private JButton g1d5;

    private JButton g2d1;
    private JButton g2d2;
    private JButton g2d3;
    private JButton g2d4;
    private JButton g2d5;

    private JButton play;

    private JFrame gameFrame;

    private int t = 1;
    private StringBuffer buf = new StringBuffer("99999");
    private boolean token = true;
    private boolean change = false;
    private int round = 1;
    private int points1;
    private int points2;

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void addRound() {
        this.round += 1;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public GamePanel(Client client, JFrame gameFrame) throws IOException {
        this.buttonIcon = ImageIO.read(new File("img\\0.jpg"));
        this.buttonIcon1 = ImageIO.read(new File("img\\1.jpg"));
        this.buttonIcon2 = ImageIO.read(new File("img\\2.jpg"));
        this.buttonIcon3 = ImageIO.read(new File("img\\3.jpg"));
        this.buttonIcon4 = ImageIO.read(new File("img\\4.jpg"));
        this.buttonIcon5 = ImageIO.read(new File("img\\5.jpg"));
        this.buttonIcon6 = ImageIO.read(new File("img\\6.jpg"));
        this.icon = new ImageIcon(buttonIcon);
        this.icon1 = new ImageIcon(buttonIcon1);
        this.icon2 = new ImageIcon(buttonIcon2);
        this.icon3 = new ImageIcon(buttonIcon3);
        this.icon4 = new ImageIcon(buttonIcon4);
        this.icon5 = new ImageIcon(buttonIcon5);
        this.icon6 = new ImageIcon(buttonIcon6);

        this.client = client;
        this.gameFrame = gameFrame;

        closureProcedure(this.gameFrame);

        startLabel = new JLabel("Oczekiwanie na drugiego gracza...");
        result1 = new JLabel("Towje kości: ");
        result2 = new JLabel("Kości przeciwnika: ");
        result1.setFont(new Font(TOOL_TIP_TEXT_KEY, 1, 15));
        result2.setFont(new Font(TOOL_TIP_TEXT_KEY, 1, 15));
        startLabel.setFont(new Font(TOOL_TIP_TEXT_KEY, 1, 15));
        startLabel.setBounds(480, 5, 300, 50);
        result1.setBounds(150, 170, 300, 50);
        result2.setBounds(700, 520, 300, 50);
        add(startLabel);
        add(result1);
        add(result2);

        setSize(1200, 800);
        setLayout(null);

        play = new JButton("Rzuć");
        play.setEnabled(false);
        play.setBounds(800, 100, 100, 50);
        add(play);

        g1d1 = new JButton(icon);
        g1d1.setEnabled(false);
//        g1d1.setText("-");
        g1d1.setBounds(100, 100, 50, 50);
        add(g1d1);

        g1d2 = new JButton(icon);
        g1d2.setEnabled(false);
//        g1d2.setText("-");
        g1d2.setBounds(200, 100, 50, 50);
        add(g1d2);

        g1d3 = new JButton(icon);
        g1d3.setEnabled(false);
//        g1d3.setText("-");
        g1d3.setBounds(300, 100, 50, 50);
        add(g1d3);

        g1d4 = new JButton(icon);
        g1d4.setEnabled(false);
//        g1d4.setText("-");
        g1d4.setBounds(400, 100, 50, 50);
        add(g1d4);

        g1d5 = new JButton(icon);
        g1d5.setEnabled(false);
//        g1d5.setText("-");
        g1d5.setBounds(500, 100, 50, 50);
        add(g1d5);

        g2d1 = new JButton(icon);
        g2d1.setEnabled(false);
//        g2d1.setText("-");
        g2d1.setBounds(650, 600, 50, 50);
        add(g2d1);

        g2d2 = new JButton(icon);
        g2d2.setEnabled(false);
//        g2d2.setText("-");
        g2d2.setBounds(750, 600, 50, 50);
        add(g2d2);

        g2d3 = new JButton(icon);
        g2d3.setEnabled(false);
//        g2d3.setText("-");
        g2d3.setBounds(850, 600, 50, 50);
        add(g2d3);

        g2d4 = new JButton(icon);
        g2d4.setEnabled(false);
//        g2d4.setText("-");
        g2d4.setBounds(950, 600, 50, 50);
        add(g2d4);

        g2d5 = new JButton(icon);
        g2d5.setEnabled(false);
//        g2d5.setText("-");
        g2d5.setBounds(1050, 600, 50, 50);
        add(g2d5);

        removeBorders();

        Listeners();
        this.repaint(1000);

    }

    public void closureProcedure(JFrame gameFrame) {
            gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {                
                client.disconnect();

            }
        });

    }

    public void enableGame() {
        startLabel.setText("Twój ruch");
        play.setEnabled(true);
        g2d1.setEnabled(true);
        g2d2.setEnabled(true);
        g2d3.setEnabled(true);
        g2d4.setEnabled(true);
        g2d5.setEnabled(true);

    }
 

    public void disableGame() {
        startLabel.setText("Ruch przeciwnika");
        play.setEnabled(false);
    }


    public void enableDice() {
        g1d1.setEnabled(true);
        g1d2.setEnabled(true);
        g1d3.setEnabled(true);
        g1d4.setEnabled(true);
        g1d5.setEnabled(true);

    }

    public void disableDice() {
        g1d1.setEnabled(false);
        g1d2.setEnabled(false);
        g1d3.setEnabled(false);
        g1d4.setEnabled(false);
        g1d5.setEnabled(false);
    }

    public void interpretPoints(int points) {
        points1 = points;
        result1.setText("Towje kości: ");
        StringBuffer sb = new StringBuffer(result1.getText());
        if (points == 3) {
            sb.append("2 pary");
            result1.setText(sb.toString());

        } else if (points == 2) {
            sb.append("para");
            result1.setText(sb.toString());
        } else if (points == 4) {
            sb.append("trójka");
            result1.setText(sb.toString());

        } else if (points == 5) {
            sb.append("mały strit");
            result1.setText(sb.toString());

        } else if (points == 6) {
            sb.append("duży strit");
            result1.setText(sb.toString());

            points = '6';
        } else if (points == 7) {
            sb.append("full");
            result1.setText(sb.toString());

            points = '8';
        } else if (points == 8) {
            sb.append("kareta");
            result1.setText(sb.toString());
            points = '7';
        } else if (points == 9) {
            sb.append("poker");
            result1.setText(sb.toString());

        } else {
            sb.append("nic");
            result1.setText(sb.toString());
        }
    }

    public void interpretEnemyPoints(int points) {
        points2 = points;
        result2.setText("Kości przeciwnika: ");
        StringBuffer sb = new StringBuffer(result2.getText());

        if (points == 3) {
            sb.append("2 pary");
            result2.setText(sb.toString());

        } else if (points == 2) {
            sb.append("para");
            result2.setText(sb.toString());
        } else if (points == 4) {
            sb.append("trójka");
            result2.setText(sb.toString());

        } else if (points == 5) {
            sb.append("mały strit");
            result2.setText(sb.toString());

        } else if (points == 6) {
            sb.append("duży strit");
            result2.setText(sb.toString());

            points = '6';
        } else if (points == 7) {
            sb.append("full");
            result2.setText(sb.toString());

            points = '8';
        } else if (points == 8) {
            sb.append("kareta");
            result2.setText(sb.toString());
            points = '7';
        } else if (points == 9) {
            sb.append("poker");
            result2.setText(sb.toString());

        } else {
            sb.append("nic");
            result2.setText(sb.toString());
        }
    }

    public void removeBorders() {
        g1d1.setBorder(new LineBorder(Color.LIGHT_GRAY));
        g1d2.setBorder(new LineBorder(Color.LIGHT_GRAY));
        g1d3.setBorder(new LineBorder(Color.LIGHT_GRAY));
        g1d4.setBorder(new LineBorder(Color.LIGHT_GRAY));
        g1d5.setBorder(new LineBorder(Color.LIGHT_GRAY));
    }

    public void activeBorders() {
        g1d1.setBorder(new LineBorder(Color.GREEN));
        g1d2.setBorder(new LineBorder(Color.GREEN));
        g1d3.setBorder(new LineBorder(Color.GREEN));
        g1d4.setBorder(new LineBorder(Color.GREEN));
        g1d5.setBorder(new LineBorder(Color.GREEN));
    }

    public void setDices(String dices) {
        g1d1.setIcon(setDice(dices.substring(0, 1)));
        g1d2.setIcon(setDice(dices.substring(1, 2)));
        g1d3.setIcon(setDice(dices.substring(2, 3)));
        g1d4.setIcon(setDice(dices.substring(3, 4)));
        g1d5.setIcon(setDice(dices.substring(4, 5)));

    }

    public void setEnemyDices(String dices) {
        g2d1.setIcon(setDice(dices.substring(0, 1)));
        g2d2.setIcon(setDice(dices.substring(1, 2)));
        g2d3.setIcon(setDice(dices.substring(2, 3)));
        g2d4.setIcon(setDice(dices.substring(3, 4)));
        g2d5.setIcon(setDice(dices.substring(4, 5)));

    }

    public void checkGame(String gracz1, String gracz2) {
        if (round == 3) {

            client.endGame(gracz1, gracz2);
        }
    }

    public void endGame() {
        if (points1 == points2) {
            JOptionPane.showMessageDialog(null, "Remis");
        } else if (points1 < points2) {
            JOptionPane.showMessageDialog(null, "Przegrałeś");
        } else {
            JOptionPane.showMessageDialog(null, "Wygrałeś");
        }

        int reply = JOptionPane.showConfirmDialog(null, "Chcesz grac od nowa?", "", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            closureProcedure(this.gameFrame);
        }
    }

    public void restartGame() {
        g1d1.setIcon(icon);
        g1d2.setIcon(icon);
        g1d3.setIcon(icon);
        g1d4.setIcon(icon);
        g1d5.setIcon(icon);
        g2d1.setIcon(icon);
        g2d2.setIcon(icon);
        g2d3.setIcon(icon);
        g2d4.setIcon(icon);
        g2d5.setIcon(icon);
        result1.setText("Towje kości: ");
        result2.setText("Kości przeciwnika: ");
        change = false;
        token = true;
        round = 1;
        points1 = 0;
        points2 = 0;
        buf = new StringBuffer("99999");
    }

    public ImageIcon setDice(String number) {
        switch (number) {
            case "1":
                return icon1;
            case "2":
                return icon2;
            case "3":
                return icon3;
            case "4":
                return icon4;
            case "5":
                return icon5;
            case "6":
                return icon6;

        }
        return icon;
    }

    public void Listeners() {

        play.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (token) {
                    if (round < 2) {

                        client.rollTheDiceHost(buf.toString());
                    } else if (round >= 2) {

                        if (change) {

                            client.rollTheDiceHost(buf.toString());
                        } else {
                            client.rollTheDiceHost("continue");
                        }
                    }
                    removeBorders();
                }

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

        g1d1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                g1d1.setBorder(new LineBorder(Color.RED));
                buf.replace(0, 1, "0");
                change = true;
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

        g1d2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                g1d2.setBorder(new LineBorder(Color.RED));
                buf.replace(1, 2, "1");
                change = true;
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

        g1d3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                g1d3.setBorder(new LineBorder(Color.RED));
                buf.replace(2, 3, "2");
                change = true;
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

        g1d4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                g1d4.setBorder(new LineBorder(Color.RED));
                buf.replace(3, 4, "3");
                change = true;
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

        g1d5.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                g1d5.setBorder(new LineBorder(Color.RED));
                buf.replace(4, 5, "4");
                change = true;
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
