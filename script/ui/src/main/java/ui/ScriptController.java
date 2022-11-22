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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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
    private VBox boardScreen;

    @FXML
    private ScrollPane noteScrollPane;

    @FXML
    private Text username, fullname;

    private User user = Globals.user;

    private List<BoardElementController> boardElementControllers = new ArrayList<>();

    private WindowManager windowManager = new WindowManager();

    /**
     * Initializes the controller every time The sceen is changed to Script.fxml.
     *
     * @see ScriptController#loadBoardButtons(List)
     * @see ScriptController#drawBoardElementControllers()
     */
    @FXML
    private void initialize() {
        Globals.scriptController = this;
        scriptSplitPane.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        remoteModelAccess = new RemoteModelAccess();
        fullname.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        username.setText(user.getUsername().toLowerCase());
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

        loadBoardButtons();

        // Creates window size listeners
        scriptSplitPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowWidth = (double) newVal;
        });
        scriptSplitPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowHeight = (double) newVal;
        });
    }

    /**
     * Aplies visual and implementational changes to the app, when the current board
     * is changed to a new board.
     *
     * @param ae an action event intance for catching the clicked on board button
     * @see ScriptController#saveBoard(Board)
     * @see ScriptController#drawBoardElementControllers()
     * @see ScriptController#updateScreen()
     */
    @FXML
    private void onBoardButtonClick(ActionEvent event) throws IOException {
        if (currentBoard != null) {
            saveBoard(currentBoard);
        }
        List<Button> boardButtons = boardGrid.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .collect(Collectors.toList());
        boardButtons.forEach(button -> button.setStyle(
                "-fx-background-color: transparent; -fx-alignment: center-left; -fx-font-size: 13px; -fx-font-family: \"Poppins Medium\"; -fx-text-fill: black;"));
        Board selectedBoard = user.getBoards().stream()
                .filter(board -> board.getName().equals(((Button) event.getSource()).getText()))
                .findFirst()
                .get();
        ((Button) event.getSource()).setStyle(
                "-fx-background-color: black; -fx-alignment: center-left; -fx-font-size: 13px; -fx-font-family: \"Poppins SemiBold\"; -fx-text-fill: white;");
        boardScreen.setVisible(true);
        boardTitle.setText(selectedBoard.getName());
        oldBoardName = selectedBoard.getName();
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

    /**
     * Executes the "newBoardButton" button's onAction function when user presses
     * Enter. More details at @see
     *
     * @param a key event instance that catches pressing Enter
     * @see ScriptController#createBoard()
     */
    @FXML
    private void handleBoardNameEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            newBoardButton.fire();
        }
    }

    /**
     * Changes the title of the current board and also updates the title on the
     * board's button, when the title text field is editet.
     *
     * @param event a key event instance that catches the board TextField
     * @see ScriptController#checkBoardName(String)
     */
    @FXML
    private void editBoardTitle(KeyEvent event) throws IOException {
        TextField boardTextField = (TextField) event.getSource();
        if (checkBoardName(boardTextField.getText())) {
            ((Button) boardGrid.getChildren().get(user.getBoards().indexOf(currentBoard) * 2))
                    .setText(boardTextField.getText());
            currentBoard.setName(boardTextField.getText());
        }
    }

    /**
     * Changes the description of the current board when the description text field
     * is editet.
     *
     * @param event a key event instance that catches the description TextField
     */
    @FXML
    private void editBoardDescription(KeyEvent event) throws IOException {
        currentBoard.setBoardDescription(((TextField) event.getSource()).getText());
    }

    /**
     * Creates a board when {@link ScriptController#newBoardButton} is clicked on,
     * and gives it to the current user.
     * The name of this board is retrieved from boardNameField.
     *
     *
     * @see ScriptController#createBoardButton(String, int)
     * @see ScriptController#newBoardButtonEnable()
     *
     */
    @FXML
    private void createBoard() {
        remoteModelAccess.createBoard(boardName.getText(), user.getUsername(), user.getPassword());
        user.addBoard(boardName.getText());
        createBoardButton(boardName.getText(), user.getBoards().size() - 1);
        boardName.clear();
        newBoardButtonEnable();
    }

    /**
     * Creates a new Note for the current board of the current user, and updates the
     * screen.
     *
     * @see ScriptController#drawBoardElementControllers()
     * @see ScriptController#updateScreen()
     */
    @FXML
    private void createNote() {
        Note note = new Note();
        currentBoard.addNote(note);
        boardElementControllers.add(new BoardElementController(note, this));
        drawBoardElementControllers();
        updateScreen();
    }

    /**
     * Creates a new CheckList for the current board of the current user, and
     * updates the screen.
     *
     * @see ScriptController#drawBoardElementControllers()
     * @see ScriptController#updateScreen()
     */
    @FXML
    private void createChecklist() {
        Checklist checklist = new Checklist();
        currentBoard.addChecklist(checklist);
        boardElementControllers.add(new BoardElementController(checklist, this));
        drawBoardElementControllers();
        updateScreen();
    }

    /**
     * Executes {@link ScriptController#newBoardButtonEnable} every time
     * boardTitleField is edited and sets a new title for the current board.
     *
     * @see ScriptController#newBoardButtonEnable()
     */
    @FXML
    private void newBoardNameEdit() {
        newBoardButtonEnable();
    }

    /**
     * Saves The current board, logs the user out and sends them back to the login
     * creen.
     *
     * @param event an action event that is passed to
     *              {@link WindowManager#switchScreen(ActionEvent, String) } method
     *              that tells the WindowManager class which screen to change to
     * @throws IOException when attempting to access a file that does not exist at
     *                     the specified location
     * @see ScriptController#saveBoard(Board)
     * @see WindowManager#switchScreen(ActionEvent, String)
     */
    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException {
        if (currentBoard != null) {
            saveBoard(currentBoard);
        }
        windowManager.switchScreen(event, "Login.fxml");
        Globals.scriptController = null;
    }

    /**
     * Deletes a board when this board's delete button is clicked on and updates the
     * user accordingly.
     *
     * @param event an action event that catches which delete buttom has been
     *              pressed
     * @throws IOException when attempting to access a file that does not exist at
     *                     the specified location
     * @see ScriptController#loadBoardButtons(List)
     * @see ScriptController#newBoardButtonEnable()
     */
    @FXML
    private void deleteBoard(ActionEvent event) throws IOException {
        String boardName = ((Button) boardGrid.getChildren().get(GridPane.getRowIndex((Button) event.getSource()) * 2))
                .getText();
        if (currentBoard.getName().equals(boardName)) {
            currentBoard = null;
            updateScreen();
        }
        try {
            remoteModelAccess.removeBoard(boardName, user.getUsername(), user.getPassword());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        user.removeBoard(boardName);
        loadBoardButtons();
        newBoardButtonEnable();
    }

    /**
     * Saves the input board via RemoteModelAccess class.
     *
     * @param board a board object to save
     * @exception IllegalArgumentException if the input argument boards object is
     *                                     invalid
     * @see RemoteModelAccess#putBoard(Board, String, String)
     */
    protected void saveBoard(Board board) {
        try {
            if (!board.getName().equals(oldBoardName)) {
                remoteModelAccess.renameBoard(oldBoardName, board.getName(), user.getUsername(),
                        user.getPassword());
            }
            remoteModelAccess.putBoard(board, user.getUsername(), user.getPassword());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            board.setName(oldBoardName);
            remoteModelAccess.putBoard(board, user.getUsername(), user.getPassword());
        }
    }

    /**
     * Make the newBoardButton visible when predicates are satisfied. These
     * predicates check whether if the name of the new board is valid
     *
     */
    private void newBoardButtonEnable() {
        newBoardButton
                .setDisable(
                        !checkBoardName(boardName.getText()) || user.getBoards().size() >= User.MAX_BOARD_COUNT);
    }

    /**
     * Loads the buttons for each of user's buttons on the screen.
     *
     * @see ScriptController#createBoardButton(String, int)
     */
    private void loadBoardButtons() {
        boardGrid.getChildren().clear();
        IntStream.range(0, user.getBoards().size()).forEach(i -> {
            createBoardButton(user.getBoards().get(i).getName(), i);
        });
        boardGrid.setStyle("-fx-background-color: transparent");
    }

    /**
     * Creates a board button that shows on the screen.
     *
     * @param boardName a string, specifying the name of the newly created board
     * @param index     an integer, specifying the index at which the button will be
     *                  created
     *
     */
    private void createBoardButton(String boardName, int index) {
        Button button = new Button(boardName);
        button.wrapTextProperty().setValue(true);
        button.setStyle(
                "-fx-background-color: transparent; -fx-alignment: center-left; -fx-font-size: 13px; -fx-font-family: \"Poppins Medium\"; -fx-text-fill: black;");
        button.setPadding(new Insets(0, 6, 0, 6));
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
        MFXButton deleteButton = new MFXButton("");
        deleteButton.setCursor(Cursor.HAND);
        Cross cross = new Cross(Color.BLACK, 1.0);
        cross.setTranslateY(4);
        deleteButton.setGraphic(cross);
        deleteButton.setStyle(
                "-fx-background-color: transparent; -fx-alignment: center-left;");
        deleteButton.setOnAction((event) -> {
            try {
                deleteBoard(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        boardGrid.addRow(index, button, deleteButton);
    }

    /**
     * Updates the BoardScreen on board change.
     */
    private void updateScreen() {
        if (!(currentBoard == null)) {
            newNoteButton.setDisable(boardElementControllers.size() == Board.MAX_ELEMENTS);
            newChecklistButton.setDisable(boardElementControllers.size() == Board.MAX_ELEMENTS);
            boardScreen.setVisible(user.getBoards().contains(currentBoard));
        } else {
            boardScreen.setVisible(false);
        }
    }

    /**
     * Checks whether if the input string is valid.
     *
     * @param text a string to be checked
     * @return a boolean implying whether the ckecks were passed or not
     */
    private Boolean checkBoardName(String text) {
        return !(text.isBlank() || user.getBoards().stream().map(board -> (board.getName()))
                .collect(Collectors.toList()).contains(text) || !Pattern.matches("^[A-Za-z0-9_.]+$", text));
    }

    /**
     * Draws all the BoardElement objects of the current board on the boardScreen.
     * This function is also used as listener fire function for updating the app
     * when each BoardElementController is updated.
     */
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

    /**
     * Removes a boardElementController object from the list of
     * boardElementControllers when the delete button is pressed and updates the
     * boardScreen.
     *
     * @param boardElementController a boardElementController to be removed.
     *
     * @see ScriptController#drawBoardElementControllers()
     * @see ScriptController#updateScreen()
     */
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

    /**
     * Returns newBoardButton. This function is a helper function for test classes.
     *
     * @return a Button fxml object
     */
    protected Button getNewBoardButton() {
        return newBoardButton;
    }

    /**
     * Returns currentBoard. This function is a helper function for test classes.
     *
     * @return a Board object
     */
    protected Board getCurrentBoard() {
        return currentBoard;
    }

    /**
     * Returns boardElementControllers. This function is a helper function for test
     * classes.
     *
     * @return an arraylist of BoardElementControllers
     */
    protected List<BoardElementController> getBoardElementControllers() {
        return boardElementControllers;
    }

}