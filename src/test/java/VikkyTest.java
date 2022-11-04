import org.openqa.selenium.By;
import org.testng.annotations.Test;
import runner.BaseTest;

public class VikkyTest extends BaseTest {

    @Test
    public void TestAstraCom() {
        getDriver().get("https://www.astracom.ru/");
        getDriver().findElement(By.className("button")).click();
        getDriver().findElement(By.partialLinkText("АРСО P25")).click();
        getDriver().findElement(By.partialLinkText("Ретрансляторы")).click();

    }

}
