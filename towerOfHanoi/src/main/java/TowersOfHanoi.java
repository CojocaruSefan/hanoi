import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.*;

public class TowersOfHanoi implements Serializable {

    final static int NUMBER_OF_DISKS = 3;
    final static int NUMBER_OF_PEGS = 3;
//    final double OPTIMAL_NO_OF_MOVES = Math.pow(2, NUMBER_OF_DISKS) - 1;

    List<Peg> pegs;
    List<Move> moves;
    Peg lastPeg;

    public TowersOfHanoi() {
    }

    public TowersOfHanoi(List<Peg> pegs, List<Move> moves, Peg lastPeg) {
        this.pegs = new ArrayList<>(pegs);
        this.moves = new ArrayList<>(moves);
        this.lastPeg = this.pegs.stream().filter(peg -> peg.getNumber() == NUMBER_OF_PEGS).findFirst().get();
    }

    public static void main(String[] args) {
        TowersOfHanoi towersOfHanoi = new TowersOfHanoi();
//        towersOfHanoi.hillClimbing(30);
        towersOfHanoi.IDS();
    }


    private void initializeGame(int numberOfDisks, int numberOfPegs) {
        moves = new ArrayList<>();
        pegs = new ArrayList<>();
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
        lastPeg = pegs.stream().filter(peg -> peg.getNumber() == NUMBER_OF_PEGS).findFirst().get();
        firstPeg.setDisks(disks);
    }

    private boolean isFinal() {
        if (!lastPeg.hasDisks()) {
            return false;
        }
        for (Peg p : pegs) {
            if (p.hasDisks() && p.getNumber() != NUMBER_OF_PEGS)
                return false;
        }
        return true;
    }

    private void hillClimbing(int noOfIterations) {

        System.out.println("HillClimbing:");
        int minimumNumberOfMoves = Integer.MAX_VALUE;

        for (int i = 0; i < noOfIterations; i++) {
            this.initializeGame(NUMBER_OF_DISKS, NUMBER_OF_PEGS);
            while (!this.isFinal()) {
                Move bestMove = new Move(this);

                boolean isCurrentMoveBest = true;
                do {
                    int numberOfNeighbours = NUMBER_OF_PEGS;
                    isCurrentMoveBest = true;
                    Move currentMove = new Move(this);
                    while (numberOfNeighbours > 0) {
                        Move newMove = new Move(this);

                        if (currentMove.getFitness(moves, this) < newMove.getFitness(moves, this)) {
                            currentMove = newMove;
                            isCurrentMoveBest = false;
                        }
                        numberOfNeighbours--;
                    }
                    if (currentMove.getFitness(moves, this) > bestMove.getFitness(moves, this)) {
                        bestMove = currentMove;
                    }
                } while (!isCurrentMoveBest);
                bestMove.executeMove();
                moves.add(bestMove);
            }
            System.out.println("Iteration no. " + i + " :" + moves.size() + " moves");
            if (minimumNumberOfMoves > moves.size()) {
                minimumNumberOfMoves = moves.size();
            }
        }
        System.out.println("Minimum number of moves: " + minimumNumberOfMoves);
    }


    private TowersOfHanoi IDS() {

        int depth = 0;
        boolean found = false;
        initializeGame(NUMBER_OF_DISKS, NUMBER_OF_PEGS);
        TowersOfHanoi result = null;

        while (result == null && depth < 20) {
            depth++;
            result = this.DLS(depth);
        }
        System.out.println("IDS: Total number of moves for first finalized game found:" + depth);
        return result;
    }

    private TowersOfHanoi DLS(int depth) {
        if (this.isFinal())
            return this;
        if (depth == 0)
            return null;

        List<Move> allPossibleMoves = this.getAllPossibleMoves();
//        allPossibleMoves.forEach(move -> {
//            System.out.println(move.getStart().getNumber()+ " -> "+move.getFinish().getNumber()+": "+move.getDisk().getWidth());
//        });

        for (Move move : allPossibleMoves) {
            TowersOfHanoi newTowerOfHanoi = (TowersOfHanoi) SerializationUtils.clone(this);
            Peg startingPeg = newTowerOfHanoi.pegs.stream()
                    .filter(peg -> peg.getNumber() == move.getStart().getNumber()).findFirst().get();
            Peg endingPeg = newTowerOfHanoi.pegs.stream()
                    .filter(peg -> peg.getNumber() == move.getFinish().getNumber()).findFirst().get();
            Move newMove = new Move(startingPeg, endingPeg);
            newTowerOfHanoi.moves.add(newMove);
            newMove.executeMove();
//            System.out.println("Depth=" + depth);
//            System.out.print("Move executed: " );
//            System.out.println(move.getStart().getNumber()+ " -> "+move.getFinish().getNumber()+": "+move.getDisk().getWidth());
//
//            newTowerOfHanoi.printGame();
            TowersOfHanoi result = newTowerOfHanoi.DLS(depth - 1);
            if (result != null)
                return result;
        }
        return null;
    }

    private List<Move> getAllPossibleMoves() {
        List<Move> moveList = new ArrayList<>();
        for (Peg startingPeg : pegs)
            for (Peg destinationPeg : pegs)
                if (!startingPeg.equals(destinationPeg)) {
                    Move move = new Move(startingPeg, destinationPeg);
                    if (move.isOk())
                        moveList.add(move);
                }

        return moveList;
    }

    @Override
    public String toString() {
        return "TowersOfHanoi{" +
                "pegs=" + pegs +
                ", moves=" + moves +
                ", lastPeg=" + lastPeg +
                '}';
    }

    public void printGame() {
        int i = 1;
        for (Peg peg : this.pegs) {
            System.out.print("[PEG" + i + "]: DISKS: ");
            i++;
            for (Disk disk : peg.getDisks())
                System.out.print(disk.getWidth() + ", ");
            System.out.println();
        }
    }
}