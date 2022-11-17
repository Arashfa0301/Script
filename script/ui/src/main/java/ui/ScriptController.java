package ui;

import core.main.Board;
import core.main.Checklist;
import core.main.Note;
import core.main.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScriptController {

    protected static final int BUTTON_WIDTH = 190,
            NOTE_SIZE = 200, H_GAP = 10;

    private Board currentBoard = null;

    private int columnsCount = 1;

    private RemoteModelAccess remoteModelAccess;

    @FXML
    private GridPane boardGrid, noteGrid;

    @FXML
    private SplitPane scriptSplitPane;

    @FXML
    private Button newNoteButton, newBoardButton, newChecklistButton;

    @FXML
    private TextField boardTitle, boardDescription, boardName;

    @FXML
    private VBox noteScreen;

    @FXML
    private ScrollPane noteScrollPane;

    @FXML
    private Text username, exampleMail;

    private User user = Globals.user;

    private List<BoardElementController> boardElementControllers = new ArrayList<>();

    @FXML
    private void initialize() {
        scriptSplitPane.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        remoteModelAccess = new RemoteModelAccess();
        user = Globals.user;
        username.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        exampleMail.setText(user.getUsername().toLowerCase() + "@example.com");
        noteScrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            int oldColumnsCount = columnsCount;
            columnsCount = (int) ((newVal.doubleValue() - 60) / (NOTE_SIZE + H_GAP));
            if (columnsCount == 0) {
                columnsCount = 1;
            }
            if (oldColumnsCount != columnsCount) {
                if (currentBoard != null) {
                    drawBoardElementControllers();
                }
            }
        });
        try {
            loadBoardButtons(user.getBoards());
        } catch (IOException e) {
            e.printStackTrace();
        }
        createWindowSizeListener();
    }

    @FXML
    private void onBoardButtonClick(ActionEvent ae) throws IOException {
        if (currentBoard != null) {
            saveBoard(currentBoard);
        }
        Board selectedBoard = user.getBoards().stream()
                .filter(board -> board.getBoardName().equals(((Button) ae.getSource()).getText()))
                .findFirst()
                .get();
        noteScreen.setVisible(true);
        boardTitle.setText(selectedBoard.getBoardName());
        boardDescription.setText(selectedBoard.getBoardDescription());
        currentBoard = selectedBoard;
        boardElementControllers.clear();
        currentBoard.getChecklists().stream()
                .forEach(boardElement -> boardElementControllers.add(new BoardElementController(boardElement, this)));
        currentBoard.getNotes().stream()
                .forEach(boardElement -> boardElementControllers.add(new BoardElementController(boardElement, this)));
        drawBoardElementControllers();
        update();
    }

    @FXML
    private void handleBoardNameEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER) && checkBoardName(boardName)) {
            createBoard();
        }
    }

    @FXML
    private void editBoardTitle(KeyEvent event) throws IOException {
        Button button = (Button) boardGrid.getChildren().get(user.getBoards().indexOf(currentBoard) * 2);
        TextField field = (TextField) event.getSource();
        if (checkBoardName(field)) {
            button.setText(field.getText());
            currentBoard.setBoardName(field.getText());
        }
    }

    @FXML
    private void editBoardDescription(KeyEvent event) throws IOException {
        currentBoard.setBoardDescription(((TextField) event.getSource()).getText());
    }

    private void saveBoard(Board board) {
        remoteModelAccess.putBoard(board, user.getUsername(), user.getPassword());
    }

    @FXML
    public void createBoard() {
        try {
            remoteModelAccess.createBoard(boardName.getText(), user.getUsername(), user.getPassword());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }

        user.addBoard(boardName.getText());
        createBoardButton(boardName.getText(), user.getBoards().size() - 1);
        boardName.clear();
        newBoardButtonEnable();
    }

    @FXML
    private void createNote() {
        Note note = new Note();
        currentBoard.addNote(note);
        boardElementControllers.add(new BoardElementController(note, this));
        drawBoardElementControllers();
        update();
    }

    @FXML
    private void createChecklist() {
        Checklist checklist = new Checklist();
        currentBoard.addChecklist(checklist);
        boardElementControllers.add(new BoardElementController(checklist, this));
        drawBoardElementControllers();
        update();
    }

    @FXML
    private void newBoardNameEdit() {
        newBoardButtonEnable();
    }

    @FXML
    private void handleLogoutButton(ActionEvent ae) throws IOException {
        if (currentBoard != null) {
            saveBoard(currentBoard);
        }
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
        newBoardButton.setDisable(!checkBoardName(boardName));
    }

    private void createWindowSizeListener() {
        scriptSplitPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowWidth = (double) newVal;
        });
        scriptSplitPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowHeight = (double) newVal;
        });
    }

    public void loadBoardButtons(List<Board> boards) throws IOException {
        boardGrid.getChildren().clear();
        IntStream.range(0, boards.size()).forEach(i -> {
            createBoardButton(boards.get(i).getBoardName(), i);
        });
        boardGrid.setStyle("-fx-background-color: transparent");
    }

    private void createBoardButton(String boardName, int index) {
        Button button = new Button(boardName);
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
        button.setId(boardName);
        button.setMaxWidth(BUTTON_WIDTH);
        MFXButton deleteButton = new MFXButton("X");
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setStyle("-fx-background-color: transparent; -fx-border-color: black;");
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
        Button boardButton = (Button) boardGrid.getChildren().get(index * 2);
        String boardName = boardButton.getText();
        try {
            remoteModelAccess.removeBoard(boardName, user.getUsername(), user.getPassword());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        user.removeBoard(boardName);
        loadBoardButtons(user.getBoards());
        update();
    }

    private void update() {
        if (!(currentBoard == null)) {
            newNoteButton.setDisable(boardElementControllers.size() == Board.MAX_ELEMENTS);
            newChecklistButton.setDisable(boardElementControllers.size() == Board.MAX_ELEMENTS);
            noteScreen.setVisible(user.getBoards().contains(currentBoard));
        }
    }

    private Boolean checkBoardName(TextField textField) {
        return !(textField.getText().isBlank() || user.getBoards().stream().map(board -> (board.getBoardName()))
                .collect(Collectors.toList()).contains(textField.getText()));
    }

    public void drawBoardElementControllers() {
        noteGrid.getChildren().clear();
        noteGrid.getColumnConstraints().clear();
        noteGrid.getRowConstraints().clear();
        noteGrid.setHgap(10);

        IntStream.range(0, columnsCount).forEach(i -> {
            ColumnConstraints column = new ColumnConstraints();
            noteGrid.getColumnConstraints().add(column);
            VBox columnVBox = new VBox();
            columnVBox.setSpacing(10);
            noteGrid.add(columnVBox, i, 0);
        });

        boardElementControllers.stream().forEach(bec -> {
            VBox columnVBox = (VBox) noteGrid.getChildren()
                    .get(boardElementControllers.stream().map(c -> c.getBoardElement()).toList()
                            .indexOf(bec.getBoardElement()) % columnsCount);
            columnVBox.getChildren().add(bec.generateControl());
        });
    }

    public void removeBoardElement(BoardElementController boardElementController) {
        boardElementControllers.remove(boardElementController);
        currentBoard.clearCheckLists();
        currentBoard.clearNotes();
        boardElementControllers.stream().map(c -> c.getBoardElement()).forEach(element -> {
            if (element instanceof Note) {
                Note note = (Note) element;
                currentBoard.addNote(note);
            } else if (element instanceof Checklist) {
                Checklist checklist = (Checklist) element;
                currentBoard.addChecklist(checklist);
            }
        });
        drawBoardElementControllers();
    }

    protected Button getNewBoardButton() {
        return newBoardButton;
    }

}