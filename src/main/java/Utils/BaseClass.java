package Utils;

import java.io.FileInputStream;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

//Base class to allow access to browser from hooks
public class BaseClass {
	//local variable that gets assigned below after properties class is instantiated
	public static WebDriver driver;
	public static Properties prop;


	
	public static WebDriver getDriver() throws IOException
	{
		prop = new Properties();
		FileInputStream fls = new FileInputStream("src\\test\\resources\\global.properties");
		prop.load(fls);
		System.setProperty("webdriver.chrome.driver", "Browsers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
	}
	public static String randomDate() {
	        
	        LocalDate from = LocalDate.of(2000, 1, 1);
	        LocalDate to = LocalDate.of(2017, 1, 1);
	        long days = from.until(to, ChronoUnit.DAYS);
	        long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
	        LocalDate randomDate = from.plusDays(randomDays);
	        return randomDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    }
	
	public static String todaysDate() {
		LocalDate today = LocalDate.now();
		return today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	public static String tomorrowsDate() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		return tomorrow.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public static String getRandom(int n)
	{

		// length is bounded by 256 Character
		byte[] array = new byte[256];
		new Random().nextBytes(array);
		String randomString
				= new String(array, Charset.forName("UTF-8"));
		// Create a StringBuffer to store the result
		StringBuffer r = new StringBuffer();
		// Append first 20 alphanumeric characters
		// from the generated random String into the result
		for (int k = 0; k < randomString.length(); k++) {
			char ch = randomString.charAt(k);
			if (((ch >= 'a' && ch <= 'z')
					|| (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9'))
					&& (n > 0)) {
				r.append(ch);
				n--;
			}
		}
		// return the resultant string
		return r.toString();
	}

	public static WebDriverWait wait(int seconds){
		return new WebDriverWait(driver, seconds);
	}

	public static void sendMail(String subject, String message, String path) throws EmailException {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(path);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("REPORT");
		attachment.setName("Report.zip");

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("maxmaragia@gmail.com", "maxipain11"));
		email.setSSLOnConnect(true);
		email.setFrom("maxmaragia@gmail.com");
		email.setSubject(subject);
		email.setMsg(message);
		email.addTo("sandeep.madavi@technobraingroup.com");
		email.addTo("maxwell.maragia@technobraingroup.com");
		email.addTo("sowjanya.jalem@technobraingroup.com");
		email.addTo("vinay.mudugal@technobraingroup.com");
		email.attach(attachment);
		email.send();
	}

	public static void zip( String srcPath,  String zipFilePath) throws IOException {
		Path zipFileCheck = Paths.get(zipFilePath);
		if(Files.exists(zipFileCheck)) { // Attention here it is deleting the old file, if it already exists
			Files.delete(zipFileCheck);
			System.out.println("Deleted");
		}
		Path zipFile = Files.createFile(Paths.get(zipFilePath));

		Path sourceDirPath = Paths.get(srcPath);
		try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
			 Stream<Path> paths = Files.walk(sourceDirPath)) {
			paths
					.filter(path -> !Files.isDirectory(path))
					.forEach(path -> {
						ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
						try {
							zipOutputStream.putNextEntry(zipEntry);

							Files.copy(path, zipOutputStream);
							zipOutputStream.closeEntry();
						} catch (IOException e) {
							System.err.println(e);
						}
					});
		}

		System.out.println("Zip is created at : "+zipFile);
	}


}
