package ui;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ExpandableTextArea extends TextArea {

    public ExpandableTextArea(String content) {
        super(content);
        this.setWrapText(true);
        this.setStyle("-fx-font-size: 13; -fx-font-family: 'Poppins Regular';");
        this.setPadding(new Insets(0, 7, 0, 7));
        resize(content + content, this.getFont());
        this.textProperty().addListener((obs, old, niu) -> {
            resize(old + niu, this.getFont());
        });
    }

    private void resize(String content, Font font) {
        Text t = new Text(content);
        t.setFont(font);
        StackPane pane = new StackPane(t);
        pane.layout();
        double height = t.getLayoutBounds().getHeight() * 0.7;
        double padding = 30;
        this.setMinHeight(height + padding);
        this.setMaxHeight(height + padding);
    }
}