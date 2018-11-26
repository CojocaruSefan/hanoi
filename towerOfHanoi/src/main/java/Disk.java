import java.io.Serializable;

public class Disk implements Serializable {

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
