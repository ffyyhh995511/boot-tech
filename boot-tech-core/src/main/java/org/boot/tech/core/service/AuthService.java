package org.boot.tech.core.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
	public boolean checkToken(String token){
		if("fyh".equals(token)){
			return true;
		}
		return false;
	}
}
