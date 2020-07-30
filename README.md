# weatherforecast-test

a) import project into eclipse as General -> Existing Project Into Workspace.

b) Setup the build path of the project by adding the jar in lib folder.

c) Download the chrome driver from https://chromedriver.chromium.org/downloads based on your chrome version.

d) Copy the driver in your file system(wherever you prefer).

e) Copy the chromedriver path and set in test.properties

d) Test NG frame work is used. We can directly run the test suite and veriy the reports in test output folder after refreshing the project.

Points to be noted:

1) In application, there are 6 cities provided to verify the data. There are no validations on city field. But as a negative test case,
 have considered non alphabetical characters. When non alphabetical characters (like numbers/special characters are given test case fails.Included in TC1 )
 
2) TC1 is just entering different cities and verifying data is displayed or not.

3)Test case 2 is clicking on Summary data and verifying whether data is present or not.

4)In TC3, are clicking on the summary and chcking the detailed rows.  we are verifying the data whether it is 3 hourly.The data on the Summary row is as follows:
 for temparature max temp  is the max temp row data of the detailed row and min temparature will be lowest temparature of the detailed records.
Wind speed is the first row data of the detailed row and rainfall is the sum of all the detailed records, Pressure is first row data of the detailed row.

5)All the values displayed are rounded off values, else error will be displayed.

6)In city, there are no validations. If there is some check like blank, character length restriction/invalid data input leading to application error would lead to negative test case.

7)All the values are rounded down with math.floor function.covered in TC3.

8)Test suite is created to run all the 3 test cases and we can verify the results in test-output folder.
