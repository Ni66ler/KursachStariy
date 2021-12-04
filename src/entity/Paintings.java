package entity;

//класс, на основе которого создаются объекты картин
public class Paintings {
    private String imageLink;//поле, хранящее путь к файлу с изображением картины
    private String name;//поле, хранящее название картины
    private String author;//поле, хранящее автора картины
    private String genre;//поле, хранящее жанр картины
    private String year;//поле, хранящее год написания картины
    private String materialOfBasis;//поле, хранящее материал, на котором была выполнена картина (холст, бумага и т.п.)
    private String dye;//поле, хранящее название художественного материала, которым картина была написана
    private String location;//поле, хранящее текущее местоположение картины
    //конструктор класса
    public Paintings(String imageLink, String name, String author, String genre, String year, String materialOfBasis, String dye, String location){
        this.imageLink = imageLink;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.materialOfBasis = materialOfBasis;
        this.dye = dye;
        this. location = location;
    }
    //get-методы для каждого поля класса
    public String getImageLink(){
        return imageLink;
    }
    public String getName(){
        return name;
    }
    public String getAuthor(){
        return author;
    }
    public String getGenre(){
        return genre;
    }
    public String getYear(){
        return year;
    }
    public String getMaterialOfBasis(){
        return materialOfBasis;
    }
    public String getDye(){
        return dye;
    }
    public String getLocation(){
        return location;
    }

}

