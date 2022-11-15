package ui;

import core.main.BoardElement;
import core.main.Checklist;
import core.main.Note;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class BoardElementController {

    private static final int BOARD_ELEMENT_WIDTH = 200, BOARD_ELEMENT_HEIGHT = 230;

    private BoardElement boardElement;

    private ScriptController listener;

    public BoardElementController(BoardElement boardElement, ScriptController listener) {
        this.boardElement = boardElement;
        this.listener = listener;

    }

    private VBox generateNote() {
        TextField titleField = new TextField(boardElement.getTitle());
        titleField.setLayoutX(2.0);
        titleField.setLayoutY(81.0);
        titleField.setPrefHeight(17.0);
        titleField.setPrefWidth(197.0);
        titleField.setStyle("-fx-font-weight: bold");
        titleField.setPromptText("Title");
        titleField.setOnKeyReleased(event -> {
            boardElement.setTitle(titleField.getText());
            listener.updateCurrentBoardElements();
        });

        TextArea textField = new TextArea(((Note) boardElement).getText());
        textField.setPromptText("Notes");
        textField.setWrapText(true);
        textField.setPrefSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_WIDTH);
        textField.setOnKeyReleased(event -> {
            ((Note) boardElement).setText(textField.getText());
            listener.updateCurrentBoardElements();
        });

        HBox topPane = new HBox();
        VBox notePane = new VBox();
        notePane.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");
        notePane.getChildren().add(topPane);
        notePane.setPrefSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_HEIGHT);
        notePane.setMaxSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_HEIGHT);
        notePane.getChildren().add(textField);
        topPane.getChildren().add(titleField);

        Button deleteButton = new Button("X");
        deleteButton.setShape(new Circle(10));
        deleteButton.setTranslateX(10);
        deleteButton.setTranslateY(-7);
        deleteButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");
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
        TextField titleField = new TextField(((Checklist) boardElement).getTitle());
        titleField.setStyle("-fx-font-weight: bold");
        titleField.setOnKeyReleased(event -> {
            boardElement.setTitle(titleField.getText());
            listener.updateCurrentBoardElements();
        });
        titleField.setPromptText("Title");

        List<TextField> listElements = new ArrayList<>();
        ((Checklist) boardElement).getChecklistLines().stream().forEach(element -> {
            TextField t = new TextField(element.getLine());
            listElements.add(t);
            t.setOnKeyReleased((event) -> {
                ((Checklist) boardElement).getChecklistLines().get(listElements.indexOf(t)).setLine(t.getText());
                // ((Checklist) boardElement).getCheckItems().set(listElements.indexOf(t),
                // t.getText());
                listener.updateCurrentBoardElements();
            });
            t.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    ((Checklist) boardElement).getChecklistLines().get(listElements.indexOf(t)).setLine(t.getText());
                    // ((Checklist) boardElement).getCheckItems().set(listElements.indexOf(t),
                    // t.getText());
                    ((Checklist) boardElement).addChecklistLine();
                    listener.updateCurrentBoardElements();
                    listener.drawBoardElementControllers();
                }
            });
            t.setPromptText("Add a list element");
        });
        if (((Checklist) boardElement).isEmpty()) {
            TextField t = new TextField();
            listElements.add(t);
            ((Checklist) boardElement).addChecklistLine();
            t.setOnKeyReleased((event) -> {
                ((Checklist) boardElement).getChecklistLines().get(listElements.indexOf(t)).setLine(t.getText());
                // ((Checklist) boardElement).getCheckItems().set(listElements.indexOf(t),
                // t.getText());
                listener.updateCurrentBoardElements();
            });
            t.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    ((Checklist) boardElement).getChecklistLines().get(listElements.indexOf(t)).setLine(t.getText());
                    // ((Checklist) boardElement).getCheckItems().set(listElements.indexOf(t),
                    // t.getText());
                    ((Checklist) boardElement).addChecklistLine();
                    listener.updateCurrentBoardElements();
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
        notePane.setMaxSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_HEIGHT);
        topPane.getChildren().add(titleField);

        Button deleteButton = new Button("X");
        deleteButton.setShape(new Circle(10));
        deleteButton.setTranslateX(25);
        deleteButton.setTranslateY(-7);
        deleteButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");
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

        listElements.forEach(e -> {
            HBox hbox = new HBox();
            CheckBox checkBox = new CheckBox();
            Checklist checklist = (Checklist) getBoardElement();
            checkBox.setSelected(checklist.getChecklistLines().get(listElements.indexOf(e)).getChecked());
            // checkBox.setSelected(checklist.isChecked(listElements.indexOf(e)));
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    // checklist.check(listElements.indexOf(e));
                    checklist.getChecklistLines().get(listElements.indexOf(e)).checked(true);
                } else {
                    checklist.getChecklistLines().get(listElements.indexOf(e)).checked(false);
                    // checklist.uncheck(listElements.indexOf(e));
                }
                listener.updateCurrentBoardElements();
            });
            hbox.getChildren().add(checkBox);
            hbox.getChildren().add(e);
            notePane.getChildren().add(hbox);
        });
        return notePane;
    }

    public VBox generateControl() {
        if (boardElement instanceof Note) {
            return generateNote();
        } else if (boardElement instanceof Checklist) {
            return generateChecklist();
        }
        return null;
    }

    public BoardElement getBoardElement() {
        return boardElement;
    }
}
