
package Logic;
    
public class RoomPlayers {
    private String player1;
    private String player2;
    private int pts1;
    private int number;
    private String roomName;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    private boolean open = true;
            
    public int getNumber() {
        return number;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPts1() {
        return pts1;
    }

    public void setPts1(int pts1) {
        this.pts1 = pts1;
    }

    public int getPts2() {
        return pts2;
    }

    public void setPts2(int pts2) {
        this.pts2 = pts2;
    }
    private int pts2;

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public RoomPlayers(int number, String player1, String player2, int pts1, int pts2, String roomName) {
        this.roomName = roomName;
        this.number = number;
        this.player1 = player1;
        this.player2 = player2;
        this.pts1 = pts1;
        this.pts2 = pts2;
    }
}
