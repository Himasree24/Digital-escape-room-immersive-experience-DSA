import java.util.*;


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

public class EscapeRoomGame {

    static Scanner sc = new Scanner(System.in);

   
    static HashMap<String, Room> rooms = new HashMap<>();

    
    static Stack<String> history = new Stack<>();

   
    static ArrayList<String> completedRooms = new ArrayList<>();

    public static void main(String[] args) {
        createRooms();
        mainMenu();
    }

    
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
                    System.out.println("Completed Doors: " + completedRooms);
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

            if (completedRooms.contains(selectedRoom)) {
                System.out.println(" You already completed this door!");
            } else {
                playRoom(selectedRoom);
            }
        }
    }

   
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
                "▲ ▲ ■ ■ ■ ●  (How many triangles, squares, circles in order?)",
                new ArrayList<>(Arrays.asList("321", "213", "231")),
                2));

        medium.addPuzzle(new Puzzle(
                "I have keys but no locks. I have space but no room. What am I?",
                new ArrayList<>(Arrays.asList("Piano", "Map", "Keyboard")),
                2));

        medium.addPuzzle(new Puzzle(
                "2-> 6, 3 -> 12, 4 -> 20, 5 -> ?",
                new ArrayList<>(Arrays.asList("25", "28", "30")),
                2));

       
        Room hard = new Room("Hard");

        hard.addPuzzle(new Puzzle(
                "XVIII + IV = ?",
                new ArrayList<>(Arrays.asList("20", "24", "22")),
                2));

        hard.addPuzzle(new Puzzle(
                "Two guardians: Which question guarantees safety?",
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

    
    static void playRoom(String roomName) {

        Room room = rooms.get(roomName);

        
        createRooms();
        room = rooms.get(roomName);

        history.push(roomName);

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
                System.out.println(" Correct!");
                room.puzzles.poll();  // Queue removes solved puzzle
            } else {
                System.out.println(" Wrong! Try again.");
            }
        }

        System.out.println("\n Congratulations! You Escaped the " + roomName + " Door!");
        completedRooms.add(roomName);

        System.out.println("Returning to Main Menu...");
    }
}
