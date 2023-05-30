package calenderapp.calenderapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;

public class CalenderAppController extends Logic {

    final int DAYS_OF_WEEK = 7;//7 days in a week
    private int colum = 5;//the most common columns in the year board

    @FXML
    private Label dateL;//label to present the corrent date
    private Label app;//the label we use to save the text enter bu input and put it into the map

    private HashMap<Calendar, String> map;//to save the text in specific erea

    @FXML
    private GridPane dayG;//grid for day buttons

    @FXML
    private ComboBox<String> monthC;

    @FXML
    private ComboBox<String> yearC;

    private Calendar day = Calendar.getInstance();//Calender object to get the specific date we want

    private Button tempKey; // get input of button click
    private Button[] btns;// for days of the month letter


    @FXML
    void enterMonth(ActionEvent event) {//comboBox activation
        clearGrid();//clear grid
        day.set(Integer.parseInt(yearC.getValue()), Integer.parseInt(monthC.getValue()) - 1, 1);//set the calender object to the year and month we get from the comboBox -1 cuz the calender object start from 0
        chooseNewDate();
    }

    @FXML
    void enterYear(ActionEvent event) {//comboBox activation
        clearGrid();
        day.set(Integer.parseInt(yearC.getValue()), Integer.parseInt(monthC.getValue()) - 1, 1);//set the calender object to the year and month we get from the comboBox -1 cuz the calender object start from 0
        chooseNewDate();
    }

    public void initialize() {
        map = new HashMap<Calendar, String>();//initialize the map
        app = new Label("");//initialize the label
        app.setWrapText(true);//so we can see all text enter
        handleComboBox();//create the content of the comboBox
        day.set(Integer.parseInt(yearC.getValue()), Integer.parseInt(monthC.getValue()) - 1, 1);//set the calender object to the year and month we get from the comboBox -1 cuz the calender object start from 0
        chooseNewDate();//default setting the date to january 2023
    }

    private void chooseNewDate() {//choose new date
        int daysInMonth = getNumberOfDaysInMonth(Integer.parseInt(yearC.getValue()), (Integer.parseInt(monthC.getValue())));//get the days of the number of days in the month we choose from the comboBox
        int days = day.get(Calendar.DAY_OF_WEEK) - 1;//set days to get which day are we in the week and -1 cuz arrays start from 0
        dateL.setText("Year: " + yearC.getValue() + "  Month: " + monthName(Integer.parseInt(monthC.getValue())));// display the number of the year and month
        btns = new Button[daysInMonth + days];//create the array length to be the days of the month and the starting day of the week, cuz it start from there and count from there the days in the month

        if ((days == 6 && daysInMonth >= 30) || (days >= 5 && daysInMonth == 31))//the 2 options that the year calender could be 6 rows
            colum = 6;
        else if (days == 0 && daysInMonth == 28)//the chance that the year calender will be 4 colum
            colum = 4;
        for (int i = 0; i < daysInMonth; i++) {//set the buttons on the grid
            btns[i + days] = new Button((i + 1) + "");//at the day of the week set the first button with the number 1 to days in month
            btns[i + days].setPrefSize((dayG.getPrefWidth() / DAYS_OF_WEEK), dayG.getPrefHeight() / colum);//all buttons same size on the grid and fit to the right row and col
            dayG.add(btns[i + days], (i + days) % DAYS_OF_WEEK, (i + days) / DAYS_OF_WEEK);//set the buttons in the right place
            btns[i + days].setOnAction(new EventHandler<ActionEvent>() {//on button press with activate the handle
                @Override
                public void handle(ActionEvent event) {
                    handleButton(event);
                }
            });
        }
    }

    private void handleButton(ActionEvent event) {
        if (tempKey == null)//first key pressed
            tempKey = (Button) event.getSource();//save the key
        day.set(Integer.parseInt(yearC.getValue()), Integer.parseInt(monthC.getValue()) - 1, Integer.parseInt(tempKey.getText()));//set the calender object to the year and month we get from the comboBox -1 cuz the calender object start from 0 with the day preesed button
        app.setText(map.get(day));//show the content that is save on the day in the label
        openDialog();//open a dialog to input appointment or edit them
        // checkLetterInWord(tempKey);//get the preesed button and check if the letter from the button is in the word we got from the text

    }

    private void openDialog() {
        TextInputDialog dialog = new TextInputDialog();// create new input
        dialog.setTitle("Appointment");
        dialog.setHeaderText("Please enter or edit appointment");

        GridPane grid = new GridPane();//grid for the dialog
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ButtonType edit = new ButtonType("Edit", ButtonBar.ButtonData.HELP);//edit button for the dialog

        TextField appointments = new TextField();//text to enter apppointment
        appointments.setPromptText("appointments");

        grid.add(new Label("Enter appointments please:"), 0, 0);
        grid.add(appointments, 1, 0);
        grid.add(new Label("Today's appointments:"), 0, 1);
        grid.add(app, 1, 1);//adding all to the grid

        dialog.getDialogPane().getButtonTypes().addAll(edit);//add the edit button to the dialog
        dialog.getDialogPane().setContent(grid);//set the grid to bet the dialog content

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {//if there is an input text
            if (app.getText() == null) {//first input entered
                if (!appointments.getText().equals(""))//the input is not empty
                    map.put(day, appointments.getText());//set the input into the map at the date we entered

            } else {
                if (!appointments.getText().equals(""))//the input is not empty
                    map.put(day, app.getText() + "\n" + appointments.getText());//set the input into the map at the date we entered
            }
        } else {
            TextInputDialog editD = new TextInputDialog();//create new dialog
            editD.setTitle("Edit Appointment");
            editD.setHeaderText("You can now edit the appointment");

            GridPane gridE = new GridPane();
            TextField textE = new TextField();//new grid and text to edit
            textE.setText(app.getText());//set the text to be the label so we can edit
            gridE.add(textE, 0, 0);//enter it to the grid

            editD.getDialogPane().setContent(gridE);//set the dialog
            editD.showAndWait();

            map.put(day, textE.getText());//set the text in the date to the new text entered
        }

        tempKey = null;//reset button

    }


    private void handleComboBox() {// create comboBox
        final int MONTHS = 12;
        final int START_YEAR = 2023, END_YEAR = 2026;

        for (int i = 1; i <= MONTHS; i++)//handle the months
            monthC.getItems().add(i + "");
        monthC.setValue("1");

        for (int i = START_YEAR; i <= END_YEAR; i++)//handle the years
            yearC.getItems().add(i + "");
        yearC.setValue("2023");


    }


    private void clearGrid() {

        dayG.setGridLinesVisible(false);// clear the grid than add the new labels for second word
        dayG.getColumnConstraints().clear();// clear the grid than add the new labels for second word
        dayG.getRowConstraints().clear();// clear the grid than add the new labels for second word
        dayG.getChildren().clear();// get the first grid made by the screen builder
    }


}