import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;


public class StreamCode {

public static void main(String args[]){

    Book yuwen1  = new Book("yuwen",110);
    Book shuxue1  = new Book("shuxue",120);
    Book yinyu1  = new Book("yinyu",130);
    Book wuli1 = new Book("wuli",140);
    Book huaxue1  = new Book("huaxue",150);

    Student zhangsan = new Student("zhangsan",100);
    zhangsan.books = new ArrayList<>();
    zhangsan.books.add(yuwen1);
    zhangsan.books.add(shuxue1);
    zhangsan.books.add(yinyu1);
    zhangsan.books.add(wuli1);
    zhangsan.books.add(huaxue1);



    Book yuwen2  = new Book("yuwen",210);
    Book shuxue2  = new Book("shuxue",220);
    Book yinyu2  = new Book("yinyu",230);
    Book wuli2  = new Book("wuli",240);
    Book huaxue2  = new Book("huaxue",250);

    Student lisi = new Student("lisi",200);
    lisi.books = new ArrayList<>();
    lisi.books.add(yuwen2);
    lisi.books.add(shuxue2);
    lisi.books.add(yinyu2);
    lisi.books.add(wuli2);
    lisi.books.add(huaxue2);



    Book yuwen3  = new Book("yuwen",310);
    Book shuxue3  = new Book("shuxue",320);
    Book yinyu3 = new Book("yinyu",330);
    Book wuli3  = new Book("wuli",340);
    Book huaxue3  = new Book("huaxue",350);
    Student wangwu = new Student("wangwu",300);
    wangwu.books = new ArrayList<>();
    wangwu.books.add(yuwen3);
    wangwu.books.add(shuxue3);
    wangwu.books.add(yinyu3);
    wangwu.books.add(wuli3);
    wangwu.books.add(huaxue3);

    School zhenghua = new School("zhenghua");
    zhenghua.students = new ArrayList<>();
    zhenghua.students.add(zhangsan);

    School jinfan = new School("jinfan");
    jinfan.students = new ArrayList<>();
    jinfan.students.add(lisi);
    jinfan.students.add(wangwu);

    School yizhong = new School("yizhong");
    yizhong.students = new ArrayList<>();


    List<School> schools = new ArrayList<School>();
    schools.add(zhenghua);
    schools.add(jinfan);
    schools.add(yizhong);


    schools.stream()
            .forEach(school -> school.students.stream()
                    .forEach(student -> student.books.stream()
                            .forEach(book -> {System.out.println(book.name);System.out.println(book.pages);})));
    }
}


class School{
    String name;
    List<Student> students;
    public School(String name) {
        this.name = name;
    }
}

class Student {
    String name;
    float height;
    List<Book> books;

    public Student(String name, float height) {
        this.name = name;
        this.height = height;
    }
}

class Book{
    String name;
    int pages;

    public Book(String name,int pages){
        this.name = name;
        this.pages = pages;
    }
}