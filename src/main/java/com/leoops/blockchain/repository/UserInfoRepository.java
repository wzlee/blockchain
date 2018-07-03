package com.leoops.blockchain.repository;

import java.io.Serializable;

import com.leoops.blockchain.domain.UserInfo;

public interface UserInfoRepository extends BaseRepository<UserInfo,Serializable> {
	
	UserInfo findByUsername(String username);
	
}
