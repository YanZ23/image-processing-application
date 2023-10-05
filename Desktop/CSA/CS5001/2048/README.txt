README

Overview:
This project contains three main files: scene.py, game.py, and main.py. The scene.py file handles the visual aspects of the game, including drawing the game board and numbered squares. The game.py file handles the game logic and data management. Finally, the main.py file is used to execute the game. This README provides instructions on how to run the program, details about the high-level design, and a list of features.

High-Level Design:
- scene.py: This file contains two classes: "BackGround" and "Block". The "BackGround" class handles drawing the game title, score, and last action performed by the player, as well as a divider between the title and game board. The "Block" class handles drawing the numbered squares on the screen.
- game.py: This file contains a class called "Game" which handles the logic and display of the game "2048". The class has attributes such as the current game score, game state, game result, and a dictionary containing the game's blocks. The class also has methods for initializing the game, updating the screen, restarting the game, handling user input, growing new blocks, and judging whether the game has been won or lost.

Instructions:
To run the game, open main.py and execute the program. Use the arrow keys (up, down, left, right) to move the numbered blocks. When two blocks with the same number collide, they merge into a single block with a number that is the sum of the two blocks. The game is won when a block with the number 2048 is created, and the game is lost when the game board is full and there are no more valid moves. Press the 'esc' key to exit the game, and press the 'r' key to restart the game.

Features:
- The game has a graphical user interface with colorful blocks and a clean design.
- Users can move blocks using the arrow keys and merge blocks with the same number.
- The game keeps track of the user's score and displays it on the screen.
- If the user creates a block with the number 2048, they win the game.
- If the game board is full and there are no more valid moves, the user loses the game.
- Users can exit the game or restart it using the 'esc' and 'r' keys, respectively.
