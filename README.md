Task1
Please note: 
- Compile and run the script in your chosen IDE after setting up all dependencies.
-All the required dependencies can be found in Pom.xml 
- Ensure that your WebDriver matches the browser version you are using.
-  New login credential to Amazon.eg has been created for this task (already included in the code)
-  Step number “ 8. add address and choose cash as a payment method” is not applicable for this testing scenario as Amazon would disable the Cash option if the  total for the order value exceeds 25000 EGP.
- Also for the address information, there was an extra step added to modify the country code  to Saudi Arabia as it should match the primary phone in the account which was verified by using OTP. 
- The test may fail if the website request the OPT again ( Not sure how to handle this exception) 
