import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    private static String libraryName, libraryLocation;
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static CellStyle CELLSTYLE = new CellStyle(CellStyle.HorizontalAlign.CENTER);
    private static Table table;
    private static final Book[] BOOKS = new Book[5];

    private static void defaultBooks() {
        BOOKS[0] = new Book(1, "Titanic", new Author("James Cameron", "1950-2010"), 1997, true);
        BOOKS[1] = new Book(2, "The Lord of the Rings", new Author("J.R.R. Tolkien", "1950-2008"), 1968, true);
        BOOKS[2] = new Book(3, "Godzilla Rings", new Author("Graham Skipper", "2015-2020"), 2015, false);
    }

    private static void tableHeader(){
        table.addCell("ID", CELLSTYLE);
        table.addCell("TITLE", CELLSTYLE);
        table.addCell("AUTHOR", CELLSTYLE);
        table.addCell("PUBLISH DATE", CELLSTYLE);
        table.addCell("STATUS", CELLSTYLE);
    }

    private static void clearTable(){
        table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER_WIDE, ShownBorders.ALL);
    }

    private static void tableBody(Book book){
        table.addCell(String.valueOf(book.getId()), CELLSTYLE);
        table.addCell(String.valueOf(book.getTitle()), CELLSTYLE);
        table.addCell(book.getAuthor().getAuthorName() + "( " + book.getAuthor().getYearActive() + " )", CELLSTYLE);
        table.addCell(String.valueOf(book.getPublishedYear()), CELLSTYLE);
        table.addCell(book.getStatus() ? "Available" : "Unavailable", CELLSTYLE);
    }

    private static void setUpLibrary() {
        System.out.println("========= SET UP LIBRARY =========");
        System.out.print("=> Enter Library's Name : ");
        libraryName = SCANNER.next();
        System.out.print("=> Enter Library's Location : ");
        libraryLocation = SCANNER.next();
        System.out.println(libraryName + " LIBRARY is already created in " + libraryLocation + " address successfully on " + LocalDateTime.now());
        defaultBooks();
        chooseOption();
    }

    private static void chooseOption() {
        while (true) {
            System.out.println("========= " + libraryName.toUpperCase() + " LIBRARY ," + libraryLocation.toUpperCase() + " =========");
            System.out.println("""
                    1- Add Book
                    2- Show All Books
                    3- Show Available Books
                    4- Borrow Book
                    5- Return Book
                    6- Exit
                    """);
            System.out.println("-----------------------------------------");
            System.out.print("=> Choose option(1-6) : ");
            int option = SCANNER.nextInt();
            switch (option) {
                case 1 -> addNewBook();
                case 2 -> showAllBooks();
                case 3 -> showAvailableBooks();
                case 4 -> borrowBook();
                case 5 -> returnBook();
                case 6 -> {
                    System.out.println("(^-^) Good Bye! (^-^)");
                    System.exit(0);
                }
            }
        }
    }

    private static void showAllBooks() {
        System.out.println("========= ALL BOOKS INFO =========");
        clearTable();
        tableHeader();
        for (Book book : BOOKS) {
            if (book != null) {
                tableBody(book);
            }
        }
        System.out.println(table.render());
    }

    private static void addNewBook(){
        System.out.println("========= ADD BOOK INFO =========");
        for (int i = 0; i < BOOKS.length; i++){
            if(BOOKS[i] == null){
                BOOKS[i] = new Book();
                Author author = new Author();
                System.out.println("=> Book ID : " + (BOOKS.length + 1));
                BOOKS[i].setId(BOOKS.length + 1);
                System.out.print("=> Enter Book's Name : ");
                BOOKS[i].setTitle(SCANNER.next());
                System.out.print("=> Enter Book Author Name : ");
                author.setAuthorName(SCANNER.next());
                System.out.print("=> Enter Author Year Active : ");
                author.setYearActive(SCANNER.next());
                BOOKS[i].setAuthor(author);
                System.out.print("=> Enter Published Year : ");
                BOOKS[i].setPublishedYear(SCANNER.nextInt());
                BOOKS[i].setStatus(true);
                System.out.println("Book is added successfully");
                break;
            }
        }
    }

    private static void showAvailableBooks() {
        System.out.println("========= AVAILABLE BOOKS INFO =========");
        clearTable();
        tableHeader();
        for (Book book : BOOKS) {
            if (book != null && book.getStatus()) {
                tableBody(book);
            }
        }
        System.out.println(table.render());
    }

    private static void borrowBook(){
        System.out.println("========= BORROW BOOK INFO =========");
        System.out.print("=> Enter Book ID to Borrow : ");
        int bookId = SCANNER.nextInt();
        boolean isNotExist = false;
        for (Book book : BOOKS){
            if(book != null && book.getId().equals(bookId)){
                if (book.getStatus()) {
                    System.out.println("Book ID : " + bookId);
                    System.out.println("Book Title : " + book.getTitle());
                    System.out.println("Book Author : " + book.getAuthor().getAuthorName() + "( " + book.getAuthor().getYearActive() + " )");
                    System.out.println("Published Year : " + book.getPublishedYear() + " is borrowed successfully...");
                    book.setStatus(false);
                } else {
                    System.out.println("Book ID : " + bookId + " is already borrowed...");
                }
                isNotExist = true;
                break;
            }
        }
        if (!isNotExist){
            System.out.println("Book ID : " + bookId + " not Exist...");
        }
    }

    private static void returnBook(){
        System.out.println("========= RETURN BOOK INFO =========");
        System.out.print("=> Enter Book ID to Return : ");
        int bookId = SCANNER.nextInt();
        boolean isNotExist = false;
        for (Book book : BOOKS){
            if(book != null && book.getId().equals(bookId)){
                if (!book.getStatus()) {
                    System.out.println("Book ID : " + bookId);
                    System.out.println("Book Title : " + book.getTitle());
                    System.out.println("Book Author : " + book.getAuthor().getAuthorName() + "( " + book.getAuthor().getYearActive() + " )");
                    System.out.println("Published Year : " + book.getPublishedYear() + " is returned successfully...");
                    book.setStatus(true);
                } else {
                    System.out.println("Book ID : " + bookId + " is failed to return...");
                }
                isNotExist = true;
                break;
            }
        }
        if (!isNotExist){
            System.out.println("Book ID : " + bookId + " not Exist...");
        }
    }

    public static void main(String[] args) {
        setUpLibrary();
    }
}