import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class TowersOfHanoi {

    private static List<Peg> pegs = new ArrayList<>();
    private static final int NUMBER_OF_DISKS = 5;
    private static final int NUMBER_OF_PEGS = 3;
    private static int NUMBER_OF_MOVES = 0;
    private static int NUMBER_OF_RESETS = 0;


    public static void main(String[] args) {
        TowersOfHanoi towersOfHanoi = new TowersOfHanoi();

        towersOfHanoi.initializeGame(NUMBER_OF_DISKS, NUMBER_OF_PEGS);
        while (!towersOfHanoi.isFinal()) {
            if (NUMBER_OF_MOVES > (Math.pow(2,NUMBER_OF_DISKS)-1)) {
                NUMBER_OF_MOVES = 0;
                System.out.println("game reset");
                NUMBER_OF_RESETS++;
                towersOfHanoi.initializeGame(NUMBER_OF_DISKS, NUMBER_OF_PEGS);
            }
            towersOfHanoi.solve();
        }
        System.out.println(NUMBER_OF_MOVES);
        System.out.println(NUMBER_OF_RESETS);
    }

    private void solve() {
        Integer sourcePegNumber = (new Random()).nextInt(NUMBER_OF_PEGS) + 1;
        Integer destinationPegNumber = (new Random()).nextInt(NUMBER_OF_PEGS) + 1;


        Peg sourcePeg = pegs.stream()
                .filter(peg -> peg.getNumber() == sourcePegNumber).findFirst().get();
        Peg destinationPeg = pegs.stream()
                .filter(peg -> peg.getNumber() == destinationPegNumber).findFirst().get();
        if (sourcePeg != destinationPeg && sourcePeg.hasDisks()) {
            moveDisk(sourcePeg, destinationPeg);
            printPeg(sourcePeg);
            printPeg(destinationPeg);
        }

    }

    private void printGame() {
        for (Peg peg : pegs) {
            if (peg.getDisks() != null) {
                System.out.println(peg.getNumber() + " " + peg.getDisks());
            } else System.out.println(peg.getNumber() + " 0");
        }
    }

    private void printPeg(Peg peg) {
        if (peg.hasDisks()) {
            System.out.println(peg.getNumber() + " " + peg.getDisks());
        } else System.out.println(peg.getNumber() + " 0");
    }

    private void moveDisk(Peg sourcePeg, Peg destinationPeg) {
        if (sourcePeg.hasDisks())
            if (isMoveOk(sourcePeg.getDisks().peek(), destinationPeg)) {
                Disk diskToBeMoved = sourcePeg.getDisks().pop();
                destinationPeg.getDisks().push(diskToBeMoved);
                NUMBER_OF_MOVES++;
            }

    }

    public boolean isMoveOk(Disk disk, Peg peg) {
        if (peg.hasDisks())
            if (disk.getWidth() > peg.getDisks().peek().getWidth()) {
                return false;
            }
        return true;
    }

    private void initializeGame(int numberOfDisks, int numberOfPegs) {
        pegs=new ArrayList<>();
        for (int index = 1; index <= numberOfPegs; index++) {

            Peg peg = new Peg(index);
            peg.setDisks(new Stack<Disk>());
            pegs.add(peg);

        }
        Stack<Disk> disks = new Stack<>();
        for (int index = numberOfDisks; index >= 1; index--) {
            disks.push(new Disk(index));
        }
        Peg firstPeg = pegs.stream()
                .filter(peg -> peg.getNumber() == 1).findFirst().get();
        firstPeg.setDisks(disks);
    }

    private boolean isFinal() {
        Peg lastPeg = pegs.stream().filter(peg -> peg.getNumber() == NUMBER_OF_PEGS).findFirst().get();
        if (!lastPeg.hasDisks()) {
            return false;
        }
        for (Peg p : pegs) {
            if (p.hasDisks() && p.getNumber() != NUMBER_OF_PEGS)
                return false;
        }
        return true;
    }

}