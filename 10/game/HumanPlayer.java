package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());

        while (true) {
            try {
                int row = in.nextInt();
                int col = in.nextInt();

                final Move move = new Move(
                        row - 1,
                        col - 1,
                        position.getTurn()
                );
                if (position.isValid(move)) {
                    return move;
                }
                System.out.println();
                System.out.println("Invalid position " + (row) + " " + (col));
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please, enter you turn once more");
                in = new Scanner(System.in);
            }
        }
    }
}

