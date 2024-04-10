/* 
 * The Data Lab 
 * CSCI 2467 Spring 2018
 * 
 **********
 * STEP 0 * Help Desk Reference Solution : All but two problems solved
 **********
 *
 * bits.c - Source file with your solutions to the Lab.
 *          This is the file you will hand in to your instructor.
 *
 * WARNING: Do not include the <stdio.h> header; it confuses the dlc
 * compiler. You can still use printf for debugging without including
 * <stdio.h>, although you might get a compiler warning. In general,
 * it's not good practice to ignore compiler warnings, but in this
 * case it's OK.  
 */

#if 0
/******************************************************
 * STEP 1: Read the following instructions carefully. *
 ******************************************************/

You will provide your solution to the Data Lab by
editing the collection of functions in this source file.

You will ONLY turn in this file (bits.c), everything else
is provided to help you check your work.

INTEGER CODING RULES:
 
  Replace the "return" statement in each function with one
  or more lines of C code that implements the function. Your code 
  must conform to the following style:
 
  int Funct(arg1, arg2, ...) {
      /* brief description of how your implementation works */
      int var1 = Expr1;
      ...
      int varM = ExprM;

      varJ = ExprJ;
      ...
      varN = ExprN;
      return ExprR;
  }

  Each "Expr" is an expression using ONLY the following:
  1. Integer constants 0 through 255 (0xFF), inclusive. You are
      not allowed to use big constants such as 0xffffffff.
  2. Function arguments and local variables (no global variables).
  3. Unary integer operations ! ~
  4. Binary integer operations & ^ | + << >>
    
  Some of the problems restrict the set of allowed operators even further.
  Each "Expr" may consist of multiple operators. You are not restricted to
  one operator per line.

  You are expressly forbidden to:
  1. Use any control constructs such as if, do, while, for, switch, etc.
  2. Define or use any macros.
  3. Define any additional functions in this file.
  4. Call any functions.
  5. Use any other operations, such as &&, ||, -, or ?:
  6. Use any form of casting.
  7. Use any data type other than int.  This implies that you
     cannot use arrays, structs, or unions.
 
  You may assume that your machine:
  1. Uses 2s complement, 32-bit representations of integers.
  2. Performs right shifts arithmetically.
  3. Has unpredictable behavior when shifting an integer by more
     than the word size.

EXAMPLES OF ACCEPTABLE CODING STYLE:
  /*
   * pow2plus1 - returns 2^x + 1, where 0 <= x <= 31
   */
  int pow2plus1(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     return (1 << x) + 1;
  }

  /*
   * pow2plus4 - returns 2^x + 4, where 0 <= x <= 31
   */
  int pow2plus4(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     int result = (1 << x);
     result += 4;
     return result;
  }

REMINDER: Use the ./btest program to check your functions
          for correctness after making any changes. The
          program ./driver.pl will check for illegal
          operators and give you your final score.
#endif

/**********************************************************************************
 * STEP 2: Modify the following functions according the coding rules given above. *
 *                                                                                *
 *         You MUST explain each function in a comment                            *
 *         if you want to receive credit                                          *
 **********************************************************************************/

/***************************************************
 * BOOLEAN operations (8 puzzles, 18 points total) *
 ***************************************************/
/* 
 * bitOr - x|y using only ~ and & 
 *   Example: bitOr(6, 5) = 7
 *   Legal ops: ~ &
 *   Max ops: 8
 *   Rating: 1
 */
int bitOr(int x, int y) {
  //Apply DeMorgan's Laws to change & into |, then negate
  return ~( (~x) & (~y) );
  //Used class hints from the handout day for this and bitAnd
}

/* 
 * bitAnd - x&y using only ~ and | 
 *   Example: bitAnd(6, 5) = 4
 *   Legal ops: ~ |
 *   Max ops: 8
 *   Rating: 1
 */
int bitAnd(int x, int y) {
  //Apply DeMorgan's Laws to change | into &, then negate
  return ~( (~x) | (~y) );
}

/* 
 * bitXor - x^y using only ~ and & 
 *   Example: bitXor(4, 5) = 1
 *   Legal ops: ~ &
 *   Max ops: 14
 *   Rating: 1
 */
int bitXor(int x, int y) {
  //First I manipulated some binary numbers, finding the & and |
  //Then I negated both the & and |
  //I noticed that (x & y) | ~(x | y) looked a lot like x^y
  //I took the negation of the above to get x^y giving ~((x & y) | (~(x | y)))
  //Then I applied DeMorgan's Laws to remove the | and change it into the form below
  return (~(x & y)) & ~(~x & ~y);
}

/* 
 * evenBits - return word with all even-numbered bits set to 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 8
 *   Rating: 2
 */
int evenBits(void) {
  //0x55 is 01010101
  //Make a word of all even bits by shifting 0x55 8 times, then filling the zeros with |
  //01010101 shifted left 8 times gives 0101010100000000
  //Then 0101010100000000 | 01010101 gives 0101010101010101
  //Repeat the process twice to fill in the rest of the 32 bits
  return ((((((0x55 << 8) | (0x55)) << 8) | 0x55) << 8) | 0x55)  ;
}

/* 
 * isNotEqual - return 0 if x == y, and 1 otherwise 
 *   Examples: isNotEqual(5,5) = 0, isNotEqual(4,5) = 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 6
 *   Rating: 2
 */
int isNotEqual(int x, int y) {
  //Anything xor itself is zero. The first ! returns 1 if the argument is 0 and 0 if it is nonzero.
  //The second ! reverses the first so that 0 means equal.
  return !!(x ^ y);
}

/* 
 * copyLSB - set all bits of result to least significant bit of x
 *   Example: copyLSB(5) = 0xFFFFFFFF, copyLSB(6) = 0x00000000
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 5
 *   Rating: 2
 */
int copyLSB(int x) {
  //Uses the properties of shifts. (x & 1) reduces x to only the LSB
  //Left shift 31 moves the LSB to the MSB
  //Right shift 31 fills the bits with the MSB
  return (x & 1) << 31 >> 31;
}

/* 
 * conditional - same as x ? y : z 
 *   Example: conditional(2,4,5) = 4
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 16
 *   Rating: 3
 */
int conditional(int x, int y, int z) {
  // xbyte is a word filled with all 1s or 0s 
   // If x is zero, fills it with 0s, if not, fill it with 1s
   // We are using xbyte to make the "decision" 
   int xbyte;
   xbyte = ((!!x) << 31) >> 31; 
   // (y^z) ^ y = z and (y^z) ^ z = y 
   // Make and xor of y and z, then apply the appropriate xor
   // Use xbyte to decide which xor to apply
   // Whichever one is & 0xffffffff is "used"
   return (y ^ z) ^ ((y & ~xbyte) | (z & xbyte));
}

/*
 * bitParity - returns 1 if x contains an odd number of 0's
 *   Examples: bitParity(5) = 0, bitParity(7) = 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 20
 *   Rating: 4
 */
int bitParity(int x) {
   // Utilizing the properties of xor, 1^1 = 0, 0^0 = 0, 1^0 = 1, 0^1 = 0
  // 01 and 10 have an odd number of bits and result in 1, 00 and 11 the opposite
  // So, I tried to apply xor to larger bit sequences
  // Trying 4 bits: 1100 ^ 1001 = 0101 1111^0001 = 1110 and many others
  // Trying 8 bits: 10110000 ^ 00000011 = 10110011 01000000 ^ 00000001 = 01000001
  // Put these in the form x1^x2 = y
  // I found that if the sum of 1's in x1 and x2 is odd, the sum of the 1's in y is also odd (same for even)
  // Applied to a 32 bit number, we can apply this expression using different parts of the number to "shrink" it
  // 32 = 2^5, so we need to do this 6 times to get to 1 bit
  int x2, x3, x4, x5;
  // Use a shift to get the more significant bits into a place where they can be compared, then use xor
  // The left bits left over after the xor do NOT matter because the data we are using xor on is smaller each time
  x2 = (x >> 16) ^ x; //32 to 16
  x3 = (x2 ^ (x2 >> 8)); //16 to 8
  x4 = x3 ^ (x3 >> 4); //8 to 4
  x5 = x4 ^ (x4 >> 2); //4 to 2
  return (x5^ (x5>>1)) & 1;
  // We got a hint for this in class, but I had figured it out before experimenting with xor
}
/*******************************************
 * INTEGERS (8 puzzles, 22 points total)   *
 *******************************************/
/* 
 * minusOne - return a value of -1 
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 2
 *   Rating: 1
 */
int minusOne(void) {
  // In two's complement we reverse the bits and add one. 
  // 1 with reversed bits is 0xFFFFFFFE, so add 1 to get the two's complment, 0xFFFFFFF which is ~0 
  return ~0;
}

/* 
 * TMax - return maximum two's complement integer 
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 4
 *   Rating: 1
 */
int tmax(void) {
  // The largest two's complement integer is 0x7FFFFFFF
  // Make this by making 0x80000000 using a shift and negating
    return ~(8<<28);
}

/* 
 * negate - return -x 
 *   Example: negate(1) = -1.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 5
 *   Rating: 2
 */
int negate(int x) {
  // Negate the bits, then account for 2's complement by adding 1
  return ~x + 1;
}

/* 
 * isNegative - return 1 if x < 0, return 0 otherwise 
 *   Example: isNegative(-1) = 1.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 6
 *   Rating: 2
 */
int isNegative(int x) {
  //The first bit indicates the sign. 0 for 0 or positive, 1 for negative.
  //Shift the first bit all the way to the last bit
  //Use a 0x1 mask on the last bit to see if it is 0 or 1
  return ((x >> 31) & 1);
}

/* 
 * isPositive - return 1 if x > 0, return 0 otherwise 
 *   Example: isPositive(-1) = 0.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 8
 *   Rating: 4
 */
int isPositive(int x) {
  // Again, the first bit indicates the sign. 0 for 0 or positive, 1 for negative
  // (x>>31) pushes the first bit (MSB) all the way through the byte
  // Use & 1 to see if the LSB is 0 or 1, 1 indicates negative, 0 indicates positive or 0
  // Because we want 1 for positive (unlike isNegative above), we need to negate this result
  // But, if we apply this to zero, we get ~((0 >> 31) & 1) = ~(0 & 1) = 1
  // So, (!!x) is used to check if x is zero and use & to override the previous expression
  // Returns 0 if x is zero and 1 if not
  return ~((x >> 31) & 1) & (!!x);
}

/* 
 * bang - Compute !x without using !
 *   Examples: bang(3) = 0, bang(0) = 1
 *   Legal ops: ~ & ^ | + << >>
 *   Max ops: 12
 *   Rating: 4 
 */
int bang(int x) {
  // This algorithm uses the same procedure used in bitParity
  // Except, use | instead of ^
  // When using |, a 0 can only make it to the last bit (result) if there are NO zeros in the word
  // An extra negation is applied to get the output to be correct (otherwise bang(0) = 0
  // There is a more efficient way to do this but I don't know it
  int x2, x3, x4, x5, result;
  x2 = (x >> 16) | x;
  x3 = x2 | (x2 >> 8);
  x4 = x3 | (x3 >> 4);
  x5 = x4 | (x4 >> 2);
  result = (~(x5 | (x5 >> 1))) & 1;
  return result;
}

/* 
 * addOK - Determine if can compute x+y without overflow
 *   Example: addOK(0x80000000,0x80000000) = 0,
 *            addOK(0x80000000,0x70000000) = 1, 
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 20
 *   Rating: 3
 */
int addOK(int x, int y) {
  int signX, signY, signSum;
  // Use the properties of how signs change in overflow
  // First, get the signs of x and y
  signX = (x >>31) & 1;
  signY = (y >>31) & 1;
  // Find the sign of their sum
  signSum = ((x + y) >> 31) & 1;
  
  // An overflow can only occur when adding two nonzero numbers of the same sign
  // (signX ^ signY) checks to see if the signs are different
  // (!x) and (!y) checks to see if either x or y is zero
  // (!(signSum ^ signX)) checks to see if the sum of x and y has a different sign than x
  // If so, indicates an overflow if the other conditions do not override it 
   return (signX ^ signY) | (!(signSum ^ signX)) | (!x) | (!y);
}

/* 
 * absVal - absolute value of x
 *   Example: absVal(-1) = 1.
 *   You may assume -TMax <= x <= TMax
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 10
 *   Rating: 4
 */
int absVal(int x) {
  // we need a 32 bit number filled with 1 for negative or 0 for positive
  int sign = x >> 31;
  // The left side of the or is the case x is negative
  // Negate x as done above in the negate function
  // Then, if x is negative sign variable will be FFFFFFFF and the negated value will be retained with the &
  // Right side of or is is x is positive, ~sign will be FFFFFFFF and x will be retained with the &
  return ((~x + 1) & (sign)) | (x & (~sign)); 
}
/*************************************************************
 * BONUS puzzles BELOW! be advised, some are quite difficult *
 ************************************************************/
/***************************************************
 INTEGERS and BOOLEAN (5 puzzles, up to 14 points) *
 ***************************************************/
/* 
 * isLessOrEqual - if x <= y  then return 1, else return 0 
 *   Example: isLessOrEqual(4,5) = 1.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 24
 *   Rating: 4
 */
int isLessOrEqual(int x, int y) {
  int diff, signX, signY, signDiff, signSum, overflow;
   // Use the properties of subtraction
   // When x > y, the sign of the difference should be positive (0)
   // When y > x, the sign should be negative (1)
   // Because of 2's complement, the difference can be found by adding the complement and 1
   diff = x +(~y + 1);
   signX = (x >> 31) & 1;
   signY = (y >> 31) & 1;
   signDiff = ((diff >> 31) & 1);
   // We also need to account for overflow
   // When an overflow occurs, the results are reversed:
   // Adding a negative to a negative = positive
   // Adding a positive to a positive = negative
   // Overflow CANNOT occur when the signs of x and y are the same so use signX^signY to check
   // Also check if the signs of x and the difference are the same (they will be different in an overflow)
   // overflow is set to 1 if the signs of x and y are different and the sign of x differs from the sign of the difference
   overflow = (signX ^ signY) & (signX ^ signDiff);
   // see the table for how the result should work out, no overflow means signDiff should not be changed
   //           overflow
   //            0   1
   //            
   //signDiff 0  0   1
   //         1  1   0
   // This looks like xor, so xor is used
   // !diff returns 1 if the difference is zero, so the function will always return 1 if the difference is zero (x = y)
   return (signDiff ^ overflow) | (!diff);
}

/* 
 * byteSwap - swaps the nth byte and the mth byte
 *  Examples: byteSwap(0x12345678, 1, 3) = 0x56341278
 *            byteSwap(0xDEADBEEF, 0, 2) = 0xDEEFBEAD
 *  You may assume that 0 <= n <= 3, 0 <= m <= 3
 *  Legal ops: ! ~ & ^ | + << >>
 *  Max ops: 25
 *  Rating: 3
 */
int byteSwap(int x, int n, int m) {
    
    // Use various bitmasks to isolate each byte
    // Swap bytes using shifts
    int toSwap1, toSwap2, toKeep1, toKeep2, toKeep;
    // m and n are the actual shift amounts: x << 3 is the same as x*8
    // To illustrate the swap, take 0x12345678 with n = 0 and m = 2
    // For the example, n will become 0 and n will become 16
    m = m << 3;
    n = n << 3;

    // (x & (0xff) << n) applies a bitmask to the n byte making a word with only the byte and 0s
    // >> n & 0xff shifts the byte to the rightmost bits and zeroes the rest of the word
    // then from the byte in the rightmost bits, shift left into the m byte (this is the "swap")
    // save the new word with the moved byte in a variable
    toSwap1 = (((x & (0xFF) << n) >> n) & 0xff) << m;
  
    // repeat the procedure for the byte at m
    toSwap2 = (((x & (0xFF) << m) >> m) & 0xff) << n;
  
    // Generate masks corresponding to the bytes that are being swapped
    // Combine the two masks and negate them to get a mask for the bytes that are being kept
    toKeep1 = (0xFF) << m;
    toKeep2 = (0xFF) << n;
    toKeep = ~(toKeep1 | toKeep2);
  
    // zero out the bytes that are being swapped using the toKeep mask
    toKeep = x & toKeep;
      // Put the swapped bytes back into the word to get the result
    x = toKeep | (toSwap1 | toSwap2); 
    return x;
}

/*
 * bitCount - returns count of number of 1's in word
 *   Examples: bitCount(5) = 2, bitCount(7) = 3
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 40
 *   Rating: 4
 */
int bitCount(int x) {
  //not attempted
  return 2;
}

/* 
 * logicalShift - shift x to the right by n, using a logical shift
 *   Can assume that 0 <= n <= 31
 *   Examples: logicalShift(0x87654321,4) = 0x08765432
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 20
 *   Rating: 3 
 */
int logicalShift(int x, int n) {
  int sign;
  // This problem obviously involves using a right shift, the challenge is removing the 1s from the left when a negative is shifted
  // The first step is to find the sign of x since right shifting a positive is already a logical shift
  sign = ((x >> 31) & 1) & (!!n);
  //printf("%x\n", sign);
  //printf("%x\n", x >> n);
  // x >> n is the actual shift, the rest is the creation and use of the mask to remove the 1s

  return (x >> n) & ( ~ ((sign << 31) >> n << (1 & !!n)));
}

/* 
 * upperBits - pads n upper bits with 1's
 *  You may assume 0 <= n <= 32
 *  Example: upperBits(4) = 0xF0000000
 *  Legal ops: ! ~ & ^ | + << >>
 *  Max ops: 10
 *  Rating: 1
 */
int upperBits(int n) {
  //shifts a bit to the msb, then shifts it back right n-1 times. Shifts zero if n is 0
  //double ! is used to check if zero
  //n + ~0 is equivalent to n-1
  return (!!n << 31) >> (n + ~0) ;
}
/************************************
 * BONUS: FLOATING POINT (6 points) *
 ************************************

  FLOATING POINT CODING RULES

For the problems that challenge you to implent floating-point operations,
the coding rules are less strict.  You are allowed to use looping (for, 
while) and conditional control (if/else).  You are allowed to use both
ints and unsigneds. You can use arbitrary integer and unsigned constants.

You are expressly forbidden to:
  1. Define or use any macros.
  2. Define any additional functions in this file.
  3. Call any functions.
  4. Use any form of casting.
  5. Use any data type other than int or unsigned.  This means that you
     cannot use arrays, structs, or unions.
  6. Use any floating point data types, operations, or constants.
*/
/* 
 * float_abs - Return bit-level equivalent of absolute value of f for
 *   floating point argument f.
 *   Both the argument and result are passed as unsigned int's, but
 *   they are to be interpreted as the bit-level representations of
 *   single-precision floating point values.
 *   When argument is NaN, return argument..
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 10
 *   Rating: 3
 */
unsigned float_abs(unsigned uf) {
  unsigned newuf;
  // The only thing to do in this problem is make the sign bit 0 (abs is always positive)
  // But we also need to check to see if the number is nan (exponent is 0xff with fraction bit starting with 1)
  // Negate 0x80000000 to get 0x7fffffff as a mask, then & with uf to get uf with sign bit 0
  newuf = (~(1 << 31)) & uf;
  // To see if the number is NaN, check to see if the fraction bit is 0xff
  // Then see if the first number of the fraction part is 1 (as in 0x7ffc0000, as shown in tests.c)
  // return the argument if NaN, otherwise return abs
  if (((newuf >> 23) == 0xff ) && (((newuf >> 22) & 1) == 1)){
    //printf("%x %x \n",newuf >> 23, newuf >> 22);
    return uf;
  }
  else{
    return newuf;
  }
}

/* 
 * float_f2i - Return bit-level equivalent of expression (int) f
 *   for floating point argument f.
 *   Argument is passed as unsigned int, but
 *   it is to be interpreted as the bit-level representation of a
 *   single-precision floating point value.
 *   Anything out of range (including NaN and infinity) should return
 *   0x80000000u.
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 30
 *   Rating: 4
 */
int float_f2i(unsigned uf) {
  // not attempted
  return 2;
}
