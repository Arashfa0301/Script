package ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import core.main.Board;
import core.main.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ScriptController {

    @FXML
    private GridPane boardGrid, noteGrid;
    
    @FXML
    private AnchorPane boardAnchor;

    @FXML
    private TextField boardTitle, boardDescription;

    @FXML
    private VBox noteScreen;

    private List<Board> testBoards = Arrays.asList(new Board("1", "a"),new Board("2", "b"),new Board("3", "c"),new Board("4", "d"));
    private List<Note> testNotes = Arrays.asList(new Note("title","Lorem ipsum dolor sit amet. Sit reprehenderit veritatis quo placeat quis ea voluptatibus sequi. In ducimus exercitationem non voluptatem molestias et cumque sunt aut sequi similique! Est voluptas aliquam et mollitia quia est voluptatum dolorem id nobis Quis qui distinctio veritatis. Qui velit nihil et animi veritatis qui neque quidem et perferendis velit."));

    @FXML
    private void initialize() {
        testBoards.get(0).addNote(testNotes.get(0));
        testBoards.get(0).addNote(testNotes.get(0));
        testBoards.get(0).addNote(testNotes.get(0));
        try {
            // createBoardButtons(getBoardsFromFile());
            createBoardButtons(testBoards);
        } catch (IOException e) {
            e.printStackTrace(); }
    }

    @FXML
    private void onBoardButtonClick(ActionEvent ae) throws IOException {
        // noteGrid.getChildren().clear();
        Button clickedButton = (Button)ae.getSource();
        // Board selectedBoard = getBoardsFromFile().stream().filter(board -> 
        //     board.getBoardName().equals(clickedButton.getText())).findFirst().get();
        Board selectedBoard = testBoards.stream().filter(board -> 
            board.getBoardName().equals(clickedButton.getText())).findFirst().get();
        setTitleAndDescription(selectedBoard);
        loadNotes(selectedBoard);
    }
    
    private void createBoardButtons(List<Board> boards) throws IOException {
        IntStream.range(0, boards.size()).forEach(i -> {
            Button button = new Button(boards.get(i).getBoardName());
            button.wrapTextProperty().setValue(true);
            button.setStyle("-fx-text-alignment: center;");
            button.setCursor(Cursor.HAND);
            button.setOnAction((event) -> {
                try {
                    onBoardButtonClick(event);
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

    private void setTitleAndDescription(Board board) {
        boardTitle.setText(board.getBoardName());
        boardDescription.setText(board.getBoardDescription());
    }

    private void loadNotes(Board board) {
        noteGrid.getChildren().clear();
        IntStream.range(0, board.getNotes().size()).forEach(i -> {
            Note note = board.getNotes().get(i);
            GridPane notePane = new GridPane();
            Label title = new Label(note.getTitle());
            TextArea text = new TextArea(note.getText());
            text.setWrapText(true);
            text.setPrefSize(200, 200);
            notePane.addRow(0, title);
            notePane.addRow(1, text);
            noteGrid.add(notePane, i%2, Math.floorDiv(i, 2));
        });
    }
}