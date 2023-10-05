'''
Yanrun Zhu
CS 5001, Spring 2023
Final Project -- 2048

This file defines two classes: "BackGround" for drawing shapes other than
numbered squares and "Block" for drawing the numbered squares. The
"BackGround" class has methods for drawing the game title, score, and last
action performed by the player, as well as a divider between the title and
game board. The "Block" class has a method for updating the numbered squares
on the screen. The code also includes a dictionary that maps each number to
a color, a list of positions for the squares, and code for initializing the
game and handling user input.
'''
import turtle

item_pos = [(-150, 50), (-50, 50), (50, 50), (150, 50),
              (-150, -50), (-50, -50), (50, -50), (150, -50),
              (-150, -150), (-50, -150), (50, -150), (150, -150),
              (-150, -250), (-50, -250), (50, -250), (150, -250)]


# Define the color of each number
dic_draw = {2: '#eee6db', 4: '#efe0cd', 8: '#f5af7b',
            16: '#fb9660', 32: '#f57d5a', 64: '#f95c3d',
            128: '#eccc75', 256: '#eece61', 512: '#efc853',
            1024: '#ebc53c', 2048: '#eec430', 4096: '#aeb879',
            8192: '#aab767', 16384: '#a6b74f'}


class BackGround(turtle.Turtle):
    '''
    Define a class for drawing shapes other than numbered squares

    Methods:
    __init__()
        Initializes the BackGround object.
    draw(state, result, score, action)
        Draws the background based on the given game state, result, score,
        and action.
    draw_title(state, result, score, action)
        Draws the game title, score, and the last action performed.
    show_divider()
        Draws a divider between the title and the game board.
    draw_block()
        Draws the background blocks.
    '''
    def __init__(self):
        '''
        Initializes a new BackGround object.
        '''
        super().__init__()
        self.penup()
        self.ht()

    def draw(self, state, result, score, action):
        '''
        Draws the background based on the given game state, result, score,
        and action.

        Parameters:
        state : int
            The current state of the game.
        result : bool
            True if the game is won, False otherwise.
        score : int
            The current score of the player.
        action : str
            The last action performed by the player.
        '''
        self.clear()
        self.draw_title(state, result, score, action)
        self.show_divider()
        self.draw_block()

    def draw_title(self, state, result, score, action):
        '''
        Draws the game title, score, and the last action performed.

        Parameters:
        state : int
            The current state of the game.
        result : bool
            True if the game is won, False otherwise.
        score : int
            The current score of the player.
        action : str
            The last action performed by the player.
        '''
        self.color('white', 'white')  
        self.goto(-200, 270)
        self.write('Press esc to exit the game', align='left',
                   font=('Helvetica', 15, 'bold'))
        self.goto(-200, 250)
        self.write('Press r to restart the game', align='left',
                   font=('Helvetica', 15, 'bold'))

        self.goto(-200, 180)
        if state == 0:
            self.write('Last action: ' + action, align='left',
                       font=('Helvetica', 15, 'bold'))
        else:
            if result:
                self.write('Congratulations, You win!', align='left',
                           font=('Helvetica', 15, 'bold'))
            else:
                self.write('What a pity, You lose!', align='left',
                           font=('Helvetica', 15, 'bold'))

        # Draws the score
        self.color('white', 'white')
        self.goto(80, 240)
        self.write('Score:', align='left', font=('Helvetica', 24, 'bold'))
        self.goto(90, 180)
        self.write(str(score), align='left', font=('Helvetica', 48, 'bold'))

    def show_divider(self):
        '''
        Draws a divider between the title and the game board.
        '''
        self.color('white', 'white') 
        self.goto(-215, 120)
        self.begin_fill()
        self.goto(215, 120)
        self.goto(215, 110)
        self.goto(-215, 110)
        self.end_fill()

    def draw_block(self):
        '''
        Draws the background squares
        '''
        self.shape('bg.gif')
        for i in item_pos:
            self.goto(i)
            self.stamp()


class Block(turtle.Turtle):
    '''
    Define a class for drawing the numbered squares

    Methods:
    __init__()
        Initializes the Block object.
    draw(self)
       Draws the number blocks on the screen.   
    '''
    def __init__(self):
        '''
        Initializes a new instance of the Block class.
        '''
        super().__init__()
        self.ht()
        self.penup()
        self.num = 0

    def draw(self):
        '''
        Updates the number blocks on the screen.
        '''
        self.clear()
        
        # If the number is greater than 0, draw the block
        if self.num > 0:

            # Select the color based on the number
            self.color(f'{dic_draw[self.num]}')  
            self.begin_fill()
            self.goto(self.xcor() + 48, self.ycor() + 48)
            self.goto(self.xcor() - 96, self.ycor())
            self.goto(self.xcor(), self.ycor() - 96)
            self.goto(self.xcor() + 96, self.ycor())
            self.goto(self.xcor(), self.ycor() + 96)
            self.end_fill()
            self.goto(self.xcor() - 48, self.ycor() - 68)

            # Choose the color of the number based on the number
            if self.num > 4:  
                self.color('white')
            else:
                self.color('#6d6058')
            self.write(f'{self.num}', align='center',
                       font=('Arial', 27, 'bold'))
            self.goto(self.xcor(), self.ycor() + 20)


