package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDto {

	private String accessToken;
	private AccountInfo accountInfo;

	public AuthDto(String accessToken, Long accountId, int roleId) {
		this.accessToken = accessToken;
		this.accountInfo = new AccountInfo(accountId, roleId);
	}

	@Data
	@AllArgsConstructor
	public class AccountInfo {
		private Long accountId;
		private int accountRoleId;
	}
}
