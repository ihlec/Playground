//sudoku.js

//Init Fields
var field1 = [
	[0, 1, 0, 5, 0, 0, 0, 0, 0],
	[9, 0, 4, 0, 0, 0, 0, 0, 8],
	[0, 0, 0, 4, 0, 0, 6, 7, 0],
	[0, 0, 0, 6, 9, 0, 0, 0, 0],
	[5, 8, 0, 0, 0, 0, 0, 9, 7],
	[0, 0, 0, 0, 4, 7, 0, 0, 0],
	[0, 5, 3, 0, 0, 4, 0, 0, 0],
	[2, 0, 0, 0, 0, 0, 3, 0, 6],
	[0, 0, 0, 0, 0, 1, 0, 8, 0],
   ]

fieldPrint(field1)
solveSudoku(field1)
fieldPrint(field1)

// ==============
// Checks
function checkDuplicates(numbersToCheck, number) {
    var numbersCount = []
    
	//iterate through each
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

function checkBox(field, row, col) {
	//slice of 0s
	var numbersBox = []

	//identify box in field
	var rowStart    = row / 3 * 3
	var rowEnd      = rowStart + 3
	var colStart    = col / 3 * 3
	var colEnd      = colStart + 3

	//Copy box numbers
	var n = 0
	for (var r = rowStart; r < rowEnd; r++) {
		for (var c = colStart; c < colEnd; c++) {
			numbersBox[n] = field[r][c]
			n++
		}
	}

	return checkDuplicates(numbersBox, field[row][col])
}

function checkRow(field, row, collumn) {
	//extract row numbers
	var numbersRow = []
	for (var i = 0; i < 9; i++) {
		numbersRow[i] = field[row][i]
	}

	return checkDuplicates(numbersRow, field[row][collumn])
}

function checkCol(field, row, collumn) {
	//extract col numbers
	var numbersCol = []
	for (var i = 0; i < 9; i++) {
		numbersCol[i] = field[i][collumn]
	}

	return checkDuplicates(numbersCol, field[row][collumn])
}

function checkValidityOfNumber(field, row, collumn) {
	//fieldPrint(field)
	return checkRow(field, row, collumn) && checkCol(field, row, collumn) && checkBox(field, row, collumn)
}

// Solve Sudoku by recursive brute force
function solveSudoku(field) {
	for (var row = 0; row < 9; row++) {
		for (var col = 0; col < 9; col++) {
			// if '0' : not solved yet
			if (field[row][col] == 0) {
				// test all numbers 1-9
				for (var num = 1; num <= 9; num++) {
					field[row][col] = num

					//Check parially-solved-field and create new solution field, if not fully solved
					if (checkValidityOfNumber(field, row, col) && solveSudoku(field)) {
						return true
					}

					// no number possible
					field[row][col] = 0
				}
				return false
			}
		}
	}
	return true //should never be reached
}

// =========================
// Output
function fieldPrint(field) {
	var outputString = ""
	for (var row = 0; row < 9; row++) {
		for (var col = 0; col < 9; col++) {
			outputString += field[row][col]
			outputString += " "
		}
		outputString += "\n"
	}
	outputString += "================="
	console.log(outputString)
}


