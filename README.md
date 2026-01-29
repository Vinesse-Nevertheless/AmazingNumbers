# Amazing Numbers (Java)

A high-performance command-line tool for analyzing the mathematical properties of natural numbers. This project was recognized as a **"Good Example"** by the JetBrains Academy/Hyperskill community for its clean architecture and effective use of Design Patterns.

## 🚀 Key Features
* **Multi-Property Analysis:** Evaluates numbers for traits like Palindromic, Gapful, Spy, Square, Sunny, Jumping, and Happy/Sad.
* **Complex Search Engine:** Supports filtering ranges with multiple inclusionary and exclusionary (`-`) properties.
* **Mutual Exclusion Logic:** A robust validation engine that detects logically impossible requests (e.g., `EVEN -EVEN` or `ODD EVEN`) before execution.
* **Memory-Efficient Streams:** Utilizes `LongStream` with lazy evaluation to maintain a near-constant memory footprint during large-scale searches.

## 🏛️ Architecture & Design Patterns

### 1. Polymorphic Enum Logic
Instead of a monolithic `switch` or `if-else` chain, I implemented a **Polymorphic Enum**. Each property is a constant that encapsulates its own mathematical test. This makes the system "Open-Closed"—new mathematical properties can be added without modifying the core execution engine.



### 2. Decoupled Validation
I separated the **Property Testing** (the math) from the **Business Rules** (the input validation). By using a dedicated validation layer with a "Conflict Map" strategy, the program identifies contradictory user inputs early, preventing unnecessary computation.



### 3. Declarative Stream Pipeline
The project utilizes a functional approach to data processing, ensuring that the application remains responsive even when processing high-volume requests:

```java
LongStream.iterate(start, n -> 1 + n)
    .filter(x -> excludes(x, exclusions))
    .filter(x -> matches(x, searchTerms))
    .limit(limit)
    .forEach(this::rangePrint);
```

## 💻 How to Run

### Prerequisites
* **JDK 17** or higher.
* A terminal or IDE (IntelliJ IDEA recommended).

### Commands
1. **Compile:**
   ```bash
   javac src/numbers/*.java
   ```
3. **Run:**
   ```bash
   java -cp src numbers.Main  
   ```
### Example Usage
* `123` — Analyze a single number.
* `1 10` — Properties for 10 consecutive numbers.
* `1 5 JUMPING` — Find the first 5 Jumping numbers starting from 1.
* `1 10 EVEN -DUCK` — Find 10 numbers that are Even but NOT "Duck" numbers.

## 📈 Lessons Learned

* **Refactoring for Scalability:** Transitioned from a procedural Stage 3 design to a decoupled Stage 7 architecture to handle complex input logic.
* **Separation of Concerns:** Learned that keeping validation rules separate from data-testing logic leads to much cleaner, more maintainable code.
   
## 🛠️ Project Evolution

This project was developed through 8 distinct stages of increasing complexity. 

For a detailed breakdown of the architectural shifts—including the transition from procedural logic to a polymorphic engine—please 
see the [project-stages.yaml](./project-stages.yaml) file.

### Evolution Summary:
* **Stages 1-4:** Focus on mathematical logic, input validation, and basic UI.
* **Stage 5:** Significant architectural refactor to a **Polymorphic Enum** pattern.
* **Stages 6-8:** Expansion of the search engine to include complex algorithms (Happy/Jumping numbers) and advanced logical filtering.
