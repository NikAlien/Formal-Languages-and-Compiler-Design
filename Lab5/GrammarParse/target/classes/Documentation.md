The `ParserRecursiveDescendent` class is a recursive descent parser designed to process sequences of strings based on a provided grammar. This class implements a backtracking mechanism to ensure successful parsing of input sequences according to the grammar's production rules. Below is a detailed explanation of its components and methods.

## Class Overview
`ParserRecursiveDescendent` facilitates parsing by using:
- **Working Stack**: Tracks the parsing process with symbols and their production indices.
- **Input Stack**: Manages symbols to be matched against the sequence.
- **Sequence**: Holds the input string sequence to be parsed.

### Fields
- `Grammar grammar`: The grammar containing production rules.
- `String state`: Current state of the parser (`q`, `b`, `e`, or `f`).
- `int position`: Current position in the input sequence.
- `Stack<Pair<String, Integer>> workingStack`: Stack for tracking parsing progress.
- `Stack<String> inputStack`: Stack for managing unmatched input symbols.
- `List<String> sequence`: List of strings representing the input sequence to parse.

### Constructor
- **`ParserRecursiveDescendent(Grammar grammar)`**:
  Initializes the parser with the provided `Grammar`.

### Public Methods
#### Accessors
- **`Stack<Pair<String, Integer>> getWorkingStack()`**: Returns the current working stack.

#### Configuration
- **`void config(String filePath)`**:
    - Reads an input file line by line and populates the `sequence` list with tokens.
    - Initializes `state` to `"q"`, `position` to `0`, and pushes the initial grammar state onto the input stack.
    - Handles file-related exceptions.

#### Parsing
- **`void recursiveDescendentParsing(String filePath)`**:
    - Configures the parser using `config()`.
    - Implements the main parsing loop based on the parser's state (`q`, `b`, `e`, `f`).
    - Invokes helper methods for actions like expanding non-terminals, advancing in the input, handling errors, and backtracking.

### Helper Methods
- **`void Expand()`**:
    - Expands the top non-terminal in the input stack using its first production.
    - Updates the working stack and input stack accordingly.

- **`void Advance()`**:
    - Matches the top of the input stack with the current symbol in the sequence.
    - Advances the `position` pointer and updates the working stack.

- **`void MomentaryInsuccess()`**:
    - Transitions the parser to backtracking state (`b`).

- **`void Back()`**:
    - Performs backtracking by decrementing `position` and restoring symbols to the input stack.

- **`void AnotherTry()`**:
    - Attempts the next production for the current non-terminal in the working stack.
    - Handles transitions between states and ensures proper stack updates.
    - Throws an exception for unexpected conditions.

- **`void Success()`**:
    - Marks the parsing process as successful by transitioning to the `f` state.

### Error Handling
- If parsing fails due to unmatched symbols or production rules, the parser transitions to the error state (`e`) and outputs an error message.
