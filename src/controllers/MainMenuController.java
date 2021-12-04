package controllers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.text.Font;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import service.WorkWithList;

//класс, отвечающий за окно главного меню программы
public class MainMenuController {

    @FXML
    private Button addPaintingButton;

    @FXML
    private ComboBox<String> sortAuthorsComboBox;

    @FXML
    private ComboBox<String> sortGenresComboBox;

    @FXML
    private ComboBox<String> sortLocationComboBox;

    @FXML
    private Button sortButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private VBox VBoxLeft;

    @FXML
    private VBox VBoxRight;

    @FXML
    void initialize() {
        //устанавливаем текущим первый элемент списка
        WorkWithList.getPaintingsListIterator().reset();
        fillVBoxes();//заполняем пустые VBox
        fillComboBoxes();//заполняем пустые ComboBox
        //устанавливаем начальные значения ComboBox
        sortAuthorsComboBox.setValue("Все художники");
        sortGenresComboBox.setValue("Все жанры");
        sortLocationComboBox.setValue("Любая локация");
        //кнопки, при наведении на них мышкой, меняют свой цвет
        searchButton.setOnMouseEntered(mouseEvent -> searchButton.setStyle("-fx-background-color: #47cffc;"));
        searchButton.setOnMouseExited(mouseEvent -> searchButton.setStyle("-fx-background-color: #BDDABC;"));
        sortButton.setOnMouseEntered(mouseEvent -> sortButton.setStyle("-fx-background-color: #47cffc;"));
        sortButton.setOnMouseExited(mouseEvent -> sortButton.setStyle("-fx-background-color: #BDDABC;"));
        addPaintingButton.setOnMouseEntered(mouseEvent -> addPaintingButton.setStyle("-fx-background-color: #47cffc;"));
        addPaintingButton.setOnMouseExited(mouseEvent -> addPaintingButton.setStyle("-fx-background-color: #BDDABC;"));
        sortAuthorsComboBox.setOnMouseEntered(mouseEvent -> sortAuthorsComboBox.setStyle("-fx-background-color: #47cffc;"+"-fx-border-color: #055461"));
        sortAuthorsComboBox.setOnMouseExited(mouseEvent -> sortAuthorsComboBox.setStyle("-fx-background-color: #BDDABC;"+"-fx-border-color: #055461"));
        sortGenresComboBox.setOnMouseEntered(mouseEvent -> sortGenresComboBox.setStyle("-fx-background-color: #47cffc;"+"-fx-border-color: #055461"));
        sortGenresComboBox.setOnMouseExited(mouseEvent -> sortGenresComboBox.setStyle("-fx-background-color: #BDDABC;"+"-fx-border-color: #055461"));
        sortLocationComboBox.setOnMouseEntered(mouseEvent -> sortLocationComboBox.setStyle("-fx-background-color: #47cffc;"+"-fx-border-color: #055461"));
        sortLocationComboBox.setOnMouseExited(mouseEvent -> sortLocationComboBox.setStyle("-fx-background-color: #BDDABC;"+"-fx-border-color: #055461"));
    }

    //метод, заполняющий VBox данными из списка
    @FXML
    private void fillVBoxes(){
        //устанавливаем текущим первый элемент списка
        WorkWithList.getPaintingsListIterator().reset();
        //цикл по всем элементам списка
        for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
            addLeftVBox(i);//добавление картины в левый VBox с передачей её номера
            if ((i+1)==WorkWithList.getPaintingsList().size()) break;
            i++;
            WorkWithList.getPaintingsListIterator().nextLink();
            addRightVBox(i);//добавление картины в правый VBox
            WorkWithList.getPaintingsListIterator().nextLink();
        }
    }

    //метод, заполняющий ComboBox данными из списка
    @FXML
    private void fillComboBoxes(){
        WorkWithList.getPaintingsListIterator().reset();
        //добавляем дефолтные значения ComboBox
        sortAuthorsComboBox.getItems().add("Все художники");
        sortGenresComboBox.getItems().add("Все жанры");
        sortLocationComboBox.getItems().add("Любая локация");
        //создание ArrayList для заполнения ComboBox
        ArrayList<String> comboBoxList = new ArrayList<>();
        //помещаем имена всех художников в ArrayList
        for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
            comboBoxList.add(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor());
            WorkWithList.getPaintingsListIterator().nextLink();
        }
        //имена художников заносятся из ArrayList в ComboBox авторов
        //повторяющиеся имена художников удаляются из ArrayList
        //чтобы в ComboBox не было нескольких одинаковых значений
        for (int i = 0; i<comboBoxList.size();i++){
            String comboBoxCell = comboBoxList.get(i);
            sortAuthorsComboBox.getItems().add(comboBoxCell);
            for (int j = i+1;j<comboBoxList.size();j++){
                if (comboBoxCell.toLowerCase().trim().equals(comboBoxList.get(j).toLowerCase().trim())){
                    comboBoxList.remove(j);
                    j--;
                }
            }
        }
        WorkWithList.getPaintingsListIterator().reset();
        //очищаем ArrayList для повторного заполнения названиями жанров
        comboBoxList.clear();
        //помещаем названиями всех жанров в ArrayList
        for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
            comboBoxList.add(WorkWithList.getPaintingsListIterator().getCurrent().getGenre());
            WorkWithList.getPaintingsListIterator().nextLink();

        }
        //названия жанров заносятся из ArrayList в ComboBox жанров
        //повторяющиеся жанры удаляются из ArrayList,
        //чтобы в ComboBox не было нескольких одинаковых значений
        for (int i = 0; i<comboBoxList.size();i++){
            String comboBoxCell = comboBoxList.get(i);
            sortGenresComboBox.getItems().add(comboBoxCell);
            for (int j = i+1;j<comboBoxList.size();j++){
                if (comboBoxCell.equals(comboBoxList.get(j))){
                    comboBoxList.remove(j);
                    j--;
                }
            }
        }
        WorkWithList.getPaintingsListIterator().reset();
        //очищаем ArrayList для повторного заполнения названиями музеев и галерей
        comboBoxList.clear();
        //помещаем названия всех музеев и галерей в ArrayList
        for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
            comboBoxList.add(WorkWithList.getPaintingsListIterator().getCurrent().getLocation());
            WorkWithList.getPaintingsListIterator().nextLink();
        }
        //названия местоположений заносятся из ArrayList в ComboBox локаций
        //повторяющиеся локации удаляются из ArrayList,
        //чтобы в ComboBox не было нескольких одинаковых значений
        for (int i = 0; i<comboBoxList.size();i++){
            String comboBoxCell = comboBoxList.get(i);
            sortLocationComboBox.getItems().add(comboBoxCell);
            for (int j = i+1;j<comboBoxList.size();j++){
                if (comboBoxCell.toLowerCase().equals(comboBoxList.get(j).toLowerCase())){
                    comboBoxList.remove(j);
                    j--;
                }
            }
        }
    }

    //метод, помещающий картину и её название в левый VBox
    @FXML
    private void addLeftVBox(int i){
        //новый укрупненный шрифт, чтобы название картины было лучше видно
        Font font = new Font("System", 16);
        //создание объекта класса Image
        //используем метод toURI() класса, чтобы получить абстрактный путь к файлу с изображением
        File file = new File(WorkWithList.getPaintingsListIterator().getCurrent().getImageLink());
        Image imageLeft = new Image(file.toURI().toString(),330,330,true,false);
        //создание объекта класса ImageView для отображения картины в приложении
        //с ранее созданным объектом класса Image
        ImageView imageViewLeft = new ImageView(imageLeft);
        //добавление обработки события нажатия на изображение картины
        imageViewLeft.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            WorkWithList.getPaintingsListIterator().reset();
            //цикл для поиска нужной картины по её номеру
            //чтобы установить её текущим элементом списка
            for (int j = 0; j < i; j++){
                WorkWithList.getPaintingsListIterator().nextLink();
            }
            try {
                callPaintingInfo();//открытие окна просмотра картины
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        });
        //помещаем ImageView c изображением картины в VBox
        VBoxLeft.getChildren().add(imageViewLeft);
        //создаём метку с названием картины
        Label nameOfThePainting = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getName());
        //устанавливаем соданный ранее шрифт
        nameOfThePainting.setFont(font);
        //помещаем метку в VBox
        VBoxLeft.getChildren().add(nameOfThePainting);
    }

    //метод, помещающий картину и её название в правый VBox
    //имеет такой же функционал, как и addLeftVBox
    @FXML
    private void addRightVBox(int i){
        Font font = new Font("System", 16);
        File file = new File(WorkWithList.getPaintingsListIterator().getCurrent().getImageLink());
        Image imageRight = new Image(file.toURI().toString(),330,330,true,false);
        ImageView imageViewRight = new ImageView(imageRight);
        imageViewRight.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            WorkWithList.getPaintingsListIterator().reset();
            for (int j = 0; j < i; j++){
                WorkWithList.getPaintingsListIterator().nextLink();
            }
            try {
                callPaintingInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        });
        VBoxRight.getChildren().add(imageViewRight);
        Label nameOfThePainting = new Label(WorkWithList.getPaintingsListIterator().getCurrent().getName());
        nameOfThePainting.setFont(font);
        VBoxRight.getChildren().add(nameOfThePainting);
    }

    //метод, отвечающий за вывод на экран окна просмотра картины
    @FXML
    private void callPaintingInfo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/forms/paintingInfo.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setTitle("Информация о картине");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        //после закрытия окна просмотра происходит обновление содержимого главного окна
        //запоминаем текущие значения ComboBox
        String authorComboBoxValue = sortAuthorsComboBox.getValue();
        String genreComboBoxValue = sortGenresComboBox.getValue();
        String locationComboBoxValue = sortLocationComboBox.getValue();
        //очищаем ComboBox
        sortAuthorsComboBox.getItems().clear();
        sortGenresComboBox.getItems().clear();
        sortLocationComboBox.getItems().clear();
        //заново заполняем ComboBox
        fillComboBoxes();
        //устанавливаем дефолтное значение ComboBox
        sortAuthorsComboBox.setValue("Все художники");
        WorkWithList.getPaintingsListIterator().reset();
        for (int i = 0; i<WorkWithList.getPaintingsList().size();i++){
            //если в списке ещё содержится запомненное ранее значение ComboBox,
            //оно устанавливается текущим значением ComboBox
            if (WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().equals(authorComboBoxValue)){
                sortAuthorsComboBox.setValue(authorComboBoxValue);
                break;
            }
            WorkWithList.getPaintingsListIterator().nextLink();
        }
        //устанавливаем дефолтное значение ComboBox
        sortGenresComboBox.setValue("Все жанры");
        WorkWithList.getPaintingsListIterator().reset();
        for (int i = 0; i<WorkWithList.getPaintingsList().size();i++){
            //если в списке ещё содержится запомненное ранее значение ComboBox,
            //оно устанавливается текущим значением ComboBox
            if (WorkWithList.getPaintingsListIterator().getCurrent().getGenre().equals(genreComboBoxValue)){
                sortGenresComboBox.setValue(genreComboBoxValue);
                break;
            }
            WorkWithList.getPaintingsListIterator().nextLink();
        }
        //устанавливаем дефолтное значение ComboBox
        sortLocationComboBox.setValue("Любая локация");
        WorkWithList.getPaintingsListIterator().reset();
        for (int i = 0; i<WorkWithList.getPaintingsList().size();i++){
            //если в списке ещё содержится запомненное ранее значение ComboBox,
            //оно устанавливается текущим значением ComboBox
            if (WorkWithList.getPaintingsListIterator().getCurrent().getLocation().equals(locationComboBoxValue)){
                sortLocationComboBox.setValue(locationComboBoxValue);
                break;
            }
            WorkWithList.getPaintingsListIterator().nextLink();
        }
        //если строка поиска не пуста, заново сортируем картины по категориям,
        //в другом случае поиск повторяется
        //сделано это, чтобы не потерять результаты сортировки и поиска после просмотра картины
        if (searchTextField.getText().isEmpty()) sortPaintings();
        else search();
    }

    //метод, отвечающий за сортировку картин по категориям из ComboBox
    @FXML
    void sortPaintings(){
        try {
            //создание объекта FileWriter
            FileWriter fw = new FileWriter("SpecificPaintingsInfo.txt");
            //запись головки таблицы
            fw.write("Image Link|Name|Author|Genre|Year|Material Of Basis|Dye|Location|\n");
            //сортировка отдельна от поиска, поэтому поле поиска очищается
            searchTextField.clear();
            WorkWithList.getPaintingsListIterator().reset();
            //очищаем содержимое VBox
            VBoxRight.getChildren().clear();
            VBoxLeft.getChildren().clear();
            //если были выбраны варианты "Все художники", "Все жанры", "Любая локация" содержимое VBox заполняется всеми картинами из списка
            if (sortAuthorsComboBox.getValue().equals("Все художники")&&sortGenresComboBox.getValue().equals("Все жанры")&&sortLocationComboBox.getValue().equals("Любая локация")) {
                fillVBoxes();
            }
            //далее, учитывая все возможные варианты выбора в ComboBox, на экран и в текстовый файл выводятся соответствующие выбору картины
            else if ((!sortAuthorsComboBox.getValue().equals("Все художники"))&&sortGenresComboBox.getValue().equals("Все жанры")&&sortLocationComboBox.getValue().equals("Любая локация")) {
                boolean paintingFound;
                for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                    //булевская переменная, флаг нахождения картины
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())){
                            paintingFound = true;//совпадение найдено
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    //если совпадение найдено
                    if (paintingFound){
                        addLeftVBox(i);//помещаем картину в VBox
                        writeSinglePaintingInfoInFile(fw);//записываем данные о ней в файл
                    }
                    else break;
                    i++;
                    if (i==WorkWithList.getPaintingsList().size()) break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addRightVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                }
            }
            else if (sortAuthorsComboBox.getValue().equals("Все художники")&&!sortGenresComboBox.getValue().equals("Все жанры")&&sortLocationComboBox.getValue().equals("Любая локация")) {
                boolean paintingFound;
                for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortGenresComboBox.getValue().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addLeftVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    i++;
                    if (i==WorkWithList.getPaintingsList().size()) break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortGenresComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound) {
                        addRightVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                }
            }
            else if (sortAuthorsComboBox.getValue().equals("Все художники")&&sortGenresComboBox.getValue().equals("Все жанры")&&!sortLocationComboBox.getValue().equals("Любая локация")) {
                boolean paintingFound;
                for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addLeftVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    i++;
                    if (i==WorkWithList.getPaintingsList().size()) break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addRightVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                }
            }
            else if ((!sortAuthorsComboBox.getValue().equals("Все художники"))&&sortGenresComboBox.getValue().equals("Все жанры")&&!sortLocationComboBox.getValue().equals("Любая локация")) {
                boolean paintingFound;
                for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())&&sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addLeftVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    i++;
                    if (i==WorkWithList.getPaintingsList().size()) break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())&&sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addRightVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                }
            }
            else if (sortAuthorsComboBox.getValue().equals("Все художники")&&!sortGenresComboBox.getValue().equals("Все жанры")&&!sortLocationComboBox.getValue().equals("Любая локация")) {
                boolean paintingFound;
                for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortGenresComboBox.getValue().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre())&&sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addLeftVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    i++;
                    if (i==WorkWithList.getPaintingsList().size()) break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortGenresComboBox.getValue().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre())&&sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addRightVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                }
            }
            else if ((!sortAuthorsComboBox.getValue().equals("Все художники"))&&!sortGenresComboBox.getValue().equals("Все жанры")&&sortLocationComboBox.getValue().equals("Любая локация")) {
                boolean paintingFound;
                for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())&&sortGenresComboBox.getValue().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addLeftVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    i++;
                    if (i==WorkWithList.getPaintingsList().size()) break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())&&sortGenresComboBox.getValue().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addRightVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                }
            }
            else{
                boolean paintingFound;
                for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())&&sortGenresComboBox.getValue().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre())&&sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                    if (paintingFound){
                        addLeftVBox(i);
                        writeSinglePaintingInfoInFile(fw);
                    }
                    else break;
                    i++;
                    if (i==WorkWithList.getPaintingsList().size()) break;
                    WorkWithList.getPaintingsListIterator().nextLink();
                    paintingFound = false;
                    while (i<WorkWithList.getPaintingsList().size()){
                        if (sortAuthorsComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())&&sortGenresComboBox.getValue().equals(WorkWithList.getPaintingsListIterator().getCurrent().getGenre())&&sortLocationComboBox.getValue().toLowerCase().equals(WorkWithList.getPaintingsListIterator().getCurrent().getLocation().toLowerCase())){
                            paintingFound = true;
                            break;
                        }
                        WorkWithList.getPaintingsListIterator().nextLink();
                        i++;
                    }
                        if (paintingFound){
                            addRightVBox(i);
                            writeSinglePaintingInfoInFile(fw);
                        }
                        else break;
                        WorkWithList.getPaintingsListIterator().nextLink();
                    }
                }
            fw.close();//закрываем поток FileWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод, отвечающий за запись данных об одной картине в текстовый файл
    private void writeSinglePaintingInfoInFile(FileWriter fw){
        try {
            fw.write(WorkWithList.getPaintingsListIterator().getCurrent().getImageLink() + "|" + WorkWithList.getPaintingsListIterator().getCurrent().getName() + "|" + WorkWithList.getPaintingsListIterator().getCurrent().getAuthor() +  "|" + WorkWithList.getPaintingsListIterator().getCurrent().getGenre() + "|" + WorkWithList.getPaintingsListIterator().getCurrent().getYear() +  "|" + WorkWithList.getPaintingsListIterator().getCurrent().getMaterialOfBasis()+ "|" + WorkWithList.getPaintingsListIterator().getCurrent().getDye() + "|" + WorkWithList.getPaintingsListIterator().getCurrent().getLocation() + "|\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод, отвечающий за вывод на экран картин, соответствующих поисковому запросу
    @FXML
    void search(){
        //если строка поискового запроса не является пустой или не состоит из пробелов
        if(!searchTextField.getText().trim().equals("")){
            WorkWithList.getPaintingsListIterator().reset();
            //очищаем VBox
            VBoxRight.getChildren().clear();
            VBoxLeft.getChildren().clear();
            //булевская переменная-флаг нахождения картины
            boolean paintingFound;
            String searchRequest = searchTextField.getText().trim().toLowerCase();
            for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                paintingFound = false;
                while (i<WorkWithList.getPaintingsList().size()){
                    //поиск ведётся по названию и автору картины с помощью метода checkSearchRequest,
                    //в параметры которого помещается строка поискового запроса и название или автор картины
                    if (checkSearchRequest(searchRequest,WorkWithList.getPaintingsListIterator().getCurrent().getName().toLowerCase())||checkSearchRequest(searchRequest,WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())){
                        paintingFound = true;
                        break;
                    }
                    WorkWithList.getPaintingsListIterator().nextLink();
                    i++;
                }
                //если картина была найдена, помещаем её в VBox
                if (paintingFound) addLeftVBox(i);
                else break;//иначе выходим из цикла
                i++;
                if (i==WorkWithList.getPaintingsList().size()) break;
                WorkWithList.getPaintingsListIterator().nextLink();
                paintingFound = false;
                while (i<WorkWithList.getPaintingsList().size()){
                    if (checkSearchRequest(searchRequest,WorkWithList.getPaintingsListIterator().getCurrent().getName().toLowerCase())||checkSearchRequest(searchRequest,WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().toLowerCase())){
                        paintingFound = true;
                        break;
                    }
                    WorkWithList.getPaintingsListIterator().nextLink();
                    i++;
                }
                if (paintingFound)addRightVBox(i);
                else break;
                WorkWithList.getPaintingsListIterator().nextLink();
            }
        }else{//если в поисковым запросом является пустая строка,
            //оцищаем поле ввода запроса и обновляем содержимое VBox
            searchTextField.clear();
            VBoxRight.getChildren().clear();
            VBoxLeft.getChildren().clear();
            fillVBoxes();
        }
    }

    //метод, отвечающий за вывод на экран окна добавления новой картине
    @FXML
    void callAddingMenu() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Добавление картины");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/forms/addingMenu.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        //после закрытия окна добавления происходит обновление содержимого главного окна
        //запоминаем текущие значения ComboBox
        String authorComboBoxValue = sortAuthorsComboBox.getValue();
        String genreComboBoxValue = sortGenresComboBox.getValue();
        String locationComboBoxValue = sortLocationComboBox.getValue();
        //очищаем ComboBox
        sortAuthorsComboBox.getItems().clear();
        sortGenresComboBox.getItems().clear();
        sortLocationComboBox.getItems().clear();
        //заполняем их заново данными из списка
        fillComboBoxes();
        //устанавливаем сохранённые значения ComboBox обратно
        sortAuthorsComboBox.setValue(authorComboBoxValue);
        sortGenresComboBox.setValue(genreComboBoxValue);
        sortLocationComboBox.setValue(locationComboBoxValue);
        //результаты сортировки и поиска после закрытия окна добавления должны сохраниться
        if (searchTextField.getText().isEmpty()) sortPaintings();
        else search();
    }

    //метод, отвечающий за поиск в строке str подстроки pat
    private static boolean checkSearchRequest(String pat, String str) {
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(str);
        return m.find();//если совпадение найдено возвращает true, не найдено - false
    }
}