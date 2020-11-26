  
package com.protocolo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.protocolo.models.Login;
import com.protocolo.repository.LoginRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/")
public class LoginResource {
	
	@Autowired
	private LoginRepository loginRepository;
	
	private Login login;
	
	@PostMapping("/protocolo/login")	
	public Login cadastroLogin(@RequestBody Login login) {
		
		if(login.getUsuario().equals("admin") && login.getSenha().equals("admin")) {
			return login;
		}else {
			return this.login;
		}
	}

}
