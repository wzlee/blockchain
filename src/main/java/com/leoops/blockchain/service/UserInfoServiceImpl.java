package com.leoops.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.leoops.blockchain.domain.UserInfo;
import com.leoops.blockchain.repository.UserInfoRepository;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, String> implements UserInfoService {
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Override
	@Cacheable(value="security_oauth_userinfo",keyGenerator="iKeyGenerator", unless="#result == null")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo = userInfoRepository.findByUsername(username);
		if(userInfo == null) {
			throw new UsernameNotFoundException("账号不存在");
		}else {
			UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder();
			userBuilder.username(username);
			userBuilder.password(userInfo.getPassword());
			userBuilder.roles("USER");
			userBuilder.authorities("USER");
			return userBuilder.build();
		}
	}

}
