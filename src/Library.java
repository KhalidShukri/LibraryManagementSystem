import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class representing a library with methods to add books, borrow books,
 * return books, and display available books.
 */
public class Library {

    private List<Book> books; // List of all books
    private Map<String, List<Book>> borrowedBooks; // Map of member name to list of borrowed books
    private Scanner scanner;

    /**
     * Constructor for the Library class.
     */
    public Library() {
        this.books = new ArrayList<>();
        this.borrowedBooks = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Method to add a book to the list.
     * @param book The Book object to be added.
     */
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book with title: " + book.getTitle() + " added");
    }

    /**
     * Method to borrow a book.
     * @param memberName The name of the member borrowing the book.
     * @param bookTitle The title of the book being borrowed.
     */
    public void borrowBook(String memberName, String bookTitle) {
        Book book = findBook(bookTitle);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            borrowedBooks.computeIfAbsent(memberName, k -> new ArrayList<>()).add(book);
            System.out.println(memberName + " has borrowed: " + bookTitle);
        } else {
            System.out.println("Book with Title: " + bookTitle + " Not Available");
        }
    }

    /**
     * Method to return a book.
     * @param memberName The name of the member returning the book.
     * @param bookTitle The title of the book being returned.
     */
    public void returnBook(String memberName, String bookTitle) {
        List<Book> memberBooks = borrowedBooks.get(memberName);
        if (memberBooks != null) {
            for (Book book: books) {
                if (book.getTitle().equals(bookTitle)) {
                    book.setAvailable(true);
                    memberBooks.remove(book);
                    System.out.println(memberName + " has returned: " + book.getTitle());
                    break; // Assuming only one copy of each title can be borrowed
                }
            }
        } else {
            System.out.println("Book with Title: " + bookTitle + " not found in the borrowed list");
        }
    }

    /**
     * Method to find a book by title.
     * @param title The title of the book to find.
     * @return The Book object with the given title, or null if not found.
     */
    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.isAvailable()) {
                return book;
            }
        }
        return null;
    }

    /**
     * Method to display available books.
     */
    public void displayAvailableBooks() {
        System.out.println("Available Books: ");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println("-" + book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    /**
     * Method to display a menu to the user and handle user input.
     */
    public void run() {
        while (true) {
            System.out.println("\n --- Library Management System ---");
            System.out.println("1.Add a Book");
            System.out.println("2.Borrow a Book");
            System.out.println("3.Return a Book");
            System.out.println("4.Display Available Books");
            System.out.println("5.Exit");
            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBookMenu();
                    break;
                case 2:
                    borrowBookMenu();
                    break;
                case 3:
                    returnBookMenu();
                    break;
                case 4:
                    displayAvailableBooks();
                    break;
                case 5:
                    System.out.println("Thank you for using the Library Management System");
                    break;
                default:
                    System.out.println("Invalid Choice, Please Try Again");
            }

        }
    }

    /**
     * Method to handle adding a book menu.
     */
    public void addBookMenu(){
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        addBook(new Book(title,author));
    }

    /**
     * Method to handle borrowing a book menu.
     */
    public void borrowBookMenu(){
        System.out.print("Enter Member Name: ");
        String memberName = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        borrowBook(memberName,title);
    }

    /**
     * Method to handle returning a book menu.
     */
    public void returnBookMenu(){
        System.out.print("Enter Member Name: ");
        String memberName = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        returnBook(memberName,title);
    }

}
