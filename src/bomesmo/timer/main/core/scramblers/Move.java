package bomesmo.timer.main.core.scramblers;

public class Move {

    private String movement;

    public Move(String movement) {
        this.movement = movement;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    @Override
    public String toString() {
        return "the move: " + getMovement();
    }
}
