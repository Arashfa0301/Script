package ui;

import core.main.Board;
import core.main.Checklist;
import core.main.Note;
import core.main.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScriptController {

    protected static final int BUTTON_WIDTH = 190,
            NOTE_SIZE = 200, H_GAP = 10;

    private Board currentBoard = null;

    private String oldBoardName = "";

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

    private WindowManager windowManager = new WindowManager();

    @FXML
    private void initialize() {
        scriptSplitPane.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        remoteModelAccess = new RemoteModelAccess();
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
        oldBoardName = selectedBoard.getBoardName();
        boardDescription.setText(selectedBoard.getBoardDescription());
        currentBoard = selectedBoard;
        boardElementControllers.clear();
        currentBoard.getChecklists().stream()
                .forEach(boardElement -> boardElementControllers.add(new BoardElementController(boardElement, this)));
        currentBoard.getNotes().stream()
                .forEach(boardElement -> boardElementControllers.add(new BoardElementController(boardElement, this)));
        drawBoardElementControllers();
        updateScreen();
    }

    @FXML
    private void handleBoardNameEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            newBoardButton.fire();
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
        try {
            if (!board.getBoardName().equals(oldBoardName)) {
                remoteModelAccess.renameBoard(oldBoardName, board.getBoardName(), user.getUsername(),
                        user.getPassword());
            }
            remoteModelAccess.putBoard(board, user.getUsername(), user.getPassword());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            board.setBoardName(oldBoardName);
            remoteModelAccess.putBoard(board, user.getUsername(), user.getPassword());
        }
    }

    @FXML
    private void createBoard() {
        System.out.println(user.getBoards().size());
        remoteModelAccess.createBoard(boardName.getText(), user.getUsername(), user.getPassword());
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
        updateScreen();
    }

    @FXML
    private void createChecklist() {
        Checklist checklist = new Checklist();
        currentBoard.addChecklist(checklist);
        boardElementControllers.add(new BoardElementController(checklist, this));
        drawBoardElementControllers();
        updateScreen();
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
        windowManager.switchScreen(ae, "Login.fxml");
    }

    private void newBoardButtonEnable() {
        newBoardButton.setDisable(!checkBoardName(boardName) || user.getBoards().size() >= User.MAX_BOARD_COUNT);
    }

    private void createWindowSizeListener() {
        scriptSplitPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowWidth = (double) newVal;
        });
        scriptSplitPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowHeight = (double) newVal;
        });
    }

    private void loadBoardButtons(List<Board> boards) throws IOException {
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
        String boardName = ((Button) boardGrid.getChildren().get(GridPane.getRowIndex((Button) ae.getSource()) * 2))
                .getText();
        try {
            remoteModelAccess.removeBoard(boardName, user.getUsername(), user.getPassword());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        user.removeBoard(boardName);
        loadBoardButtons(user.getBoards());
        newBoardButtonEnable();
    }

    private void updateScreen() {
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
                currentBoard.addNote((Note) element);
            } else if (element instanceof Checklist) {
                currentBoard.addChecklist((Checklist) element);
            }
        });
        drawBoardElementControllers();
        updateScreen();
    }

    protected Button getNewBoardButton() {
        return newBoardButton;
    }

}