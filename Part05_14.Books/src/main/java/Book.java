
public class Book {

    private String name;
    private int publicationYear;

    public Book(String name, int publicationYear) {
        this.name = name;
        this.publicationYear = publicationYear;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object compared) {
        Book book = (Book) compared;
        return this.name.equals(((Book) compared).name) && this.publicationYear == book.publicationYear;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

}
