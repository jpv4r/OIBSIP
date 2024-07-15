import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Book{
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private boolean isAvail;

    public Book(Long id,String title,String author,String isbn,boolean isAvail){
        this.id=id;
        this.title=title;
        this.author=author;
        this.isbn=isbn;
        this.isAvail=isAvail;
    }
    public Long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getIsbn(){
        return isbn;
    }
    public boolean isAvail(){
        return isAvail;
    }
    public void setAvailable(boolean isAvail){
        this.isAvail=isAvail;
    }
}
class Member{
    private Long id;
    private String name;
    private String email;
    private String phone;

    public Member(Long id,String name,String email,String phone){
        this.id=id;
        this.name=name;
        this.email=email;
        this.phone=phone;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
}
class Transaction{
    private Long id;
    private Long bookId;
    private Long memberId;
    private Date issueDate;
    private Date returnDate;
    private double fine;

    public Transaction(Long id,Long bookId,Long memberId,Date issueDate,Date returnDate,double fine){
        this.id=id;
        this.bookId=bookId;
        this.memberId=memberId;
        this.issueDate=issueDate;
        this.returnDate=returnDate;
        this.fine=fine;
    }
    public Long getId(){
        return id;
    }
    public Long getBookId(){
        return bookId;
    }
    public Long getMemberId(){
        return memberId;
    }
    public Date getIssueDate(){
        return issueDate;
    }
    public Date getReturnDate(){
        return returnDate;
    }
    public double getFine(){
        return fine;
    }
    public void setReturnDate(Date returnDate){
        this.returnDate=returnDate;
    }
}
class LibraryService{
    private List<Book> books;
    private List<Member> members;
    private List<Transaction> transactions;
    private long bookIdCounter;
    private long memberIdCounter;

    public LibraryService(){
        books=new ArrayList<>();
        members=new ArrayList<>();
        transactions=new ArrayList<>();
        bookIdCounter=1;
        memberIdCounter=1;
    }
    public void addBook(String title,String author,String isbn){
        Book book=new Book(bookIdCounter++,title,author,isbn,true);
        books.add(book);
        System.out.println("Book added: "+book.getTitle());
    }
    public void updateBook(Book book){
        for(Book b:books){
            if(b.getId().equals(book.getId())){
                b.setAvailable(book.isAvail());
                System.out.println("Book updated: "+b.getTitle());
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void deleteBook(Long bookId){
        books.removeIf(book->book.getId().equals(bookId));
        System.out.println("Book deleted.");
    }

    public Book getBook(Long bookId){
        for(Book book:books){
            if(book.getId().equals(bookId)){
                return book;
            }
        }
        System.out.println("Book not found.");
        return null;
    }

    public List<Book> searchBooks(String query){
        List<Book> results=new ArrayList<>();
        for(Book book:books){
            if(book.getTitle().toLowerCase().contains(query.toLowerCase())|| book.getAuthor().toLowerCase().contains(query.toLowerCase())){
                results.add(book);
            }
        }
        return results;
    }

    public void addMember(String name,String email,String phone){
        Member member=new Member(memberIdCounter++,name,email,phone);
        members.add(member);
        System.out.println("Member added: "+member.getName());
    }

    public void updateMember(Member member){
        for(Member m:members){
            if(m.getId().equals(member.getId())){
                m=member;
                System.out.println("Member updated: "+m.getName());
                return;
            }
        }
        System.out.println("Member not found.");
    }

    public void deleteMember(Long memberId){
        members.removeIf(member -> member.getId().equals(memberId));
        System.out.println("Member deleted.");
    }

    public Member getMember(Long memberId){
        for(Member member:members){
            if(member.getId().equals(memberId)){
                return member;
            }
        }
        System.out.println("Member not found.");
        return null;
    }

    public void issueBook(Long bookId,Long memberId){
        Book book=getBook(bookId);
        if(book != null && book.isAvail()){
            book.setAvailable(false);
            Transaction transaction=new Transaction((long)(transactions.size()+ 1),bookId,memberId,new Date(),null,0.0);
            transactions.add(transaction);
            System.out.println("Book issued: "+book.getTitle()+ " to member ID "+memberId);
        }
        else{
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(Long bookId,Long memberId){
        Book book=getBook(bookId);
        if(book != null && !book.isAvail()){
            book.setAvailable(true);
            for(Transaction transaction:transactions){
                if(transaction.getBookId().equals(bookId)&& transaction.getMemberId().equals(memberId)){
                    transaction.setReturnDate(new Date());
                    System.out.println("Book returned: "+book.getTitle()+ " by member ID "+memberId);
                    return;
                }
            }
        }
        else{
            System.out.println("Book is not issued.");
        }
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public void generateReport(){
        System.out.println("Books in Library:");
        for(Book book:books){
            System.out.println(book.getTitle()+ " by "+book.getAuthor()+ " (Available: "+book.isAvail()+ ")");
        }
        System.out.println("Members in Library:");
        for(Member member:members){
            System.out.println(member.getName()+ " (Email: "+member.getEmail()+ ")");
        }
        System.out.println("Transactions:");
        for(Transaction transaction:transactions){
            System.out.println("Book ID: "+transaction.getBookId()+ " issued to Member ID: "+transaction.getMemberId()+ " on "+transaction.getIssueDate()+ " (Returned: "+transaction.getReturnDate()+ ")");
        }
    }
}

public class LibraryManagementApp{
    public static void main(String[] args){
        LibraryService libraryService=new LibraryService();
        Scanner sc=new Scanner(System.in);

        while (true){
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Search Books");
            System.out.println("5. Add Member");
            System.out.println("6. Update Member");
            System.out.println("7. Delete Member");
            System.out.println("8. Issue Book");
            System.out.println("9. Return Book");
            System.out.println("10. Generate Report");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    System.out.print("Enter book title: ");
                    String title=sc.nextLine();
                    System.out.print("Enter book author: ");
                    String author=sc.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String isbn=sc.nextLine();
                    libraryService.addBook(title,author,isbn);
                    break;
                case 2:
                    System.out.print("Enter book ID to update: ");
                    Long bookIdToUpdate=sc.nextLong();
                    sc.nextLine();
                    Book bookToUpdate=libraryService.getBook(bookIdToUpdate);
                    if(bookToUpdate != null){
                        System.out.print("Is the book available (true/false): ");
                        boolean isAvail=sc.nextBoolean();
                        bookToUpdate.setAvailable(isAvail);
                        libraryService.updateBook(bookToUpdate);
                    }
                    break;
                case 3:
                    System.out.print("Enter book ID to delete: ");
                    Long bookIdToDelete=sc.nextLong();
                    libraryService.deleteBook(bookIdToDelete);
                    break;
                case 4:
                    System.out.print("Enter search query: ");
                    String query=sc.nextLine();
                    List<Book> searchResults=libraryService.searchBooks(query);
                    System.out.println("Search Results:");
                    for(Book book:searchResults){
                        System.out.println(book.getTitle()+ " by "+book.getAuthor()+ " (Available: "+book.isAvail()+ ")");
                    }
                    break;
                case 5:
                    System.out.print("Enter member name: ");
                    String name=sc.nextLine();
                    System.out.print("Enter member email: ");
                    String email=sc.nextLine();
                    System.out.print("Enter member phone: ");
                    String phone=sc.nextLine();
                    libraryService.addMember(name,email,phone);
                    break;
                case 6:
                    System.out.print("Enter member ID to update: ");
                    Long memberIdToUpdate=sc.nextLong();
                    sc.nextLine();
                    Member memberToUpdate=libraryService.getMember(memberIdToUpdate);
                    if(memberToUpdate != null){
                        System.out.print("Enter new name: ");
                        name=sc.nextLine();
                        System.out.print("Enter new email: ");
                        email=sc.nextLine();
                        System.out.print("Enter new phone: ");
                        phone=sc.nextLine();
                        memberToUpdate=new Member(memberIdToUpdate,name,email,phone);
                        libraryService.updateMember(memberToUpdate);
                    }
                    break;
                case 7:
                    System.out.print("Enter member ID to delete: ");
                    Long memberIdToDelete=sc.nextLong();
                    libraryService.deleteMember(memberIdToDelete);
                    break;
                case 8:
                    System.out.print("Enter book ID to issue: ");
                    Long bookIdToIssue=sc.nextLong();
                    System.out.print("Enter member ID to issue to: ");
                    Long memberIdToIssue=sc.nextLong();
                    libraryService.issueBook(bookIdToIssue,memberIdToIssue);
                    break;
                case 9:
                    System.out.print("Enter book ID to return: ");
                    Long bookIdToReturn=sc.nextLong();
                    System.out.print("Enter member ID returning the book: ");
                    Long memberIdToReturn=sc.nextLong();
                    libraryService.returnBook(bookIdToReturn,memberIdToReturn);
                    break;
                case 10:
                    libraryService.generateReport();
                    break;
                case 11:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
