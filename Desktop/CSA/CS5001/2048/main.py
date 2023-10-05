'''
Yanrun Zhu
CS 5001, Spring 2023
Final Project -- 2048

This is a main function that executes the 2048 game.
'''
import turtle
from game import *

def main():

    # Create the main window
    ms = turtle.Screen()  
    ms.setup(430, 630, 400, 50)

    # Set the background color
    ms.bgcolor('gray')
    ms.title('2048')
    ms.tracer(0)
    ms.register_shape('bg.gif')

    game = Game()
    game.init()

    ms.listen()
    ms.onkey(game.move_up, 'Up')
    ms.onkey(game.move_down, 'Down')
    ms.onkey(game.move_left, 'Left')
    ms.onkey(game.move_right, 'Right')
    ms.onkey(game.win_lose_clear, 'Return')
    ms.onkey(game.exit, 'Escape')
    ms.onkey(game.restart, 'r')

    ms.mainloop()
    
if __name__ == "__main__":
    main()
