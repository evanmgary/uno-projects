TITLE pin

INCLUDE Irvine32.inc

.data 
range1 BYTE 5, 2, 4, 1, 3
range2 BYTE 9, 5, 8, 4, 6

test1 BYTE 6, 2, 5, 2, 4
test2 BYTE 7, 3, 4, 6, 9

validMsg BYTE "Valid Pin",0
invalidMsg BYTE "Invalid Pin, Invalid value at digit: ",0


.code

main PROC
	mov esi, OFFSET test1
	call DisplayPin
	call ValidatePin
	call DisplayResult
	call Crlf

	mov esi, OFFSET test2
	call DisplayPin
	call ValidatePin
	call DisplayResult
	call Crlf
	
	exit
main ENDP

;This procedure is used to display the pin entered, it is not required for the assignment
DisplayPin PROC
	push ebp
	push ecx
	push eax
	mov ecx, 0
	L1:
	mov eax, 0
	mov al, [esi+ecx]
	call WriteDec
	inc ecx
	cmp ecx, 5
	jnae L1
	call Crlf
	pop eax
	pop ecx
	pop ebp
	ret
DisplayPin ENDP

ValidatePin PROC
	push ebp				;push used registers to save values
	push ecx
	push edx
	push ebx

	mov ecx, 0				;start at index 0 of the arrays
	mov ebx, OFFSET range1	;lower limit of range
	mov edx, OFFSET range2	;higher limit of range
	mov eax, 0				;make eax 0 to clear higher parts of register

	L2:
	mov al, BYTE PTR[esi+ecx]	; al will contain the digit we are comparing from the user PIN
	cmp BYTE PTR[ebx+ecx], al	; ebx contains the lower range array, only care about the LSB
	ja LFail					; if the lower limit is above the PIN digit jump
	cmp BYTE PTR[edx+ecx], al	; edx contains the higher range array, only care about the LSB
	jb LFail					; if the higher limit is below the PIN digit jump
	inc ecx						; move on to next digit
	cmp ecx, 5					; see if all five digits have been compared
	jnae L2
	mov eax, 0					; eax should be 0 if the pin is valid
	jmp LEnd
	LFail:
	inc ecx						; put the correct digit into ecx (is one higher than the register has)
	mov eax, ecx				; put the location of the digit into eax to return
	LEnd:
	pop ebx
	pop edx
	pop ecx
	pop ebp
	ret
ValidatePin ENDP

;This displays whether or not the pin was valid. It is NOT required for the assignment
DisplayResult PROC
	push ebp
	push edx
	cmp eax, 0
	je L3
	mov edx, OFFSET invalidMsg
	call WriteString
	call WriteDec
	call Crlf
	jmp LExit
	L3 :
	mov edx, OFFSET validMsg
	call WriteString
	call Crlf
	LExit :
	pop edx
	pop ebp
	ret 
DisplayResult ENDP

END main