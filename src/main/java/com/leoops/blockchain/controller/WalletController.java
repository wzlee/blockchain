package com.leoops.blockchain.controller;

import java.security.KeyPair;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.leoops.blockchain.domain.TransactionOutput;
import com.leoops.blockchain.domain.WalletInfo;
import com.leoops.blockchain.repository.TransactionOutputRepository;
import com.leoops.blockchain.repository.WalletInfoRepository;
import com.leoops.blockchain.utils.RSAUtil;

@Controller
@RequestMapping("/wallet")
public class WalletController {
	
	// TODO 暂时直接调用repository,后面再加多一层service
	
	@Autowired
	WalletInfoRepository walletInfoRepository;	
	
	@Autowired
	TransactionOutputRepository transactionOutputRepository;
	
	@PostMapping("/create")
	public ResponseEntity<?> createWallet(@RequestParam String name){
		KeyPair keyPair = RSAUtil.generateKeyPair();
		String privateKey = RSAUtil.getPrivateKey(keyPair.getPrivate());
		String publicKey = RSAUtil.getPublicKey(keyPair.getPublic());
		WalletInfo walletInfo =  walletInfoRepository.save(WalletInfo.builder()
				.name(name)
				.privateKey(privateKey)
				.publicKey(publicKey).build());
		return new ResponseEntity<>(walletInfo,HttpStatus.OK);
	}
	
	@GetMapping("/balance")
	public ResponseEntity<?> balanceWallet(@RequestParam String publicKey){
		List<TransactionOutput> utxos = transactionOutputRepository.findByReciepient(publicKey);
		DoubleSummaryStatistics dss = utxos.stream().collect(Collectors.summarizingDouble((TransactionOutput utxo)->utxo.value));
		return new ResponseEntity<>(dss.getSum(),HttpStatus.OK);
	}
	
}
