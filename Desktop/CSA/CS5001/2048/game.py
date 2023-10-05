'''
Yanrun Zhu
CS 5001, Spring 2023
Final Project -- 2048

This file contains a class called "Game" which handles the logic and display
of the game "2048". The class has attributes such as the current game score,
game state (whether the game is in progress or over), game result (whether
the game was a success or failure), and a dictionary containing the game's
blocks. The class also has methods for initializing the game, updating the
screen, restarting the game, handling user input (such as arrow keys),
growing new blocks, and judging whether the game has been won or lost. The
code also imports the "turtle" and "random" modules, as well as a custom
"scene" module that contains the "Block" and "BackGround" classes used
in the game.
'''
import turtle, random
from scene import *

class Game:
    '''
    This class handles the 2048 game's data and operations, such as moving
     the blocks and displaying the game's score.

    Attributes:
    - score (int): the current game score
    - state (int): the game state (0 = in progress, 1 = game over)
    - result (int): the game result (0 = failed, 1 = succeeded)
    - last_action (str): the last user action
    - back (BackGround): the game's background instance
    - block_dic (dict): a dictionary containing the game's blocks

    Methods:
    __init__(self), init(self), refresh(self), win_lose_clear(self),
    restart(self), exit(self), grow(self),judge(self), move_up(self),
    move_down(self), move_right(self), move_left(self),
    move_move(self, allpos1, allpos2, allpos3, allpos4),move(self, pos_list),
    list_oper(self, num_list)
    '''
    def __init__(self):
        '''
        initialize the game's score, state, result, last_action, background,
        and blocks dictionary
        '''
        self.score = 0
        
        # 0 In-Game 1 Ended
        self.state = 0

        # 0 Failure 1 Success
        self.result = 0
        self.last_action = 'Game started'
        self.back = None
        self.block_dic = {}

    def init(self):
        '''
        create the game's background and draw the blocks
        '''
        self.back = BackGround()

        # 16 numbers
        for i in item_pos:

            # draw 16 blocks with corresponding turtle positions
            block = Block()
            block.goto(i)
            self.block_dic[i] = block

        # grow two new blocks
        self.grow()
        self.grow()

    def refresh(self):
        '''
        update the screen by drawing the background and blocks
        '''
        self.back.draw(self.state, self.result, self.score, self.last_action)
        for block in self.block_dic.values():
            block.draw()

    def win_lose_clear(self):
        pass

    def restart(self):
        '''
        restart the game by resetting the score, state, result, clearing the
        blocks and background, and growing two new blocks
        '''
        self.score = 0
        self.state = 0
        self.result = 0

        for i in self.block_dic.values():
            i.num = 0
            i.clear()
        self.back.clear()
        self.last_action = 'Game started'
        self.grow()
        self.grow()

    def exit(self):
        turtle.bye()

    def grow(self):
        '''
        appears a random block of 2 or 4 numbers appears
        '''
        block_list = []
        for i in item_pos:
            
            # pick out the blank blocks
            if self.block_dic[i].num == 0:
                block_list.append(self.block_dic[i])

        # randomly select one of them
        turtle_choice = random.choice(block_list)

        # assign property num=2/4
        random_num = random.choice([2, 2, 2, 2, 4])
        turtle_choice.num = random_num

        self.judge()
        self.refresh()

    def judge(self):
        '''
        display game failure and hint text for reaching 2048
        '''
        
        # check if there is still a position to move
        judge = 0  
        for i in self.block_dic.values():
            for j in self.block_dic.values():
                if i.num == 0 or i.num == j.num and i.distance(j) == 100:
                    judge += 1

        # this condition allows the judgement of 2048 achievement to be made
        # only once
        if self.state == 0:  

            # no position to move, the game failed
            if judge == 0:
                self.state = 1
                self.result = 0

            for k in self.block_dic.values():
                # sucesss
                if k.num == 2048:  
                    self.state = 1
                    self.result = 1

    def move_up(self):
        '''
        handle the up key
        '''
        self.last_action = 'up'
        allpos1 = item_pos[::4]  
        allpos2 = item_pos[1::4]
        allpos3 = item_pos[2::4]
        allpos4 = item_pos[3::4]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_down(self):
        '''
        handle the down key
        '''
        self.last_action = 'down'
        allpos1 = item_pos[-4::-4]
        allpos2 = item_pos[-3::-4]
        allpos3 = item_pos[-2::-4]
        allpos4 = item_pos[-1::-4]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_left(self):
        '''
        handle the left key
        '''
        self.last_action = 'left'
        allpos1 = item_pos[:4]
        allpos2 = item_pos[4:8]
        allpos3 = item_pos[8:12]
        allpos4 = item_pos[12:16]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_right(self):
        '''
        handle the right key
        '''
        self.last_action = 'right'
        allpos1 = item_pos[-1:-5:-1]
        allpos2 = item_pos[-5:-9:-1]
        allpos3 = item_pos[-9:-13:-1]
        allpos4 = item_pos[-13:-17:-1]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_move(self, allpos1, allpos2, allpos3, allpos4):
        '''
        Move blocks and check if new blocks should be generated

        Parameters:
        allpos1 (list): List of turtle positions for first row or column
        of blocks
        allpos2 (list): List of turtle positions for second row or column
        of blocks
        allpos3 (list): List of turtle positions for third row or column
        of blocks
        allpos4 (list): List of turtle positions for fourth row or column
        of blocks
        '''
        if self.state == 0:

            # Move each row or column
            count1 = self.move(allpos1)  
            count2 = self.move(allpos2)
            count3 = self.move(allpos3)
            count4 = self.move(allpos4)
            
            # If any block moved, generate new block
            if count1 or count2 or count3 or count4:  
                self.grow()

    def move(self, pos_list):
        '''
        Move blocks in a row or column

        Parameters:
        pos_list (list): List of turtle positions for row or column of blocks

        Returns:
        count (bool): True if any block moved, False otherwise
        '''

        # Get list of numbers for blocks in the row or column
        num_list = []  
        for i in pos_list:
            
            # form the NUM of these turtles into a list
            num_list.append(self.block_dic[i].num)
            
        # Perform list operations on numbers
        new_num_list, count = self.list_oper(num_list)
        
        # Update block numbers and redraw blocks
        for j in range(len(new_num_list)):
            self.block_dic[pos_list[j]].num = new_num_list[j]
            self.block_dic[pos_list[j]].draw()
        return count

    def list_oper(self, num_list):
        '''
        Perform operations on list of numbers for blocks in a row or column

        Parameters:
        num_list (list): List of numbers for blocks in a row or column

        Returns:
        new_temp (list): List of numbers after operations are performed
        count (bool): True if any block moved, False otherwise
        '''
        global score
        count = True
        temp = []
        new_temp = []

        # remove all zeros from list and store in temp
        for j in num_list:
            if j != 0:
                temp.append(j)  
        flag = True

        # perform operations on temp and store in new_temp
        for k in range(len(temp)):
            if flag:
                if k < len(temp) - 1 and temp[k] == temp[k + 1]:
                    new_temp.append(temp[k] * 2)
                    flag = False
                    self.score += temp[k] * 2
                else:
                    new_temp.append(temp[k])
            else:
                flag = True

        # Add zeros to end of new_temp to match original length of num_list
        for m in range(len(num_list) - len(new_temp)):
            new_temp.append(0)  
        if new_temp == num_list:
            count = False  
        return (new_temp, count)

    
