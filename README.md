# CIT300 Data Structure and Graph Analyzer

A Java-based application developed for the CIT300 Data Structures and Algorithms module. The project demonstrates fundamental data structures, graph traversal algorithms, searching techniques, and basic performance analysis through an interactive command-line interface.

## Group Information

**Group Number:** 22  
**Module:** CIT300 — Data Structures and Algorithms  
**Project Title:** Data Structure and Graph Analyzer

### Group Members and Contributions

| No. | Student Name | Student ID | Contribution |

| 1 | M.M.M. Ayyash | 23DA2-1033 | Array implementation, searching algorithms, main application integration, performance comparison, and GitHub repository management |
| 2 | M.A.J. Hafsa | 23DA2-1058 | Stack and Queue implementations |
| 3 | M.T. Aathifa | 23DA2-0916 | Custom Linked List implementation |
| 4 | M.F.F. Aaysha | 23DA2-0834 | Graph implementation, vertex and edge management, graph display, Breadth-First Search (BFS), and Depth-First Search (DFS) |

### Collaborative Integration

The individual modules were integrated into a single Java application through the main menu. The integrated project was compiled and tested to verify the functionality of the data structures, searching algorithms, graph operations, performance analysis, and command-line interface.


## Features

- **Array:** Dynamic integer array with insertion, deletion, searching, and resizing.
- **Linked List:** Linked-list operations for managing integer elements.
- **Stack:** Last-In, First-Out (LIFO) operations.
- **Queue:** First-In, First-Out (FIFO) operations.
- **Graph:** Undirected graph representation with vertex and edge management.
- **Graph Traversal:** Breadth-First Search (BFS) and Depth-First Search (DFS).
- **Searching:** Linear search and binary search, including comparison counts.
- **Performance Analysis:** Measures search execution time and compares searching algorithms across different dataset sizes.
- **Interactive Menu:** Provides access to the implemented modules through a command-line interface.
- **Comprehensive QA Suite:** Includes automated checks for the data structures, graph, searching, performance analysis, and main application integration.

## Technologies

- **Language:** Java
- **Tested Java version:** 23.0.2
- **Build tool:** Java compiler (`javac`)
- **Interface:** Command-line interface
- **Development environment:** Windows PowerShell

## Project Structure

```text
CIT-Data-Structure-Graph-Analyzer/
├── src/
│   ├── Main.java
│   ├── data/
│   │   ├── ArrayManager.java
│   │   ├── LinkedList.java
│   │   ├── Stack.java
│   │   └── Queue.java
│   ├── graph/
│   │   └── Graph.java
│   ├── searching/
│   │   ├── SearchAlgorithms.java
│   │   └── SearchResult.java
│   └── performance/
│       ├── PerformanceAnalyzer.java
│       └── PerformanceResult.java
├── ComprehensiveQaSuite.java
├── README.md
└── .gitignore
```

The `out/` directory is generated during compilation and contains compiled Java class files.

## Requirements

Install a Java Development Kit (JDK). This project has been compiled and tested using Java 23.0.2.

Verify your Java installation:

```powershell
java -version
javac -version
```

## Compilation

Open PowerShell in the project root directory.

Create the output directory if necessary:

```powershell
New-Item -ItemType Directory -Force out
```

Compile the application and comprehensive QA suite:

```powershell
$sources = Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName }
javac -Xlint:all -d out $sources .\ComprehensiveQaSuite.java
```

The `-Xlint:all` option enables compiler warnings to help identify potential issues.

## Running the Application

After successful compilation, run:

```powershell
java -cp out Main
```

Follow the interactive menu to select a data structure, perform searching operations, explore graph traversal, or run performance analysis. Use the displayed exit option to close the application.

## Running the Comprehensive QA Suite

Compile the application and test suite first, as described above. Then run:

```powershell
java -cp out ComprehensiveQaSuite
```

The suite reports its test results in the terminal. A successful run should report zero failed tests.

## Algorithms and Concepts

### Data Structures
- Dynamic arrays
- Linked lists
- Stacks and queues

### Graph Algorithms
- Breadth-First Search (BFS)
- Depth-First Search (DFS)

### Searching Algorithms
- Linear search
- Binary search, which requires the input array to be sorted

### Performance Analysis
The application measures searching performance across multiple dataset sizes. Execution-time results are intended for educational comparison and may vary with the machine, JVM, and runtime conditions.

## Project Purpose

This project demonstrates the implementation and integration of core data structures and algorithms in Java, with an emphasis on interactive use, algorithmic behavior, and introductory performance analysis.

---

**Module:** CIT300 — Data Structures and Algorithms  
**Project:** Data Structure and Graph Analyzer
