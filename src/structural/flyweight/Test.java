package structural.flyweight;

public class Test {
    public static void main(String[] args) {
        Terrain terrain = new Terrain();

        for (int i = 0; i < 5; i++) {
            terrain.render("Oak",
                    Math.random() * Terrain.CANVAS_SIZE,
                    Math.random() * Terrain.CANVAS_SIZE);
        }

        for (int i = 0; i < 5; i++) {
            terrain.render("Acacia",
                    Math.random() * Terrain.CANVAS_SIZE,
                    Math.random() * Terrain.CANVAS_SIZE);
        }

        for (int i = 0; i < 5; i++) {
            terrain.render("Jungle",
                    Math.random() * Terrain.CANVAS_SIZE,
                    Math.random() * Terrain.CANVAS_SIZE);
        }

        Memory.print();
    }
}
