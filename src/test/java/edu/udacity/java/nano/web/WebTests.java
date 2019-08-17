package edu.udacity.java.nano.web;

import edu.udacity.java.nano.BaseSeleniumTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebTests extends BaseSeleniumTests {
	@Test
	public void get_to_login() {
		this.webDriver.get(this.HOST_ENDPOINT);

		String expected = "Chat Room Login";
		String title = this.webDriver.getTitle();

		Assert.assertEquals(expected, title);
	}

	@Test
	public void login_to_chat() {
		this.webDriver.get(this.HOST_ENDPOINT);

		String username = "foo";

		WebElement inputElement = this.webDriver.findElement(By.id("username"));
		WebElement submitElement = this.webDriver.findElement(By.className("submit"));

		inputElement.sendKeys(username);
		submitElement.click();

		String currentUrl = this.webDriver.getCurrentUrl();

		Assert.assertTrue(currentUrl.contains(String.format("index?username=%s", username)));
	}

	@Test
	public void send_chat_message() {
		String username = "foo";
		String textMessage = "bar";
		String expected = String.format("%s：%s", username, textMessage);

		this.webDriver.get(String.format("%s/index?username=%s", this.HOST_ENDPOINT, username));

		WebElement inputElement = this.webDriver.findElement(By.id("msg"));
		WebElement submitElement = this.webDriver.findElement(By.id("submit"));
		WebElement messagesContainerElement = this.webDriver.findElement(By.className("message-container"));
		WebDriverWait wait = new WebDriverWait(this.webDriver, 5);

		inputElement.sendKeys(textMessage);
		submitElement.click();

		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("message-content"), 0));

		WebElement messageElement = messagesContainerElement.findElement(By.className("message-content"));

		Assert.assertNotNull(messageElement);
		Assert.assertEquals(expected, messageElement.getText());
	}

	@Test
	public void leave_chat() {
		this.webDriver.get(String.format("%s/index?username=foo", this.HOST_ENDPOINT));

		WebElement leaveElement = this.webDriver.findElement(By.id("leave"));

		leaveElement.click();

		String currentUrl = this.webDriver.getCurrentUrl();

		Assert.assertEquals(String.format("%s/", this.HOST_ENDPOINT), currentUrl);
	}
}
