package GameModel.ShapeFabric;


import GameModel.Shape_Kind;

import java.util.HashMap;

//Выбирает соответствующий
public final class Builder_Selector {
    private final HashMap<Shape_Kind, ShapeBuilder> selector;
    public Builder_Selector(){
        selector = new HashMap<Shape_Kind, ShapeBuilder>();
        selector.put(Shape_Kind.T_SHAPE, new T_shape());
        selector.put(Shape_Kind.L_SHAPE, new L_shape());
        selector.put(Shape_Kind.J_SHAPE, new J_shape());
        selector.put(Shape_Kind.REVERSED_Z_SHAPE, new Reversed_Z_shape());
        selector.put(Shape_Kind.SQUARE_SHAPE, new Square_shape());
        selector.put(Shape_Kind.Z_SHAPE, new Z_shape());
        selector.put(Shape_Kind.I_SHAPE, new I_shape());
    }

    public ShapeBuilder getBuilder(Shape_Kind shapeKind){
        return selector.get(shapeKind);
    }
}
