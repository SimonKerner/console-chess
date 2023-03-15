package chess;

public class Player {
    Color color;
    String player;

    public Player(String player, Color color) {
        this.player = player;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColorWhite() {
        this.color = Color.WHITE;
    }

    public void setColorBlack() {
        this.color = Color.BLACK;
    }

    public String toString() {
        return player + ": " + color.toString();
    }

}


