import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class TowersOfHanoi {

    private static List<Peg> pegs = new ArrayList<>();
    private static final int NUMBER_OF_DISKS = 5;
    private static final int NUMBER_OF_PEGS = 5;


    public void solve(int n, String start, String auxiliary, String end) {
        if (n == 1) {
            System.out.println(start + " -> " + end);
        } else {
            solve(n - 1, start, end, auxiliary);
            System.out.println(start + " -> " + end);
            solve(n - 1, auxiliary, start, end);
        }
    }

    public static void main(String[] args) {
        TowersOfHanoi towersOfHanoi = new TowersOfHanoi();
//        System.out.print("Enter number of discs: ");
//        Scanner scanner = new Scanner(System.in);
//        int discs = scanner.nextInt();
//        towersOfHanoi.solve(discs, "A", "B", "C");

        towersOfHanoi.initializeGame(NUMBER_OF_DISKS, NUMBER_OF_PEGS);
        for (Peg peg : pegs) {
            if (peg.getDisks() != null) {
                System.out.println(peg.getNumber() + " " + peg.getDisks());
            } else System.out.println(peg.getNumber() + " 0");
        }
    }

    public void solve() {
        Integer sourcePegNumber = (new Random()).nextInt(NUMBER_OF_PEGS) + 1;
        Integer destinationPegNumber = (new Random()).nextInt(NUMBER_OF_PEGS) + 1;


        Peg sourcePeg = pegs.stream()
                .filter(peg->peg.getNumber() == sourcePegNumber).findFirst().get();
        Peg destinationPeg = pegs.stream()
                .filter(peg->peg.getNumber() == destinationPegNumber).findFirst().get();
        if(sourcePeg != destinationPeg){
            moveDisk(sourcePeg, destinationPeg);
        }
    }

    public void moveDisk(Peg sourcePeg, Peg destinationPeg) {
        if (isMoveOk(sourcePeg.getDisks().peek(), destinationPeg)) {
            Disk diskToBeMoved = sourcePeg.getDisks().pop();
            destinationPeg.getDisks().push(diskToBeMoved);
        }
    }

    public boolean isMoveOk(Disk disk, Peg peg) {
        if (disk.getWidth() > peg.getDisks().pop().getWidth()) {
            return false;
        }
        return true;
    }

    private void initializeGame(int numberOfDisks, int numberOfPegs) {
        for (int index = 1; index <= numberOfPegs; index++) {
            pegs.add(new Peg(index));
        }
        Stack<Disk> disks = new Stack<>();
        for (int index = numberOfDisks; index >= 1; index--) {
            disks.push(new Disk(index));
        }
        Peg firstPeg = pegs.stream()
                .filter(peg -> peg.getNumber() == 1).findFirst().get();
        firstPeg.setDisks(disks);
    }
}