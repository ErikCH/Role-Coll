;****************************************************************************
; Author	: Zack Norcross
; Program	: RoleColl Motion Sensor rev A
; Description	: Main program for CS 426 final project
; For		: 89C4051 @ 11.0592MHz
;****************************************************************************

;----------------------------------------------------------------------------
; Defined Variables
;----------------------------------------------------------------------------
; TODO FIGURE OUT BUTTONS NEEDED
CR		EQU	0DH			; DEFINE CARRIAGE RETURN ASCII CODE	
LF		EQU	0AH			; DEFINE LINE FEED ASCII CODE

;----------------------------------------------------------------------------
; Vectors
;----------------------------------------------------------------------------

		ORG	0000H				; Reset Vector
		JMP	MAIN
		
		ORG	0003H				; External 0 Interrupt Vector
		LCALL	INT_0
		RETI
				
		ORG	000BH				; Timer 0 Interrupt Vector
		RETI
				
		ORG	0013H				; External 1 Interrupt Vector
		LCALL	INT_1
		RETI
		
		ORG	001BH				; Timer 1 Interrupt Vector
		RETI
		
		ORG	0023H				; Serial Interrupt Vector
		RETI

		ORG	0100H
;----------------------------------------------------------------------------		
; Interrupt Functions
;----------------------------------------------------------------------------

;****************************************************************************
; Name		: INT_0
; Affects	: A, PSW, DPTR
; Description	: INT0 interrupt handler. Triggered every time the motion
;		  sensor on the left is triggered.
; Input		: R0
;****************************************************************************
INT_0:		PUSH	ACC
		PUSH	PSW
		PUSH	DPH
		PUSH	DPL
		CJNE 	R0, #02H, INT_0_0		; IF RO SET
		MOV	R0, #00H			; CLEAR RO
	MOV	DPTR, #REVERSE			; POINT DPTR TO THE STRING
	LCALL	STRING
;		MOV	A, PGUP				; MOVE RIGHT TO LEFT
;SEND_PGUP:	JNB     TI, SEND_PGUP		   	; CHECK TBE
;		CLR     TI	        	  	; CLEAR TBE
;	        MOV     SBUF, A	        		; SEND CHARACTER
		SJMP	EXIT_INT_0
INT_0_0:	MOV	R0, #01H			; SET MOVE LEFT TO RIGHT	 
EXIT_INT_0:	SETB	P3.2				; RESET INTERUPT
		POP	DPL
		POP	DPH
		POP	PSW
		POP	ACC
		RET
		
;****************************************************************************
; Name		: INT_1
; Affects	: A, PSW, DPTR
; Description	: INT1 interrupt handler. Triggered every time the motion
;		  sensor on the right is triggered.
; Input		: R0
;****************************************************************************
INT_1:		PUSH	ACC
		PUSH	PSW
		PUSH	DPH
		PUSH	DPL
		CJNE 	R0, #01H, INT_1_1		; IF RO SET
		MOV	R0, #00H			; CLEAR RO
	MOV	DPTR, #FOWARD			; POINT DPTR TO THE STRING
	LCALL	STRING
;		MOV	A, PGDN				; MOVE LEFT TO RIGHT
;SEND_PGDN:	JNB     TI, SEND_PGDN		   	; CHECK TBE
;		CLR     TI	        	  	; CLEAR TBE
;	        MOV     SBUF, A	        		; SEND CHARACTER
		SJMP	EXIT_INT_1
INT_1_1:	MOV	R0, #02H			; SET MOVE RIGHT TO LEFT
EXIT_INT_1:	SETB	P3.3				; RESET INTERUPT
		POP	DPL
		POP	DPH
		POP	PSW
		POP	ACC
		RET
		
;----------------------------------------------------------------------------
; Main Program.
;----------------------------------------------------------------------------
MAIN:		LCALL 	INIT
LOOP:		SJMP	LOOP				; WAIT FOR INTERUPT

;----------------------------------------------------------------------------
; Functions
;----------------------------------------------------------------------------

;****************************************************************************
; Name		: Init
; Affects	: All ports
; Description	: Initilize
; Input		: None
;****************************************************************************
INIT:		MOV	R0, #00H			; CLEAR R0
		MOV 	PCON, #0H	 	 	; MAKE SURE BAUD NOT DOUBLED
		MOV 	IE, #0H				; DISABLE INTERRUPTS				
		SETB	EA				; ENABLE INTERPTS
		SETB	P3.2				; SET INT0
		SETB	P3.3				; SET INT1
		SETB	EX0				; ENABLE INT0
		SETB	EX1				; ENABLE INT1
		MOV 	TH1, #0FDH			; 9600 BAUD RATE
		MOV 	SCON, #52H			; 8-BIT UART MODE ALSO SET TO RECEIVE
		MOV 	TMOD, #21H			; TIMER 1 8-BIT RELOAD MODE
		SETB	TR1				; TURN TIMER 1 ON
		SETB	TI				; TI 
		CLR	RI				; RI 
		RET
		
;****************************************************************************
; Name		: Send
; Affects	: TI, SBUF, A, R7
; Description	: Send character to serial port
; Input		: R7
;****************************************************************************
SEND:		JNB     TI, SEND	   	; CHECK TBE
		CLR     TI	          	; CLEAR TBE
		MOV	A, R7
	        MOV     SBUF, A	        	; SEND CHARACTER FROM R7
		RET

;****************************************************************************
; Name		: String
; Affects	: A, DPTR, R7
; Description	: Print a string
; Input		: Requires DPTR set to string
;****************************************************************************
STRING:		CLR	A			; CLEARS A
		MOVC	A, @A+DPTR		; MOVE DATA AT DPTR TO A
		JZ	STRING_EXIT		; IF NULL 
		MOV	R7, A
		INC	DPTR			; NEXT CHAR
		LCALL	SEND			; SEND TO DISPLAY 
		SJMP	STRING			; JUMP TO BEGINING
STRING_EXIT:	RET

FOWARD:		DB	0AH,0DH,"FOWARD",0AH,0DH,0		
REVERSE:	DB	0AH,0DH,"REVERSE",0AH,0DH,0		

END