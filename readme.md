# Password Generator

This project is a simple desktop application to generate passwords based on user-provided words and optional numbers.  
It uses Java Swing for the GUI and can be packaged into an executable file (e.g., using Launch4j on Windows).

## Features

1. **Word-Based Password Generation:**  
   The user inputs one or more words, which form the basis of the generated passwords.

2. **Optional Numbers:**  
   Users can provide numbers (comma-separated) that will be inserted randomly among the converted words.

3. **Password Length Selection:**  
   The user can specify the desired length of the passwords. If the generated string is shorter, it will be padded with special characters; if longer, it will be truncated.

4. **Character Transformations:**  
   Certain letters are replaced with similar-looking numbers (e.g., 'a' -> '4', 'e' -> '3', 'i' -> '1', 'o' -> '0', 's' -> '5', 't' -> '7'). Letters not in the substitution map are still included but may be randomly capitalized or lowercased, maintaining the overall "word-like" feel.

5. **Multiple Passwords:**  
   The application generates three sample passwords at once, each slightly different.

6. **Copy to Clipboard:**  
   Each generated password is displayed with a "Copy" button for easy transfer to the clipboard.

## How to Build and Run

1. **Prerequisites:**
    - Java JDK (e.g., Java 8 or higher)
    - A Java IDE (IntelliJ IDEA, Eclipse, or NetBeans) or command line tools
    - (Optional) Launch4j to package into an .exe for Windows

2. **Compiling and Running (Command Line):**
   ```bash
   javac src/main/java/org/password/passwordcreator/*.java
   java -cp src/main/java org.password.passwordcreator.PasswordGenerator
    ```
    Adjust the classpath as necessary depending on your project structure.
    
    If using Maven, you can run:
    
    ```bash
    mvn clean compile exec:java -Dexec.mainClass="org.password.passwordcreator.PasswordGenerator"
    ```

3. **Creating an Executable (Windows):**

   1. Build a fat JAR with:
       ```bash
       mvn clean package
       ```
      This should produce a .jar file in the target directory.
   
   2.  Use Launch4j to wrap this ``.jar`` into an ``.exe``:
        * Install and open Launch4j.
        * Set the input JAR to your generated ``.jar`` file.
        * Configure output to ``PasswordGenerator.exe``.
        * (Optionally) bundle a JRE if you want the ``.exe`` to run without a pre-installed Java runtime.


## Project Structure
  * ``PasswordGenerator.java:`` The main GUI class.
  * ``PasswordUtil.java:`` Utility class for transformations and password generation logic.
  * ``pom.xml:`` Maven configuration file.

## License
This code is open-source and can be distributed under the MIT License.