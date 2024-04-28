package Game;

public class Vector2 {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void add(Vector2 v) {
        x += v.x;
        y += v.y;
    }

    void subtract(Vector2 v) {
        x -= v.x;
        y -= v.y;
    }

    void multiply(Vector2 v) {
        x *= v.x;
        y *= v.y;
    }

    Vector2 sum(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    Vector2 difference(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    boolean checkBounds(int max_x, int max_y){
        return checkBounds(max_x, max_y, 0,0);
    }

    boolean checkBounds(int max_x, int max_y, int min_x, int min_y){
        return x < max_x && x >= min_x && y < max_y && y >= min_y;
    }
}

