import java.util.Scanner;
import java.util.InputMismatchException;

public class PlaneManagement {
    public static int[][] seats = new int[4][14];
    public static final int ROWS = 4;
    public static final int[] SEATS_PER_ROW = {14, 12, 12, 14};
    public static Ticket[] tickets = new Ticket[ROWS * 14]; //Assuming maximum capacity

    public static void main(String[] args) {
        //calling the main methods
        displayWelcomeMessage();
        checkingTheChoice();
    }

    public static void displayWelcomeMessage() {
        System.out.println("\nWELCOME TO THE PLANE MANAGEMENT APPLICATION\n" +
                "-------------------------------------------------------------------\n" +
                "\t\t\tMenu Options\n\n" +
                "1)\tBuy a seat\n" +
                "2)\tCancel a seat\n" +
                "3)\tFind first available seat\n" +
                "4)\tShow seating plan\n" +
                "5)\tPrint tickets information and total sales\n" +
                "6)\tSearch ticket\n" +
                "0)\tQuit\n" +
                "-------------------------------------------------------------------");

    }

    public static void checkingTheChoice() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        while (choice != 0) {
            System.out.print("\nPlease select an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        buySeat();
                        break;
                    case 2:
                        cancelSeat();
                        break;
                    case 3:
                        findFirstAvailableSeat();
                        break;
                    case 4:
                        seatingPlan();
                        break;
                    case 5:
                        ticketsInformation();
                        break;
                    case 6:
                        searchTickets();
                        break;
                    case 0:
                        System.out.println("Your exiting from this program...");
                        break;
                    default:
                        //when the user input an invalid input
                        System.out.println("Invalid choice. Please check the menu and try again.");
                }
                //Displaying an error message when the input is incorrect
            } else {
                System.out.println("Please enter a number between 1-6 to choose options or 0 to exit");
                scanner.next();
            }
        }
    }

    public static double calculatePrice(char row, int seatNumber) {
        if ((row == 'A' || row == 'B' || row == 'C' || row == 'D') && seatNumber >= 1 && seatNumber <= 5) {
            return 200.0;
        } else if ((row == 'A' || row == 'B' || row == 'C' || row == 'D') && seatNumber >= 6 && seatNumber <= 9) {
            return 150.0;
        } else if ((row == 'A' || row == 'B') && seatNumber >= 10 && seatNumber <= 14) {
            return 180.0;
        } else if ((row == 'C' || row == 'D') && seatNumber >= 10 && seatNumber <= 12) {
            return 180.0;
        } else {
            //Default price if seat number is invalid
            return 0.0;
        }
    }

    public static void buySeat() {
        Scanner scanner = new Scanner(System.in);
        char row = '\0';  //Initializing row with a default value
        int seatNumber = 0; //Initializing seatNumber with a default value
        boolean validRow = false;
        boolean validSeatNumber = false;

        while (!validRow || !validSeatNumber) {
            if (!validRow) {
                System.out.print("\nEnter the letter of the row(A-D): ");
                String rowInput = scanner.next();
                char rowChar = rowInput.toUpperCase().charAt(0);
                if (rowInput.length() == 1 && rowChar >= 'A' && rowChar <= 'D') {
                    row = rowChar;
                    validRow = true;
                } else {
                    System.out.println("Invalid input for the row. Please enter a letter (A-D).");
                }
            }

            if (!validRow) {
                continue; //If the row input is invalid, skip asking for the seat number
            }

            if (!validSeatNumber) {
                try {
                    System.out.print("Enter the seat number: ");
                    seatNumber = scanner.nextInt();
                    int rowIndex = row - 'A';
                    if (seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowIndex]) {
                        System.out.println("Seat number is incorrect. Enter a valid seat number.");
                    } else {
                        validSeatNumber = true; // If the input is valid, exit the loop
                    }
                } catch (InputMismatchException e) {
                    System.out.println("In valid input for seat number. Please enter a valid number.");
                    scanner.next();
                }
            }
        }

        int rowIndex = row - 'A';
        int seatIndex = seatNumber - 1;

        //If the person entered invalid row or seat number this will display
        if (rowIndex < 0 || rowIndex >= ROWS || seatNumber > SEATS_PER_ROW[rowIndex]) {
            System.out.println("Invalid row or seat number. Please try again.");
            return;
        }
        //If the seat is already sold this will display
        if (seats[rowIndex][seatIndex] == 1) {
            System.out.println("Seat " + row + seatNumber + " is already sold.");
        } else {
            //Asking for passenger information
            System.out.print("\nEnter passenger's name: ");
            String name = scanner.next();
            System.out.print("Enter passenger's surname: ");
            String surname = scanner.next();
            System.out.print("Enter passenger's email: ");
            String email = scanner.next();

            //Creating Person object
            Person person = new Person(name, surname, email);

            //Adding ticket to 'tickets' array
            int ticketIndex = rowIndex * SEATS_PER_ROW[rowIndex] + seatIndex;
            Ticket ticket = new Ticket(row, seatNumber, calculatePrice(row, seatNumber), person);
            tickets[ticketIndex] = ticket;
            ticket.save();

            //Updating seat status
            seats[rowIndex][seatIndex] = 1;

            //Displaying the sold information
            System.out.println("Seat " + row + seatNumber + " has been sold to " + name.toUpperCase() + " " + surname.toUpperCase());
        }
    }


    public static void cancelSeat() {
        Scanner scanner = new Scanner(System.in);
        char row = '\0';  //Initializing row with a default value
        int seatNumber = 0; //Initializing seatNumber with a default value
        boolean validRow = false;
        boolean validSeatNumber = false;

        while (!validRow || !validSeatNumber) {
            if (!validRow) {
                System.out.print("\nEnter the letter of the row(A-D): ");
                String rowInput = scanner.next();
                char rowChar = rowInput.toUpperCase().charAt(0);
                if (rowInput.length() == 1 && rowChar >= 'A' && rowChar <= 'D') {
                    row = rowChar;
                    validRow = true;
                } else {
                    System.out.println("Invalid input for the row. Please enter a letter (A-D).");
                }
            }

            if (!validRow) {
                continue; //If the row input is invalid, skip asking for the seat number
            }

            if (!validSeatNumber) {
                try {
                    System.out.print("Enter the seat number: ");
                    seatNumber = scanner.nextInt();
                    int rowIndex = row - 'A';
                    if (seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowIndex]) {
                        System.out.println("Seat number is incorrect. Enter a valid seat number.");
                    } else {
                        validSeatNumber = true; //If the input is valid, exit the loop
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for seat number. Please enter a valid number.");
                    scanner.next(); //Clear the input buffer
                }
            }
        }

        int rowIndex = row - 'A';
        int seatIndex = seatNumber - 1;

        //If the person entered invalid row or seat number this will display
        if (rowIndex < 0 || rowIndex >= ROWS || seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowIndex]) {
            System.out.println("Invalid row or seat number. Please refer the seating plan.");
            return;
        }
        if (seats[rowIndex][seatIndex] == 0) {
            System.out.println("Seat " + row + seatNumber + " is already available. No need to cancel.");
        } else {
            //Clearing ticket from tickets array
            int ticketIndex = rowIndex * SEATS_PER_ROW[rowIndex] + seatIndex;
            tickets[ticketIndex] = null;

            //Updating seat status
            seats[rowIndex][seatIndex] = 0;

            //Displaying the seat cancelling message
            System.out.println("Seat " + row + seatNumber + " has been cancelled.");
        }
    }

    public static void findFirstAvailableSeat() {
        boolean found = false;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS_PER_ROW[i]; j++) {
                if (seats[i][j] == 0) {
                    System.out.println("First available seat is: " + (char) ('A' + i) + (j + 1));
                    found = true;
                    return;
                }
            }
        }

        //If the plane is fully booked this will display
        if (!found) {
            System.out.println("We are sorry! There are no available seats.");
        }
    }

    public static void seatingPlan() {
        //Displaying the seating plan
        System.out.println("\n    1   2   3   4   5   6   7   8   9   10  11  12  13  14");
        char row = 'A';
        for (int i = 0; i < seats.length; i++) {
            if (row == 'B' || row == 'C') {
                System.out.print(row + "  ");
                for (int j = 0; j < seats[i].length - 2; j++) {
                    if (seats[i][j] == 1) {
                        System.out.print(" X  ");
                    } else {
                        System.out.print(" O  ");
                    }
                }
                System.out.println();
            } else {
                System.out.print(row + "  ");
                for (int j = 0; j < seats[i].length; j++) {
                    if (seats[i][j] == 1) {
                        System.out.print(" X  ");
                    } else {
                        System.out.print(" O  ");
                    }
                }
                System.out.println();
            }
            row++;
        }
    }

    public static void ticketsInformation() {
        double totalPrice = 0.0;
        //Displaying the ticket information
        System.out.println("\nTICKETS INFORMATION");
        for (Ticket ticket : tickets) {
            System.out.println("\nTicket: " + ticket.getRow() + ticket.getSeat() +
                    "\nPrice: £" + ticket.getPrice() +
                    "\nPassenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
            totalPrice += ticket.getPrice();
        }
        System.out.println("\nTotal Price of Tickets Sold: £" + totalPrice);
    }


    public static void searchTickets() {
        Scanner scanner = new Scanner(System.in);
        char row = '\0';  // Initializing row with a default value
        int seatNumber = 0; // Initializing seatNumber with a default value
        boolean validRow = false;
        boolean validSeatNumber = false;

        while (!validRow || !validSeatNumber) {
            if (!validRow) {
                System.out.print("\nEnter the letter of the row(A-D): ");
                String rowInput = scanner.next();
                char rowChar = rowInput.toUpperCase().charAt(0);
                if (rowInput.length() == 1 && rowChar >= 'A' && rowChar <= 'D') {
                    row = rowChar;
                    validRow = true;
                } else {
                    System.out.println("Invalid input for the row. Please enter a letter (A-D).");
                }
            }

            if (!validRow) {
                continue; //If the row input is invalid, skip asking for the seat number
            }

            if (!validSeatNumber) {
                try {
                    System.out.print("Enter the seat number: ");
                    seatNumber = scanner.nextInt();
                    int rowIndex = row - 'A';
                    if (seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowIndex]) {
                        System.out.println("Seat number is incorrect. Enter a valid seat number.");
                    } else {
                        validSeatNumber = true; //If the input is valid, exit the loop
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for seat number. Please enter a valid number.");
                    scanner.next(); //Clear the input buffer
                }
            }
        }

        int rowIndex = row - 'A';
        int seatIndex = seatNumber - 1;

        if (rowIndex < 0 || rowIndex >= ROWS || seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowIndex]) {
            System.out.println("Invalid row or seat number. Please try again.");
            return;
        }

        //Find the ticket with the specified row and seat number
        boolean found = false;
        for (Ticket ticket : tickets) {
            if (ticket.getRow() == row && ticket.getSeat() == seatNumber) {
                found = true;
                System.out.println("\nTicket found!");
                System.out.println("Seat: " + ticket.getRow() + ticket.getSeat());
                System.out.println("Price: £" + ticket.getPrice());
                System.out.println("Passenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                break;
            }
        }

        if (!found) {
            System.out.println("\nThis seat is available.");
        }
    }
}

