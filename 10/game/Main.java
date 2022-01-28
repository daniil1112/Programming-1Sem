package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);
            int m = Integer.parseInt(args[1]);
            int k = Integer.parseInt(args[2]);
            int players = Integer.parseInt(args[3]);

            int result;
            Board board = new TicTacToeBoard(n, m, k, players);
            Scanner scanner = new Scanner(System.in);

            if (args.length < players + 4) {
                throw new IllegalArgumentException("In game must play from two to four players");
            }

            List<Player> playersObj = new ArrayList<>();

            for (int i = 0; i < players; i++) {
                switch (args[i + 4]) {
                    case "h":
                        playersObj.add(new HumanPlayer(scanner));
                        break;
                    case "c":
                        playersObj.add(new CheatingPlayer());
                        break;
                    case "r":
                        playersObj.add(new RandomPlayer());
                        break;
                    case "s":
                        playersObj.add(new SequentialPlayer());
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown player number " + (i + 1));
                }
            }

            result = new TwoFourPlayerGame(
                    board,
                    playersObj
            ).play(true);

            switch (result) {
                case 1:
                    System.out.println("First player won");
                    break;
                case 2:
                    System.out.println("Second player won");
                    break;
                case 3:
                    System.out.println("Third player won");
                    break;
                case 4:
                    System.out.println("Fourth player won");
                    break;
                case 0:
                    System.out.println("Draw");
                    break;
                default:
                    throw new AssertionError("Unknown result " + result);
            }
        } catch (IllegalArgumentException | AssertionError e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error with input args");
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }
}
