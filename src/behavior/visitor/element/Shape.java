package behavior.visitor.element;

import behavior.visitor.visitor.Visitor;

public interface Shape {

    void move(int x, int y);
    void draw();
    String accept(Visitor visitor);

}
