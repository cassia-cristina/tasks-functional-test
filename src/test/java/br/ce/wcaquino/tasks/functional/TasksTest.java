package br.ce.wcaquino.tasks.functional;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TasksTest {
	WebDriver driver;	
	
	public WebDriver inicializaDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://localhost:8001/tasks/");
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() {
		driver = inicializaDriver();
		try {
			driver.findElement(By.cssSelector("#addTodo")).click();
			driver.findElement(By.cssSelector("input[name=task]")).sendKeys("Tarefa inserida pelo Selenium");
			driver.findElement(By.cssSelector("input[name=dueDate]")).sendKeys("01/01/2022");
			driver.findElement(By.cssSelector("input[value=Save]")).click();		
			assertEquals("Success!", driver.findElement(By.className("alert-success")).getText());
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataInvalida() {
		driver = inicializaDriver();
		try {
			driver.findElement(By.cssSelector("#addTodo")).click();
			driver.findElement(By.cssSelector("input[name=task]")).sendKeys("Tarefa inserida pelo Selenium");
			driver.findElement(By.cssSelector("input[name=dueDate]")).sendKeys("01/01/2010");
			driver.findElement(By.cssSelector("input[value=Save]")).click();		
			assertEquals("Due date must not be in past", driver.findElement(By.className("alert-danger")).getText());			
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		driver = inicializaDriver();
		try {			
			driver.findElement(By.cssSelector("#addTodo")).click();
			driver.findElement(By.cssSelector("input[name=dueDate]")).sendKeys("01/01/2024");
			driver.findElement(By.cssSelector("input[value=Save]")).click();	
			assertEquals("Fill the task description", driver.findElement(By.className("alert-danger")).getText());
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		driver = inicializaDriver();
		try {
			driver.findElement(By.cssSelector("#addTodo")).click();
			driver.findElement(By.cssSelector("input[name=task]")).sendKeys("Tarefa inserida pelo Selenium");
			driver.findElement(By.cssSelector("input[value=Save]")).click();
			assertEquals("Fill the due date", driver.findElement(By.className("alert-danger")).getText());
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricaoEsemData() {
		driver = inicializaDriver();
		try {			
			driver.findElement(By.cssSelector("#addTodo")).click();
			driver.findElement(By.cssSelector("input[value=Save]")).click();	
			assertEquals("Fill the task description", driver.findElement(By.className("alert-danger")).getText());
		} finally {
			driver.quit();
		}
	}
	
}