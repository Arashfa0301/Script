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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private DataHandler datahandler;

    @FXML
    private GridPane boardGrid, noteGrid;

    @FXML
    private SplitPane scriptSplitPane;

    @FXML
    private Button newNoteButton, newBoardButton, newChecklistButton;

    @FXML
    private AnchorPane boardAnchor;

    @FXML
    private TextField boardTitle, boardDescription, boardName;

    @FXML
    private VBox noteScreen;

    @FXML
    private ScrollPane noteScrollPane;

    @FXML
    private Text username, exampleMail;

    private User user;

    private List<BoardElement> currentBoardElements = new ArrayList<>();

    @FXML
    private void initialize() {
        scriptSplitPane.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        datahandler = new DataHandler();
        user = Globals.user;
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
                    loadNotes(currentBoard);
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
        // finds the first (and only) board with a name that equals the text on the
        // clicked button
        Board selectedBoard = boards.stream()
                .filter(board -> board.getBoardName().equals(((Button) ae.getSource()).getText()))
                .findFirst()
                .get();
        noteScreen.setVisible(true);
        setTitleAndDescription(selectedBoard);
        currentBoard = selectedBoard;
        currentBoardElements.clear();
        currentBoard.getChecklists().stream().forEach(c -> currentBoardElements.add(c));
        currentBoard.getNotes().stream().forEach(n -> currentBoardElements.add(n));
        loadNotes(selectedBoard);
        // sets the currentBoard variable to the currently selected board
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
        if (event.getSource().getClass() == TextArea.class) {
            TextArea area = (TextArea) event.getSource();
            VBox pane = (VBox) area.getParent();
            int row = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
            int column = GridPane.getColumnIndex(pane.getParent());
            ((Note) (currentBoardElements.get(columnsCount * row + column))).setText(area.getText());
        } else if (event.getSource().getClass() == TextField.class) {
            TextField field = (TextField) event.getSource();
            VBox pane = (VBox) field.getParent().getParent();
            int row = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
            int column = GridPane.getColumnIndex(pane.getParent());
            currentBoardElements.get(columnsCount * row + column).setTitle(field.getText());
        }
        save();
    }

    @FXML
    private void onChecklistTitleEdit(KeyEvent event) throws IOException {
        TextField field = (TextField) event.getSource();
        VBox pane = (VBox) field.getParent().getParent();
        int row = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
        int column = GridPane.getColumnIndex(pane.getParent());
        currentBoardElements.get(columnsCount * row + column).setTitle(field.getText());
        save();
    }

    @FXML
    private void onChecklistElementEdit(KeyEvent event) throws IOException {
        TextField field = (TextField) event.getSource();
        HBox hbox = (HBox) field.getParent();
        VBox pane = (VBox) field.getParent().getParent();
        int row = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
        int column = GridPane.getColumnIndex(pane.getParent());
        Checklist c = (Checklist) currentBoardElements.get(columnsCount * row + column);
        c.getList().set(pane.getChildren().indexOf(hbox) - 1, field.getText());
        save();
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
        TextField field = (TextField) event.getSource();
        currentBoard.setBoardDescription(field.getText());
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
        Note note = new Note("", "");
        currentBoard.addNote(note);
        currentBoardElements.add(note);
        loadNotes(currentBoard);
        update();
        save();
    }

    @FXML
    private void createChecklist() {
        Checklist checklist = new Checklist("", new ArrayList<String>());
        currentBoard.addchecklist(checklist);
        currentBoardElements.add(checklist);
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
    private void handleChecklistEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            TextField textField = (TextField) ke.getSource();
            VBox vbox = (VBox) textField.getParent().getParent();
            HBox hbox = new HBox();
            Button checkbutton = new Button();
            hbox.getChildren().add(checkbutton);
            TextField newTextField = new TextField();
            hbox.getChildren().add(newTextField);
            vbox.getChildren().add(hbox);
            newTextField.setOnKeyPressed(event -> {
                handleChecklistEnter(event);
            });
            newTextField.setOnKeyReleased((event) -> {
                try {
                    onChecklistElementEdit(event);
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            });
            newTextField.setPromptText("Add a list element");
            int row = vbox.getParent().getChildrenUnmodifiable().indexOf(vbox);
            int column = GridPane.getColumnIndex(vbox.getParent());
            Checklist checklist = (Checklist) currentBoardElements.get(columnsCount * row + column);
            checklist.addListElement("");
        }
    }

    @FXML
    private void deleteNote(ActionEvent ae) {
        Button button = (Button) ae.getSource();
        VBox pane = (VBox) button.getParent().getParent();
        int row = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
        int column = GridPane.getColumnIndex(pane.getParent());
        currentBoardElements.remove(columnsCount * row + column);
        loadNotes(currentBoard);
        update();
        save();
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
        // add button that is used to delete the board button that was just made
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

    private void setTitleAndDescription(Board board) {
        boardTitle.setText(board.getBoardName());
        boardDescription.setText(board.getBoardDescription());
    }

    private void loadNote(Board board, BoardElement element) {
        Note note = (Note) element;
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
        VBox notePane = new VBox();
        notePane.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");
        notePane.getChildren().add(topPane);
        notePane.setPrefSize(NOTE_SIZE, 230);
        notePane.setMaxSize(NOTE_SIZE, 230);
        notePane.getChildren().add(text);
        topPane.getChildren().add(title);
        Button deleteButton = new Button("X");
        deleteButton.setOnAction((event) -> deleteNote(event));
        // on notePane hover
        // set width and height of deleteButton to 20
        // set opacity of deleteButton to 1
        // make deleteButton a circle
        deleteButton.setShape(new Circle(10));
        deleteButton.setTranslateX(25);
        deleteButton.setTranslateY(-7);
        deleteButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setVisible(false);
        topPane.getChildren().add(deleteButton);
        notePane.setOnMouseEntered((event) -> {
            // add dropshadow
            deleteButton.setVisible(true);
            notePane.setEffect(new DropShadow(12, new Color(0, 0, 0, 0.15)));
        });
        notePane.setOnMouseExited((event) -> {
            // remove dropshadow
            deleteButton.setVisible(false);
            notePane.setEffect(null);
        });
        VBox columnVBox = (VBox) noteGrid.getChildren().get(currentBoardElements.indexOf(element) % columnsCount);
        columnVBox.getChildren().add(notePane);
    }

    private void loadChecklist(Board board, BoardElement element) {
        Checklist checklist = (Checklist) element;
        TextField title = new TextField(checklist.getTitle());
        title.setStyle("-fx-font-weight: bold");
        title.setOnKeyReleased((event) -> {
            try {
                onChecklistTitleEdit(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        title.setPromptText("Title");
        List<TextField> listElements = new ArrayList<>();
        checklist.getList().stream().forEach(e -> {
            TextField t = new TextField(e);
            listElements.add(t);
            t.setOnKeyPressed(event -> {
                handleChecklistEnter(event);
            });
            t.setOnKeyReleased((event) -> {
                try {
                    onChecklistElementEdit(event);
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            });
            t.setPromptText("Add a list element");
        });
        if (checklist.isEmpty()) {
            TextField t = new TextField();
            listElements.add(t);
            t.setOnKeyPressed(event -> {
                handleChecklistEnter(event);
            });
            checklist.addListElement("");
            t.setOnKeyReleased((event) -> {
                try {
                    onChecklistElementEdit(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.setPromptText("Add a list element");
        }
        HBox topPane = new HBox();
        VBox notePane = new VBox();
        notePane.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");
        notePane.getChildren().add(topPane);
        notePane.setPrefSize(NOTE_SIZE, 230);
        notePane.setMaxSize(NOTE_SIZE, 230);
        topPane.getChildren().add(title);
        Button deleteButton = new Button("X");
        deleteButton.setOnAction((event) -> deleteNote(event));
        deleteButton.setShape(new Circle(10));
        deleteButton.setTranslateX(25);
        deleteButton.setTranslateY(-7);
        deleteButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setVisible(false);
        topPane.getChildren().add(deleteButton);
        notePane.setOnMouseEntered((event) -> {
            // add dropshadow
            deleteButton.setVisible(true);
            notePane.setEffect(new DropShadow(12, new Color(0, 0, 0, 0.15)));
        });
        notePane.setOnMouseExited((event) -> {
            // remove dropshadow
            deleteButton.setVisible(false);
            notePane.setEffect(null);
        });
        listElements.forEach(e -> {
            HBox hbox = new HBox();
            Button checkbutton = new Button();
            hbox.getChildren().add(checkbutton);
            hbox.getChildren().add(e);
            notePane.getChildren().add(hbox);
        });
        VBox columnVBox = (VBox) noteGrid.getChildren().get(currentBoardElements.indexOf(element) % columnsCount);
        columnVBox.getChildren().add(notePane);
    }

    private void loadNotes(Board board) {
        noteGrid.getChildren().clear();
        // remove notegrid columns and rows
        noteGrid.getColumnConstraints().clear();
        noteGrid.getRowConstraints().clear();
        noteGrid.setHgap(10);
        // add columns and rows to notegrid
        IntStream.range(0, columnsCount).forEach(i -> {
            ColumnConstraints column = new ColumnConstraints();
            noteGrid.getColumnConstraints().add(column);
            VBox columnVBox = new VBox();
            columnVBox.setSpacing(10);
            noteGrid.add(columnVBox, i, 0);
        });
        currentBoardElements.stream().forEach(element -> {
            if (element instanceof Note) {
                loadNote(board, element);
            } else if (element instanceof Checklist) {
                loadChecklist(board, element);
            }
        });
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

    public List<Board> getBoards() {
        return boards;
    }

    public Button getNewBoardButton() {
        return newBoardButton;
    }

    public void setBoardName(String boardName) {
        this.boardName.setText(boardName);
    }

}