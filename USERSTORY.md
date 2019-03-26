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

User story: As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts.
Example: The application includes models designed specifically for tablets.

User story: As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font that standard mode.
Example: The application includes models for both landscape and portrait viewing.

User story: As a teacher, I want to specify a list of word pairs for my students to practice this week.
Example: Teachers can send out a CSV file with word pairs and timestamps to students which can be imported into the application. When the students start a new game, the application would select word pairs based on the timestamps and today's date.

User story: As a student working with a textbook, I want to load pairs of words to practice from each chapter of the book.
Example: Users can "bulk import" word pairs and the system automatically groups the word pairs by chapter.

User story: As a student, I want the Sudoku app to keep track of the vocabulary words that I am having difficulty recognizing so that they will be used more often in my practice puzzles.
Example: Maintain a list of words that the user is learning and the count number of times the user uses that word correctly and incorrectly.

User story: As a student who wants to practice my understanding of spoken words in the language that I am learning, I want a listening comprehension mode. In this mode, numbers will appear in the pre-filled cells.
Example: When a user presses the number, the corresponding word in the language that he/she is learning will be read out. The user can then test his/her listening comprehension by entering the English translation of the word into an appropriate puzzle cell.

User story: As a language learner, I want to be able to peek at the correct translation of a word, so that I can try to remember it in filling out the puzzle.
Example: When a user selects a Sudoku cell that is part of the pre-filled configuration of a puzzle, the translation of that word is momentarily displayed.

User story: As a teacher, I want to specify a list of word pairs for my students to practice this week.
Example: When a teacher wishes to provide a word list for practice with the Sudoku app, the teacher may send the pairs of words to practice as a CSV file. When a student selects import word list, the system will parse and store the given word list and update the word list submenu with the name of the word list. When a student selects a word list from the word list submenu, puzzles will be generated using word pairs from that list.

User story: As a Sudoku player, I want to play the game in night mode.
Example: When a user starts the application, every layout the user sees is in the night mode color scheme.

User story: As a travel enthusiast, I want to learn new languages.
Example: Before the users start a new game, they can select a language that they want to learn.
