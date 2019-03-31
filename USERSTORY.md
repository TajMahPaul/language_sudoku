Given: that listening comprehension mode is enabled
When: the user initiates a new puzzle
Then: the user sees a standard Sudoku grid with some pre-filled cells showing digits in the range 1..9 and all other cells empty

Given: that the user is filling in the grid in listening comprehension mode,
and that the grid includes a cell with the pre-filled digit 4
and that word pair 4 is (green, vert)
When: the user presses the pre-filled cell having the digit 4
Then: the user hears the word "vert" read out and pronounced in French.

Given: that the user is filling in the grid in listening comprehension mode,
and that the grid includes a cell with the pre-filled digit 4
and that word pair 4 is (green, vert)
When: the user selects a non-pre-filled cell to enter the word "green"
Then: the word "green" appears in the list of words that may be selected, but not in the fourth position

Given: that the user is filling in the grid in listening comprehension mode,
When: the users presses a cell and hears the word "vert"
Then: the user does not see the word "vert" anywhere on the game grid.

Given: that the user uses a tablet for Sudoku vocabulary practice,
When: the user is practicing his vocabulary at home
Then: the words will be conveniently displayed in larger, easier to read fonts.

Given: that the user uses his phone in landscape mode for Sudoku vocabulary practice,
When: the user is practicing his vocabulary while taking the bus
Then: longer words are displayed in a larger font than standard mode.

Given: that the user is a student working with a textbook,
When: the user wants to practice from each chapter of the book
Then: the user can load pairs of words into the application to practice.

Given: that the user is using the Sudoku app,
When: the user is having difficulty recognizing the word "vocabulary"
Then: the word "vocabulary" will be used more often in the practice puzzles.

Given: that the user is practicing his understanding of spoken words in the language he is learning,
and listening comprehension mode is enabled
When: the user presses a number
Then: the corresponding word in the language that he is learning will be read out.

Given: that the user is filling in the grid in reading comprehension mode,
When: the user selects a Sudoku cell that is part of the pre-filled configuration of a puzzle
Then: the translation of that word is momentarily displayed.

Given: that the user specifies a list of word pairs for his students to practice this week,
When: a teacher wish to provide a word list for practice with the Sudoku app
Then: the teacher may send the pairs of words to practice as a CSV file.

Given: that the user specifies a list of word pairs for his students to practice this week,
When: a student selects import word list
Then: the system will parse and store the given word list and update the word list submenu with the name of the word list

Given: that the user specifies a list of word pairs for his students to practice this week,
When: a student selects a word list from the word list submenu
Then: puzzles will be generated using word pairs from that list

Given: that the user wants to select a different grid size for his Sudoku practice,
When: the user initiates the game
Then: the application displays a pop up which prompts the user to select a grid size.

Given: that the user wants to track his students' language learner progress,
When: the user clicks on the leaderboard button
Then: the application displays every student's statistics (XP, words learned, words that the student is having trouble with)

Given: that the user needs an encouragement to practice Sudoku vocabulary regularly,
When: the user has solved a Sudoku puzzle for 3 consecutive days
Then: the application rewards the user with a 3 day streak.

Given: that the user wants to set a daily goal of 50 XP per day for Sudoku vocabulary practice,
When: the user enters the settings menu
Then: the user can select (50 XP per day) to stay motivated while learning a language.

Given: that the user is having trouble memorizing some words in a foreign language,
When: the user enters the "Flash Cards" menu
Then: for each word pair in a word category, the word is displayed in the user' native language

Given: that the user is having trouble memorizing the word "abbigliamento"
and that word pair is (clothing, abbigliamento)
and that the user enters the "Flash Card" menu
When: the user clicks on the card containing the English word "clothing"
Then: the card is flipped and "abbigliamento" is displayed

Given: that the user is having trouble memorizing the word "abbigliamento"
and that word pair is (clothing, abbigliamento)
and that the card containing "clothing" is flipped
When: the user clicks on the voice button
Then: the user hears "abbigliamento" read out and pronounced in Italian.
