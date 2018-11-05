import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TowersOfHanoi {

    List<Peg> pegs = new ArrayList<>();

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
        System.out.print("Enter number of discs: ");
        Scanner scanner = new Scanner(System.in);
        int discs = scanner.nextInt();
        towersOfHanoi.solve(discs, "A", "B", "C");
    }

    public boolean isMoveOk(Disk disk, Peg peg) {
        if (disk.getWidth() > peg.getDisks().pop().getWidth()) {
            return false;
        }
        return true;
    }

    public void initializeGame(int numberOfDisks, int numberOfPegs) {
        for (int index = 1; index <= numberOfPegs; index++) {
            pegs.add(new Peg(index));
        }
        Peg firstPeg = pegs.stream()
                .filter(peg -> peg.getNumber() == 1).findFirst().get();
        for (int index = numberOfDisks; index >= 1; index--) {
            firstPeg.getDisks().push(new Disk(index));
        }
    }
}