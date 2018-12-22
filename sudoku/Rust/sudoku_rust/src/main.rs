// Sudoku Rust

fn main() {
	let mut field: [[u8; 9];9];
	field = [
		[0, 1, 0, 5, 0, 0, 0, 0, 0],
		[9, 0, 4, 0, 0, 0, 0, 0, 8],
		[0, 0, 0, 4, 0, 0, 6, 7, 0],
		[0, 0, 0, 6, 9, 0, 0, 0, 0],
		[5, 8, 0, 0, 0, 0, 0, 9, 7],
		[0, 0, 0, 0, 4, 7, 0, 0, 0],
		[0, 5, 3, 0, 0, 4, 0, 0, 0],
		[2, 0, 0, 0, 0, 0, 3, 0, 6],
		[0, 0, 0, 0, 0, 1, 0, 8, 0],
	];

	print_sudoku(&mut field);
	solve_sudoku(&mut field);
	print_sudoku(&mut field);
}

// Console Output Helper
fn print_sudoku(field: &mut [[u8; 9];9]){
	for row in 0..9{
		for col in 0..9{
			print!("{} ", field[row][col])
		}
		println!("");
	}
	println!("==================");
}

// Recursive brute force
fn solve_sudoku(field: &mut [[u8; 9];9]) -> bool {
	for row in 0..9{
		for col in 0..9{
			//Unsolved == 0
			if field[row][col] == 0{
				//Try numbers 1-9
				for num in 1..10{
					field[row][col] = num;

					//Check parially-solved-field and create new solution field, if not fully solved
					if check_number_validity(field, row, col) && solve_sudoku(field) {
						return true;
					}

					// no number possible
					field[row][col] = 0
				}
				return false;
			}
		}	
	}
	return true
}

// check partiall solution attempt
fn check_number_validity(field: &mut [[u8; 9];9], row: usize, collumn: usize) -> bool {
	//print_sudoku(field);
	return check_row(field, row, collumn) && check_col(field, row, collumn) && check_box(field, row, collumn)
}

fn check_row(field: &mut [[u8; 9];9], row: usize, collumn: usize) -> bool {
	//extract row numbers
	let mut row_numbers = [0; 9];
	for num in 0..9 {
		row_numbers[num] = field[row][num];
	}

	return check_duplicates(&mut row_numbers, field[row][collumn])
}

fn check_col(field: &mut [[u8; 9];9], row: usize, collumn: usize) -> bool {
	//extract col numbers check_duplicates(&mut box_numbers, field[row][collumn])
	let mut col_numbers = [0; 9];
	for num in 0..9 {
		col_numbers[num] = field[num][collumn];
	}

	return check_duplicates(&mut col_numbers, field[row][collumn])
}

fn check_box(field: &mut [[u8; 9];9], row: usize, collumn: usize) -> bool {
	let mut box_numbers = [0; 9];

	//identify box in field
	let row_start = row / 3 * 3;
	let row_end = row_start + 3;
	let col_start = collumn / 3 * 3;
	let col_end = col_start + 3;

	//Copy box numbers
	let mut num = 0;
	for r in row_start..row_end {
		for c in col_start..col_end {
			box_numbers[num] = field[r][c];
			num+=1;
		}
	}

	return check_duplicates(&mut box_numbers, field[row][collumn])
}

fn check_duplicates(numbers: &mut [u8; 9], number: u8) -> bool {
	let mut duplicate_counter = 0;

	//iterate through numbers
	for num in 0..numbers.len() {
		if numbers[num] == number {
			duplicate_counter+=1;
		}
		if duplicate_counter == 2 {
			return false;
		}
	}

	return true;
}