# Currency Exchange and Discount Application

## Overview
This application calculates the net payable amount on a bill based on various discounts and converts calculated bill amount to desired currency.

### Deliverables
1. Spring Boot Application
2. Application UML Diagram ***(available at artifacts/currency-exchange-drawio.pdf)***
3. Source code static analysis report ***(available at artifacts/pmd-lint-analysis)***
4. Test coverage report ***(available at artifacts/jacoco-test-coverage)***
5. Sonarqube summary for code quality ***(available at artifacts/sonarqube-report)***
6. Sample cURL to run application ***(available at artifacts/sample-curl-request.txt file)***

### Code Analysis & Coverage
1. Junit is used for Application Unit testing
2. For Test Coverage **[Jacoco](https://www.jacoco.org/)** Plugin is used
3. For Static Code Analysis **[PMD](https://pmd.github.io/)** Plugin is used
4. For Code quality  **[Sonar Qube](https://www.sonarsource.com/)** is used

## How to Run

### Prerequisites
- Java 21
- Maven

### Steps
1. Clone the repository:
    ```bash
    git clone <repository_url>
    cd currency-exchange
    ```

2. Build the project:
    ```bash
    mvn clean install
    ```

3. Run the tests:
    ```bash
    mvn test
    ```

4. Generate code coverage report:
    ```bash
    mvn jacoco:report
    ```

5. Run static code analysis:
    ```bash
	mvn clean verify
    ```

6. To run the application, execute following command from root directory of application:
    ```bash
	java -jar .\target\currency-exchange-0.1.jar
    ```

7. API authentication
	```bash
	http basic authentication
	username: user1
	password: SecurePassword@1
	```

7. Sample cURL request
	```bash
	.\artifacts\sample-curl-request.txt
	```	
