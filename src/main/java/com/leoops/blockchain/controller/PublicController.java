package com.leoops.blockchain.controller;

import java.security.KeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.leoops.blockchain.domain.UserInfo;
import com.leoops.blockchain.domain.WalletInfo;
import com.leoops.blockchain.repository.WalletInfoRepository;
import com.leoops.blockchain.service.UserInfoService;
import com.leoops.blockchain.utils.RSAUtil;

@Controller
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	UserInfoService userInfoService;	
	
	@Autowired
	WalletInfoRepository walletInfoRepository;	
	
	@GetMapping("/signup")
	public String userSignup(){
		return "signup";
	}
	
	@PostMapping("/signup")
	public String userSignup(@RequestParam String username,@RequestParam String password,@RequestParam String nickname,Model model){
		UserInfo userInfo = userInfoService.save(UserInfo.builder().username(username).password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password)).nickname(nickname).build());
		KeyPair keyPair = RSAUtil.generateKeyPair();
		String privateKey = RSAUtil.getPrivateKey(keyPair.getPrivate());
		String publicKey = RSAUtil.getPublicKey(keyPair.getPublic());
		WalletInfo walletInfo =  walletInfoRepository.save(WalletInfo.builder().userId(userInfo.getId()).privateKey(privateKey).publicKey(publicKey).build());
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("walletInfo", walletInfo);
		return "success";
	}
	
}
