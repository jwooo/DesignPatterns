package behavior.visitor.concrete_visitor;

import behavior.visitor.element.Circle;
import behavior.visitor.element.CompoundShape;
import behavior.visitor.element.Dot;
import behavior.visitor.element.Rectangle;
import behavior.visitor.element.Shape;
import behavior.visitor.visitor.Visitor;

public class XMLExportVisitor implements Visitor {

    public String export(Shape... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");

        for (Shape shape : args) {
            sb.append(shape.accept(this)).append("\n");
        }

        return sb.toString();
    }

    @Override
    public String visitDot(Dot dot) {
        return "<dot>" + "\n" +
                "    <id>" + dot.getId() + "</id>" + "\n" +
                "    <x>" + dot.getX() + "</x>" + "\n" +
                "    <y>" + dot.getY() + "</y>" + "\n" +
                "</dot>";
    }

    @Override
    public String visitCircle(Circle circle) {
        return "<circle>" + "\n" +
                "    <id>" + circle.getId() + "</id>" + "\n" +
                "    <x>" + circle.getX() + "</x>" + "\n" +
                "    <y>" + circle.getY() + "</y>" + "\n" +
                "    <radius>" + circle.getRadius() + "</radius>" + "\n" +
                "</circle>";
    }

    @Override
    public String visitRectangle(Rectangle rectangle) {
        return "<rectangle>" + "\n" +
                "    <id>" + rectangle.getId() + "</id>" + "\n" +
                "    <x>" + rectangle.getX() + "</x>" + "\n" +
                "    <y>" + rectangle.getY() + "</y>" + "\n" +
                "    <width>" + rectangle.getWidth() + "</width>" + "\n" +
                "    <height>" + rectangle.getHeight() + "</height>" + "\n" +
                "</rectangle>";
    }

    @Override
    public String visitCompoundGraphic(CompoundShape cg) {
        return "<compound_graphic>" + "\n" +
                "   <id>" + cg.getId() + "</id>" + "\n" +
                    visitInnerCompoundGraphic(cg) +
                "</compound_graphic>";
    }

    private String visitInnerCompoundGraphic(CompoundShape cg) {
        StringBuilder sb = new StringBuilder();

        for (Shape shape : cg.children) {
            String obj = shape.accept(this);

            obj = "    " + obj.replace("\n", "\n    ") + "\n";
            sb.append(obj);
        }

        return sb.toString();
    }

}
