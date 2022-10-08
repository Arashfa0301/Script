package ui;

import core.main.Board;
import core.main.Note;
import core.main.User;
import data.ScriptModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScriptController {

    private static final int BUTTON_WIDTH = 190, NOTE_SIZE = 200;

    private Board currentBoard;

    private int columnsCount = 1;

    private List<Board> boards;

    private ScriptModule scriptModule;

    @FXML
    private GridPane boardGrid, noteGrid;

    @FXML
    private SplitPane scriptSplitPane;

    @FXML
    private Button newNoteButton, newBoardButton;

    @FXML
    private AnchorPane boardAnchor;

    @FXML
    private TextField boardTitle, boardDescription, boardName;

    @FXML
    private VBox noteScreen;

    @FXML
    private Text username, exampleMail;

    private User user;

    @FXML
    private void initialize() {
        scriptSplitPane.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        scriptModule = new ScriptModule();
        user = Globals.user;
        username.setText(user.getName());
        exampleMail.setText(user.getName().toLowerCase() + "@example.com");
        noteGrid.widthProperty().addListener((obs, oldVal, newVal) -> {
            int oldColumnsCount = columnsCount;
            columnsCount = (int) (newVal.doubleValue() / NOTE_SIZE);
            if (columnsCount == 0) {
                columnsCount = 1;
            }
            if (oldColumnsCount != columnsCount) {
                loadNotes(currentBoard);
            }
        });
        boards = user.getBoards();
        try {
            loadBoardButtons(boards);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createWindowSizeListener();
    }

    @FXML
    private void onBoardButtonClick(ActionEvent ae) throws IOException {
        // finds the first (and only) board with a name that equals the text on the
        // clicked button
        Board selectedBoard = boards.stream()
                .filter(board -> board.getBoardName().equals(((Button) ae.getSource()).getText()))
                .findFirst()
                .get();
        setTitleAndDescription(selectedBoard);
        loadNotes(selectedBoard);
        // sets the currentBoard variable to the currently selected board
        currentBoard = selectedBoard;
        noteScreen.setVisible(true);
        update();
    }

    @FXML
    private void handleBoardNameEnter(KeyEvent ke) {
        // checks if "ENTER" is clicked on the keyboard and if the name written is valid
        if (ke.getCode().equals(KeyCode.ENTER) && checkNewBoardName()) {
            createBoard();
        }
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
        save();
    }

    @FXML
    private void editBoardDescription(KeyEvent event) throws IOException {
        TextField field = (TextField) event.getSource();
        currentBoard.setBoardDescription(field.getText());
        save();
    }

    private void save() {
        user.setBoards(boards);
        scriptModule.write(user);
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
        save();
    }

    @FXML
    private void createBoard() {
        Board newBoard = new Board(boardName.getText(), "");
        boards.add(newBoard);
        createBoardButton(newBoard, boards.size() - 1);
        boardName.clear();
        newBoardButtonEnable();
        save();
    }

    @FXML
    private void createNote() {
        currentBoard.addNote(new Note("", ""));
        loadNotes(currentBoard);
        update();
        save();
    }

    @FXML
    private void newBoardNameEdit() {
        newBoardButtonEnable();
    }

    @FXML
    private void handleLogoutButton(ActionEvent ae) throws IOException {
        switchScreen(ae, "Login.fxml");
    }

    private void switchScreen(ActionEvent ae, String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void newBoardButtonEnable() {
        newBoardButton.setDisable(checkNewBoardName() ? false : true);
    }

    private void createWindowSizeListener() {
        scriptSplitPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowWidth = (double) newVal;
        });
        scriptSplitPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowHeight = (double) newVal;
        });
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
        save();
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
        // add button that is used to delete the board button that was just made
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
        save();
    }

    private void setTitleAndDescription(Board board) {
        boardTitle.setText(board.getBoardName());
        boardDescription.setText(board.getBoardDescription());
    }

    private void loadNotes(Board board) {
        noteGrid.getChildren().clear();
        // remove notegrid columns and rows
        noteGrid.getColumnConstraints().clear();
        noteGrid.getRowConstraints().clear();
        // add columns and rows to notegrid
        IntStream.range(0, columnsCount).forEach(i -> {
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(200);
            noteGrid.getColumnConstraints().add(column);
        });
        IntStream.range(0, (int) (Math.floorDiv(board.getNotes().size(), columnsCount) + 1)).forEach(i -> {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(600);
            noteGrid.getRowConstraints().add(row);
        });
        IntStream.range(0, board.getNotes().size()).forEach(i -> {
            Note note = board.getNotes().get(i);
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
            HBox topPane = new HBox();
            topPane.setStyle("-fx-background-color: #ffffff");
            VBox notePane = new VBox();
            notePane.getChildren().add(topPane);
            topPane.getChildren().add(title);
            Button deleteButton = new Button("Delete note");
            deleteButton.setOnAction((event) -> deleteNote(event));
            topPane.getChildren().add(deleteButton);
            notePane.getChildren().add(text);
            noteGrid.add(notePane, i % columnsCount, Math.floorDiv(i, columnsCount));
        });
    }

    private void update() {
        if (!(currentBoard == null)) {
            newNoteButton.setDisable(currentBoard.getNotes().size() == 6 ? true : false);
            noteScreen.setVisible(!boards.contains(currentBoard) ? false : true);
        }
    }

    private Boolean checkNewBoardName() {
        return !(boardName.getText().isBlank() || boards.stream().map(board -> (board.getBoardName()))
                .collect(Collectors.toList()).contains(boardName.getText()));
    }
}