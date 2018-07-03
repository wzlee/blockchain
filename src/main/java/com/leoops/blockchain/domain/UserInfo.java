package com.leoops.blockchain.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区块
 * 
 * @author leo
 *
 */
@Document(collection = "user_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

	@Id
	private String id;
	
	@NotNull
	@Size(min=3,max=11,message="用户名格式不正确")
	@Indexed(unique=true)
	private String username;

	@NotNull
	@Size(min=6,max=20,message="密码格式不正确")
	private String password;

	@NotNull
	@Size(min=3,max=10,message="昵称格式不正确")
	private String nickname;

}
