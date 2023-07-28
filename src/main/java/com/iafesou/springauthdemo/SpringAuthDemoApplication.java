package com.iafesou.springauthdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringAuthDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuthDemoApplication.class, args);
	}

}

@RestController
class HttpController {
	@GetMapping("/public")
	String publicRoute(){
		return "<h1>Public Route - Everyone can see this page</h1>";
	}

	@GetMapping("/private")
	String privateRoute(@AuthenticationPrincipal OidcUser principal){
		return String.format("""
  			<h1>Private UserRoute</h1>
		""");
	}

	@GetMapping("/cookie")
	String cookie(@AuthenticationPrincipal OidcUser principal){
		return String.format("""
  			<h1>Private UserRoute</h1>
  			<h4>Principal : %s</h4>
  			<h4>Authorization : %s</h4>		
		""", principal, principal.getAttribute("email"), principal.getAuthorities(), principal.getIdToken().getTokenValue());
	}

	@GetMapping("jwt")
	String jwt(@AuthenticationPrincipal Jwt jwt){
		return String.format("""
      		Principal : %s \n
   			Email attribute : %s \n
			JsonWebToken : %s \n
		""", jwt.getClaims(), jwt.getClaim("email"), jwt.getTokenValue());
	}
}
