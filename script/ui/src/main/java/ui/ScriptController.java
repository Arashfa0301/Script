package ui;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import core.main.Board;
import core.main.Note;
import core.main.User;
import data.ScriptModule;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.geometry.*;

public class ScriptController {

    private final int BUTTON_WIDTH = 190, NOTE_SIZE = 200;

    private Board currentBoard;

    private List<Board> boards;

    private ScriptModule scriptModule;

    @FXML
    private GridPane boardGrid, noteGrid;

    @FXML
    private Button newNoteButton, newBoardButton;

    @FXML
    private AnchorPane boardAnchor;

    @FXML
    private TextField boardTitle, boardDescription, boardName;

    @FXML
    private VBox noteScreen;

    private User user;

    @FXML
    private void initialize() {
        scriptModule = new ScriptModule();
        user = scriptModule.read();
        boards = user.getBoards();
        try {
            loadBoardButtons(boards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBoardButtonClick(ActionEvent ae) throws IOException {
        Button clickedButton = (Button) ae.getSource();
        Board selectedBoard = boards.stream().filter(board -> board.getBoardName().equals(clickedButton.getText()))
                .findFirst().get();
        setTitleAndDescription(selectedBoard);
        loadNotes(selectedBoard);
        currentBoard = selectedBoard;
        noteScreen.setVisible(true);
        update();
    }

    @FXML
    private void onNoteEdit(KeyEvent event) throws IOException {
        editNote(event);
    }

    @FXML
    private void editBoardTitle(KeyEvent event) throws IOException {
        GridPane pane = (GridPane) boardGrid.getChildren().get(boards.indexOf(currentBoard));
        Button button = (Button) pane.getChildren().get(0);
        TextField field = (TextField) event.getSource();
        button.setText(field.getText());
        currentBoard.setBoardName(field.getText());
    }

    @FXML
    private void editBoardDescription(KeyEvent event) throws IOException {
        TextField field = (TextField) event.getSource();
        currentBoard.setBoardDescription(field.getText());
    }

    @FXML
    private void save(ActionEvent ae) {
        user.setBoards(boards);
        scriptModule.write(user);
        Button button = (Button) ae.getSource();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(button);
        transition.setAutoReverse(false);
        button.setDisable(true);
        transition.setOnFinished(evt -> button.setDisable(false));
        transition.play();
    }

    private void editNote(KeyEvent event) {
        if (event.getSource().getClass() == TextArea.class) {
            TextArea area = (TextArea) event.getSource();
            GridPane pane = (GridPane) area.getParent();
            int row = GridPane.getRowIndex(pane);
            int column = GridPane.getColumnIndex(pane);
            currentBoard.getNotes().get(2 * row + column).setText(area.getText());
        } else if (event.getSource().getClass() == TextField.class) {
            TextField field = (TextField) event.getSource();
            GridPane pane = (GridPane) field.getParent();
            int row = GridPane.getRowIndex(pane);
            int column = GridPane.getColumnIndex(pane);
            currentBoard.getNotes().get(2 * row + column).setTitle(field.getText());
        }
    }

    @FXML
    private void createBoard() {
        Board newBoard = new Board(boardName.getText());
        boards.add(newBoard);
        createBoardButton(newBoard, boards.size() - 1);
        boardName.clear();
    }

    @FXML
    private void createNote() {
        currentBoard.addNote(new Note());
        loadNotes(currentBoard);
        update();
    }

    @FXML
    private void newBoardNameEdit() {
        checkNewBoardName();
    }

    @FXML
    private void deleteNote(ActionEvent ae) {
        Button button = (Button) ae.getSource();
        GridPane pane = (GridPane) button.getParent().getParent();
        int row = GridPane.getRowIndex(pane);
        int column = GridPane.getColumnIndex(pane);
        currentBoard.getNotes().remove(2 * row + column);
        loadNotes(currentBoard);
        update();
    }

    private void loadBoardButtons(List<Board> boards) throws IOException {
        boardGrid.getChildren().clear();
        IntStream.range(0, boards.size()).forEach(i -> {
            createBoardButton(boards.get(i), i);
        });
        boardGrid.setStyle("-fx-background-color: transparent");
    }

    private void createBoardButton(Board board, int index) {
        Button button = new Button(board.getBoardName());
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-background-color: transparent; -fx-alignment: center-left;");
        button.setPadding(new Insets(0, 0, 0, 0));
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> {
            try {
                onBoardButtonClick(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button.setMaxWidth(BUTTON_WIDTH);
        Button deleteButton = new Button("X");
        deleteButton.setShape(new Circle(1.5));
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setStyle("-fx-text-fill: black; -fx-font-family: 'Poppins';");
        deleteButton.setOnAction((event) -> {
            try {
                deleteBoard(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        boardGrid.addRow(index, button, deleteButton);

    }

    @FXML
    private void deleteBoard(ActionEvent ae) throws IOException {
        Button button = (Button) ae.getSource();
        int index = GridPane.getRowIndex(button);
        boards.remove(index);
        loadBoardButtons(boards);
        update();
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
            TextField title = new TextField(note.getTitle());
            title.setStyle("-fx-font-weight: bold");
            title.setOnKeyReleased((event) -> {
                try {
                    onNoteEdit(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            title.setPromptText("Title");
            TextArea text = new TextArea(note.getText());
            text.setOnKeyReleased((event) -> {
                try {
                    onNoteEdit(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            text.setPromptText("Notes");
            text.setWrapText(true);
            text.setPrefSize(NOTE_SIZE, NOTE_SIZE);
            GridPane topPane = new GridPane();
            topPane.setStyle("-fx-background-color: #ffffff");
            notePane.addRow(0, topPane);
            topPane.addColumn(0, title);
            Button deleteButton = new Button("Delete note");
            deleteButton.setOnAction((event) -> deleteNote(event));
            topPane.addColumn(1, deleteButton);
            notePane.addRow(1, text);
            noteGrid.add(notePane, i % 2, Math.floorDiv(i, 2));
        });
    }

    private void update() {
        if (!(currentBoard == null)) {
            if (currentBoard.getNotes().size() == 6)
                newNoteButton.setDisable(true);
            else
                newNoteButton.setDisable(false);
            if (!boards.contains(currentBoard))
                noteScreen.setVisible(false);
            else
                noteScreen.setVisible(true);
        }
    }

    private void checkNewBoardName() {
        if (boardName.getText().isBlank() || boards.stream().map(board -> (board.getBoardName()))
                .collect(Collectors.toList()).contains(boardName.getText()))
            newBoardButton.setDisable(true);
        else
            newBoardButton.setDisable(false);
    }
}