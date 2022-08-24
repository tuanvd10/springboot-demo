package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.AccountSession;

public interface AuthRepositoryCustom {
	public List<AccountSession> findAccountSesionByToken(String token);
}
