import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.time.Duration;
import java.util.Random;

public class SeleniumTest {
    private static final String CALCULATOR_URL = "file:///C:/Users/reddy/Desktop/SeleniumTesting/src/main/resources/Calculator.html";
    private static final String RESULT_XLSX_PATH = "test_results.xlsx";

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(CALCULATOR_URL);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test Results");
        createHeaderRow(sheet);

        try {
            for (int i = 0; i < 1000; i++) {
                String[] testCase = generateRandomTestCase();
                String op1 = testCase[0];
                String op2 = testCase[1];
                String operator = testCase[2];
                String expectedOutput = testCase[3];

                try {
                    performCalculation(wait, driver, op1, operator, op2);
                    WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("screen")));
                    String actualOutput = result.getAttribute("value");
                    resetCalculator(wait, driver);  // Reset the calculator after each test case
                    documentResult(sheet, i, op1, op2, operator, expectedOutput, actualOutput);
                } catch (Exception e) {
                    System.out.println("Error during testing for: " + String.join(",", testCase));
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(driver, workbook);
        }
    }

    private static void performCalculation(WebDriverWait wait, WebDriver driver, String op1, String operator, String op2) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn" + op1))).click();
        WebElement operatorButton = getOperatorButton(operator, wait);
        operatorButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn" + op2))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@value='=']"))).click();
    }

    private static void resetCalculator(WebDriverWait wait, WebDriver driver) {
        WebElement acButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@value='all-clear']")));
        acButton.click();  // Clicks the AC button to clear the calculator
    }

    private static void documentResult(Sheet sheet, int rowIndex, String op1, String op2, String operator, String expected, String actual) {
        Row row = sheet.createRow(rowIndex + 1);
        fillRow(row, op1, op2, operator, expected, actual);
    }

    private static void closeResources(WebDriver driver, Workbook workbook) {
        try {
            driver.quit();
            FileOutputStream outputStream = new FileOutputStream(RESULT_XLSX_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Operand1");
        headerRow.createCell(1).setCellValue("Operand2");
        headerRow.createCell(2).setCellValue("Operator");
        headerRow.createCell(3).setCellValue("Expected Output");
        headerRow.createCell(4).setCellValue("Actual Output");
    }

    private static void fillRow(Row row, String op1, String op2, String operator, String expected, String actual) {
        row.createCell(0).setCellValue(op1);
        row.createCell(1).setCellValue(op2);
        row.createCell(2).setCellValue(operator);
        row.createCell(3).setCellValue(expected);
        row.createCell(4).setCellValue(actual);
    }

    private static WebElement getOperatorButton(String operator, WebDriverWait wait) {
        return switch (operator) {
            case "+" -> wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@value='+']"))); // User-defined XPath
            case "-" -> wait.until(ExpectedConditions.elementToBeClickable(By.id("sub")));
            case "*" -> wait.until(ExpectedConditions.elementToBeClickable(By.id("mul")));
            case "/" -> wait.until(ExpectedConditions.elementToBeClickable(By.id("div")));
            default -> throw new IllegalStateException("Unexpected operator: " + operator);
        };
    }

    private static String[] generateRandomTestCase() {
        Random random = new Random();
        String op1 = String.valueOf(random.nextInt(10));  // Random operand from 0 to 9
        String op2 = String.valueOf(random.nextInt(10));  // Random operand from 0 to 9
        String[] operators = {"+", "-", "*", "/"};
        String operator = operators[random.nextInt(operators.length)];
        double result = calculate(Double.parseDouble(op1), Double.parseDouble(op2), operator);
        String expectedOutput = String.format("%.2f", result);  // Format to two decimal places
        return new String[]{op1, op2, operator, expectedOutput};
    }

    private static double calculate(double op1, double op2, String operator) {
        return switch (operator) {
            case "+" -> op1 + op2;
            case "-" -> op1 - op2;
            case "*" -> op1 * op2;
            case "/" -> op2 != 0 ? op1 / op2 : Double.POSITIVE_INFINITY; // Handle division by zero gracefully
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
