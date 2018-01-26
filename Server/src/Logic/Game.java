package Logic;

import java.util.Random;

public class Game {

    private Random rand;
    private short[] dice;
    private char points;
    private short sum;
    private String[] choosen;

    public short[] getDice() {
        return dice;
    }

    public void setDice(short[] dice) {
        this.dice = dice;
    }

    public char getPoints() {
        return points;
    }

    public void setPoints(char points) {
        this.points = points;
    }

    public Game() {
        rand = new Random();
        dice = new short[5];
        choosen = new String[5];
    }

    public void Play(String t) {
        if (t.equals("continue")) {

        } else {
            boolean next = false;
            int p = 0;
            for (int i = 0; i < t.length(); i++) {
                choosen[i] = t.substring(i, i + 1);

                if (Integer.parseInt(choosen[i]) != 9) {
                    next = true;
                }

            }
            if (!next) {
                for (int i = 0; i < dice.length; i++) {
                    dice[i] = (short) (rand.nextInt(6) + 1);
                    sum += dice[i];

                }
            } else {

                for (int i = 0; i < dice.length; i++) {
                    p = 9;
                    for (int j = 0; j < choosen.length; j++) {
                        if (i == Integer.parseInt(choosen[j])) {

                            p = i;
                            break;
                        }

                    }
                    if (i == p) {
                        dice[i] = (short) (rand.nextInt(6) + 1);
                        sum += dice[i];
                    }
                }
            }
        }

    }

    public void display() {
        for (short s : dice) {
            System.out.println(s);
        }
    }

    public int getSum() {
        sum = 0;
        for (short s : dice) {
            sum += s;
        }
        return sum;
    }

    public String getDiceList() {
        StringBuffer sb = new StringBuffer();
        for (short s : dice) {
            sb.append(s);
        }
        return sb.toString();
    }

    public char CheeckDice() {
        boolean pair = false;
        boolean threes = false;
        boolean twoPair = false;
        boolean four = false;
        boolean poker = false;
        boolean smallStraight = false;
        boolean bigStraight = false;
        boolean nothing = false;
        boolean full = false;
        short[] tab = new short[7];
        for (int i = 0; i < dice.length; i++) {
            switch (dice[i]) {
                case 1:
                    tab[1]++;
                    break;
                case 2:
                    tab[2]++;
                    break;
                case 3:
                    tab[3]++;
                    break;
                case 4:
                    tab[4]++;
                    break;
                case 5:
                    tab[5]++;
                    break;
                case 6:
                    tab[6]++;
                    break;
            }
        }

        for (int i = 1; i < tab.length; i++) {
            // System.out.println(tab[i]);
            if (tab[i] >= 2) {
                if (pair) {
                    twoPair = true;
                    pair = false;
                }
                pair = true;
                if (tab[i] >= 3) {
                    pair = false;
                    threes = true;
                    if (tab[i] >= 4) {
                        four = true;
                        threes = false;
                        pair = false;
                        if (tab[i] == 5) {
                            four = false;
                            threes = false;
                            pair = false;
                            poker = true;
                        }
                    }
                }
            }

            if (tab[1] == 1 && tab[2] == 1 && tab[3] == 1 && tab[4] == 1 && tab[5] == 1) {
                smallStraight = true;
            }
            if (tab[2] == 1 && tab[3] == 1 && tab[4] == 1 && tab[5] == 1 && tab[6] == 1) {
                bigStraight = true;
            }

            if (pair && threes) {
                pair = false;
                threes = false;
                full = true;
            }

        }
        if (twoPair) {
            points = '3';
//            System.out.println("2 pary");
        } else if (pair) {
            points = '2';
//            System.out.println("para");
        } else if (threes) {
            points = '4';
//            System.out.println("trojka");
        } else if (smallStraight) {
            points = '5';
//            System.out.println("maly srit");
        } else if (bigStraight) {
//            System.out.println("duyz srit");
            points = '6';
        } else if (four) {
//            System.out.println("kareta");
            points = '8';
        } else if (full) {
//            System.out.println("full");
            points = '7';
        } else if (poker) {
//            System.out.println("poker");
            points = '9';
        } else {
            points = '1';
//            System.out.println("nic");
        }

        return points;
    }


}
