// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.


  @last_color
  M = 0
  
(START)
  @color
  M = 0  
  
  // if key is pressed, set color to black
  @KBD
  D = M
  @CHECK_CHANGE
  D; JLE
  @color
  M = -1

(CHECK_CHANGE)  
  @last_color
  D = M
  @color
  D = D - M
  @START
  D; JEQ  
  
(SET_PIXEL)
  @color
  D = M
  @last_color
  M = D
  @SCREEN
  D = A
  @addr
  M = D
  
(LOOP)
  @color
  D = M
  @addr
  A = M
  M = D
  
  @addr
  M = M + 1
  
  @KBD
  D = A
  @addr
  D = D - M
  @START
  D; JEQ
  
  @LOOP
  0; JMP