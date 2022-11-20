package ui;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cross extends HBox {
    public Cross(Color color, double size) {
        super();
        this.setSpacing(-10.5f * size);
        Line line1 = new Line(0, 0, 8 * size, 8 * size);
        Line line2 = new Line(0, 8 * size, 8 * size, 0);
        line1.setStrokeWidth(1.5f);
        line2.setStrokeWidth(1.5f);
        line1.setStroke(color);
        line2.setStroke(color);
        this.getChildren().addAll(line1, line2);
    }
}
