package service;

import entity.Paintings;
import javafx.scene.control.Alert;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WorkWithList {
    //создание объекта класса MyLinkedList
    private static MyLinkedList paintingsList = new MyLinkedList();
    //создание объекта класса MyIterator
    private static MyIterator paintingsListIterator= new MyIterator(paintingsList);
    //get-методы для списка и итератора
    public static MyLinkedList getPaintingsList() {
        return paintingsList;
    }
    public static MyIterator getPaintingsListIterator() {
        return paintingsListIterator;
    }
    //метод, отвечающий за запись данных о картинах из списка в файл
    public static void loadListInFile(){
        //может появиться исключение, поэтому используем try-catch
        try {
            FileWriter fw = new FileWriter("AllPaintingsInfo.txt");//создание объекта FileWriter
            fw.write("Image Link|Name|Author|Genre|Year|Material Of Basis|Dye|Location|\n");//запись головки таблицы
            getPaintingsListIterator().reset();
            for (int i = 0; i < getPaintingsList().size(); i++) {
                fw.write(getPaintingsListIterator().getCurrent().getImageLink() + "|" + getPaintingsListIterator().getCurrent().getName() + "|" + getPaintingsListIterator().getCurrent().getAuthor() +  "|" + getPaintingsListIterator().getCurrent().getGenre() + "|" + getPaintingsListIterator().getCurrent().getYear() +  "|" + getPaintingsListIterator().getCurrent().getMaterialOfBasis()+ "|" + getPaintingsListIterator().getCurrent().getDye() + "|" + getPaintingsListIterator().getCurrent().getLocation() + "|\n");
                getPaintingsListIterator().nextLink();
            }
            fw.close();//закрываем поток FileWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //метод, отвечающий за запись данных из файла в список картин
    public static void loadListFromFile(){
        if (Files.exists(Paths.get("AllPaintingsInfo.txt"))) {//проверка, существует ли файл
            List listForLoadingInfoFromFile = null;
            //может появиться исключение, поэтому используем try-catch
            try {//try определяет блок кода, в котором может произойти ошибка
                listForLoadingInfoFromFile = Files.readAllLines(Paths.get("AllPaintingsInfo.txt"));//считывание данных из файла и запись их в List
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (listForLoadingInfoFromFile.size()<2){
                showError();//если файл является пустым, на экран выводится окно с собщением о том, что список пуст
            }
            else {
                //проходка по List, начиная с 1-го элемента, т.к. в 0-ом элементе содержится головка таблицы
                getPaintingsListIterator().reset();
                for (int i = 1; i < listForLoadingInfoFromFile.size(); i++) {
                    String[] strMas = ((String) listForLoadingInfoFromFile.get(i)).split("\\|");
                    //проверка, существует ли файл с изображением, если его нет картина не добавтся в список
                    if (new File(strMas[0]).isFile()) {
                        //создание и добавление нового объекта в конец списка
                        getPaintingsListIterator().addAfter(new Paintings(strMas[0], strMas[1], strMas[2], strMas[3], strMas[4], strMas[5], strMas[6], strMas[7]));//создание и добавление нового объекта в конец списка
                    }
                }
            }
        }else showError();//если файл не существует на экран выводится окно с собщением о том, что список пуст
    }

    //метод, отвечающий за вывод окна с собщением о том, что список пуст
    private static void showError(){
        Alert listIsEmpty = new Alert(Alert.AlertType.ERROR);
        listIsEmpty.setTitle("Ошибка");
        listIsEmpty.setHeaderText("Список пуст!");
        listIsEmpty.showAndWait();
    }

}
