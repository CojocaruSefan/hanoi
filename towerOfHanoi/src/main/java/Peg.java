import java.util.Stack;

public class Peg {

    private int number;
    private Stack<Disk> disks;

    public Peg() {
    }

    Peg(int number) {
        this.number = number;
    }

    int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    Stack<Disk> getDisks() {
        return disks;
    }

    void setDisks(Stack<Disk> disks) {
        this.disks = disks;
    }

    public boolean hasDisks(){
        if (this.getDisks().isEmpty()){
            return false;
        }
        return true;
    }

}
