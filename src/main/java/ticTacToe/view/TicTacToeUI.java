package ticTacToe.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ticTacToe.model.GameLogic;

public class TicTacToeUI extends Application {

	private final GameLogic logic = new GameLogic();

	private boolean vsComputer = false;

	private String playerXName = "Player 1";
	private String playerOName = "Player 2";

	@Override
	public void start(Stage stage) {

		BorderPane root = new BorderPane();

		Label title = new Label(" Tic Tac Toe\n" + "+-----------+\n" + "| X | O | X |\n" + "|---|---|---|\n"
				+ "| O | X | O |\n" + "|---|---|---|\n" + "| X | O | X |\n" + "+-----------+");

		title.setFont(Font.font("Monospaced", 25));

		HBox topBox = new HBox(title);
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(10));

		root.setTop(topBox);

		VBox setupBox = new VBox(15);
		setupBox.setAlignment(Pos.CENTER);

		TextField p1Field = new TextField();
		p1Field.setPromptText("Player 1");

		TextField p2Field = new TextField();
		p2Field.setPromptText("Player 2");

		p1Field.setMaxWidth(200);
		p2Field.setMaxWidth(200);

		RadioButton pvp = new RadioButton("Player vs Player");
		RadioButton pvc = new RadioButton("Vs Computer");

		ToggleGroup group = new ToggleGroup();

		pvp.setToggleGroup(group);
		pvc.setToggleGroup(group);

		pvp.setSelected(true);

		pvc.setOnAction(e -> {
			p2Field.setDisable(true);
			p2Field.setText("Computer");
		});

		pvp.setOnAction(e -> {
			p2Field.setDisable(false);
			p2Field.setText("");
		});

		Button startButton = new Button("Start Game");
		startButton.setFont(Font.font(18));

		setupBox.getChildren().addAll(p1Field, p2Field, pvp, pvc, startButton);

		root.setCenter(setupBox);

		startButton.setOnAction(e -> {

			playerXName = p1Field.getText().trim().isEmpty() ? "Player 1" : p1Field.getText().trim();

			if (pvc.isSelected()) {
				playerOName = "Computer";
				vsComputer = true;
			} else {

				playerOName = p2Field.getText().trim().isEmpty() ? "Player 2" : p2Field.getText().trim();

				vsComputer = false;
			}

			startGame(stage);
		});

		Scene scene = new Scene(root, 450, 500);

		stage.setTitle("Tic Tac Toe");
		stage.setScene(scene);
		stage.show();
	}

	private void startGame(Stage stage) {

		logic.resetGame();

		BorderPane root = new BorderPane();

		Label statusLabel = new Label("Turn: " + playerXName);
		statusLabel.setFont(Font.font("Monospaced", 28));

		HBox topBox = new HBox(statusLabel);
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(15));

		root.setTop(topBox);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		Button[] buttons = new Button[9];

		for (int i = 0; i < 9; i++) {

			Button button = new Button(" ");
			button.setFont(Font.font("Monospaced", 40));
			button.setPrefSize(100, 100);

			buttons[i] = button;

			int index = i;

			button.setOnAction(e -> {

				if (!logic.makeMove(index)) {
					return;
				}

				button.setText(logic.getCurrentTurn());

				if (logic.checkWinner()) {
					statusLabel.setText("Winner: " + getCurrentPlayerName() + "\nLooser: " + getLooserPlayerName());
					return;
				}

				if (logic.isDraw()) {
					statusLabel.setText("Draw!");
					return;
				}

				logic.changeTurn();

				statusLabel.setText("Turn: " + getCurrentPlayerName());

				if (vsComputer && logic.getCurrentTurn().equals("O")) {

					int move = logic.getComputerMove();

					logic.makeMove(move);

					buttons[move].setText(logic.getCurrentTurn());

					if (logic.checkWinner()) {
						statusLabel.setText("Winner: " + getCurrentPlayerName() + "\nLooser: " + getLooserPlayerName());
						return;
					}

					if (logic.isDraw()) {
						statusLabel.setText("Draw!");
						return;
					}

					logic.changeTurn();

					statusLabel.setText("Turn: " + getCurrentPlayerName());
				}
			});
		}

		grid.add(buttons[0], 0, 0);
		grid.add(buttons[1], 1, 0);
		grid.add(buttons[2], 2, 0);

		grid.add(buttons[3], 0, 1);
		grid.add(buttons[4], 1, 1);
		grid.add(buttons[5], 2, 1);

		grid.add(buttons[6], 0, 2);
		grid.add(buttons[7], 1, 2);
		grid.add(buttons[8], 2, 2);

		root.setCenter(grid);

		Button restartButton = new Button("Restart");

		restartButton.setFont(Font.font(18));

		restartButton.setOnAction(e -> {

			logic.resetGame();

			for (Button button : buttons) {
				button.setText(" ");
			}

			statusLabel.setText("Turn: " + playerXName);
		});

		HBox bottomBox = new HBox(restartButton);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(15));

		root.setBottom(bottomBox);

		Scene scene = new Scene(root, 450, 500);

		stage.setScene(scene);
	}

	private String getCurrentPlayerName() {

		if (logic.getCurrentTurn().equals("X")) {
			return playerXName;
		}

		return playerOName;
	}

	private String getLooserPlayerName() {

		if (getCurrentPlayerName().equals(playerXName)) {
			return playerOName;
		}

		return playerXName;
	}
}
