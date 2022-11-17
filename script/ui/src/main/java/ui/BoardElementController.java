package ui;

import core.main.BoardElement;
import core.main.Checklist;
import core.main.Note;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.scene.Cursor;
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

    protected static final int BOARD_ELEMENT_WIDTH = 200, BOARD_ELEMENT_HEIGHT = 230, TITLE_LIMIT = 23;

    private BoardElement boardElement;

    private ScriptController listener;

    public BoardElementController(BoardElement boardElement, ScriptController listener) {
        this.boardElement = boardElement;
        this.listener = listener;

    }

    private VBox generateNote() {
        TextField titleField = new TextField(boardElement.getTitle());
        titleField.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        titleField.setPromptText("Title");
        titleField.setOnKeyReleased(event -> {
            try {
                boardElement.setTitle(titleField.getText());
                listener.updateCurrentBoardElements();
            } catch (IllegalArgumentException e) {
                titleField.setText(titleField.getText().substring(0, TITLE_LIMIT));
                titleField.positionCaret(TITLE_LIMIT);
                boardElement.setTitle(titleField.getText());
                listener.updateCurrentBoardElements();
            }
        });

        TextArea textArea = new TextArea(((Note) boardElement).getText());
        textArea.setPromptText("Notes");
        textArea.setWrapText(true);
        textArea.setPrefSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_WIDTH);
        textArea.setOnKeyReleased(event -> {
            ((Note) boardElement).setText(textArea.getText());
            listener.updateCurrentBoardElements();
        });

        HBox topPane = new HBox();
        VBox notePane = new VBox();
        notePane.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");
        notePane.getChildren().add(topPane);
        notePane.setPrefSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_HEIGHT);
        notePane.setMaxSize(BOARD_ELEMENT_WIDTH, BOARD_ELEMENT_HEIGHT);
        notePane.getChildren().add(textArea);
        topPane.getChildren().add(titleField);

        MFXButton deleteButton = new MFXButton("X");
        deleteButton.setShape(new Circle(1));
        deleteButton.setStyle("-mfx-button-type: RAISED; -mfx-depth-level: LEVEL1");
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
        titleField.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
        titleField.setOnKeyReleased(event -> {
            boardElement.setTitle(titleField.getText());
        });
        titleField.setPromptText("Title");
        titleField.setOnKeyReleased(event -> {
            try {
                boardElement.setTitle(titleField.getText());
                listener.updateCurrentBoardElements();
            } catch (IllegalArgumentException e) {
                titleField.setText(titleField.getText().substring(0, TITLE_LIMIT));
                titleField.positionCaret(TITLE_LIMIT);
                boardElement.setTitle(titleField.getText());
                listener.updateCurrentBoardElements();
            }
        });

        List<TextField> listElements = new ArrayList<>();
        ((Checklist) boardElement).getChecklistLines().stream().forEach(element -> {
            TextField t = new TextField(element.getLine());
            listElements.add(t);
            t.setDisable(
                    ((Checklist) getBoardElement()).getChecklistLines().get(listElements.indexOf(t)).getChecked());
            t.setOnKeyReleased((event) -> {
                ((Checklist) boardElement).setChecklistline(listElements.indexOf(t), t.getText());
            });
            t.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    ((Checklist) boardElement).setChecklistline(listElements.indexOf(t), t.getText());
                    ((Checklist) boardElement).addChecklistLine();
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
                ((Checklist) boardElement).setChecklistline(listElements.indexOf(t), t.getText());
            });
            t.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    ((Checklist) boardElement).setChecklistline(listElements.indexOf(t), t.getText());
                    ((Checklist) boardElement).addChecklistLine();
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

        MFXButton deleteButton = new MFXButton("X");
        deleteButton.setShape(new Circle(1));
        deleteButton.setStyle("-mfx-button-type: RAISED; -mfx-depth-level: LEVEL1");
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
            Checklist checklist = (Checklist) getBoardElement();
            MFXCheckbox checkBox = new MFXCheckbox();
            checkBox.setSelected(checklist.getChecklistLines().get(listElements.indexOf(e)).getChecked());
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    checklist.setChecklistChecked(listElements.indexOf(e), true);
                } else {
                    checklist.setChecklistChecked(listElements.indexOf(e), false);
                }
                listener.drawBoardElementControllers();
            });
            MFXButton delButton = new MFXButton("X");
            delButton.setShape(new Circle(1));
            delButton.setStyle("-mfx-button-type: RAISED; -mfx-depth-level: LEVEL1");
            delButton.setCursor(Cursor.HAND);
            delButton.setVisible(false);
            delButton.setOnAction((event) -> {
                checklist.removeChecklistLine(listElements.indexOf(e));
                listener.drawBoardElementControllers();
            });
            HBox hbox = new HBox();
            hbox.getChildren().add(checkBox);
            hbox.getChildren().add(e);
            if (listElements.size() > 1) {
                hbox.getChildren().add(delButton);
                hbox.setOnMouseEntered(event -> delButton.setVisible(true));
                hbox.setOnMouseExited(event -> delButton.setVisible(false));
            }
            notePane.getChildren().add(hbox);
        });
        MFXButton addLineButton = new MFXButton("+");
        addLineButton.setOnAction(event -> {
            ((Checklist) boardElement).addChecklistLine();
            listener.drawBoardElementControllers();
        });
        addLineButton.setStyle("-mfx-button-type: RAISED");
        addLineButton.setShape(new Circle(1));
        addLineButton.setCursor(Cursor.HAND);
        HBox hbox = new HBox();
        hbox.getChildren().add(addLineButton);
        notePane.getChildren().add(hbox);
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

    protected BoardElement getBoardElement() {
        return boardElement;
    }
}
