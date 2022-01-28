package game;

import java.util.List;

public class TwoFourPlayerGame {
    private final Board board;
    private final Player[] players;

    public TwoFourPlayerGame(Board board, List<Player> playersIn) {
        this.board = board;
        if (playersIn.size() < 2 || playersIn.size() > 4) {
            throw new IllegalArgumentException("In game must play from two to four players");
        }
        players = new Player[playersIn.size()];
        for (int i = 0; i < players.length; i++) {
            players[i] = playersIn.get(i);
        }
    }

    public TwoFourPlayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.players = new Player[2];
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public TwoFourPlayerGame(Board board, Player player1, Player player2, Player player3) {
        this.board = board;
        this.players = new Player[3];
        this.players[0] = player1;
        this.players[1] = player2;
        this.players[2] = player3;
    }

    public TwoFourPlayerGame(Board board, Player player1, Player player2, Player player3, Player player4) {
        this.board = board;
        this.players = new Player[4];
        this.players[0] = player1;
        this.players[1] = player2;
        this.players[2] = player3;
        this.players[3] = player4;
    }

    public int play(boolean log) {
        while (true) {
            for (int i = 0; i < players.length; i++) {
                final int result = makeMove(players[i], i + 1, log);
                if (result != -1) {
                    return result;
                }
            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        final Move move = player.makeMove(board.getPosition());
        final GameResult result = board.makeMove(move);
        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }
        switch (result) {
            case WIN:
                return no;
            case LOOSE:
                return players.length + 1 - no;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
