import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

public class Peg implements Serializable {

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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean hasDisks(){
        if (this.getDisks().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peg peg = (Peg) o;
        return number == peg.number &&
                Objects.equals(disks, peg.disks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, disks);
    }
}
