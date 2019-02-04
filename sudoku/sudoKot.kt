//Sudoku Solver Kotlin

fun main(args: Array<String>) {
    val field = arrayOf(
            intArrayOf(8, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 3, 6, 0, 0, 0, 0, 0),
            intArrayOf(0, 7, 0, 0, 9, 0, 2, 0, 0),
            intArrayOf(0, 5, 0, 0, 0, 7, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 4, 5, 7, 0, 0),
            intArrayOf(0, 0, 0, 1, 0, 0, 0, 3, 0),
            intArrayOf(0, 0, 1, 0, 0, 0, 0, 6, 8),
            intArrayOf(0, 0, 8, 5, 0, 0, 0, 1, 0),
            intArrayOf(0, 9, 0, 0, 0, 0, 4, 0, 0)
            )

    val s = Sudoku()
    s.printSudoku(field)
    s.solveSudoku(field)
    s.printSudoku(field)
}

class Sudoku {

    //Console Output Helper
    fun printSudoku(field: Array<IntArray>) {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                print(field[row][col].toString() + " ")
            }
            println()
        }
        println("==================")
    }

    // Recurisive Brute Force
    fun solveSudoku(field: Array<IntArray>): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                // 0 is still unsolved
                if (field[row][col] == 0) {
                    // Try numbers 1..9
                    for (num in 1 until 10) {
                        field[row][col] = num

                        // Check if partially solved AND create new solution field, if not fully solved
                        if (checkNumberValidity(field, row, col) && solveSudoku(field)) {
                            return true
                        }

                        // No number from 1..9 possible
                        field[row][col] = 0
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun checkNumberValidity(field: Array<IntArray>, row: Int, col: Int): Boolean {
        return (checkRow(field, row, col) && checkCol(field, row, col) && checkBox(field, row, col))
    }

    private fun checkBox(field: Array<IntArray>, row: Int, col: Int): Boolean {
        var boxNums = IntArray(9)

        // identify box in field
        val rowStart = row / 3 * 3
        val rowEnd = rowStart + 3
        val colStart = col / 3 * 3
        val colEnd = colStart + 3

        //Copy box numbers
        var num = 0
        for (r in rowStart until rowEnd) {
            for (c in colStart until colEnd) {
                boxNums[num] = field[r][c]
                num += 1
            }
        }

        return checkDuplicates(boxNums, field[row][col])
    }

    private fun checkCol(field: Array<IntArray>, row: Int, col: Int): Boolean {
        // Extract col numbers:
        var colNumbers = IntArray(9)
        for (num in 0 until 9) {
            colNumbers[num] = field[num][col]
        }

        return checkDuplicates(colNumbers, field[row][col])
    }

    private fun checkRow(field: Array<IntArray>, row: Int, col: Int): Boolean {
        // Extract row numbers:
        var rowNumbers = IntArray(9)
        for (num in 0 until 9) {
            rowNumbers[num] = field[row][num]
        }

        return checkDuplicates(rowNumbers, field[row][col])
    }

    private fun checkDuplicates(nums: IntArray, num: Int): Boolean {
        var duplicateCounter: Int = 0

        //iterate through numbers
        for (n in nums) {
            if (n == num) {
                duplicateCounter += 1
            }

            if (duplicateCounter == 2) {
                return false
            }
        }

        return true
    }
}