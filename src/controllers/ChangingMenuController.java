package controllers;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import entity.Paintings;
import service.WorkWithList;

//класс, отвечающий за окно изменения информации о картине
public class ChangingMenuController {

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> genreComboBox;

    @FXML
    private ComboBox<String> dyeComboBox;

    @FXML
    private ComboBox<String> materialOfBasisComboBox;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Button confirmChangingButton;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextField locationTextField;


    @FXML
    void initialize() {
        //заполняем текстовые поля данными о текущем элементе списка
        nameTextField.setText(WorkWithList.getPaintingsListIterator().getCurrent().getName());
        authorTextField.setText(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor());
        yearTextField.setText(WorkWithList.getPaintingsListIterator().getCurrent().getYear());
        imageTextField.setText(WorkWithList.getPaintingsListIterator().getCurrent().getImageLink());
        locationTextField.setText(WorkWithList.getPaintingsListIterator().getCurrent().getLocation());
        //заполняем ComboBox всеми возможными вариантами выбора
        genreComboBox.getItems().addAll("Портрет","Натюрморт","Пейзаж","Марина","Историческая живопись","Батальная живопись","Жанровая живопись","Архитектурная живопись","Живопись на религиозные темы","Анималистическая живопись","Абстрактная живопись");
        materialOfBasisComboBox.getItems().addAll("холст", "деревянная доска", "картон", "бумага","стена","штукатурка");
        dyeComboBox.getItems().addAll("масло","темпера","эмаль","гуашь","пастель","тушь","граттаж","клеевые краски","восковые краски","акварель","акрил","смешанная техника");
        //устанавливаем выбранными значения текущего элемента списка картин
        genreComboBox.setValue(WorkWithList.getPaintingsListIterator().getCurrent().getGenre());
        materialOfBasisComboBox.setValue(WorkWithList.getPaintingsListIterator().getCurrent().getMaterialOfBasis());
        dyeComboBox.setValue(WorkWithList.getPaintingsListIterator().getCurrent().getDye());
        //кнопки, при наведении на них мышкой, меняют свой цвет
        materialOfBasisComboBox.setOnMouseEntered(mouseEvent -> materialOfBasisComboBox.setStyle("-fx-background-color: #47cffc;"));
        materialOfBasisComboBox.setOnMouseExited(mouseEvent -> materialOfBasisComboBox.setStyle("-fx-background-color: #BDDABC;"));
        dyeComboBox.setOnMouseEntered(mouseEvent -> dyeComboBox.setStyle("-fx-background-color: #47cffc;"));
        dyeComboBox.setOnMouseExited(mouseEvent -> dyeComboBox.setStyle("-fx-background-color: #BDDABC;"));
        genreComboBox.setOnMouseEntered(mouseEvent -> genreComboBox.setStyle("-fx-background-color: #47cffc;"));
        genreComboBox.setOnMouseExited(mouseEvent -> genreComboBox.setStyle("-fx-background-color: #BDDABC;"));
        confirmChangingButton.setOnMouseEntered(mouseEvent -> confirmChangingButton.setStyle("-fx-background-color: #47cffc;"));
        confirmChangingButton.setOnMouseExited(mouseEvent -> confirmChangingButton.setStyle("-fx-background-color: #BDDABC;"));
        backToMenuButton.setOnMouseEntered(mouseEvent -> backToMenuButton.setStyle("-fx-background-color: #47cffc;"));
        backToMenuButton.setOnMouseExited(mouseEvent -> backToMenuButton.setStyle("-fx-background-color: #BDDABC;"));
    }

    //метод, отвечающий за проверку ввода и добавление изменённого объекта в список картин
    @FXML
    void confirmChanging(){
        //создаём флаг, отвечающий за некорректность ввода
        boolean isInputIncorrect = false;
        //названием картины может быть любой набор символов, кроме символа вертикальной черты
        if(nameTextField.getText().trim().equals("") || isInputIncorrectCheck("\\|",nameTextField.getText())) isInputIncorrect = true;
        //именем художника должна являться строка состоящая из символов латиницы и кириллицы, а также символов дефиса и пробела
        if (authorTextField.getText().trim().equals("") || isInputIncorrectCheck("[^a-zA-Zа-яА-Я[\\s][\\-]]", authorTextField.getText())) isInputIncorrect = true;
        //местоположением должна являться строка состоящая из символов латиницы и кириллицы, а также символов дефиса, пробела и запятой
        if (locationTextField.getText().trim().equals("")||isInputIncorrectCheck("[^a-zA-Zа-яА-Я[\\s][\\,][\\-]]",locationTextField.getText())) isInputIncorrect = true;
        //годом написания может быть строка состоящая из 3 или 4 цифровых символов, таких что получившееся число будет меньше 2021
        if (!(Pattern.matches("\\d{3,4}",yearTextField.getText().trim())&&Integer.parseInt(yearTextField.getText().trim())<2021)) isInputIncorrect = true;
        //делим строку пути к файлу с изображением картины вокруг символа точки и заносим результат в массив
        String[] mas = imageTextField.getText().split("\\.");
        //последним элементом массива будет расширение файла, которое может быть png, jpg, gif
        if (!(mas[mas.length - 1].equals("jpg") || mas[mas.length - 1].equals("png") || mas[mas.length - 1].equals("gif"))) isInputIncorrect = true;
        //проверяем существует ли файл с изображением
        File file = new File(imageTextField.getText());
        if (!file.isFile()) isInputIncorrect = true;
        //если хотя бы одна проверка не была пройдена, выведется окно с сообщением о некорректном вводе
        if (isInputIncorrect) showError();
        //если же ввод был правильным
        else{
            //удаляем текущий элемент списка
            WorkWithList.getPaintingsListIterator().deleteCurrent();
            //устанавливаем первый элемент текущим
            WorkWithList.getPaintingsListIterator().reset();
            //получаем фамилию автора, разделив строку имени вокруг символа пробела и взяв последнюю строку результата разделения
            String lastNameOfAuthor = authorTextField.getText().trim().split("\\s+")[authorTextField.getText().trim().split("\\s+").length-1];
            //цикл поиска места вставки
            for (int i = 0; i<WorkWithList.getPaintingsList().size(); i++){
                if ((lastNameOfAuthor.compareTo(WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().split("\\s+")[WorkWithList.getPaintingsListIterator().getCurrent().getAuthor().split("\\s+").length-1]))<0){
                    break;
                }
                WorkWithList.getPaintingsListIterator().nextLink();
            }
            //добавляем изменённый объект в список по его месту вставки
            WorkWithList.getPaintingsListIterator().addBefore(new Paintings(imageTextField.getText(),nameTextField.getText(),authorTextField.getText(),genreComboBox.getValue(),yearTextField.getText(),materialOfBasisComboBox.getValue(),dyeComboBox.getValue(),locationTextField.getText()));
            //возрат в меню просмотра после вставки
            backToInfoMenu();
        }
    }

    //метод, отвечающий за вывод окна с собщением о том, что ввод некорректен
    private void showError() {
        Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR);
        invalidInputAlert.setTitle("Ошибка");
        invalidInputAlert.setHeaderText("Некорректный ввод данных");
        invalidInputAlert.showAndWait();
    }

    //метод, отвечающий за закрытие текущего окна и возврат в меню просмотра
    @FXML
    void backToInfoMenu() {
        Stage stage = (Stage) backToMenuButton.getScene().getWindow();
        stage.close();

    }

    //метод, отвечающий за поиск в строке str подстроки pat
    private static boolean isInputIncorrectCheck(String pat, String str) {
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(str);
        return m.find();
    }
}

