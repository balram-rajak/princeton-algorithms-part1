# Princeton — Algorithms, Part I (Coursera)

Coursework, notes, and implementations while following **Algorithms, Part I** by Robert Sedgewick and Kevin Wayne (Princeton University) on Coursera.

- Course: https://www.coursera.org/learn/algorithms-part1
- Booksite (Algorithms, 4th ed.): https://algs4.cs.princeton.edu/home/
- Code + libraries: https://algs4.cs.princeton.edu/code/
- Lectures index: https://algs4.cs.princeton.edu/lectures/

## What’s in this repo

- Module-wise folder structure matching the Coursera course modules.
- Java (primary), plus optional helpers for quickly compiling/running small C++/Python/JS snippets.
- A lightweight local runner ([lib/TaskRunner.cpp](lib/TaskRunner.cpp)) used by a VS Code task.

## Repository structure (high level)

```text
princeton-algorithms-part1/
  Module 01 - Course Introduction/
  Module 02 - Union-Find/
  Module 03 - Analysis of Algorithms/
  Module 04 - Stacks and Queues/
  Module 05 - Elementary Sorts/
  Module 06 - Mergesort/
  Module 07 - Quicksort/
  Module 08 - Priority Queues/
  Module 09 - Elementary Symbol Tables/
  Module 10 - Balanced Search Trees/
  Module 11 - Geometric Applications of BSTs/
  Module 12 - Hash Tables/
  Module 13 - Symbol Table Applications/
  lib/
    TaskRunner.cpp
    Main_Boilerplate/
    algs4.jar
  .gitignore
  inputf.in
  outputf.in
```

## Prerequisites

- **Java**: JDK 8+ (recommended: 17+)
- Optional (if you use the TaskRunner for other languages):
  - C++ compiler (MinGW g++ on Windows)
  - Python 3
  - Node.js

If you’re using the Princeton `algs4.jar`, it’s included in [lib/algs4.jar](lib/algs4.jar).

## Clone

```bash
git clone https://github.com/balram-rajak/princeton-algorithms-part1.git
cd princeton-algorithms-part1
```

## Run (Java)

### Option A — Plain `javac` / `java`

Compile a Java file:

```bash
javac -cp "lib/algs4.jar;." "Module 02 - Union-Find/Percolation.java"
```

Run it:

```bash
java -cp "lib/algs4.jar;." Percolation
```

Notes:
- Some assignments require the Princeton libraries and specific class names/signatures.
- If your class is in a folder and not in a package, make sure you run it from the repo root and that the class is on the classpath.

### Option B — VS Code TaskRunner (quick run)

This repo includes a helper runner used by a VS Code task (see `.vscode/tasks.json` if present in your setup). The runner compiles/runs the currently opened file and uses `inputf.in` → `outputf.in` for stdin/stdout redirection.

Typical workflow:
1. Put input in `inputf.in`
2. Run the default build task in VS Code
3. Read output from `outputf.in`

## Notes / best practices

- Each module folder contains a short `README.md` with the topic focus.
- Keep solutions modular and small; prefer clear APIs and unit-testable helpers.
- Academic integrity: use this repo for learning; avoid submitting copied solutions to graded assessments.

## License

This repository is for personal learning and reference. Princeton/Coursera course content and the `algs4` library are owned by their respective authors.
