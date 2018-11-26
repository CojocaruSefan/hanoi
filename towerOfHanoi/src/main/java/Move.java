import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Move {
    private Peg start;
    private Peg finish;
    private Disk disk;

    public Move(TowersOfHanoi towersOfHanoi) {
        do {
            Integer sourcePegNumber = (new Random()).nextInt(towersOfHanoi.NUMBER_OF_PEGS) + 1;
            Integer destinationPegNumber = (new Random()).nextInt(towersOfHanoi.NUMBER_OF_PEGS) + 1;
            this.start = towersOfHanoi.pegs.stream()
                    .filter(peg -> peg.getNumber() == sourcePegNumber).findFirst().get();
            this.finish = towersOfHanoi.pegs.stream()
                    .filter(peg -> peg.getNumber() == destinationPegNumber).findFirst().get();
            if (start.hasDisks())
                this.disk = start.getDisks().peek();
        } while (!this.isOk());
    }

    public Move(Peg start, Peg finish) {
        this.start = start;
        this.finish = finish;
        if (start.hasDisks())
            this.disk = start.getDisks().peek();
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public Peg getStart() {
        return start;
    }

    public void setStart(Peg start) {
        this.start = start;
    }

    public Peg getFinish() {
        return finish;
    }

    public void setFinish(Peg finish) {
        this.finish = finish;
    }

    public double getFitness(List<Move> moveList, TowersOfHanoi towersOfHanoi) {
        double fitness = 1;

        if (!moveList.isEmpty()) {

            //check if we are in a loop
            if (moveList.size() > 2)
                if (this.equals(moveList.get(moveList.size() - 2))) {
                    return 0; //the move is VERY BAD, exit immediately
                }
            //check if we're going back a move
            if (moveList.size() > 1)
                if (this.reverse(moveList.get(moveList.size() - 1))) {
                    fitness = 0D;
                }

            if (this.getFinish() == towersOfHanoi.lastPeg) { //if we're about to move on the last peg
                if (this.disk.getWidth() == towersOfHanoi.NUMBER_OF_DISKS) { //and we are about to move the biggest disk
                    fitness = 2;
                }
                if (!this.finish.getDisks().isEmpty())
                    if (this.finish.getDisks().peek().getWidth() == this.disk.getWidth() + 1) { //or we are moving the next natural disk
                        fitness = 2;
                    }
            }
        }

        if (this.finish.getDisks().isEmpty()) {
            fitness += this.disk.getWidth() / (towersOfHanoi.NUMBER_OF_DISKS + 1);
        } else {
            fitness += this.disk.getWidth() / this.finish.getDisks().peek().getWidth();
        }
        return fitness;
    }

    public boolean isOk() {
        if (this.start == this.finish || !this.start.hasDisks())
            return false;
        if (this.finish.hasDisks() && disk.getWidth() > this.finish.getDisks().peek().getWidth())
            return false;
        return true;
    }

    public void executeMove() {
        Disk diskToBeMoved = this.start.getDisks().pop();
        this.finish.getDisks().push(diskToBeMoved);
    }

    private boolean reverse(Move move) {
        return this.getStart() == move.getFinish() && this.getFinish() == move.getStart();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(start, move.start) &&
                Objects.equals(finish, move.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, finish);
    }
}

