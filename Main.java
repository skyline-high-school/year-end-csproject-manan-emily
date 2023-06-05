package TextEditor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Text Editor");
        System.out.println("Enter commands:");
        System.out.println("  - insert <position> <text>");
        System.out.println("  - delete <startPosition> <endPosition>");
        System.out.println("  - undo");
        System.out.println("  - redo");
        System.out.println("  - move <position>");
        System.out.println("  - print");
        System.out.println("  - cursor");
        System.out.println("  - save <fileName>");
        System.out.println("  - load <fileName>");
        System.out.println("  - exit");

        System.out.print("Enter starting text: ");
        String startingText = scanner.nextLine();
        editor.insert(0, startingText);
        System.out.println("Text inserted.");
        editor.printTextWithCursor();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            String[] parts = input.split(" ");
            String command = parts[0];

            if (command.equals("insert")) {
                int position = Integer.parseInt(parts[1]);
                String newText = input.substring(parts[0].length() + parts[1].length() + 2);
                editor.insert(position, newText);
                System.out.println("Text inserted.");
                editor.printTextWithCursor();
            } else if (command.equals("delete")) {
                int startPosition = Integer.parseInt(parts[1]);
                int endPosition = Integer.parseInt(parts[2]);
                editor.delete(startPosition, endPosition);
                System.out.println("Text deleted.");
                editor.printTextWithCursor();
            } else if (command.equals("undo")) {
                editor.undo();
                System.out.println("Undo performed.");
                editor.printTextWithCursor();
            } else if (command.equals("redo")) {
                editor.redo();
                System.out.println("Redo performed.");
                editor.printTextWithCursor();
            } else if (command.equals("move")) {
                int position = Integer.parseInt(parts[1]);
                editor.moveCursor(position);
                editor.printTextWithCursor();
            } else if (command.equals("print")) {
                editor.printTextWithCursor();
            } else if (command.equals("cursor")) {
                int cursorPosition = editor.getCursorPosition();
                System.out.println("Cursor position: " + cursorPosition);
            } else if (command.equals("save")) {
                String fileName = parts[1];
                editor.saveToFile(fileName);
            } else if (command.equals("load")) {
                String fileName = parts[1];
                editor.loadFromFile(fileName);
                editor.printTextWithCursor();
            } else if (command.equals("exit")) {
                System.out.println("Exiting text editor.");
                break;
            } else {
                System.out.println("Invalid command.");
            }
        }

        scanner.close();
    }
}