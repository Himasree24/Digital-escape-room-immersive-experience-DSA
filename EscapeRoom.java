import java.util.*;

/* ---------------- CO2 : Linked List ---------------- */

class Node {
    String data;
    Node next;

    Node(String data) {
        this.data = data;
        this.next = null;
    }
}

class AttemptedRoomsList {

    Node head;

    void add(String room) {
        Node newNode = new Node(room);

        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head;
        while (temp.next != null)
            temp = temp.next;

        temp.next = newNode;
    }

    void display() {
        Node temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }

        System.out.println("NULL");
    }
}

/* ---------------- Puzzle Class ---------------- */

class Puzzle {
    String question;
    ArrayList<String> options;
    int correctIndex;

    Puzzle(String question, ArrayList<String> options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}

/* ---------------- Room Class ---------------- */

class Room {
    String name;
    Queue<Puzzle> puzzles;

    Room(String name) {
        this.name = name;
        this.puzzles = new LinkedList<>();
    }

    void addPuzzle(Puzzle p) {
        puzzles.add(p);
    }
}

/* ---------------- Main Class ---------------- */

public class EscapeRoom{

    static Scanner sc = new Scanner(System.in);

    // CO4 Hashing
    static HashMap<String, Room> rooms = new HashMap<>();

    // CO3 Stack
    static Stack<String> history = new Stack<>();

    static ArrayList<String> completedRooms = new ArrayList<>();

    // CO2 Linked List
    static AttemptedRoomsList attemptedRooms = new AttemptedRoomsList();

    public static void main(String[] args) {
        createRooms();
        mainMenu();
    }

    /* ---------------- CO1 Bubble Sort ---------------- */

    static void sortCompletedRooms() {

        for (int i = 0; i < completedRooms.size(); i++) {

            for (int j = 0; j < completedRooms.size() - i - 1; j++) {

                if (completedRooms.get(j)
                        .compareTo(completedRooms.get(j + 1)) > 0) {

                    String temp = completedRooms.get(j);

                    completedRooms.set(j, completedRooms.get(j + 1));

                    completedRooms.set(j + 1, temp);
                }
            }
        }
    }

    /* ---------------- CO1 Linear Search ---------------- */

    static boolean searchRoom(String room) {

        for (String r : completedRooms) {

            if (r.equals(room))
                return true;
        }

        return false;
    }

    /* ---------------- Menu ---------------- */

    static void mainMenu() {

        while (true) {

            System.out.println("\n====== DIGITAL ESCAPE ROOM ======");
            System.out.println("1. Easy Door");
            System.out.println("2. Medium Door");
            System.out.println("3. Hard Door");
            System.out.println("4. View Completed Doors");
            System.out.println("5. Exit");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            String selectedRoom = "";

            switch (choice) {

                case 1 -> selectedRoom = "Easy";

                case 2 -> selectedRoom = "Medium";

                case 3 -> selectedRoom = "Hard";

                case 4 -> {

                    sortCompletedRooms();

                    System.out.println("Completed Doors: " + completedRooms);

                    System.out.print("Attempted Rooms (Linked List): ");
                    attemptedRooms.display();

                    continue;
                }

                case 5 -> {
                    System.out.println("Exiting Game...");
                    return;
                }

                default -> {
                    System.out.println("Invalid choice!");
                    continue;
                }
            }

            if (searchRoom(selectedRoom)) {

                System.out.println("You already completed this door!");

            } else {

                playRoom(selectedRoom);
            }
        }
    }

    /* ---------------- Create Rooms ---------------- */

    static void createRooms() {

        Room easy = new Room("Easy");

        easy.addPuzzle(new Puzzle(
                "What comes after 2, 4, 6?",
                new ArrayList<>(Arrays.asList("9", "11", "8")),
                2));

        easy.addPuzzle(new Puzzle(
                "I speak without a mouth and hear without ears. What am I?",
                new ArrayList<>(Arrays.asList("Shadow", "Echo", "Ghost")),
                1));

        easy.addPuzzle(new Puzzle(
                "What disappears when you say its name?",
                new ArrayList<>(Arrays.asList("Darkness", "Silence", "Fear")),
                1));


        Room medium = new Room("Medium");

        medium.addPuzzle(new Puzzle(
                "▲ ▲ ■ ■ ■ ● (How many triangles, squares, circles?)",
                new ArrayList<>(Arrays.asList("321", "213", "231")),
                2));

        medium.addPuzzle(new Puzzle(
                "I have keys but no locks. What am I?",
                new ArrayList<>(Arrays.asList("Piano", "Map", "Keyboard")),
                2));

        medium.addPuzzle(new Puzzle(
                "2->6, 3->12, 4->20, 5->?",
                new ArrayList<>(Arrays.asList("25", "28", "30")),
                2));


        Room hard = new Room("Hard");

        hard.addPuzzle(new Puzzle(
                "XVIII + IV = ?",
                new ArrayList<>(Arrays.asList("20", "24", "22")),
                2));

        hard.addPuzzle(new Puzzle(
                "Two guardians puzzle?",
                new ArrayList<>(Arrays.asList(
                        "Which door is safe?",
                        "Are you the liar?",
                        "If I asked the other guardian which door is safe, which would he choose?")),
                2));

        hard.addPuzzle(new Puzzle(
                "1=A, 2=B ... 8-1-18-4 = ?",
                new ArrayList<>(Arrays.asList("FIRE", "FEAR", "HARD")),
                2));

        rooms.put("Easy", easy);
        rooms.put("Medium", medium);
        rooms.put("Hard", hard);
    }

    /* ---------------- Play Room ---------------- */

    static void playRoom(String roomName) {

        createRooms();
        Room room = rooms.get(roomName);

        history.push(roomName);        // CO3 Stack
        attemptedRooms.add(roomName);  // CO2 Linked List

        System.out.println("\nEntering " + roomName + " Door...");

        while (!room.puzzles.isEmpty()) {

            Puzzle current = room.puzzles.peek();

            System.out.println("\n" + current.question);

            for (int i = 0; i < current.options.size(); i++) {
                System.out.println((i + 1) + ". " + current.options.get(i));
            }

            System.out.print("Choose option: ");
            int answer = sc.nextInt();
            sc.nextLine();

            if (answer - 1 == current.correctIndex) {

                System.out.println("Correct!");

                room.puzzles.poll();   // CO3 Queue

            } else {

                System.out.println("Wrong! Try again.");
            }
        }

        System.out.println("\nCongratulations! You Escaped the " + roomName + " Door!");

        completedRooms.add(roomName);

        System.out.println("Returning to Main Menu...");
    }
}