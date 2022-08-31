package top.sunquest.temp2project.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SampleController {

	@GetMapping("/validate")
	public String validate(HttpServletResponse response,HttpServletRequest request) throws InterruptedException {

		//System.out.println(request.getHeader("Set-Cookie"));

		//Cookie ck = new Cookie("sunk","sunquest");
		//ck.setDomain("localhost");
		//ck.setPath("/");
		//ck.setMaxAge(100000);
		//response.addCookie(ck);

		return "sample";
	}

	@GetMapping("/setDevDomain")
	public String setDevDomain(HttpServletResponse response,HttpServletRequest request) throws InterruptedException {

		Cookie ck = new Cookie("token","devToken");
		ck.setDomain("dev.sunquest.com");
		ck.setPath("/");
		ck.setMaxAge(100000);
		response.addCookie(ck);

		return "setDevDomain success";
	}

	@GetMapping("/setRootDomain")
	public String setRootDomain(HttpServletResponse response,HttpServletRequest request) throws InterruptedException {

		Cookie ck = new Cookie("token","rootToken");
		ck.setDomain("sunquest.com");
		ck.setPath("/");
		ck.setMaxAge(100000);
		response.addCookie(ck);

		return "setRootDomain success";
	}
}
