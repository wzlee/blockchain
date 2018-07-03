package com.leoops.blockchain.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.leoops.blockchain.domain.UserInfo;

public interface UserInfoService extends BaseService<UserInfo, String>, UserDetailsService {
	
}
