package com.example.sample1app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootApplication

public class Sample1appApplication {

	DataObject[] data  =  {
		new DataObject("noname", "no email address", 0),
		new DataObject("taro", "taro@yamada", 39),
		new DataObject("hanako", "hanako@flower", 28),
		new DataObject("sachiko", "sachiko@happy", 17),
		new DataObject("jiro", "jiro@change", 6)
	};

	public static void main(String[] args) {
		// SpringApplication app = new SpringApplication(Sample1appApplication.class);
		// app.setBannerMode(Mode.OFF);
		// app.run(args);
		SpringApplication.run(Sample1appApplication.class, args);

	}

	@RequestMapping("/")
	public String index(
		HttpServletRequest request,
		HttpServletResponse response) {
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		String content = """
				<html>
					<head>
					<title>Sample App</title>
					</head>
					<body>
					<h1>Sample App</h1>
					<p>This is sample app page!</p>
				</html>
				""";
		return content;
	}


}

class DataObject {
	private String name;
	private String mail;
	private int age;

	DataObject(String name, String mail, int age) {
		super();
		this.name = name;
		this.mail = mail;
		this.age = age;
	}

	public String getName() { return name; }

	public void setName(String name) {
		this.name = name;
	}

	public String getMail()  { return mail; }

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getAge() { return age; }

	public void setAge(int age) {
		this.age = age;
	}
}