include 'Irvine32.inc'

;The actual homework does not require prompts or any kind of printing

.data 

fibo DWORD 50 DUP (?)
prompt BYTE "What number of the Fibonacci sequence do you want (n >= 0)? ", 0
answer1 BYTE "The result is: ", 0

.code 
main PROC

        mov edx, OFFSET prompt
        call WriteString
        call ReadDec
        mov esi, OFFSET fibo
        mov ecx, 47
        call Fibonacci

        mov edx, OFFSET answer1
        call WriteString
        shl eax, 2
        mov eax, fibo[eax]
        call WriteDec
        call Crlf

        exit
main ENDP


fibonacci PROC
        push ebx
        push eax

        mov ebx, 0
        mov eax, 1
        sub ecx, 1

        L1:
        mov edi, eax            ; edi holds n-1 (previous iteration's n)
        add eax, ebx            ; eax holds n-1, ebx holds n-2; eax now holds n
        mov ebx, edi            ; ebx now holds n-2 for next iteration
        mov [esi], eax          ; store in array
        add esi, 4
        call WriteDec
        call Crlf
        loop L1

        pop eax
        pop ebx

        ret
fibonacci ENDP


END main