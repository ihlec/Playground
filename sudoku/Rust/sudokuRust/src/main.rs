// Sudoku Rust

fn main() {
    println!("Hello, world!");

    //Init Fields
	let mut field1 = [9][9][
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

	fieldPrint(&field1)
	solveSudoku(&field1)
	fieldPrint(&field1)

}