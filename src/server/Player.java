package server;

import java.util.Scanner;
import java.io.PrintWriter;

public class Player {
    public String name;
    public Scanner scanner;
    public PrintWriter writer;

    public Player(String name, PrintWriter writer, Scanner scanner) {
        this.name = name;
        this.writer = writer;
        this.scanner = scanner;
    }

    @Override
    public String toString() {
        return "Player(" + this.name + ")";
    }
}
