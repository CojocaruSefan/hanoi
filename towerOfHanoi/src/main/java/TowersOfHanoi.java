import java.util.*;

public class TowersOfHanoi {

    final int NUMBER_OF_DISKS = 5;
    final int NUMBER_OF_PEGS = 3;
//    final double OPTIMAL_NO_OF_MOVES = Math.pow(2, NUMBER_OF_DISKS) - 1;

    List<Peg> pegs;
    List<Move> moves;
    Peg lastPeg;


    public static void main(String[] args) {
        TowersOfHanoi towersOfHanoi = new TowersOfHanoi();
        towersOfHanoi.hillClimbing(30);
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

}