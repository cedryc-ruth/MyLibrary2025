package be.iccbxl.poo.mylibrary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.UUID;

import be.iccbxl.poo.mylibrary.entities.Book;
import be.iccbxl.poo.mylibrary.entities.MyLibrary;
import be.iccbxl.poo.mylibrary.entities.NotAvailableException;
import be.iccbxl.poo.mylibrary.entities.Person;
import be.iccbxl.poo.mylibrary.entities.RangeException;

public class Main {
    public static void main(String[] args) {
        /*
         * 76.	//Affichage du menu
            77.	écrire "Menu principal
                1 - Ajouter un membre
	            2 - Ajouter un livre
                3 - Emprunter un livre
                4 - Afficher les statistiques
                5 - Sauvegarder les membres
                6 - Sauvegarder les livres
                7 - Charger les membres
                8 - Charger les livres
                0 – Quitter
         */

        String xmlFilename = "mylibrary/data/books.xml";
        String jsonFilename = "mylibrary/data/books.json";
        String csvFilename = "mylibrary/data/books.csv";

        MyLibrary library = new MyLibrary();
        library.setName("Nid des lecteurs");
        
        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        String menuPrincipal = "Menu principal\n"
            +"\t1 - Ajouter un membre\n"
	        +"\t2 - Ajouter un livre\n"
            +"\t3 - Emprunter un livre\n"
            +"\t4 - Afficher les statistiques\n"
            +"\t5 - Sauvegarder les membres\n"
            +"\t6 - Sauvegarder les livres\n"
            +"\t7 - Charger les membres\n"
            +"\t8 - Charger les livres\n"
            +"\t0 - Quitter";

        //Affichage du menu
        do {
            try {
                System.out.println(menuPrincipal);

                //Lecture du choix
                choix = scanner.nextInt();
                scanner.nextLine(); //Vider le tampon

                //Valider l'entrée utilisateur (entier compris entre 0 et 4)
                if(choix<0 || choix>8) {
                    throw new RangeException("Choix non valide :"+choix+" ([0,1,2,3,4])");
                }
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre parmi ceux proposés.");
                scanner.nextLine(); //Vider le tampon
            } catch(RangeException e) {
                System.out.println("Veuillez entrer un nombre parmi ceux proposés.");
                scanner.nextLine(); //Vider le tampon
                choix = -1;
            }
        } while(choix==-1);

        //Traitement du choix
        switch (choix) {
            case 1:
                //Demander le nom
                System.out.println("Quel est le nom du nouveau membre ?");
                String nom = scanner.nextLine();

                //Créer une Person
                Map<LocalDate,ArrayList<Book>> loans = new TreeMap<>();
                Person p = new Person(UUID.randomUUID(),nom,LocalDate.now(),loans);
                
                //Ajouter à la library
                library.addPerson(p);
                break;
            
                case 2 :
                    //TODO
                case 3 :
                /* Mock */
                    //Sélection d'un Person person...
                    Person person = new Person();
                    //Sélection d'un Book book...
                    Book book = new Book();
                /* Mock */

                    //Emprunt
                    try {
                        person.borrows(book);
                    } catch (NotAvailableException e) {
                        System.out.println("Le livre sélectionné n'est plus disponible.");
                    }


                    ArrayList<Book> books = person.getLoans().get(LocalDate.now());
                    books.add(book);
                    person.getLoans().put(LocalDate.now(), books);
        
            case 5 :
                /* Mock */
                //library.savePeople(library.getPeople());              
                
                //TODO Gestion des cas d'erreur
            case 6 :
                /* Mock */
                Book b = new Book("Une vie","Maupassant",(short)120);
                
                Person person2 = new Person();
                person2.setName("Bob Sull");
                person2.borrows(b);
               
                library.addBook(b);
                library.addBook(new Book("Twilight","Stephenie Meyer",(short)523));
                library.addBook(new Book("Les rivières éteintes","Dia",(short)148));

                /* Mock END */

                //library.saveBooks(xmlFilename, "xml");
                //library.saveBooks(jsonFilename, "json");
                library.saveBooksToCSVFile(csvFilename);
                break;
            case 7 :
                    //TODO
                    break;
            case 8 :
                library.loadBooks(xmlFilename, "xml");
                //library.loadBooks(jsonFilename, "json");
                System.out.println(library.getBooks());
                break;            
            default:
                System.out.println("Fin du programme.");
                System.exit(0);
        }

        System.out.println(library);
    }
}