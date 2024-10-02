import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    //Constructor
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    //Getters and setters
    public char getRow() {

        return row;
    }

    public void setRow(char row) {

        this.row = row;
    }

    public int getSeat() {

        return seat;
    }

    public void setSeat(int seat) {

        this.seat = seat;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public Person getPerson() {

        return person;
    }

    public void setPerson(Person person) {

        this.person = person;
    }

    //Method to print information of a ticket
    public void printInfo() {
        System.out.println("TICKET INFORMATION");
        System.out.println("Row letter: " + row);
        System.out.println("Seat number: " + seat);
        System.out.println("Price for the ticket : $" + price);
        System.out.println("PASSENGER INFORMATION");
        person.printInfo();
    }

    //Method to save to the file
    public void save() {
        String fileName = row + String.valueOf(seat) + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("TICKET INFORMATION\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + price + "\n");
            writer.write("\nPASSENGER INFORMATION\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            System.out.println("Ticket information saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket information: " + e.getMessage());
        }
    }
}

