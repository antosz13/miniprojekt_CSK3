package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Szotar;
import model.Szopar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SzotarController {
    private Szotar szotar;
    private List<Label> angolLabels = new ArrayList<>();
    private List<ComboBox<String>> magyarCombos = new ArrayList<>();
    private List<String> magyarOptions = new ArrayList<>();
    private Slider pairSlider;
    private Button kovetkezoGomb;
    private Button ellenorzesGomb;
    private Button vegeGomb;
    private boolean allCorrect = false;
    private int totalPairs;
    private List<Szopar> displayedPairs = new ArrayList<>();

    public SzotarController(Szotar szotar) {
        this.szotar = szotar;
        // Initialize Hungarian options with some default words if needed
    }

    public void setup(Stage stage) {
        // Ask user for the number of word pairs and input them using text fields
        totalPairs = askForNumberOfPairs();
        showWordPairInputForm(totalPairs);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setVgap(10);
        pane.setHgap(10);

        pairSlider = new Slider(1, totalPairs, totalPairs);
        pairSlider.setShowTickMarks(true);
        pairSlider.setShowTickLabels(true);
        pairSlider.setMajorTickUnit(1);
        pairSlider.setBlockIncrement(1);
        pairSlider.setSnapToTicks(true);

        displayWordPairs(pane, totalPairs);

        pairSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            displayWordPairs(pane, newVal.intValue());
        });

        ellenorzesGomb = new Button("Ellenőrzés");
        kovetkezoGomb = new Button("Következő");
        vegeGomb = new Button("Vége");

        kovetkezoGomb.setDisable(true);

        ellenorzesGomb.setOnAction(e -> {
            allCorrect = checkAnswers();
            if (allCorrect) {
                kovetkezoGomb.setDisable(false);
            }
        });

        kovetkezoGomb.setOnAction(e -> {
            if (allCorrect) {
                loadRemainingPairs(pane);
                kovetkezoGomb.setDisable(true);
            }
        });

        vegeGomb.setOnAction(e -> {
            confirmExit(stage); // Call exit confirmation dialog
        });

        int bottomRowIndex = totalPairs;
        pane.add(new Label("Adjust word pairs:"), 0, bottomRowIndex);
        pane.add(pairSlider, 1, bottomRowIndex);
        pane.add(ellenorzesGomb, 0, bottomRowIndex + 1);
        pane.add(kovetkezoGomb, 1, bottomRowIndex + 1);
        pane.add(vegeGomb, 0, bottomRowIndex + 2); // Add the exit button

        Scene scene = new Scene(pane, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Angol-Magyar Szótár Gyakorló");
        stage.show();
    }

    private int askForNumberOfPairs() {
        TextInputDialog dialog = new TextInputDialog("4");
        dialog.setTitle("Word Pair Input");
        dialog.setHeaderText("How many word pairs do you want to input?");
        dialog.setContentText("Please enter the number:");

        Optional<String> result = dialog.showAndWait();
        return result.map(Integer::parseInt).orElse(4);
    }

    private void showWordPairInputForm(int numPairs) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Input Word Pairs");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        List<TextField> englishFields = new ArrayList<>();
        List<TextField> hungarianFields = new ArrayList<>();

        for (int i = 0; i < numPairs; i++) {
            TextField englishField = new TextField();
            englishField.setPromptText("English word");
            englishFields.add(englishField);

            TextField hungarianField = new TextField();
            hungarianField.setPromptText("Hungarian word");
            hungarianFields.add(hungarianField);

            grid.add(new Label("English:"), 0, i);
            grid.add(englishField, 1, i);
            grid.add(new Label("Hungarian:"), 2, i);
            grid.add(hungarianField, 3, i);
        }

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                for (int i = 0; i < numPairs; i++) {
                    String englishWord = englishFields.get(i).getText();
                    String hungarianWord = hungarianFields.get(i).getText();
                    szotar.addSzopar(new Szopar(englishWord, hungarianWord));
                    magyarOptions.add(hungarianWord);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void displayWordPairs(GridPane pane, int numPairs) {
        angolLabels.clear();
        magyarCombos.clear();
        displayedPairs.clear();

        pane.getChildren().removeIf(node -> GridPane.getRowIndex(node) < totalPairs);

        for (int i = 0; i < numPairs && i < szotar.getSzoparok().size(); i++) {
            Szopar szopar = szotar.getSzoparok().get(i);
            displayedPairs.add(szopar);

            Label angolLabel = new Label(szopar.getAngol());
            angolLabels.add(angolLabel);

            List<String> shuffledMagyarOptions = new ArrayList<>(magyarOptions);
            Collections.shuffle(shuffledMagyarOptions);

            ComboBox<String> magyarCombo = new ComboBox<>();
            magyarCombo.getItems().addAll(shuffledMagyarOptions);
            magyarCombos.add(magyarCombo);

            pane.add(new Label("Angol:"), 0, i);
            pane.add(angolLabel, 1, i);
            pane.add(new Label("Magyar:"), 2, i);
            pane.add(magyarCombo, 3, i);
        }

        pairSlider.setMax(szotar.getSzoparok().size());
        pairSlider.setValue(Math.min(pairSlider.getValue(), pairSlider.getMax()));
    }

    private boolean checkAnswers() {
        boolean allCorrect = true;

        for (int i = 0; i < displayedPairs.size(); i++) {
            Szopar szopar = displayedPairs.get(i);
            ComboBox<String> magyarCombo = magyarCombos.get(i);
            if (magyarCombo.getValue() != null && magyarCombo.getValue().equals(szopar.getMagyar())) {
                magyarCombo.setStyle("-fx-background-color: lightgreen;");
            } else {
                magyarCombo.setStyle("-fx-background-color: lightcoral;");
                allCorrect = false;
            }
        }

        return allCorrect;
    }

    private void loadRemainingPairs(GridPane pane) {
        szotar.getSzoparok().removeAll(displayedPairs);

        if (szotar.getSzoparok().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nincsenek több szópárok!");
            alert.showAndWait();
            return;
        }

        Collections.shuffle(szotar.getSzoparok());
        int pairsToDisplay = Math.min((int) pairSlider.getValue(), szotar.getSzoparok().size());
        displayWordPairs(pane, pairsToDisplay);
    }

    private void confirmExit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos ki akarsz lépni?");
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Exit Application");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
        }
    }

    public void addWordPair(String dog, String kutya) {
        szotar.addSzopar(new Szopar(dog, kutya));
    }

    public Szotar getSzotar() {
        return szotar;
    }

    public boolean checkAnswer(Szopar szopar, String kutya) {
        return szopar.getMagyar().equals(kutya);
    }

    public Alert showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos ki akarsz lépni?");
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Exit Application");
        return alert;
    }
}