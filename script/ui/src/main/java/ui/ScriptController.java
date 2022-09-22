package ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import core.main.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ScriptController{

    @FXML
    private GridPane boardGrid;
    
    @FXML
    private AnchorPane boardAnchor;

    @FXML
    private void initialize() {
        try {
            createBoardButtons(getBoardsFromFile());
        } catch (IOException e) {
            e.printStackTrace(); }
    }

    @FXML
    private void clickBoardButton(ActionEvent ae) throws IOException {
        
    }
    
    private void createBoardButtons(List<Board> boards) throws IOException {
        IntStream.range(0, boards.size()).forEach(i -> {
            Button button = new Button(boards.get(i).getBoardName());
            button.wrapTextProperty().setValue(true);
            button.setStyle("-fx-text-alignment: center;");
            button.setCursor(Cursor.HAND);
            button.setOnAction((event) -> {
                try {
                    clickBoardButton(event);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button.setMaxWidth(190);
            boardGrid.add(button, 0, i);
        });
        ScrollPane scrollPane = new ScrollPane(boardGrid);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        boardAnchor.getChildren().add(scrollPane);
    }

    private List<Board> getBoardsFromFile() {
        return Arrays.asList();
    }
}