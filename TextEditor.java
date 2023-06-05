package TextEditor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class TextEditor {
    private StringBuilder text;
    private Stack<StringBuilder> undoStack;
    private Stack<StringBuilder> redoStack;
    private Stack<Integer> cursorStack;
    private String currentFileName;

    public TextEditor() {
        text = new StringBuilder();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        cursorStack = new Stack<>();
        currentFileName = null;
    }

    public void insert(int position, String newText) {
        undoStack.push(new StringBuilder(text));
        text.insert(position, newText);
    }

    public void delete(int startPosition, int endPosition) {
        undoStack.push(new StringBuilder(text));
        text.delete(startPosition, endPosition);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new StringBuilder(text));
            text = undoStack.pop();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new StringBuilder(text));
            text = redoStack.pop();
        }
    }

    public void moveCursor(int position) {
        position = Math.max(0, Math.min(position, text.length()));
        cursorStack.push(position);
    }

    public int getCursorPosition() {
        return cursorStack.isEmpty() ? 0 : cursorStack.peek();
    }

    public void printTextWithCursor() {
        int cursorPosition = getCursorPosition();
        StringBuilder textWithCursor = new StringBuilder(text);
        textWithCursor.insert(cursorPosition, "|");
        System.out.println(textWithCursor);
    }

    public void saveToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(text.toString());
            writer.close();
            System.out.println("File saved: " + fileName);
            currentFileName = fileName;
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
        }
    }

    public void loadFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            StringBuilder loadedText = new StringBuilder();
            while (scanner.hasNextLine()) {
                loadedText.append(scanner.nextLine());
                if (scanner.hasNextLine()) {
                    loadedText.append("\n");
                }
            }
            text = loadedText;
            undoStack.clear();
            redoStack.clear();
            cursorStack.clear();
            currentFileName = fileName;
            System.out.println("File loaded: " + fileName);
            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading the file.");
        }
    }

    public String getText() {
        return text.toString();
    }
}