## Selenium Testing for Calculator Application
This project uses Selenium WebDriver to automate testing of a web-based calculator. It includes a Maven setup to handle dependencies and ensure a smooth operation for generating and verifying a series of arithmetic operations.

# Prerequisites
Java JDK 20
Maven 3.6 or higher
Google Chrome Browser
ChromeDriver compatible with the installed version of Google Chrome
Project Structure
src/main/resources/Calculator.html - HTML file for the Calculator.
src/test/java/SeleniumTest.java - Main test script using Selenium.
test_results.xlsx - Excel file generated with test results.
pom.xml - Maven configuration file.
Setup Instructions
Clone the Repository

Obtain the project files by cloning this repository or downloading the files directly.
Install ChromeDriver

Download ChromeDriver from ChromeDriver - WebDriver for Chrome.
Ensure it is placed in a directory included in your system's PATH, or modify the test script to point directly to the executable.
Configure Maven

Ensure that Maven is installed on your system. Run ```mvn -v ``` in your terminal or command prompt to check if Maven is installed and which version.
Dependencies are defined in pom.xml and should be automatically handled by Maven.
Running the Tests

Navigate to the project directory in your terminal or command prompt.
Execute the tests using Maven with the command:
bash
```
mvn clean test
```
Alternatively, you can run the test directly from an IDE like IntelliJ IDEA or Eclipse, but ensure the dependencies are properly synced.
Dependencies
This project relies on several key dependencies:

Selenium WebDriver for browser automation.
Apache POI for reading from and writing to Excel files, which is used for documenting the test results.
Maven Dependencies
Here's a snippet from pom.xml showing the Selenium and Apache POI dependencies:
```
xml

<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.20.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.2</version>
    </dependency>
</dependencies>

```
Troubleshooting
Driver Issues: If the Selenium WebDriver fails to initiate, ensure that the ChromeDriver version matches the version of Google Chrome installed on your system.
Dependency Errors: If Maven fails to download dependencies, check your internet connection and ensure that Maven is configured to access the central repository.
Contributing
Contributions to this project are welcome. Please ensure to maintain the existing coding style, update tests as appropriate, and make a pull request detailing your changes.
