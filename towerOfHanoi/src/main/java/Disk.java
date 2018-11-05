public class Disk {

    private int width;

    public Disk() {
    }

    public Disk(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "width=" + width +
                '}';
    }
}
