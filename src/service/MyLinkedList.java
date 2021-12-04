package service;
//класс списка
public class MyLinkedList{
    private Link first; //ссылка на первый элемент списка
    private int size = 0;//поле, отвечающее за размер списка
    MyLinkedList(){ //конструктор списка
        first = null; //список пока не содержит элементов
    }
    public int size(){//метод, возвращающий значение размера списка
        return size;
    }
    void incSize(){//метод, увеличивающий размер списка на единицу
        size++;
    }
    void decSize(){//метод, уменьшающий размер списка на единицу
        size--;
    }
    Link getFirst(){//метод, возвращающий первый элемент списка
        return first;
    }
    void setFirst(Link link){//метод для установки нового первого элемента
        first = link;
    }
    boolean isEmpty(){//метод, возвращающий true, если список пуст
        return first==null;
    }
    void clear(){//метод для очистки списка
        first = null;
        size = 0;//обнуляем размер
    }
}
