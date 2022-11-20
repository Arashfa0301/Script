package ui;

import core.main.BoardElement;
import core.main.Checklist;
import core.main.Note;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class BoardElementController {

    protected static final int BOARD_ELEMENT_WIDTH = 220, BOARD_ELEMENT_HEIGHT = 430, TITLE_LIMIT = 23;

    private BoardElement boardElement;

    private ScriptController listener;

    private Cross cross;

    public BoardElementController(BoardElement boardElement, ScriptController listener) {
        this.boardElement = boardElement;
        this.listener = listener;
        this.cross = new Cross(Color.WHITE, 1.0);
        this.cross.setPadding(new Insets(3, 0, 0, 0));

    }

    private VBox generateNote() {
        TextField titleField = new TextField(boardElement.getTitle());
        titleField.setStyle("-fx-font-family: 'Poppins SemiBold'; -fx-font-size: 14;");
        titleField.setPadding(new Insets(15, 14, 0, 14));
        titleField.setPromptText("Title");
        titleField.setOnKeyReleased(event -> {
            try {
                boardElement.setTitle(titleField.getText());
            } catch (IllegalArgumentException e) {
                titleField.setText(titleField.getText().substring(0, TITLE_LIMIT));
                titleField.positionCaret(TITLE_LIMIT);
                boardElement.setTitle(titleField.getText());
            }
        });
        Note note = (Note) boardElement;
        ExpandableTextArea textArea = new ExpandableTextArea(note.getContent());
        textArea.setPromptText("Notes");
        textArea.setWrapText(true);
        textArea.setPrefSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_WIDTH);
        textArea.setOnKeyReleased(event -> {
            note.setContent(textArea.getText());
        });

        HBox topPane = new HBox();
        VBox notePane = new VBox();
        notePane.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");
        notePane.getChildren().add(topPane);
        notePane.setPrefSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_HEIGHT);
        notePane.setMaxSize(BOARD_ELEMENT_WIDTH, 1000);
        notePane.getChildren().add(textArea);
        topPane.getChildren().add(titleField);

        Button deleteButton = new Button("");
        deleteButton.setGraphic(cross);
        deleteButton.setShape(new Circle(1));
        deleteButton.setMinSize(24, 23.5);
        deleteButton.setMaxSize(24, 23.5);
        deleteButton.setStyle("-fx-background-color: black;");
        deleteButton.setTranslateX(10);
        deleteButton.setTranslateY(-10);
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setVisible(false);

        deleteButton.setOnAction((event) -> {
            listener.removeBoardElement(this);
        });

        topPane.getChildren().add(deleteButton);
        notePane.setOnMouseEntered((event) -> {
            deleteButton.setVisible(true);
            notePane.setEffect(new DropShadow(12, new Color(0, 0, 0, 0.15)));
        });
        notePane.setOnMouseExited((event) -> {
            deleteButton.setVisible(false);
            notePane.setEffect(null);
        });
        return notePane;
    }

    private VBox generateChecklist() {
        Checklist checklist = (Checklist) boardElement;
        TextField titleField = new TextField(checklist.getTitle());
        titleField.setStyle("-fx-font-family: 'Poppins SemiBold'; -fx-font-size: 14;");
        titleField.setPadding(new Insets(15, 14, 0, 14));
        titleField.setOnKeyReleased(event -> {
            boardElement.setTitle(titleField.getText());
        });
        titleField.setPromptText("Title");
        titleField.setOnKeyReleased(event -> {
            try {
                boardElement.setTitle(titleField.getText());
            } catch (IllegalArgumentException e) {
                titleField.setText(titleField.getText().substring(0, TITLE_LIMIT));
                titleField.positionCaret(TITLE_LIMIT);
                boardElement.setTitle(titleField.getText());
            }
        });

        List<TextField> listElements = new ArrayList<>();
        checklist.getChecklistLines().stream().forEach(element -> {
            TextField t = new TextField(element.getLine());
            listElements.add(t);
            t.setDisable(
                    ((Checklist) getBoardElement()).getChecklistLines().get(listElements.indexOf(t)).isChecked());
            t.setOnKeyReleased((event) -> {
                checklist.setChecklistline(listElements.indexOf(t), t.getText());
            });
            t.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    checklist.setChecklistline(listElements.indexOf(t), t.getText());
                    addChecklistLine(checklist);
                    listener.drawBoardElementControllers();
                }
            });
            t.setPromptText("Add a list element");
            t.setStyle("-fx-font-family: 'Poppins Regular'; -fx-font-size: 13; -fx-padding: 0 0 5 0;");
        });
        if (checklist.isEmpty()) {
            TextField t = new TextField();
            listElements.add(t);
            checklist.addChecklistLine();
            t.setOnKeyReleased((event) -> {
                checklist.setChecklistline(listElements.indexOf(t), t.getText());
            });
            t.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    checklist.setChecklistline(listElements.indexOf(t), t.getText());
                    addChecklistLine(checklist);
                    listener.drawBoardElementControllers();
                }
            });
            t.setPromptText("Add a list element");
        }

        HBox topPane = new HBox();
        VBox notePane = new VBox();
        notePane.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");
        notePane.getChildren().add(topPane);
        notePane.setPrefSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_HEIGHT);
        notePane.setMaxSize(BOARD_ELEMENT_WIDTH, 1000);
        topPane.getChildren().add(titleField);

        Button deleteButton = new Button("");
        deleteButton.setGraphic(cross);
        deleteButton.setShape(new Circle(1));
        deleteButton.setMinSize(24, 23.5);
        deleteButton.setMaxSize(24, 23.5);
        deleteButton.setStyle("-fx-background-color: black;");
        deleteButton.setTranslateX(10);
        deleteButton.setTranslateY(-10);
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setVisible(false);
        deleteButton.setOnAction((event) -> {
            listener.removeBoardElement(this);
        });
        topPane.getChildren().add(deleteButton);
        topPane.setPadding(new Insets(0, 0, 10, 0));

        notePane.setOnMouseEntered((event) -> {
            deleteButton.setVisible(true);
            notePane.setEffect(new DropShadow(12, new Color(0, 0, 0, 0.15)));
        });
        notePane.setOnMouseExited((event) -> {
            deleteButton.setVisible(false);
            notePane.setEffect(null);
        });

        listElements.forEach(e -> {
            MFXCheckbox checkBox = new MFXCheckbox();
            checkBox.setSelected(checklist.getChecklistLines().get(listElements.indexOf(e)).isChecked());
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    checklist.setChecklistChecked(listElements.indexOf(e), true);
                } else {
                    checklist.setChecklistChecked(listElements.indexOf(e), false);
                }
                listener.drawBoardElementControllers();
            });
            Button delLineButton = new Button("");
            Cross delLineCross = new Cross(Color.BLACK, 1.0);
            delLineButton.setGraphic(delLineCross);
            delLineButton.setShape(new Circle(1));
            delLineButton.setMinSize(24, 23.5);
            delLineButton.setMaxSize(24, 23.5);
            delLineButton.setStyle("-fx-background-color: white;");
            delLineButton.setCursor(Cursor.HAND);
            delLineButton.setVisible(false);
            delLineButton.setOnAction((event) -> {
                checklist.removeChecklistLine(listElements.indexOf(e));
                listener.drawBoardElementControllers();
            });
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(2, 14, 2, 14));
            hbox.getChildren().add(checkBox);
            hbox.getChildren().add(e);
            if (listElements.size() > 1) {
                hbox.getChildren().add(delLineButton);
                hbox.setOnMouseEntered(event -> delLineButton.setVisible(true));
                hbox.setOnMouseExited(event -> delLineButton.setVisible(false));
            }
            notePane.getChildren().add(hbox);
        });
        MFXButton addLineButton = new MFXButton("");
        Pane plus = new Pane();
        plus.setPadding(new Insets(0, 0, 0, 8));
        Line line3 = new Line(0, 6, 8, 6);
        Line line4 = new Line(4, 2, 4, 10);
        line3.setStrokeWidth(1.5f);
        line4.setStrokeWidth(1.5f);
        plus.getChildren().addAll(line3, line4);
        addLineButton.setGraphic(plus);
        addLineButton.setOnAction(event -> {
            addChecklistLine(checklist);
            listener.drawBoardElementControllers();
        });
        addLineButton.setShape(new Circle(1));
        addLineButton.setCursor(Cursor.HAND);
        addLineButton.setMinSize(20, 20);
        addLineButton.setMaxSize(20, 20);
        addLineButton.setStyle(
                "-mfx-button-type: RAISED; -mfx-depth-level: LEVEL1; -fx-background-color: white;");
        addLineButton.setTranslateX(14);
        addLineButton.setTranslateY(2);
        HBox hbox = new HBox();
        hbox.getChildren().add(addLineButton);
        notePane.getChildren().add(hbox);
        return notePane;
    }

    private void addChecklistLine(Checklist checklist) {
        try {
            checklist.addChecklistLine();
        } catch (IllegalStateException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Checklist has reached the maximum amount of lines");
            alert.show();
        }
    }

    protected VBox generateControl() {

        return (boardElement instanceof Note) ? generateNote()
                : (boardElement instanceof Checklist) ? generateChecklist() : null;

    }

    protected BoardElement getBoardElement() {
        return boardElement;
    }
}
