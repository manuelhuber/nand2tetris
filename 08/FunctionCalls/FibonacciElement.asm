@256
D=M
@SP
M=D
call Sys.init
// function Main.fibonacci 0
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// push constant 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
// lt                     // checks if n<2
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
D=M-D
@true0
D;JLT
@SP
A=M
M=0
@continue1
0;JMP
(true0)
@SP
A=M
M=-1
(continue1)
@SP
M=M+1
// if-goto IF_TRUE
// goto IF_FALSE
// label IF_TRUE          // if n<2, return n
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// return
// label IF_FALSE         // if n>=2, returns fib(n-2)+fib(n-1)
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// push constant 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
// sub
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@SP
M=M+1
// call Main.fibonacci 1  // computes fib(n-2)
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// push constant 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
// sub
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@SP
M=M+1
// call Main.fibonacci 1  // computes fib(n-1)
// add                    // returns fib(n-1) + fib(n-2)
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M+D
@SP
M=M+1
// return
// function Sys.init 0
// push constant 4
@4
D=A
@SP
A=M
M=D
@SP
M=M+1
// call Main.fibonacci 1   // computes the 4'th fibonacci element
// label WHILE
// goto WHILE              // loops infinitely
