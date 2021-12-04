package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.WorkWithList;


//класс, отвечающий за окно с подробной информацией о картине
public class InfoMenuController {

    @FXML
    private Button changePaintingButton;

    @FXML
    private Button deletePaintingButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    private ImageView paintingImageView;

    @FXML
    private GridPane gridPaneInfo;

    @FXML
    private Label nameLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label characteristicsLabel;

    @FXML
    private Label locationLabel;


    @FXML
    void initialize() {
        //заполняем GridPane информацией о картине
        fillGridPane();
        //создаём объект класса Image
        Image image = new Image(new File(WorkWithList.getPaintingsListIterator().getCurrent().getImageLink()).toURI().toString(), 513, 362, true, false);
        //помещаем его в ImageView
        paintingImageView.setImage(image);
        //кнопки, при наведении на них мышкой, меняют свой цвет
        changePaintingButton.setOnMouseEntered(mouseEvent -> changePaintingButton.setStyle("-fx-background-color: #47cffc;"));
        changePaintingButton.setOnMouseExited(mouseEvent -> changePaintingButton.setStyle("-fx-background-color: #055461;"));
        deletePaintingButton.setOnMouseEntered(mouseEvent -> deletePaintingButton.setStyle("-fx-background-color: #47cffc;"));
        deletePaintingButton.setOnMouseExited(mouseEvent -> deletePaintingButton.setStyle("-fx-background-color: #055461;"));
        backToMenuButton.setOnMouseEntered(mouseEvent -> backToMenuButton.setStyle("-fx-background-color: #47cffc;"));
        backToMenuButton.setOnMouseExited(mouseEvent -> backToMenuButton.setStyle("-fx-background-color:  #055461;"));
    }

    //метод заполняющий GridPane метками с информацией о картине
    @FXML
    private void fillGridPane(){
        Font font = new Font("System",13);
        nameLabel = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getName());
        nameLabel.setFont(font);
        gridPaneInfo.add(nameLabel,1,0);
        authorLabel = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor());
        authorLabel.setFont(font);
        gridPaneInfo.add(authorLabel,1,1);
        yearLabel = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getYear());
        yearLabel.setFont(font);
        gridPaneInfo.add(yearLabel,1,2);
        genreLabel = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getGenre());
        genreLabel.setFont(font);
        gridPaneInfo.add(genreLabel,1,3);
        characteristicsLabel = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getMaterialOfBasis() + ", " + WorkWithList.getPaintingsListIterator().getCurrent().getDye());
        characteristicsLabel.setFont(font);
        gridPaneInfo.add(characteristicsLabel,1,4);
        locationLabel = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getLocation());
        locationLabel.setFont(font);
        gridPaneInfo.add(locationLabel,1,5);
    }

    //метод, отвечающий за вывод на экран окна изменения картины
    @FXML
    void callChangingPaintingMenu(){
        Stage stage = new Stage();
        stage.setTitle("Изменение картины");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/forms/changingMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        //после закрытия окна изменения обновляем содержимое окна просмотра
        Image image = new Image(new File(WorkWithList.getPaintingsListIterator().getCurrent().getImageLink()).toURI().toString(), 513, 362, true, false);
        paintingImageView.setImage(image);
        nameLabel.setText(WorkWithList.getPaintingsListIterator().getCurrent().getName());
        authorLabel.setText(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor());
        yearLabel.setText(WorkWithList.getPaintingsListIterator().getCurrent().getYear());
        genreLabel.setText(WorkWithList.getPaintingsListIterator().getCurrent().getGenre());
        characteristicsLabel.setText(WorkWithList.getPaintingsListIterator().getCurrent().getMaterialOfBasis() + ", " + WorkWithList.getPaintingsListIterator().getCurrent().getDye());
        locationLabel.setText(WorkWithList.getPaintingsListIterator().getCurrent().getLocation());
    }

    //метод, отвечающий за вывод окна с подтверждением удаления
    //если удаление подтверждено, картина удаляется из списка и происходит возврат в главное меню
    @FXML
    void deletePainting(){
        Alert InsuranceAlert = new Alert(Alert.AlertType.CONFIRMATION);
        InsuranceAlert.setTitle("Удаление картины");
        InsuranceAlert.setHeaderText("Вы действительно хотите удалить данную картину?");
        ButtonType yes = new ButtonType("Да");
        ButtonType no = new ButtonType("Нет");
        InsuranceAlert.getButtonTypes().clear();
        InsuranceAlert.getButtonTypes().addAll(yes,no);
        Optional<ButtonType> option = InsuranceAlert.showAndWait();
        if (option.get().equals(yes)){
            WorkWithList.getPaintingsListIterator().deleteCurrent();
            backToMainMenu();
        }
    }

    //метод, отвечающий за закрытие текущего окна и возврат в главное меню
    @FXML
    private void backToMainMenu(){
        Stage stage = (Stage) backToMenuButton.getScene().getWindow();
        stage.close();
    }
}
