.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib

.data
_c DQ ?
_b DQ ?
c2 DQ 2.2
DividirCero db "Error al dividir por cero!", 0
OverflowSuma db "La suma ha generado un Overflow!", 0

.code
start:
invoke ExitProcess, 0
end start