public class Move {
    private Peg start;
    private Peg finish;

    public Move() {
    }

    public Move(Peg start, Peg finish) {
        this.start = start;
        this.finish = finish;
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
}
