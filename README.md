**#Selenium Automation Framework with LambdaTest Integration**
**Overview**
This automation framework is designed to enable the testing of web applications using Selenium WebDriver, integrated with LambdaTest's cloud-based platform for cross-browser testing. It utilizes TestNG for test management and Extent Reports for detailed test reporting, including screenshots for each test step.

**Features:**
Cross-browser and cross-platform testing on the cloud (LambdaTest).
Screenshot attachment for individual steps in the report.
Integration with Extent Reports for test result visualization.
TestNG framework support for running tests in parallel.
WebDriver factory for dynamic browser instantiation.
LambdaTest integration for remote testing.

**Prerequisites:**
Java Development Kit (JDK): Ensure you have JDK installed. You can download it from the Oracle website.
Eclipse IDE/IntelliJ IDEA: Use either IDE to run the Java code.
Maven/Gradle: For dependency management 

**Framework Dependencies :**

Key Dependencies:
Selenium:
selenium-java: Provides the main Selenium bindings for Java.
selenium-remote-driver: Allows interaction with remote web browsers, essential for integrating with LambdaTest.

TestNG:
Used for test management, enabling parallel execution, and better handling of test flows.

ExtentReports:
For generating detailed HTML reports with stepwise logging, screenshots, and status updates.

Logging:
log4j-api and log4j-core: Provides logging capabilities for debugging and monitoring test runs.

RestAssured:
rest-assured and json-schema-validator: Enables API testing and schema validation of JSON responses.

Google Gson:
For parsing and serializing JSON data (optional but helpful for API testing).

WebDriverManager:
Automatically handles the setup of WebDriver binaries (e.g., ChromeDriver, GeckoDriver).

Apache POI:
For reading and writing Excel (XLSX) files, useful for data-driven testing.

JSON Validation:
jsonassert: Validates the structure and values of JSON responses in tests.

Data Generation:
javafaker: Automatically generates fake data (e.g., names, addresses) for testing purposes.

OAuth:
scribejava-apis: For handling OAuth authentication in API requests.
