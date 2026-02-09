Tests run and allure report
# Base set up:
    Install Java and set up in path in Environment variables;
    Download Maven and set up in path in Environment variables;
	Download Allure and set up in path in Environment variables;

	
### **Run test scenario via Maven command:**

*    To run tests via maven: 'mvn clean test' or 'mvn clean install'
*    To run tests via testNG: select single test -> right click and select 'Run methodName()'
*    To debug tests via testNG: select single test -> right click and select 'Debug methodName()'


### **Generate and view report:**
* Test results are generated in ./target/allure-results of the current module
* Generate report via mvn command: 'allure generate --single-file target/allure-results --clean' and report is generated in ./target/allure-report.
  Report is ./target/allure-report/index.html file.
* Command 'allure serve target/allure-results' generates report and opens it in Chrome browser in graphical representation.


This test framework tests APIs that relates to Books management and Authors management. There are several tests that checks positive scenarios 
such as entities creation/update/delete and get information about those entities or entity by id as well as negative to see tests failures in Allure report.
Base url is https://fakerestapi.azurewebsites.net and its saved in env.properties file. Also there is an allure.properties file that can have allure properties.