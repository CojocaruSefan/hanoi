import java.util.Stack;

public class Peg {

    private int number;
    private Stack<Disk> disks;

    public Peg() {
    }

    public Peg(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Stack<Disk> getDisks() {
        return disks;
    }

    public void setDisks(Stack<Disk> disks) {
        this.disks = disks;
    }
}
