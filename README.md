Tests run and report

### Base set up:
    Install Java set up in path in Environment variables;
    Download Maven set up in path in Environment variables;
	Download Allure set up in path in Environment variables;

	
### **Run test scenario via Maven command:**

*    To run tests via maven: 'mvn clean test' or 'mvn clean install'
*    To run tests via testNG: select single test -> right click and select 'Run methodName()'
*    To debug tests via testNG: select single test -> right click and select 'Debug methodName()'


# Generate and view report
* Test results are generated in ./target/allure-results of the current module
* Generate report via mvn command: allure generate target/allure-results and report is generated in ./target/allure-report
  Report is ./target/allure-report/index.html file.
* Command 'allure serve target/allure-results' generates report and opens it in Chrome browser in graphical representation