package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    private TextField applicationId;
    private TextField fullname;
    private TextField address;
    private TextField phone;
    private TextField email;
    private TextField educationalAttachment;
    private ComboBox<String> gender;
    private DatePicker dateAvailable;
    private TextField position;
    private TextField salary;
    private ToggleGroup isLegal;
    private ToggleGroup isRelative;
    private TextField explanation;

    static void main(String[] args){launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setTop(createHeader());
        root.setCenter(createContent());


        Scene scene = new Scene(root, 600, 800);

        stage.setTitle("Employment Applications");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createHeader() throws Exception {
        Label title = new Label("Employment Application");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

        HBox header = new HBox(title);
        header.setAlignment(Pos.CENTER);

        return header;
    }

    private VBox createContent() throws Exception {
        VBox formSection = new VBox(15);
        GridPane fieldsGrid = new GridPane();
        fieldsGrid.setHgap(15);
        fieldsGrid.setVgap(12);
        fieldsGrid.setAlignment(Pos.CENTER);

        applicationId = new TextField();
        applicationId.setEditable(false);

        fullname = new TextField();
        address = new TextField();
        phone = new TextField();
        email = new TextField();
        educationalAttachment = new TextField();
        gender = new ComboBox<>();
        gender.getItems().addAll("Female", "Male", "Non-binary", "Prefer not to say");
        dateAvailable = new DatePicker();
        position = new TextField();
        salary = new TextField();

        isLegal = new ToggleGroup();
        RadioButton legalTrue = new RadioButton("Yes");
        RadioButton legalFalse = new RadioButton("No");
        legalTrue.setToggleGroup(isLegal);
        legalFalse.setToggleGroup(isLegal);
        HBox legalContainer = new HBox(10, legalTrue, legalFalse);

        isRelative = new ToggleGroup();
        RadioButton relativeTrue = new RadioButton("Yes");
        RadioButton relativeFalse = new RadioButton("No");
        relativeTrue.setToggleGroup(isRelative);
        relativeFalse.setToggleGroup(isRelative);
        HBox relativeContainer = new HBox(10, relativeTrue, relativeFalse);

        explanation = new TextField();

        fieldsGrid.add(new Label("Enter your fullname: "), 0, 0);
        fieldsGrid.add(fullname, 1, 0);

        fieldsGrid.add(new Label("Enter your address: "), 0, 1);
        fieldsGrid.add(address, 1, 1);

        fieldsGrid.add(new Label("Enter your phone: "), 0, 2);
        fieldsGrid.add(phone, 1, 2);

        fieldsGrid.add(new Label("Enter your email: "), 0, 3);
        fieldsGrid.add(email, 1, 3);

        fieldsGrid.add(new Label("Enter your highest educational attainment"), 0, 4);
        fieldsGrid.add(educationalAttachment, 1, 4);

        fieldsGrid.add(new Label("Choose your gender"), 0, 5);
        fieldsGrid.add(gender, 1,  5);

        fieldsGrid.add(new Label("Choose day for a meeting"), 0, 6);
        fieldsGrid.add(dateAvailable, 1, 6);

        fieldsGrid.add(new Label("What is your position"), 0, 7);
        fieldsGrid.add(position, 1, 7);

        fieldsGrid.add(new Label("What is your expectational salary?"), 0, 8);
        fieldsGrid.add(salary, 1, 8);

        fieldsGrid.add(new Label("Are you legal to work in Canada?"), 0, 9);
        fieldsGrid.add(legalContainer, 1, 9);

        fieldsGrid.add(new Label("Do you have relatives working for our company?"), 0, 10);
        fieldsGrid.add(relativeContainer, 1, 10);

        fieldsGrid.add(new Label("If yes, please explain futher"), 0, 11);
        fieldsGrid.add(explanation, 1, 11);

        Button submitButton = new Button("Submit Application");
        submitButton.setOnAction(event -> handleSubmit());

        HBox buttonContainer = new HBox(submitButton);
        buttonContainer.setAlignment(Pos.CENTER);

        formSection.getChildren().addAll(
                fieldsGrid,
                buttonContainer
        );

        return formSection;
    }

    private void handleSubmit() {
        if (!validateForm()) {
            return;
        }

        RadioButton selectedLegal =
                (RadioButton) isLegal.getSelectedToggle();

        RadioButton selectedRelative =
                (RadioButton) isRelative.getSelectedToggle();

        if (selectedLegal == null || selectedRelative == null) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Please answer both Yes/No questions."
            ).showAndWait();
            return;
        }

        org.example.Application application = new org.example.Application(
                fullname.getText(),
                address.getText(),
                phone.getText(),
                email.getText(),
                educationalAttachment.getText(),
                gender.getValue(),
                dateAvailable.getValue(),
                position.getText(),
                salary.getText(),
                selectedLegal.getText().equals("Yes"),
                selectedRelative.getText().equals("Yes"),
                explanation.getText()
        );

        try {
            ApplicationDAO dao = new ApplicationDAO();
            dao.saveApplication(application);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Application submitted successfully!");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Failed to save application");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        if (fullname.getText().isBlank()) {
            errors.append("Full name is required.\n");
        }

        if (address.getText().isBlank()) {
            errors.append("Address is required.\n");
        }

        if (!email.getText().matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            errors.append("Invalid email address.\n");
        }

        if (!phone.getText().matches("\\+?[0-9()\\s-]{7,20}")) {
            errors.append("Invalid phone number.\n");
        }

        if (gender.getValue() == null) {
            errors.append("Please select your gender.\n");
        }

        if (dateAvailable.getValue() == null) {
            errors.append("Please select an available date.\n");
        }

        if (position.getText().isBlank()) {
            errors.append("Position is required.\n");
        }

        try {
            double value = Double.parseDouble(salary.getText().trim());

            if (!Double.isFinite(value) || value < 0) {
                errors.append("Salary must be a valid non-negative number.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("Salary must be a number.\n");
        }

        if (isLegal.getSelectedToggle() == null) {
            errors.append("Please indicate your work eligibility.\n");
        }

        RadioButton selectedRelative =
                (RadioButton) isRelative.getSelectedToggle();

        if (selectedRelative != null
                && selectedRelative.getText().equals("Yes")
                && explanation.getText().isBlank()) {

            errors.append(
                    "Please explain your relationship with the employee.\n"
            );
        }

        if (isRelative.getSelectedToggle() == null) {
            errors.append("Please answer the relatives question.\n");
        }

        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errors.toString());
            alert.showAndWait();

            return false;
        }

        return true;
    }
}
