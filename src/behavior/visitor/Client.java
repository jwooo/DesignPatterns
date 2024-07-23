package behavior.visitor;

import behavior.visitor.concrete_visitor.XMLExportVisitor;
import behavior.visitor.element.Circle;
import behavior.visitor.element.CompoundShape;
import behavior.visitor.element.Dot;
import behavior.visitor.element.Rectangle;

public class Client {

    public static void main(String[] args) {
        Dot dot = new Dot(1, 10, 55);
        Circle circle = new Circle(2, 23, 15, 10);
        Rectangle rectangle = new Rectangle(3, 10 ,17, 20, 30);

        CompoundShape compoundShape = new CompoundShape(4);

        compoundShape.add(dot);
        compoundShape.add(circle);
        compoundShape.add(rectangle);

        CompoundShape innerCompoundShape = new CompoundShape(5);

        innerCompoundShape.add(dot);
        compoundShape.add(innerCompoundShape);

        System.out.println(new XMLExportVisitor().export(circle, compoundShape));
    }

}
