# weatherforecast-test
Pre-requisites:
1) Install Jdk8 on your machine.
2) Install Eclipse and configure eclipse to use the installed JDK8.
3) Install TestNg plugin in eclipse from eclipse market place
4) Download the Chrome driver from https://chromedriver.chromium.org/downloads based on your chrome version.
5) clone  weatherforecast application from git hub(https://github.com/buildit/acceptance-testing) and follow the steps in readme file and bring it up and running.

Build set up:

a) clone/Download weatherforecast test project from git hub https://github.com/swethananduri/weatherforecast-test.

b) Import project into eclipse as General -> Existing Project Into Workspace.

c) Setup the build path of the project by adding the jar in lib folder.

d) Copy the Downloaded chrome driver into your file system(wherever you prefer).

e) Copy the chromedriver path and set it in test.properties file in  com.weatherforecast.test.utils/test.properties

f) As we are using Test NG frame work, We can directly run the test suite and verify the reports in test output folder after refreshing the project.
testsite.xml -> Right Click -> Run As -> TestNG Suite.

Execution steps:

As the requirements are shared on Git hub, I have used Visual studio to download and copy the code.
After that I have ran the given commands (in read me given to me) and launched the application.

1) In application, there are 6 cities provided to verify the data. There are no validations on city field. If included, it could result in negative test cases.

2) Test Case 1 is just entering different cities and verifying data is displayed or not corresponding to the city.

3) Test Case 2 is clicking on Summary data and verifying whether data is present or not.

4) Assumptions: Validating as per the assumption:  

Test Case 3 is  clicking on the summary and checking the detailed rows.  We are verifying the data whether it is 3 hourly data or not.

The data on the Summary row is as follows:

 Max Temperature : Max of all maximum temperatures of the day in the detailed rows.
 Min Temperature : Min of all minimum temperatures of the day in the detailed rows.
 Wind Speed :      First row data of the detailed rows.
 Rain fall :       Rainfall is the sum of all the detailed records.
 Pressure  :     Pressure is first row data of the detailed row
 
 
5) All the values displayed are rounded down values.

6) For city, there are no validations, Hence I coudn't add negative test cases.
   If there are checks like blank, character length restriction/invalid data input then we could have negative test cases.

7) All the values are rounded down , covered in Test Case 3.

8) Test suite is created to run all the 3 test cases and we can verify the results in test-output folder.
   a) testsite.xml -> Right Click -> Run As -> TestNG Suite.
   b) After step a is completed, Refresh the project then go to test-output folder -> index.html Right click and open in browser to view the report.

Frame work additions:

Have not included the below due to time constraint:

1) Must have included POM which includes below:
 -where we can create test base,create config file, excel reader, page actions, data in the form of excel, reports, screenshots of test results.
 

