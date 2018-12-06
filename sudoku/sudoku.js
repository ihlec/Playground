//Sudoku.js

// ==============
// Checks
function checkDuplicates(numbersToCheck, number) {
    var numbersCount = 0
    
	//itersate through each
	for (var i = 0; i < 9; i++) {
		if (numbersToCheck[i] == number) {
			numbersCount++
		}
		if (numbersCount == 2) {
			return false
		}
	}
	return true
}

func checkBox(field *[][]int, row int, col int) bool {
	//slice of 0s
	var numbersBox [9]int

	//identify box in field
	rowStart := row / 3 * 3
	rowEnd := rowStart + 3
	colStart := col / 3 * 3
	colEnd := colStart + 3

	//Copy box numbers
	n := 0
	for r := rowStart; r < rowEnd; r++ {
		for c := colStart; c < colEnd; c++ {
			numbersBox[n] = (*field)[r][c]
			n++
		}
	}

	return checkDuplicates(numbersBox, (*field)[row][col])
}

func checkRow(field *[][]int, row int, collumn int) bool {
	//extract row numbers
	var numbersRow [9]int
	for i := 0; i < 9; i++ {
		numbersRow[i] = (*field)[row][i]
	}

	return checkDuplicates(numbersRow, (*field)[row][collumn])
}

func checkCol(field *[][]int, row int, collumn int) bool {
	//extract col numbers
	var numbersCol [9]int
	for i := 0; i < 9; i++ {
		numbersCol[i] = (*field)[i][collumn]
	}

	return checkDuplicates(numbersCol, (*field)[row][collumn])
}

func checkValidityOfNumber(field *[][]int, row int, collumn int) bool {
	//fieldPrint(field)
	return checkRow(field, row, collumn) && checkCol(field, row, collumn) && checkBox(field, row, collumn)
}

// Solve Sudoku by recursive brute force
func solveSudoku(field *[][]int) bool {
	for row := 0; row < 9; row++ {
		for col := 0; col < 9; col++ {
			// if '0' : not solved yet
			if (*field)[row][col] == 0 {
				// test all numbers 1-9
				for num := 1; num <= 9; num++ {
					(*field)[row][col] = num

					//Check parially-solved-field and create new solution field, if not fully solved
					if checkValidityOfNumber(field, row, col) && solveSudoku(field) {
						return true
					}

					// no number possible
					(*field)[row][col] = 0
				}
				return false
			}
		}
	}
	return true //should never be reached
}

// =========================
// Output
func fieldPrint(field *[][]int) {
	for row := 0; row < 9; row++ {
		for col := 0; col < 9; col++ {
			print((*field)[row][col], " ")
		}
		println()
	}
	println("===================")
}

func main() {
	//Init Fields
	var field1 = [][]int{
		{0, 1, 0, 5, 0, 0, 0, 0, 0},
		{9, 0, 4, 0, 0, 0, 0, 0, 8},
		{0, 0, 0, 4, 0, 0, 6, 7, 0},
		{0, 0, 0, 6, 9, 0, 0, 0, 0},
		{5, 8, 0, 0, 0, 0, 0, 9, 7},
		{0, 0, 0, 0, 4, 7, 0, 0, 0},
		{0, 5, 3, 0, 0, 4, 0, 0, 0},
		{2, 0, 0, 0, 0, 0, 3, 0, 6},
		{0, 0, 0, 0, 0, 1, 0, 8, 0},
	}

	// var field2 = [][]int{
	// 	{8, 0, 0, 0, 0, 0, 0, 0, 0},
	// 	{0, 0, 3, 6, 0, 0, 0, 0, 0},
	// 	{0, 7, 0, 0, 9, 0, 2, 0, 0},
	// 	{0, 5, 0, 0, 0, 7, 0, 0, 0},
	// 	{0, 0, 0, 0, 4, 5, 7, 0, 0},
	// 	{0, 0, 0, 1, 0, 0, 0, 3, 0},
	// 	{0, 0, 1, 0, 0, 0, 0, 6, 8},
	// 	{0, 0, 8, 5, 0, 0, 0, 1, 0},
	// 	{0, 9, 0, 0, 0, 0, 4, 0, 0},
	// }

	fieldPrint(&field1)
	solveSudoku(&field1)
	fieldPrint(&field1)

	// fieldPrint(&field2)
	// solveSudoku(&field2)
	// fieldPrint(&field2)
}
