package structural.flyweight;

public class Tree {
    long objSize = 10;
    double position_x;
    double position_y;
    TreeModel model;

    public Tree(TreeModel model, double position_x, double position_y) {
        this.model = model;
        this.position_x = position_x;
        this.position_y = position_y;

        Memory.size += this.objSize;
    }
}
