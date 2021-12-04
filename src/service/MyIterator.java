package service;
import entity.Paintings;
//класс итератора
public class MyIterator{
    private Link current;//ссылка на текущий элемент списка
    private Link previous;//ссылка на предыдущий элемент списка
    private MyLinkedList list;//ссылка на "родительский" список
    MyIterator(MyLinkedList list){//конструктор итератора
        this.list = list;
        reset();
    }
    public void reset(){//метод для возвращения к первому элементу
        current = list.getFirst();
        previous = null;
    }
    public void nextLink(){//метод для перехода к следующему элементу
        previous = current;//присваивание текущего элемента предыдущему
        current = current.next;//присваивание следующего элементу текущему
    }
    public Paintings getCurrent(){//получение текущего элемента
        return current.data;
    }
    //метод для вставки после текущего элемента, используется чтобы добавлять элементы в конец списка
    void addAfter(Paintings d){
        Link newLink = new Link(d);//создание нового звена списка
        if(list.isEmpty()){//если список пуст, новый элемент устанавливается первым в списке
            list.setFirst(newLink);
            current = newLink;//ссылка на текущий элемент теперь указывает на новое звено
        }else{//если же список не пуст
            newLink.next = current.next;//новый элемент должен указывать на следующий за текущим элементом
            current.next = newLink;//следующий за текущим элемент должен указывать на новое звено списка
            nextLink();//переход к новому элементу
        }
        list.incSize();//увеличиваем размер списка
    }
    //метод для вставки перед текущим элементом, используется чтобы добавлять и изменять элементы по индексу
    public void addBefore(Paintings d){
        Link newLink = new Link(d);//создание нового звена списка
        if(previous == null){//если в начале списка(или список пуст)
            newLink.next = list.getFirst();//новый элемент должен указывать на первый
            list.setFirst(newLink);//новый элемент устанавливается первым в списке
            reset();
        }
        else{//если же не в начале списка
            newLink.next = previous.next;//новый элемент должен указывать на текущий
            previous.next = newLink;//элемент, стоящий перед текущим, должен указывать на новое звено
            current = newLink;//новое звено становится текущим элементом списка
        }
        list.incSize();//увеличиваем размер списка
    }
    public void deleteCurrent(){//метод для удаления текущего элемента
        if(list.size() == 1) list.clear();//если в списке только 1 элемент, очищаем список
        else {//иначе
            if (previous == null) {//если текущий элемент является первым
                list.setFirst(current.next);//устанавливаем первым элемент, следующий за текущим
                reset();
            } else {//если текущий элемент не является первым
                previous.next = current.next;//элемент, стоящий перед текущим должен ссылаться на следующее за текущим звено
                current = current.next;//устанавливаем текущим следующий элемент
            }
            list.decSize();//уменьшаем размер списка
        }
    }
}

