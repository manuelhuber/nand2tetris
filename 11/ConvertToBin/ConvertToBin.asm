@256
D=A
@SP
M=D
@MAIN.Sys.init$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(MAIN.Sys.init$ret.0)
// function Main.main 1
(Main.main)
@SP
A=M
M=0
@SP
M=M+1
// push constant 8001
@8001
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 16
@16
D=A
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
// neg
@SP
M=M-1
@SP
A=M
M=-M
@SP
M=M+1
// call Main.fillMemory 3
@Main.Main.fillMemory$ret.1
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@3
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fillMemory
0;JMP
(Main.Main.fillMemory$ret.1)
// pop temp 0
@SP
M=M-1
@SP
A=M
D=M
@5
M=D
// push constant 8000
@8000
D=A
@SP
A=M
M=D
@SP
M=M+1
// call Memory.peek 1
@Main.Memory.peek$ret.2
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@1
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Memory.peek
0;JMP
(Main.Memory.peek$ret.2)
// pop local 0
@0
D=A
@LCL
D=D+M
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D
// push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// call Main.convert 1
@Main.Main.convert$ret.3
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@1
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.convert
0;JMP
(Main.Main.convert$ret.3)
// pop temp 0
@SP
M=M-1
@SP
A=M
D=M
@5
M=D
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// return
@LCL
D=M
@13
M=D
@LCL
D=M
@5
A=D-A
D=M
@14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@13
M=M-1
A=M
D=M
@THAT
M=D
@13
M=M-1
A=M
D=M
@THIS
M=D
@13
M=M-1
A=M
D=M
@ARG
M=D
@13
M=M-1
A=M
D=M
@LCL
M=D
@14
A=M
0;JMP
// function Main.convert 3
(Main.convert)
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
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
// neg
@SP
M=M-1
@SP
A=M
M=-M
@SP
M=M+1
// pop local 2
@2
D=A
@LCL
D=D+M
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D
// label start0
(start0)
// push local 2
@2
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// not
@SP
M=M-1
@SP
A=M
M=!M
@SP
M=M+1
// if-goto end0
@SP
M=M-1
@SP
A=M
D=M
@end0
D;JNE
// push local 1
@1
D=A
@LCL
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
// add
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
// pop local 1
@1
D=A
@LCL
D=D+M
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D
// push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// call Main.nextMask 1
@Main.Main.nextMask$ret.4
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@1
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.nextMask
0;JMP
(Main.Main.nextMask$ret.4)
// pop local 0
@0
D=A
@LCL
D=D+M
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D
// push local 1
@1
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// push constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
// gt
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
@true5
D;JGT
@SP
A=M
M=0
@continue6
0;JMP
(true5)
@SP
A=M
M=-1
(continue6)
@SP
M=M+1
// not
@SP
M=M-1
@SP
A=M
M=!M
@SP
M=M+1
// if-goto true1
@SP
M=M-1
@SP
A=M
D=M
@true1
D;JNE
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// pop local 2
@2
D=A
@LCL
D=D+M
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D
// goto end1
@end1
0;JMP
// label true1
(true1)
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
// push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// and
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M&D
@SP
M=M+1
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// eq
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
@true7
D;JEQ
@SP
A=M
M=0
@continue8
0;JMP
(true7)
@SP
A=M
M=-1
(continue8)
@SP
M=M+1
// not
@SP
M=M-1
@SP
A=M
M=!M
@SP
M=M+1
// if-goto true2
@SP
M=M-1
@SP
A=M
D=M
@true2
D;JNE
// push constant 8000
@8000
D=A
@SP
A=M
M=D
@SP
M=M+1
// push local 1
@1
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// add
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
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// call Memory.poke 2
@Main.Memory.poke$ret.9
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@2
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Memory.poke
0;JMP
(Main.Memory.poke$ret.9)
// pop temp 0
@SP
M=M-1
@SP
A=M
D=M
@5
M=D
// goto end2
@end2
0;JMP
// label true2
(true2)
// push constant 8000
@8000
D=A
@SP
A=M
M=D
@SP
M=M+1
// push local 1
@1
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// add
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
// push constant 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
// call Memory.poke 2
@Main.Memory.poke$ret.10
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@2
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Memory.poke
0;JMP
(Main.Memory.poke$ret.10)
// pop temp 0
@SP
M=M-1
@SP
A=M
D=M
@5
M=D
// label end2
(end2)
// label end1
(end1)
// goto start0
@start0
0;JMP
// label end0
(end0)
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// return
@LCL
D=M
@13
M=D
@LCL
D=M
@5
A=D-A
D=M
@14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@13
M=M-1
A=M
D=M
@THAT
M=D
@13
M=M-1
A=M
D=M
@THIS
M=D
@13
M=M-1
A=M
D=M
@ARG
M=D
@13
M=M-1
A=M
D=M
@LCL
M=D
@14
A=M
0;JMP
// function Main.nextMask 0
(Main.nextMask)
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
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// eq
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
@true11
D;JEQ
@SP
A=M
M=0
@continue12
0;JMP
(true11)
@SP
A=M
M=-1
(continue12)
@SP
M=M+1
// if-goto true3
@SP
M=M-1
@SP
A=M
D=M
@true3
D;JNE
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
// call Math.multiply 2
@Main.Math.multiply$ret.13
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@2
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Math.multiply
0;JMP
(Main.Math.multiply$ret.13)
// return
@LCL
D=M
@13
M=D
@LCL
D=M
@5
A=D-A
D=M
@14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@13
M=M-1
A=M
D=M
@THAT
M=D
@13
M=M-1
A=M
D=M
@THIS
M=D
@13
M=M-1
A=M
D=M
@ARG
M=D
@13
M=M-1
A=M
D=M
@LCL
M=D
@14
A=M
0;JMP
// goto end3
@end3
0;JMP
// label true3
(true3)
// push constant 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
// return
@LCL
D=M
@13
M=D
@LCL
D=M
@5
A=D-A
D=M
@14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@13
M=M-1
A=M
D=M
@THAT
M=D
@13
M=M-1
A=M
D=M
@THIS
M=D
@13
M=M-1
A=M
D=M
@ARG
M=D
@13
M=M-1
A=M
D=M
@LCL
M=D
@14
A=M
0;JMP
// label end3
(end3)
// function Main.fillMemory 0
(Main.fillMemory)
// label start4
(start4)
// push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// gt
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
@true14
D;JGT
@SP
A=M
M=0
@continue15
0;JMP
(true14)
@SP
A=M
M=-1
(continue15)
@SP
M=M+1
// not
@SP
M=M-1
@SP
A=M
M=!M
@SP
M=M+1
// if-goto end4
@SP
M=M-1
@SP
A=M
D=M
@end4
D;JNE
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
// push argument 2
@2
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
// call Memory.poke 2
@Main.Memory.poke$ret.16
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@2
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D
@SP
D=M
@LCL
M=D
@Memory.poke
0;JMP
(Main.Memory.poke$ret.16)
// pop temp 0
@SP
M=M-1
@SP
A=M
D=M
@5
M=D
// push argument 1
@1
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
// neg
@SP
M=M-1
@SP
A=M
M=-M
@SP
M=M+1
// add
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
// pop argument 1
@1
D=A
@ARG
D=D+M
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D
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
// add
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
// pop argument 0
@0
D=A
@ARG
D=D+M
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D
// goto start4
@start4
0;JMP
// label end4
(end4)
// push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
// return
@LCL
D=M
@13
M=D
@LCL
D=M
@5
A=D-A
D=M
@14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@13
M=M-1
A=M
D=M
@THAT
M=D
@13
M=M-1
A=M
D=M
@THIS
M=D
@13
M=M-1
A=M
D=M
@ARG
M=D
@13
M=M-1
A=M
D=M
@LCL
M=D
@14
A=M
0;JMP
