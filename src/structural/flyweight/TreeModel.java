package structural.flyweight;

final class TreeModel {
    long objSize = 90;
    String type;
    Object mesh;
    Object texture;

    public TreeModel(String type, Object mesh, Object texture) {
        this.type = type;
        this.mesh = mesh;
        this.texture = texture;

        Memory.size += this.objSize;
    }
}
