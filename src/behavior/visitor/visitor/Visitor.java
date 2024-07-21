package behavior.visitor.visitor;

import behavior.visitor.element.Circle;
import behavior.visitor.element.CompoundShape;
import behavior.visitor.element.Dot;
import behavior.visitor.element.Rectangle;

public interface Visitor {

    String visitDot(Dot dot);
    String visitCircle(Circle circle);
    String visitRectangle(Rectangle rectangle);
    String visitCompoundGraphic(CompoundShape cg);
}
