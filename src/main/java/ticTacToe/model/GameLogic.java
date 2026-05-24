package ticTacToe.model;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

	private String[] board;
	private String currentTurn;
	private boolean gameEnded;

	private final Random random = new Random();

	public GameLogic() {
		resetGame();
	}

	public boolean makeMove(int position) {

		if (gameEnded || !board[position].equals(" ")) {
			return false;
		}

		board[position] = currentTurn;

		return true;
	}

	public boolean checkWinner() {

		int[][] wins = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 },
				{ 2, 4, 6 } };

		for (int[] win : wins) {

			String first = board[win[0]];

			if (!first.equals(" ") && first.equals(board[win[1]]) && first.equals(board[win[2]])) {

				gameEnded = true;
				return true;
			}
		}

		return false;
	}

	public boolean isDraw() {

		for (String cell : board) {
			if (cell.equals(" ")) {
				return false;
			}
		}

		gameEnded = true;
		return true;
	}

	public void changeTurn() {

		if (currentTurn.equals("X")) {
			currentTurn = "O";
		} else {
			currentTurn = "X";
		}
	}

	public int getComputerMove() {

		ArrayList<Integer> emptyPositions = new ArrayList<>();

		for (int i = 0; i < board.length; i++) {
			if (board[i].equals(" ")) {
				emptyPositions.add(i);
			}
		}

		if (emptyPositions.isEmpty()) {
			return -1;
		}

		return emptyPositions.get(random.nextInt(emptyPositions.size()));
	}

	public void resetGame() {

		board = new String[9];

		for (int i = 0; i < board.length; i++) {
			board[i] = " ";
		}

		currentTurn = "X";
		gameEnded = false;
	}

	public String getCurrentTurn() {
		return currentTurn;
	}

	public String getCellValue(int position) {
		return board[position];
	}

	public boolean isGameEnded() {
		return gameEnded;
	}
}