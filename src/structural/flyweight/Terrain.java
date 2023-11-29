package structural.flyweight;

public class Terrain {
    static final int CANVAS_SIZE = 10000;

    public void render(String type, double position_x, double position_y) {
        TreeModel model = TreeModelFactory.getInstance(type);

        Tree tree = new Tree(model, position_x, position_y);

        System.out.println("x: " + tree.position_x + " y: " + tree.position_y + " 위치에 " + type + " 나무 생성 완료");
    }
}
