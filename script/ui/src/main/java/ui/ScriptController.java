package ui;

import core.main.Board;
import core.main.BoardElement;
import core.main.Checklist;
import core.main.Note;
import core.main.User;
import data.DataHandler;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScriptController {

    private static final int BUTTON_WIDTH = 190, NOTE_SIZE = 200;

    private static final int H_GAP = 10;

    private Board currentBoard = null;

    private int columnsCount = 1;

    private List<Board> boards;

    private DataHandler datahandler = new DataHandler();

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

    private List<BoardElement> currentBoardElements = new ArrayList<>();

    private List<BoardElementController> boardElementControllers = new ArrayList<>();

    @FXML
    private void initialize() {

        scriptSplitPane.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        username.setText(user.getName());
        exampleMail.setText(user.getName().toLowerCase() + "@example.com");
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

        Board selectedBoard = boards.stream()
                .filter(board -> board.getBoardName().equals(((Button) ae.getSource()).getText()))
                .findFirst()
                .get();
        noteScreen.setVisible(true);
        boardTitle.setText(selectedBoard.getBoardName());
        boardDescription.setText(selectedBoard.getBoardDescription());
        currentBoard = selectedBoard;
        currentBoardElements.clear();
        currentBoard.getChecklists().stream().forEach(c -> currentBoardElements.add(c));
        currentBoard.getNotes().stream().forEach(n -> currentBoardElements.add(n));

        drawBoardElementControllers();
        update();
    }

    @FXML
    private void handleBoardNameEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER) && checkNewBoardName()) {
            createBoard();
        }
    }

    @FXML
    private void editBoardTitle(KeyEvent event) throws IOException {
        Button button = (Button) boardGrid.getChildren().get(boards.indexOf(currentBoard) * 2);
        TextField field = (TextField) event.getSource();
        button.setText(field.getText());
        currentBoard.setBoardName(field.getText());
        save();
    }

    @FXML
    private void editBoardDescription(KeyEvent event) throws IOException {
        currentBoard.setBoardDescription(((TextField) event.getSource()).getText());
        save();
    }

    private void save() {
        if (!(currentBoard == null)) {
            currentBoard.getChecklists().clear();
            currentBoard.getNotes().clear();
            currentBoardElements.stream().forEach(element -> {
                if (element instanceof Note) {
                    Note note = (Note) element;
                    currentBoard.addNote(note);
                } else if (element instanceof Checklist) {
                    Checklist checklist = (Checklist) element;
                    currentBoard.addchecklist(checklist);
                }
            });
        }
        user.setBoards(boards);
        datahandler.write(user);
    }

    @FXML
    public void createBoard() {
        Board newBoard = new Board(boardName.getText(), "");
        boards.add(newBoard);
        createBoardButton(newBoard, boards.size() - 1);
        boardName.clear();
        newBoardButtonEnable();
        save();
    }

    @FXML
    private void createNote() {
        Note note = new Note();
        currentBoard.addNote(note);
        currentBoardElements.add(note);
        drawBoardElementControllers();
        update();
        save();
    }

    @FXML
    private void createChecklist() {
        Checklist checklist = new Checklist("", new ArrayList<String>());
        currentBoard.addchecklist(checklist);
        currentBoardElements.add(checklist);
        drawBoardElementControllers();
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

    public void loadBoardButtons(List<Board> boards) throws IOException {
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
        button.setId(board.getBoardName());
        button.setMaxWidth(BUTTON_WIDTH);
        Button deleteButton = new Button("X");
        deleteButton.setShape(new Circle(10));
        deleteButton.setCursor(Cursor.HAND);
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

    private void update() {
        if (!(currentBoard == null)) {
            newNoteButton.setDisable(currentBoardElements.size() == Board.MAX_ELEMENTS ? true : false);
            newChecklistButton.setDisable(currentBoardElements.size() == Board.MAX_ELEMENTS ? true : false);
            noteScreen.setVisible(!boards.contains(currentBoard) ? false : true);
        }
    }

    private Boolean checkNewBoardName() {
        return !(boardName.getText().isBlank() || boards.stream().map(board -> (board.getBoardName()))
                .collect(Collectors.toList()).contains(boardName.getText()));
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

        boardElementControllers.clear();
        currentBoardElements.stream().forEach(el -> {
            boardElementControllers.add(new BoardElementController(el, this));
        });
        boardElementControllers.stream().forEach(bec -> {
            VBox columnVBox = (VBox) noteGrid.getChildren()
                    .get(currentBoardElements.indexOf(bec.getBoardElement()) % columnsCount);
            columnVBox.getChildren().add(bec.generateControl());
        });
    }

    public void updateCurrentBoardElements() {
        currentBoardElements.clear();
        boardElementControllers.stream().forEach(cntr -> currentBoardElements.add(cntr.getBoardElement()));
        save();
    }

    public void removeBoardElement(BoardElementController boardElementController) {
        boardElementControllers.remove(boardElementController);
        currentBoardElements.remove(boardElementController.getBoardElement());
        drawBoardElementControllers();
        save();
    }

}