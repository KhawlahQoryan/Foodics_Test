

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

public class FoodicsTestingTask {
    public static void main(String[] args) {
        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Step 1 - Open Amazon website and login
            driver.get("https://www.amazon.eg/");
            driver.findElement(By.id("nav-link-accountList")).click();
            driver.findElement(By.id("ap_email")).sendKeys("kholah.foodics.test@gmail.com");
            driver.findElement(By.id("continue")).click();
            driver.findElement(By.id("ap_password")).sendKeys("Foodics1100");
            driver.findElement(By.id("signInSubmit")).click();

            // Step 2 - Open "All" menu
            driver.findElement(By.id("nav-hamburger-menu")).click();
            // Step 2.1 - Expand the list
            driver.findElement(By.id("See all")).click();

            // Step 3 - Click on “video games” and then “all video games”
            driver.findElement(By.linkText("Video Games")).click();
            driver.findElement(By.linkText("All Video Games")).click();

            // Step 4 - Add filters
            driver.findElement(By.xpath("//span[text()='Free Shipping']")).click();
            driver.findElement(By.xpath("//span[text()='New']")).click();

            // Step 5 - Sort by price: high to low
            driver.findElement(By.id("s-result-sort-select")).click();
            driver.findElement(By.xpath("//a[contains(text(), 'Price: High to Low')]")).click();

            // Step 6 - Add products below 15k EGP to cart
            addProductsBelow15kToCart(driver);

            // Step 7 - Verify all products are in the cart
            verifyProductsInCart(driver);

            // Step 8 - Add address and choose cash payment method
            addAddressAndChoosePayment(driver);

            // Step 9 - Verify total amount
            verifyTotalAmount(driver);

        } finally {
            // Close the browser
            driver.quit();
        }
    }

    // Additional helper methods for steps f.6 to i.9 should be implemented here

    private static void addProductsBelow15kToCart(WebDriver driver) {
        // Logic to add products to cart
    	
    	 boolean productAdded = false;
         while (!productAdded) {
             List<WebElement> products = driver.findElements(By.cssSelector(".s-main-slot .s-result-item"));

             for (WebElement product : products) {
                 WebElement priceElement = product.findElement(By.cssSelector(".a-price-whole"));
                 String priceText = priceElement.getText().replace(",", "");
                 int price = Integer.parseInt(priceText);

                 if (price < 15000) {
                     WebElement addToCartButton = product.findElement(By.cssSelector(".a-button-inner > .a-button-text"));
                     addToCartButton.click();
                     productAdded = true;
                 }
             }

             if (!productAdded) {
                 WebElement nextButton = driver.findElement(By.cssSelector(".s-pagination-next"));
                 nextButton.click();
             }
         }
    }

    private static void verifyProductsInCart(WebDriver driver) {
        // Logic to verify products in the cart
    	
    	 // Step g: Verify all products are added to the cart
        driver.findElement(By.id("nav-cart")).click();
    }

    private static void addAddressAndChoosePayment(WebDriver driver) {
        // Click on Checkout to proceed with the address info
    	WebElement proceedToCheckoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.name("proceedToRetailCheckout")
                    )
                );
        
        // Wait for the address page to load 
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("address-ui-widgets-enterAddressFullName")));

        // Fill in address details
        WebElement fullNameField = driver.findElement(By.id("address-ui-widgets-enterAddressFullName"));
        fullNameField.clear();
        fullNameField.sendKeys("Foodics");
        
        // A valid phone number which is associated with account needs to be added 
    
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("address-ui-widgets-enterAddressISDPhoneNumber")));

        dropdown.click();

        WebElement saudiArabiaOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '+966')]")));
        saudiArabiaOption.click();
        
        //Enter the phone number
        
        WebElement phoneNumberField = driver.findElement(By.id("address-ui-widgets-enterAddressPhoneNumber"));
        phoneNumberField.clear();
        phoneNumberField.sendKeys("536873021");
        
        //Enter Street name

        WebElement addressField = driver.findElement(By.id("address-ui-widgets-enterAddressLine1"));
        addressField.clear();
        addressField.sendKeys("1234 Test");
        
        //Enter building number
        
        WebElement cityField = driver.findElement(By.id("address-ui-widgets-enter-building-name-or-number"));
        cityField.clear();
        cityField.sendKeys("11");
        
        // Enter City name
        
        WebElement cityField = driver.findElement(By.id("address-ui-widgets-enterAddressCity"));
        cityField.clear();
        cityField.sendKeys("Cairo");
        
        //Enter district name
        
        WebElement stateField = driver.findElement(By.id("address-ui-widgets-enterAddressDistrictOrCounty"));
        stateField.clear();
        stateField.sendKeys("New Cairo City-Al Amal");
        
        // Enter Governorate name

        WebElement stateField = driver.findElement(By.id("address-ui-widgets-enterAddressStateOrRegion"));
        stateField.clear();
        stateField.sendKeys("Cairo Governorate");


        // Click the "Add Address" button
        WebElement addAddressButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input.a-button-input[aria-labelledby='address-ui-widgets-form-submit-button-announce']")
            ));
          
            addAddressButton.click();
        
        //Proceed with the payment by clicking on " Proceed to Buy button"
            driver.findElement(By.id("sc-buy-box-ptc-button")).click();
    
       
    }

    private static void verifyTotalAmount(WebDriver driver) {
  
    	
        // Verify total amount with shipping fees
        WebElement totalElement = driver.findElement(By.id("sc-subtotal-amount-activecart"));
        String totalText = totalElement.getText().replace("EGP", "").replace(",", "").trim();
        double totalAmount = Double.parseDouble(totalText);
        
        // Locate the shipping fee element
        WebElement shippingElement = driver.findElement(By.id("sc-shipping-fee-amount"));
        String shippingText = shippingElement.getText().replace("EGP", "").replace(",", "").trim();
        double shippingFee = Double.parseDouble(shippingText);
        
        // Locate the total with shipping amount element
        WebElement totalWithShippingElement = driver.findElement(By.id("sc-total-amount"));
        String totalWithShippingText = totalWithShippingElement.getText().replace("EGP", "").replace(",", "").trim();
        double totalWithShipping = Double.parseDouble(totalWithShippingText);

        // Calculate the expected total amount
        double expectedTotal = totalAmount + shippingFee;
        
        //  Verify if the total amount matches the calculated total
        if (Math.abs(totalWithShipping - expectedTotal) < 0.01) {  // Allowing minor differences due to rounding
            System.out.println("Total amount verification passed: " + totalWithShipping);
        } else {
            System.out.println("Total amount verification failed. Expected: " + expectedTotal + ", but found: " + totalWithShipping);
        }
    }
    
}
