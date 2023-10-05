'''
Yanrun Zhu
CS 5001, Spring 2023
Final Project -- 2048

This is test functions for those functions that do not use turtle drawing.
'''

import unittest
from game import *
from scene import *

class TestGame(unittest.TestCase):
    
    def setUp(self):
        self.game = Game()
        
    def test_init(self):
        self.assertEqual(self.game.score, 0)
        self.assertEqual(self.game.state, 0)
        self.assertEqual(self.game.result, 0)
        self.assertEqual(self.game.last_action, 'Game started')
        self.assertIsNone(self.game.back)
        self.assertDictEqual(self.game.block_dic, {})

    def test_list_oper(self):
        
        # Test case 1: No blocks moved
        num_list = [2, 4, 8, 16]
        expected_output = ([2, 4, 8, 16], False)
        self.assertEqual(self.game.list_oper(num_list), expected_output)

        # Test case 2: Blocks moved to the left
        num_list = [0, 2, 0, 2]
        expected_output = ([4, 0, 0, 0], True)
        self.assertEqual(self.game.list_oper(num_list), expected_output)

        # Test case 3: Blocks merged and moved to the left
        num_list = [4, 4, 8, 8]
        expected_output = ([8, 16, 0, 0], True)
        self.assertEqual(self.game.list_oper(num_list), expected_output)

if __name__ == '__main__':
    unittest.main()
