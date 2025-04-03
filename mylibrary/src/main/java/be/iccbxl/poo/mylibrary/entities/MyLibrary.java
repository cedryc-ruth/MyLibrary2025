package be.iccbxl.poo.mylibrary.entities;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLibrary {
    private String name;

    /**
	 * Liste des livres
	 */
	private ArrayList<Book> books = new ArrayList<>();
	
	/**
	 * Liste des membres
	 */
	private ArrayList<Person> people = new ArrayList<>();

    private final static byte BOOK_LIMIT = 3;

    public ArrayList<Book> findBooksByTitle(String title) {
        //TODO
        return null;
    }

    public void addBook(Book book) {
		this.books.add(book);
	}

	public void addPerson(Person person) {
		this.people.add(person);
	}
	
	public void printBooks() {
		Iterator<Book> it = this.getBooks().iterator();
		
		while(it.hasNext()) {
			Book b = it.next();						
			System.out.println(b.getTitle() + " - " + b.getAuthor());
		}
	}

	public void printMembers() {
		Iterator<Person> it = this.getPeople().iterator();
		
		while(it.hasNext()) {
			Person p = it.next();		
			System.out.println(p.getName() + ", inscrit le " + p.getRegistrationDate());
		}
	}

    public void saveBooks(String filename, String format) {
		XStream xs = null;

		if(format.equals("xml")) {
        	xs = new XStream(new DomDriver());

			configureXStream(xs);
		} else if(format.equals("json")) {
			xs = new XStream(new JettisonMappedXmlDriver());
		} else {
			throw new RuntimeException("Format de fichier non géré !");
		}

		String xmlBooks = xs.toXML(this.books);

		//Ecrire la chaîne XML dans un fichier texte
		Path path = Paths.get(filename);

		try {
			Files.writeString(path, xmlBooks, StandardOpenOption.CREATE);
		} catch(IOException e) {
			System.out.println(e);
		}

		//System.out.println(xmlBooks);
    }

	@SuppressWarnings("unchecked")
	public void loadBooks(String xmlFilename, String format) {
		//Lecture du fichier XML
		/*String xmlString = null;
		Path path = Paths.get(xmlFilename);

		try {
			List<String> lines = Files.readAllLines(path);
			xmlString = lines.toString();
		} catch (IOException e) {
			System.out.println(e);
		}*/
		
		XStream xs = null;

		if(format.equals("xml")) {
        	xs = new XStream(new DomDriver());

			configureXStream(xs);
		} else if(format.equals("json")) {
			xs = new XStream(new JettisonMappedXmlDriver());
		} else {
			throw new RuntimeException("Format de fichier non géré !");
		}
		
		//this.setBooks((ArrayList<Book>) xs.fromXML(xmlString));
		this.setBooks((ArrayList<Book>) xs.fromXML(xmlFilename));
	}

	private void configureXStream(XStream xs) {
		xs.alias("book", Book.class);
		xs.useAttributeFor(Book.class, "id");
		xs.addImplicitCollection(Book.class, "borrowers");
		xs.omitField(Book.class, "nbCopies");
		xs.alias("person", Person.class);
		xs.useAttributeFor(Person.class, "name");
		xs.aliasField("fullname", Person.class, "name");
	}

	public void saveBooksToCSVFile(String csvFilename) {
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(csvFilename);
		
			CSVPrinter printer = CSVFormat.EXCEL.builder()
				.setHeader("id", "title", "author", "totalPages", "loadPeriod","rentalPrice")
				.setDelimiter(";")
				.get()
				.print(fw);

			try {
				printer.printRecord("id", "title", "author", "totalPages", "loadPeriod","rentalPrice");

				for(Book book : this.books) {
					printer.printRecord(book.getId(), book.getTitle(), book.getAuthor(), book.getTotalPages(), book.getLoadPeriod(), book.getRentalPrice());
				}
			} finally {
				printer.close();
			}
		} catch(IOException e) {
			System.out.println(e);
		}

	}

}
