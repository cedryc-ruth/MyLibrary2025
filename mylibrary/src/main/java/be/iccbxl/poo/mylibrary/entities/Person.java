package be.iccbxl.poo.mylibrary.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private UUID id;
    private String name;
    private LocalDate registrationDate;
    private Map<LocalDate, ArrayList<Book>> loans = new TreeMap<LocalDate, ArrayList<Book>>();

    public ArrayList<Book> getLateBooks() {
        //TODO
        return null;
    }

    public void borrows(Book book) {
        ArrayList<Book> loanedBooks = loans.get(LocalDate.now());

        if(loanedBooks!=null)   {
            if(!loanedBooks.contains(book)) loanedBooks.add(book);
        } else {
            loanedBooks = new ArrayList<>();
        }
        
        loans.put(LocalDate.now(), loanedBooks);

        if(!book.getBorrowers().contains(this)) book.getBorrowers().add(this);
    }
}
