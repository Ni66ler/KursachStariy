package service;
import entity.Paintings;
//класс-звено, на основе которого создаются элементы списка
class Link{
    Paintings data; //данные класса Paintings, хранимые в звене
    Link next;//поле, в котором хранится ссылка на следующий элемент списка
    Link(Paintings d){ //конструктор создания звена списка
        data = d;//присваивание данных типа Paintings
    }
}