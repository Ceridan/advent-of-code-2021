package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day04 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day04.txt");

        System.out.printf("Day 04, part 1: %d\n", part1(data));
        System.out.printf("Day 04, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        Bingo bingo = new Bingo(data);

        for (int number : bingo.getNumbers()) {
            for (Board board : bingo.getBoards()) {
                int result = board.markNumber(number);

                if (result > -1) {
                    return result * number;
                }
            }
        }

        return -1;
    }

    static int part2(List<String> data) {
        Bingo bingo = new Bingo(data);
        Set<Integer> winningBoards = new HashSet<>(bingo.getBoards().size());

        for (int number : bingo.getNumbers()) {
            for (Board board : bingo.getBoards()) {
                if (winningBoards.contains(board.getId())) {
                    continue;
                }

                int result = board.markNumber(number);

                if (result > -1) {
                    winningBoards.add(board.getId());
                }

                if (winningBoards.size() == bingo.getBoards().size()) {
                    return result * number;
                }
            }
        }

        return -1;
    }

    private static class Bingo {
        private final List<Board> boards = new ArrayList<>();
        private List<Integer> numbers;

        public Bingo(List<String> data) {
            init(data);
        }

        public List<Board> getBoards() {
            return boards;
        }

        public List<Integer> getNumbers() {
            return numbers;
        }

        private void init(List<String> data) {
            numbers = Arrays.stream(data.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

            int boardId = 0;

            // Starting from the third line with first board and step over board and empty line
            for (int k = 2; k < data.size(); k += 6) {
                Integer[][] board = new Integer[5][];
                for (int i = 0; i < 5; i++) {
                    board[i] = Arrays.stream(data.get(k + i).trim().split("[ ]+"))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);
                }

                boards.add(new Board(boardId++, board));
            }
        }
    }

    private static class Board {
        private final int id;
        private final HashMap<Integer, Position> boardMap = new HashMap<>(25);
        private final HashMap<Integer, Integer> rows = new HashMap<>(5);
        private final HashMap<Integer, Integer> cols = new HashMap<>(5);
        private int unmarkedSum = 0;

        public Board(int boardId, Integer[][] board) {
            id = boardId;
            init(board);
        }

        public int markNumber(int number) {
            Position pos = boardMap.get(number);
            if (pos == null) {
                return -1;
            }

            unmarkedSum -= number;

            int rowCount = rows.get(pos.getRow()) + 1;
            rows.put(pos.getRow(), rowCount);
            if (rowCount == 5) {
                return unmarkedSum;
            }

            int colCount = cols.get(pos.getCol()) + 1;
            cols.put(pos.getCol(), colCount);
            if (colCount == 5) {
                return unmarkedSum;
            }

            return -1;
        }

        public int getId() {
            return id;
        }

        private void init(Integer[][] board) {
            for (int i = 0; i < 5; i++) {
                rows.put(i, 0);
                cols.put(i, 0);
                for (int j = 0; j < 5; j++) {
                    int number = board[i][j];
                    boardMap.put(number, new Position(i, j));
                    unmarkedSum += number;
                }
            }
        }

        private static class Position {
            private final int row;
            private final int col;

            public Position(int row, int col) {
                this.row = row;
                this.col = col;
            }

            public int getRow() {
                return row;
            }

            public int getCol() {
                return col;
            }
        }
    }
}
