import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class ListaPlusTest {

    private static AndroidDriver<AndroidElement> driver;

    public static void main(String[] args) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, "deviceName");
            caps.setCapability(MobileCapabilityType.APP, "caminho/para/aplicativo.apk");

            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);

            testLoginWithEmail();
            testMemorizePreviousLogin();
            testCreateAccountWithName();
            testForgotLogin();
            testNavigationBar();
            testCreateShoppingList();
            testEditShoppingList();
            testMenu();
            testSearch();
            testQuickSuggestions();
            testSelectProductAddedToCart();
            testOpenAndClosedShoppingLists();
            testListHistory();
            testDeleteShoppingList();

            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testLoginWithEmail() {
        MobileElement emailInput = driver.findElement(By.id("com.lista:id/email_input"));
        emailInput.sendKeys("test@example.com");

        MobileElement passwordInput = driver.findElement(By.id("com.lista:id/password_input"));
        passwordInput.sendKeys("password123");

        MobileElement loginButton = driver.findElement(By.id("com.lista:id/login_button"));
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/success_message")));

        MobileElement successMessage = driver.findElement(By.id("com.lista:id/success_message"));
        Assert.assertEquals("Login bem-sucedido!", successMessage.getText());
    }

    private static void testMemorizePreviousLogin() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/login_button")));

        MobileElement emailInput = driver.findElement(By.id("com.lista:id/email_input"));
        String emailText = emailInput.getText();
        Assert.assertEquals("", emailText);

        MobileElement passwordInput = driver.findElement(By.id("com.lista:id/password_input"));
        String passwordText = passwordInput.getText();
        Assert.assertEquals("", passwordText);

        MobileElement loginButton = driver.findElement(By.id("com.lista:id/login_button"));
        loginButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/success_message")));

        MobileElement successMessage = driver.findElement(By.id("com.lista:id/success_message"));
        Assert.assertEquals("Login bem-sucedido!", successMessage.getText());
    }

    private static void testCreateAccountWithName() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/create_account_button")));

        MobileElement nameInput = driver.findElement(By.id("com.lista:id/name_input"));
        nameInput.sendKeys("John Doe");

        MobileElement emailInput = driver.findElement(By.id("com.lista:id/email_input"));
        emailInput.sendKeys("john.doe@example.com");

        MobileElement passwordInput = driver.findElement(By.id("com.lista:id/password_input"));
        passwordInput.sendKeys("password123");

        MobileElement createAccountButton = driver.findElement(By.id("com.lista:id/create_account_button"));
        createAccountButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/success_message")));

        MobileElement successMessage = driver.findElement(By.id("com.lista:id/success_message"));
        Assert.assertEquals("Conta criada com sucesso!", successMessage.getText());
    }

    private static void testForgotLogin() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/forgot_login_button")));

        MobileElement forgotLoginButton = driver.findElement(By.id("com.lista:id/forgot_login_button"));
        forgotLoginButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/email_input")));

        MobileElement emailInput = driver.findElement(By.id("com.lista:id/email_input"));
        emailInput.sendKeys("john.doe@example.com");

        MobileElement recoverButton = driver.findElement(By.id("com.lista:id/recover_button"));
        recoverButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/reset_success_message")));

        MobileElement resetSuccessMessage = driver.findElement(By.id("com.lista:id/reset_success_message"));
        Assert.assertEquals("Um e-mail de recuperação de login foi enviado para o seu endereço de e-mail.", resetSuccessMessage.getText());
    }

    private static void testNavigationBar() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/navigation_bar")));

        MobileElement navigationBar = driver.findElement(By.id("com.lista:id/navigation_bar"));

        // Testar a presença dos itens da barra de navegação
        MobileElement homeButton = navigationBar.findElement(By.id("com.lista:id/home_button"));
        Assert.assertTrue(homeButton.isDisplayed());

        MobileElement profileButton = navigationBar.findElement(By.id("com.lista:id/profile_button"));
        Assert.assertTrue(profileButton.isDisplayed());

        MobileElement settingsButton = navigationBar.findElement(By.id("com.lista:id/settings_button"));
        Assert.assertTrue(settingsButton.isDisplayed());

        // Testar a funcionalidade de troca de tela ao clicar nos itens
        profileButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/profile_screen")));
        Assert.assertTrue(driver.findElement(By.id("com.lista:id/profile_screen")).isDisplayed());

        settingsButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/settings_screen")));
        Assert.assertTrue(driver.findElement(By.id("com.lista:id/settings_screen")).isDisplayed());

        homeButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/home_screen")));
        Assert.assertTrue(driver.findElement(By.id("com.lista:id/home_screen")).isDisplayed());
    }

    private static void testCreateShoppingList() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/create_shopping_list_button")));

        MobileElement createListButton = driver.findElement(By.id("com.lista:id/create_shopping_list_button"));
        createListButton.click();

        MobileElement listNameField = driver.findElement(By.id("com.lista:id/list_name_field"));
        listNameField.sendKeys("Lista de Compras 1");

        MobileElement item1Field = driver.findElement(By.id("com.lista:id/item1_field"));
        item1Field.sendKeys("Item 1");

        MobileElement item2Field = driver.findElement(By.id("com.lista:id/item2_field"));
        item2Field.sendKeys("Item 2");

        MobileElement saveButton = driver.findElement(By.id("com.lista:id/save_button"));
        saveButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/shopping_list_1")));
        MobileElement shoppingList1 = driver.findElement(By.id("com.lista:id/shopping_list_1"));
        Assert.assertTrue(shoppingList1.isDisplayed());
    }

    private static void testEditShoppingList() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/shopping_list_1")));

        MobileElement shoppingList1 = driver.findElement(By.id("com.lista:id/shopping_list_1"));
        shoppingList1.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/edit_button")));
        MobileElement editButton = driver.findElement(By.id("com.lista:id/edit_button"));
        editButton.click();

        MobileElement item1Field = driver.findElement(By.id("com.lista:id/item1_field"));
        item1Field.clear();
        item1Field.sendKeys("Novo Item 1");

        MobileElement item2Field = driver.findElement(By.id("com.lista:id/item2_field"));
        item2Field.clear();
        item2Field.sendKeys("Novo Item 2");

        MobileElement saveButton = driver.findElement(By.id("com.lista:id/save_button"));
        saveButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Novo Item 1']")));
        MobileElement newItem1 = driver.findElement(By.xpath("//android.widget.TextView[@text='Novo Item 1']"));
        Assert.assertTrue(newItem1.isDisplayed());

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Novo Item 2']")));
        MobileElement newItem2 = driver.findElement(By.xpath("//android.widget.TextView[@text='Novo Item 2']"));
        Assert.assertTrue(newItem2.isDisplayed());
    }

    private static void testMenu() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/menu_button")));

        MobileElement menuButton = driver.findElement(By.id("com.lista:id/menu_button"));
        menuButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/menu_item1")));
        MobileElement menuItem1 = driver.findElement(By.id("com.lista:id/menu_item1"));
        Assert.assertTrue(menuItem1.isDisplayed());

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/menu_item2")));
        MobileElement menuItem2 = driver.findElement(By.id("com.lista:id/menu_item2"));
        Assert.assertTrue(menuItem2.isDisplayed());

        // Continue verificando os outros itens do menu
    }

    private static void testSearch() {
        driver.resetApp();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/search_button")));

        MobileElement searchButton = driver.findElement(By.id("com.lista:id/search_button"));
        searchButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/search_input")));
        MobileElement searchInput = driver.findElement(By.id("com.lista:id/search_input"));
        searchInput.sendKeys("Item de teste");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.lista:id/search_button")));
        MobileElement searchSubmitButton = driver.findElement(By.id("com.lista:id/search_button"));
        searchSubmitButton.click();
    }

    private static void testQuickSuggestions() {
        String inputText = "Maçã";
        MobileElement inputField = driver.findElement(By.id("inputField"));
        inputField.sendKeys(inputText);

        Thread.sleep(2000);

        MobileElement suggestion1 = driver.findElement(By.id("suggestion1"));
        MobileElement suggestion2 = driver.findElement(By.id("suggestion2"));
        MobileElement suggestion3 = driver.findElement(By.id("suggestion3"));

        String suggestionText1 = suggestion1.getText();
        String suggestionText2 = suggestion2.getText();
        String suggestionText3 = suggestion3.getText();

        if (suggestionText1.equals("Banana") && suggestionText2.equals("Laranja") && suggestionText3.equals("Pera")) {
            System.out.println("Teste de sugestões rápidas: SUCESSO");
        } else {
            System.out.println("Teste de sugestões rápidas: FALHA");
        }

        inputField.clear();
    }

    private static void testSelectProductAddedToCart() {
        // Código de teste para a função de selecionar produto adicionado ao carrinho
        // ...
    }

    private static void testOpenAndClosedShoppingLists() {
        // Código de teste para a função de listas de compras abertas e fechadas
        // ...
    }

    private static void testListHistory() {
        // Código de teste para a função de histórico de listas
        // ...
    }

    private static void testDeleteShoppingList() {
        // Código de teste para a função de exclusão de lista de compras
        // ...
    }
}