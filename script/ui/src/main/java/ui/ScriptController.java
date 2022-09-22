package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import core.main.Board;
import core.main.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ScriptController {

    private final int BUTTON_WIDTH = 190, NOTE_SIZE = 200;

    private Board currentBoard;

    private List<Board> boards;
    
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

    private List<Board> testBoards = Arrays.asList(new Board("1", "a"),new Board("2", "b"),new Board("3", "c"),new Board("4", "d"));
    private List<Note> testNotes = Arrays.asList(new Note("title","Lorem ipsum dolor sit amet. Sit reprehenderit veritatis quo placeat quis ea voluptatibus sequi. In ducimus exercitationem non voluptatem molestias et cumque sunt aut sequi similique! Est voluptas aliquam et mollitia quia est voluptatum dolorem id nobis Quis qui distinctio veritatis. Qui velit nihil et animi veritatis qui neque quidem et perferendis velit."));

    @FXML
    private void initialize() {
        // boards = getBoardsFromFile();
        boards = new ArrayList<>(testBoards);
        testBoards.get(0).addNote(testNotes.get(0));
        testBoards.get(0).addNote(testNotes.get(0));
        testBoards.get(0).addNote(testNotes.get(0));
        testBoards.get(0).addNote(testNotes.get(0));
        testBoards.get(0).addNote(testNotes.get(0));
        testBoards.get(0).addNote(testNotes.get(0));
        try {
            // createBoardButtons(getBoardsFromFile());
            loadBoardButtons(boards);
        } catch (IOException e) {
            e.printStackTrace(); }
    }

    @FXML
    private void onBoardButtonClick(ActionEvent ae) throws IOException {
        // noteGrid.getChildren().clear();
        Button clickedButton = (Button)ae.getSource();
        // Board selectedBoard = getBoardsFromFile().stream().filter(board -> 
        //     board.getBoardName().equals(clickedButton.getText())).findFirst().get();
        Board selectedBoard = boards.stream().filter(board -> 
            board.getBoardName().equals(clickedButton.getText())).findFirst().get();
        setTitleAndDescription(selectedBoard);
        loadNotes(selectedBoard);
        currentBoard = selectedBoard;
        noteScreen.setVisible(true);
        update();
    }

    @FXML private void onNoteEdit(KeyEvent event) throws IOException {
        editNote(event);
    }

    @FXML private void editBoardTitle(KeyEvent event) throws IOException {
        Button button = (Button)boardGrid.getChildren().get(boards.indexOf(currentBoard));
        TextField field = (TextField)event.getSource();
        button.setText(field.getText());
        currentBoard.setBoardName(field.getText());
    }

    @FXML private void editBoardDescription(KeyEvent event) throws IOException {
        TextField field = (TextField)event.getSource();
        currentBoard.setBoardDescription(field.getText());
    }

    private void editNote(KeyEvent event) {
        if (event.getSource().getClass() == TextArea.class) {
            TextArea area = (TextArea)event.getSource();
            GridPane pane = (GridPane)area.getParent();
            int row = GridPane.getRowIndex(pane);
            int column = GridPane.getColumnIndex(pane);
            currentBoard.getNotes().get(2*row+column).setText(area.getText());
        }
        else if (event.getSource().getClass() == TextField.class) {
            TextField field = (TextField)event.getSource();
            GridPane pane = (GridPane)field.getParent();
            int row = GridPane.getRowIndex(pane);
            int column = GridPane.getColumnIndex(pane);
            currentBoard.getNotes().get(2*row+column).setTitle(field.getText());
        }
    }

    @FXML
    private void createBoard() {
        Board newBoard = new Board(boardName.getText());
        boards.add(newBoard);
        createBoardButton(newBoard, boards.size()-1);
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
    
    private void loadBoardButtons(List<Board> boards) throws IOException {
        IntStream.range(0, boards.size()).forEach(i -> {
            createBoardButton(boards.get(i), i);
        });
        ScrollPane scrollPane = new ScrollPane(boardGrid);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        boardAnchor.getChildren().add(scrollPane);
    }

    private void createBoardButton(Board board, int index) {
        Button button = new Button(board.getBoardName());
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
        button.setMaxWidth(BUTTON_WIDTH);
        boardGrid.add(button, 0, index);
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
            TextField title = new TextField(note.getTitle());
            title.setStyle("-fx-font-weight: bold");
            title.setOnKeyReleased((event) -> {
                try {
                    // onNoteTitleEdit(event);
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
            notePane.addRow(0, title);
            notePane.addRow(1, text);
            noteGrid.add(notePane, i%2, Math.floorDiv(i, 2));
        });
    }

    private void update() {
        if (currentBoard.getNotes().size() == 6) newNoteButton.setDisable(true);
        else newNoteButton.setDisable(false);
    }

    private void checkNewBoardName() {
        if (boardName.getText().isBlank() || boards.stream().map(board -> (board.getBoardName())).collect(Collectors.toList()).contains(boardName.getText())) newBoardButton.setDisable(true);
        else newBoardButton.setDisable(false);
    }
}