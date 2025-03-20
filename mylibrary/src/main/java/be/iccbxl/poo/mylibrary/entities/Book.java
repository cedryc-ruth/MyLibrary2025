package be.iccbxl.poo.mylibrary.entities;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private UUID id;
    private String title;
    private String author;
    private short totalPages;
    private byte loadPeriod;
    private double rentalPrice;
    private Language language;
    private short nbCopies;
    private boolean borrowable;
    private ArrayList<Person> borrowers = new ArrayList<>();

    public LocalDate computeReturnDate() {
        //TODO
        return null;
    }
}
