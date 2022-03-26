package selenium;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static final String URL = "http://localhost:8080/frontend/form";
    private WebDriver driver;
    String randomEmail;

    @Before
    public void setUp()
    {
        System.out.println("Iniciando configuración...");
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get(URL);
		driver.manage().window().maximize();
        System.out.println("Iniciando Pruebas...");
    }

    @After
    public void tearDown() 
    {
      driver.quit();
    }

    @Test
    public void calcularRetiro() {
        String sueldo= "1000000";
        String ahorro= "10000000";

        driver.manage().window().setSize(new Dimension(1792, 1057));
        driver.findElement(By.id("sueldo")).click();
        driver.findElement(By.id("sueldo")).sendKeys(sueldo);
        driver.findElement(By.id("ahorro")).click();
        driver.findElement(By.id("ahorro")).sendKeys(ahorro);
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        assertEquals(sueldo, driver.findElement(By.id("sueldo")).getAttribute("innerHTML"));
        assertEquals(ahorro, driver.findElement(By.id("ahorro")).getAttribute("innerHTML"));

        Integer dxc = getDxc(Integer.parseInt(ahorro), Double.parseDouble(driver.findElement(By.id("uf")).getAttribute("innerHTML")));
        assertEquals(String.valueOf(dxc), driver.findElement(By.id("dxc")).getAttribute("innerHTML"));
        Integer Saldo = (Integer.parseInt(ahorro)) - dxc;
        assertEquals(String.valueOf(Saldo), driver.findElement(By.id("saldo")).getAttribute("innerHTML"));

    }

    public static int getDxc(int ahorro, double uf) {
        if (((ahorro * 0.1) / uf) > 150) {
            return (int) (150 * uf);
        } else if ((ahorro * 0.1) <= 1000000 && ahorro >= 1000000) {
            return (int) 1000000;
        } else if (ahorro <= 1000000) {
            return (int) ahorro;
        } else {
            return (int) (ahorro * 0.1);
        }
    }
}
