// Name: Negin Heidari
// Description: The TicTacToeJavaFx class is used to create a GUI for the Tic Tac Toe game.

package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Arrays;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class TicTacToeJavaFx extends Application {

    Board board = new Board(3);
    MinMax ai;
    boolean isPlayer1Turn;
    Player player1obj;
    Player player2obj;
    int[] move = new int[2];
    boolean newGame = false;


    @Override
    public void start(Stage stage) throws IOException {
        //-----------------components and Fields-----------------
        Label header = new Label("Tic Tac Toe");
        header.setAlignment(Pos.CENTER);
        header.setPrefSize(600, 100);
        header.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        Label message = new Label();
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        Label player1 = new Label("Player 1:");
        TextField player1Name = new TextField();

        player1Name.setFocusTraversable(false);
        Label player2 = new Label("Player 2:");
        TextField player2Name = new TextField();
        player2Name.setFocusTraversable(false);

        Label gameMode = new Label("Game Mode:");
        RadioButton singlePlayer = new RadioButton("Single-Player");
        singlePlayer.setFocusTraversable(false);

        RadioButton multiPlayer = new RadioButton("Multi-Player");
        multiPlayer.setFocusTraversable(false);

        Label AIMode = new Label("AI Mode:");
        RadioButton weak = new RadioButton("Weak");
        weak.setFocusTraversable(false);
        weak.setDisable(true);

        RadioButton intelligent = new RadioButton("Intelligent");
        intelligent.setFocusTraversable(false);
        intelligent.setDisable(true);


        Label player1Symbol_Label = new Label("Player 1 Symbol:");
        RadioButton player1Symbol_x = new RadioButton("X");
        player1Symbol_x.setFocusTraversable(false);
        RadioButton player1Symbol_o = new RadioButton("O");
        player1Symbol_o.setFocusTraversable(false);

        Label Player2Symbol_Label = new Label("Player 2 Symbol:");
        RadioButton player2Symbol_x2 = new RadioButton("X");
        player2Symbol_x2.setFocusTraversable(false);
        RadioButton player2Symbol_o2 = new RadioButton("O");
        player2Symbol_o2.setFocusTraversable(false);

        Label playFirst = new Label("First to Play:");
        playFirst.setPadding(new Insets(0, 0, 10, 0));
        RadioButton player1First = new RadioButton("Player 1");
        player1First.setFocusTraversable(false);
        RadioButton player2First = new RadioButton("Player 2");
        player2First.setFocusTraversable(false);

        Button startButton = new Button("Start");
        startButton.setPrefSize(100, 30);
        startButton.setFocusTraversable(false);
        startButton.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        Button resetButton = new Button("Reset");
        resetButton.setPrefSize(100, 30);
        resetButton.setFocusTraversable(false);
        resetButton.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #000000;");


        Label[] Labels = {player1, player2, gameMode, AIMode, player1Symbol_Label, Player2Symbol_Label, playFirst};
        for (Label l : Labels) {
            l.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #000000;");
        }

        RadioButton[] radioButtons = {singlePlayer, multiPlayer, weak, intelligent, player1Symbol_x, player1Symbol_o, player2Symbol_x2, player2Symbol_o2, player1First, player2First};
        for (RadioButton r : radioButtons) {
            r.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #000000;");
        }


        Button[][] buttons = new Button[3][3];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new Button();
            }
        }

        for (Button[] item : buttons) {
            for (Button button : item) {
                button.setPrefSize(100, 100);
                button.setFocusTraversable(false);
                button.setDisable(true);
                button.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #000000;");
            }
        }

        //---------------------------------layout---------------------------------
        BorderPane borderPane = new BorderPane(); //parent layout
        VBox headContainer = new VBox();
        GridPane root = new GridPane();  //grid layout in center of the parent layout
        VBox playerVboxContainer = new VBox(); //vbox container for playerVbox
        VBox playerVbox = new VBox(); //vbox layout in left of the parent layout
        HBox player1NameHbox = new HBox(); //hbox layout in vbox layout
        HBox player2NameHbox = new HBox(); //hbox layout in vbox layout
        HBox gameModeHbox = new HBox(); //hbox layout in vbox layout
        HBox AILevelHbox = new HBox(); //hbox layout in vbox layout
        HBox player1SymbolHbox = new HBox(); //hbox layout in vbox layout
        HBox player2SymbolHbox = new HBox(); //hbox layout in vbox layout
        HBox playFirstHbox = new HBox();  //hbox layout in vbox layout
        HBox buttonHbox = new HBox(); //hbox layout in vbox layout


        //-----------------------------styling---------------------------------
        BackgroundFill[] fills = new BackgroundFill[]{
                new BackgroundFill(Color.web("hsla(0,50%,100%,1)"), null, null),
                new BackgroundFill(
                        new RadialGradient(
                                -1,   // focus distance
                                0.3, // radius
                                0.01, // centerX
                                0.2, // centerY
                                1.5, // radius proportional
                                true, // proportional
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("hsla(23,63%,100%,1)")),
                                new Stop(0.5, Color.TRANSPARENT)
                        ),
                        null, null
                ),
                new BackgroundFill(
                        new RadialGradient(
                                0,   // focus distance
                                0.4, // radius
                                0.6, // centerX
                                0.04,   // centerY
                                1, // radius proportional
                                true, // proportional
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("hsla(189,85%,80%,1)")),
                                new Stop(0.5, Color.TRANSPARENT)
                        ),
                        null, null
                ),
                new BackgroundFill(
                        new RadialGradient(
                                0,   // focus distance
                                .2, // radius
                                -.2,   // centerX
                                .5,// centerY
                                .6, // radius proportional
                                true, // proportional
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("hsla(355,20%,100%,1)")),
                                new Stop(.5, Color.TRANSPARENT)
                        ),
                        null, null
                ),
                new BackgroundFill(
                        new RadialGradient(
                                0,   // focus distance
                                0.3, // radius
                                0.6, // centerX
                                0.8, // centerY
                                1.5, // radius proportional
                                true, // proportional
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("hsla(340,70%,86%,1)")),
                                new Stop(0.5, Color.TRANSPARENT)
                        ),
                        null, null
                ),
                new BackgroundFill(
                        new RadialGradient(
                                0.8,   // focus distance
                                0.45, // radius
                                -0.2,   // centerX
                                1,   // centerY
                                1.2, // radius proportional
                                true, // proportional
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("hsla(18,80%,100%,1)")),
                                new Stop(0.5, Color.TRANSPARENT)
                        ),
                        null, null
                ),
                new BackgroundFill(
                        new RadialGradient(
                                0,   // focus distance
                                .6, // radius
                                0.7, // centerX
                                1.18,   // centerY
                                .4, // radius proportional
                                true, // proportional
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("hsla(239,90%,100%,1)")),
                                new Stop(5, Color.TRANSPARENT)
                        ),
                        null, null
                ),
                new BackgroundFill(
                        new RadialGradient(
                                0,   // focus distance
                                0.32, // radius
                                -0.25,   // centerX
                                .05,   // centerY
                                0.75, // radius proportional
                                true, // proportional
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("hsla(343,60%,95%,1)")),
                                new Stop(0.5, Color.TRANSPARENT)
                        ),
                        null, null
                )
        };

        borderPane.setBackground(new Background(fills));

        ToggleGroup gameModeT = new ToggleGroup();
        singlePlayer.setToggleGroup(gameModeT);
        multiPlayer.setToggleGroup(gameModeT);

        ToggleGroup AILevel = new ToggleGroup();
        weak.setToggleGroup(AILevel);
        intelligent.setToggleGroup(AILevel);

        ToggleGroup player1Symbol = new ToggleGroup();
        player1Symbol_x.setToggleGroup(player1Symbol);
        player1Symbol_o.setToggleGroup(player1Symbol);

        ToggleGroup player2Symbol = new ToggleGroup();
        player2Symbol_x2.setToggleGroup(player2Symbol);
        player2Symbol_o2.setToggleGroup(player2Symbol);

        BorderPane.setAlignment(header, Pos.CENTER);
        headContainer.setAlignment(Pos.CENTER);
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);

        playerVboxContainer.setAlignment(Pos.CENTER);

        playerVbox.setAlignment(Pos.CENTER);
        playerVbox.setSpacing(10);
        playerVbox.setPadding(new Insets(10, 10, 10, 10));
        playerVbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));

        player1NameHbox.setSpacing(10);
        player2NameHbox.setSpacing(10);

        gameModeHbox.setSpacing(10);
        gameModeHbox.setPadding(new Insets(10, 0, 5, 0));

        AILevelHbox.setSpacing(10);
        AILevelHbox.setPadding(new Insets(0, 0, 6, 0));

        player1SymbolHbox.setSpacing(10);
        player1SymbolHbox.setPadding(new Insets(0, 0, 10, 0));
        player2SymbolHbox.setSpacing(10);
        player2SymbolHbox.setPadding(new Insets(0, 0, 10, 0));

        ToggleGroup firstPlayer = new ToggleGroup();
        player1First.setToggleGroup(firstPlayer);
        player2First.setToggleGroup(firstPlayer);


        playFirstHbox.setSpacing(10);

        buttonHbox.setSpacing(10);
        buttonHbox.setAlignment(Pos.CENTER);


        //-----------------add components to layout-----------------
        headContainer.getChildren().addAll(header, message);
        borderPane.setTop(headContainer);
        borderPane.setCenter(root);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                root.add(buttons[i][j], i, j);
            }
        }

        player1NameHbox.getChildren().addAll(player1, player1Name);
        player2NameHbox.getChildren().addAll(player2, player2Name);

        gameModeHbox.getChildren().addAll(gameMode, singlePlayer, multiPlayer);

        AILevelHbox.getChildren().addAll(AIMode, weak, intelligent);

        player1SymbolHbox.getChildren().addAll(player1Symbol_Label, player1Symbol_x, player1Symbol_o);
        player2SymbolHbox.getChildren().addAll(Player2Symbol_Label, player2Symbol_x2, player2Symbol_o2);

        playFirstHbox.getChildren().addAll(playFirst, player1First, player2First);

        buttonHbox.getChildren().addAll(resetButton, startButton);

        playerVbox.getChildren().addAll(player1NameHbox,
                player2NameHbox,
                gameModeHbox,
                AILevelHbox,
                player1SymbolHbox,
                player2SymbolHbox,
                playFirstHbox,
                buttonHbox);


        playerVboxContainer.getChildren().add(playerVbox);

        borderPane.setLeft(playerVboxContainer);


        //-----------------scene and stage-----------------
        try {
            Scene scene = new Scene(borderPane, 850, 750);
            stage.setMinWidth(850);
            stage.setMinHeight(750);


            stage.setTitle("Tic Tac Toe");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //-----------------event handling-----------------

        gameModeT.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == singlePlayer) {
                player2Name.setText("Computer");
                player2Name.setDisable(true);
                player2Symbol_x2.setDisable(true);
                player2Symbol_o2.setDisable(true);

                weak.setDisable(false);
                intelligent.setDisable(false);

                player1Symbol.selectedToggleProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (!newGame) {
                        if (newValue1 == player1Symbol_x) {
                            player2Symbol_o2.setSelected(true);
                        } else {
                            player2Symbol_x2.setSelected(true);
                        }
                    }
                });

            } else { // multiplayer
                if (player2Name.getText().equals("Computer")) {
                    player2Name.setText("");
                }

                player2Name.setDisable(false);
                player2Symbol_x2.setDisable(false);
                player2Symbol_o2.setDisable(false);

                weak.setDisable(true);
                intelligent.setDisable(true);

                for (Toggle rdb : AILevel.getToggles()) {
                    rdb.setSelected(false);
                }

                player2Symbol.selectedToggleProperty().addListener((observable2, oldValue2, newValue2) -> {
                    if (!newGame) {
                        if (newValue2 == player2Symbol_x2) {
                            player1Symbol_o.setSelected(true);
                        } else {
                            player1Symbol_x.setSelected(true);
                        }
                    }
                });
            }
        });

        startButton.setOnAction(e -> {
            if (player1Name.getText().trim().isEmpty() || player2Name.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Player name is empty!");
                alert.setContentText("Please enter a name for both players!");
                alert.showAndWait();
            } else if (player1Name.getText().equals(player2Name.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Player name is the same!");
                alert.setContentText("Please enter a different name for one of the players!");
                alert.showAndWait();

            } else if (!singlePlayer.isSelected() && !multiPlayer.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Game mode is not selected!");
                alert.setContentText("Please select a game mode!");
                alert.showAndWait();

            } else if (singlePlayer.isSelected() && (!weak.isSelected() && !intelligent.isSelected())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("AI level is not selected!");
                alert.setContentText("Please select an AI level!");
                alert.showAndWait();

            } else if (!player1Symbol_x.isSelected() && !player1Symbol_o.isSelected() || !player2Symbol_x2.isSelected() && !player2Symbol_o2.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Symbol is not selected!");
                alert.setContentText("Please select a symbol for both players!");
                alert.showAndWait();

            } else if (player1Symbol_x.isSelected() && player2Symbol_x2.isSelected() || player1Symbol_o.isSelected() && player2Symbol_o2.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Symbol is already taken!");
                alert.setContentText("Please select a different symbol for one of the players!");
                alert.showAndWait();
            } else if (player1First.isSelected() && player2First.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Both players cannot play first!");
                alert.setContentText("Please select a different option for one of the players!");
                alert.showAndWait();

            } else if (!player1First.isSelected() && !player2First.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Play first is not selected!");
                alert.setContentText("Please select an option for who plays first!");
                alert.showAndWait();

            } else {
                for (Button[] button : buttons) {
                    for (Button value : button) {
                        value.setDisable(false);
                    }
                }

                player1Name.setDisable(true);
                player2Name.setDisable(true);
                singlePlayer.setDisable(true);
                multiPlayer.setDisable(true);

                weak.setDisable(true);
                intelligent.setDisable(true);

                player1Symbol_x.setDisable(true);
                player1Symbol_o.setDisable(true);
                player2Symbol_x2.setDisable(true);
                player2Symbol_o2.setDisable(true);

                player1First.setDisable(true);
                player2First.setDisable(true);


                if (player1Symbol_x.isSelected()) { // checking which symbol is selected
                    player1obj = new Player(player1Name.getText(), 'X');
                    player2obj = new Player(player2Name.getText(), 'O');
                } else {
                    player1obj = new Player(player1Name.getText(), 'O');
                    player2obj = new Player(player2Name.getText(), 'X');
                }

                if (intelligent.isSelected() || weak.isSelected()) {
                    ai = new MinMax(player2obj.getSymbol(), -1);
                }


                if (player1First.isSelected()) { // checking who plays first
                    message.setText(player1Name.getText() + ", it's your turn.");

                    player1.setStyle("-fx-background-color: #00ff00; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");
                    player2.setStyle("-fx-background-color: #ffffff; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");
                    isPlayer1Turn = true;
                } else {
                    message.setText(player2Name.getText() + ", it's your turn.");

                    player1.setStyle("-fx-background-color: #ffffff; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");
                    player2.setStyle("-fx-background-color: #00ff00; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");
                    isPlayer1Turn = false;

                    if (intelligent.isSelected()) {
                        move = ai.getBestMove(board);
                        buttons[move[0]][move[1]].setText(String.valueOf(player2obj.getSymbol()));
                        board.makeMove(player2obj.getSymbol(), move[0], move[1]);
                        isPlayer1Turn = true;
                        message.setText(player1Name.getText() + ", it's your turn.");
                    } else if (weak.isSelected()) {
                        move = ai.getRandomLegalMove(board);
                        buttons[move[0]][move[1]].setText(String.valueOf(player2obj.getSymbol()));
                        board.makeMove(player2obj.getSymbol(), move[0], move[1]);
                        isPlayer1Turn = true;
                        message.setText(player1Name.getText() + ", it's your turn.");
                    }
                }
            }
        });


        resetButton.setOnAction(e -> {
            newGame = true;

            for (Button[] button : buttons) {
                for (Button value : button) {
                    value.setDisable(true);
                    value.setText("");
                }
            }

            message.setText("");

            player1Name.setDisable(false);
            player2Name.setDisable(false);
            player1Name.setText("");
            player2Name.setText("");
            player1.setStyle("-fx-background-color: none; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #000000;");
            player2.setStyle("-fx-background-color: none; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #000000;");

            //enable all the buttons
            singlePlayer.setDisable(false);
            multiPlayer.setDisable(false);
            if (!newGame) {
                weak.setDisable(false);
                intelligent.setDisable(false);
            }
            player1Symbol_x.setDisable(false);
            player1Symbol_o.setDisable(false);
            player2Symbol_x2.setDisable(false);
            player2Symbol_o2.setDisable(false);
            player1First.setDisable(false);
            player2First.setDisable(false);

            //unselect all the buttons
            singlePlayer.setSelected(false);
            multiPlayer.setSelected(false);

            player1Symbol_x.setSelected(false);
            player1Symbol_o.setSelected(false);
            player2Symbol_x2.setSelected(false);
            player2Symbol_o2.setSelected(false);
            player1First.setSelected(false);
            player2First.setSelected(false);

            board.resetBoard();
            for (Button[] button : buttons) {
                for (Button value : button) {
                    value.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #000000;");
                }
            }

            newGame = false;
        });

        // adding event handlers to the buttons
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int finalI = i;
                int finalJ = j;

                buttons[i][j].addEventHandler(MOUSE_CLICKED, (e) -> {
                    if (board.isLegalMove(finalI, finalJ)) {
                        buttons[finalI][finalJ].setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #000000;");
                        if (isPlayer1Turn) {

                            if (multiPlayer.isSelected()) {
                                message.setText(player2Name.getText() + ", it's your turn.");
                            }

                            player1.setStyle("-fx-background-color: #ffffff; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");
                            player2.setStyle("-fx-background-color: #00ff00; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");

                            buttons[finalI][finalJ].setText(String.valueOf(player1obj.getSymbol()));
                            board.makeMove(player1obj.getSymbol(), finalI, finalJ);

                            if (intelligent.isSelected() && !board.isFull()) {
                                move = ai.getBestMove(board);
                                buttons[move[0]][move[1]].setText(String.valueOf(player2obj.getSymbol()));
                                board.makeMove(player2obj.getSymbol(), move[0], move[1]);
                            } else if (weak.isSelected()) {
                                move = ai.getRandomLegalMove(board);
                                buttons[move[0]][move[1]].setText(String.valueOf(player2obj.getSymbol()));
                                board.makeMove(player2obj.getSymbol(), move[0], move[1]);
                            } else {
                                isPlayer1Turn = false;
                            }
                        } else {
                            message.setText(player1Name.getText() + ", it's your turn.");

                            player1.setStyle("-fx-background-color: #00ff00; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");
                            player2.setStyle("-fx-background-color: #ffffff; -fx-padding: 5px; -fx-background-radius: 10%; -fx-font-size: 14px; -fx-font-weight: bold;");
                            buttons[finalI][finalJ].setText(String.valueOf(player2obj.getSymbol()));
                            board.makeMove(player2obj.getSymbol(), finalI, finalJ);
                            isPlayer1Turn = true;
                        }

                        int score = board.getScore(player1obj.getSymbol());
                        if (score == 1) {
                            message.setText(player1Name.getText() + ", has WON.");
                            int[][] winningButtons = board.getWinnerIndices();
                            for (int[] ints : winningButtons)
                                buttons[ints[0]][ints[1]].setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #ff0000;");

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game Over");
                            alert.setHeaderText("Player 1 won");
                            alert.setContentText("Player 1 won the game");
                            alert.showAndWait();
                            resetButton.fire();
                        } else if (score == -1) {
                            message.setText(player2Name.getText() + ", has WON.");

                            int[][] winningButtons = board.getWinnerIndices();
                            for (int[] ints : winningButtons)
                                buttons[ints[0]][ints[1]].setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #ff0000;");

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game Over");
                            alert.setHeaderText("Player 2 won");
                            alert.setContentText("Player 2 won the game");
                            alert.showAndWait();
                            resetButton.fire();
                        } else if (score == 0) {
                            message.setText("It's a draw.");

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game Over");
                            alert.setHeaderText("Draw");
                            alert.setContentText("The game ended in a draw");
                            alert.showAndWait();
                            resetButton.fire();
                        }
                    }
                });
            }
        }

    }


    public static void main(String[] args) {
        launch();

    }
}