package ui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ExpandableTextArea extends TextArea {

    public ExpandableTextArea(String content) {
        super(content);
        this.setWrapText(true);
        this.setStyle("-fx-font-size: 13; -fx-font-family: 'Poppins Regular';");
        this.setPadding(new Insets(0, 7, 0, 7));
        this.resize(content, true);
        this.textProperty().addListener((obs, old, niu) -> {
            resize(niu, false);
        });
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        setWrapText(true);
        ScrollPane scrollPane = (ScrollPane) lookup(".scroll-pane");
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    }

    private void resize(String content, Boolean initial) {
        Text t = new Text(content);
        t.setFont(this.getFont());
        t.setWrappingWidth(206);
        StackPane pane = new StackPane(t);
        pane.setMaxWidth(206);
        pane.layout();
        double height = t.getLayoutBounds().getHeight() * 1.1 * (initial ? 1.3 : 1);
        double padding = 30;
        this.setMinHeight(height + padding);
        this.setMaxHeight(height + padding);
    }
}
