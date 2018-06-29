package com.leoops.blockchain;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leoops.blockchain.domain.BlockInfo;
import com.leoops.blockchain.domain.TransactionInfo;
import com.leoops.blockchain.domain.TransactionInput;
import com.leoops.blockchain.domain.TransactionOutput;
import com.leoops.blockchain.repository.BlockInfoRepository;
import com.leoops.blockchain.repository.TransactionInfoRepository;
import com.leoops.blockchain.repository.TransactionInputRepository;
import com.leoops.blockchain.repository.TransactionOutputRepository;
import com.leoops.blockchain.repository.WalletInfoRepository;
import com.leoops.blockchain.utils.RSAUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockchainApplicationTests {

    public static ArrayList<BlockInfo> blockchain = new ArrayList<BlockInfo>();
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();

    public static int difficulty = 3;
    public static float minimumTransaction = 0.1f;
	
	@Autowired
	BlockInfoRepository blockInfoRepository;
	
	@Autowired
	TransactionInfoRepository transactionInfoRepository;
	
	@Autowired
	TransactionInputRepository transactionInputRepository;
	
	@Autowired
	TransactionOutputRepository transactionOutputRepository;
	
	@Autowired
	WalletInfoRepository walletInfoRepository;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testRepo() {
		System.out.println(blockInfoRepository.save(BlockInfo.builder().hash("2dsjfjsdjfjjasdjfjasdf").build()));
	}
	
	@Test
	public void testTransaction() {
		String previousHash = blockInfoRepository.findFirstByOrderByTimeStampDesc().getHash();
		long height = blockInfoRepository.count();
		BlockInfo blockInfo = BlockInfo.builder().previousHash(previousHash).height(height+1).timeStamp(System.currentTimeMillis()).build();
		String sender = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdTSGJv6t8v6CBwmQaUFwnBzg2B+hRWobiBG21umy0xs4FITU7PoLXayQzeUZNd+kwrSakBl4eJhKjxPqptalytwNiHmuEud57ud06wBGSE6+O+w8Obm9P/1qkPeh2w8HocInNYaVYgoNV5Mt7uo3T09UsmlHrM9Wo0/NLcBelVQIDAQAB";
		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ1NIYm/q3y/oIHCZBpQXCcHODYH6FFahuIEbbW6bLTGzgUhNTs+gtdrJDN5Rk136TCtJqQGXh4mEqPE+qm1qXK3A2Iea4S53nu53TrAEZITr477Dw5ub0//WqQ96HbDwehwic1hpViCg1Xky3u6jdPT1SyaUesz1ajT80twF6VVAgMBAAECgYBFoIuU3pt4t5XDehk3MJTpzZ9LA0sJH6oOx2kMhL1zc4Bj8uQOGkhuNAWtJyRATDCTFEHDXvIwJCgiB6HwlvGunDlBXEYYvu5zzjtcfZjUeIxZr3A20Dj0My0slE7xjjSdkU/jmuNqVqKVuDplPITS0a20Wi8aLPJULVlyG8kejQJBANips3OCVJisUggE46X+hTfACVyJmusE4YZmHODkmW+N4pLZvO5uBEgiePLKkLPxUm6NZtQPqO2ncf+n0+lxBtcCQQC53FsA3qRjUZut4ZmWr/ARsrv9sHhatXKlPmmcSyBcc/O9iOi9fqP+0fWM94Y6qykZPai01aX3z7m2o0dm7GuzAkBjQht8J5EqAIf0NIk2WswdJlUjl8bpNGs9bzp7rT695HqQXEA52x0LDv/p+vMf/MaO7yjUrffuKHAJd5GVuLoxAkBLB/sxAbCJa9OWQaH4ZDRdYLNluc0MiX2r2eNWtjltOV4noNKcVTitUWN4siBdJOXli6/EuQ9UWGaXpZjYow7jAkA/hBzBMuLU8PYwBxAdyoO2RtRKdRFjZE1r3ZiCIHrq8QoNK1mvHB9OpGBU8h0exUJ5V4IJHN3ioeP37wGJKrNH";
		String reciepient = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK3rRO+a0fchEtbPkDBXfBxeMsUhy+RmU3BlzbMHvd8vFt9Z3prNrDXd1YUjwRyaKS9eR0oaWR56DI+3ClBxDfnXhIZNvTjkh1eKOx+JYNh4L2lXOquf0IGf9qdpxde26sl6B1C85D62MfTgYgyWpi0o0tEOd6d5WQ9dlZ99WFXwIDAQAB";
		double amount = 101.00;
		String txid = RSAUtil.sha256(sender + reciepient + String.valueOf(amount));
		byte[] signature = RSAUtil.signature((sender + reciepient + String.valueOf(amount)).getBytes(), privateKey);
		TransactionOutput transactionOutput = transactionOutputRepository.save(TransactionOutput.builder().txId(txid).reciepient(reciepient).value(amount).build());
		TransactionInput transactionInput = transactionInputRepository.save(TransactionInput.builder().UTXO(transactionOutput).build());
        TransactionInfo transactionInfo = transactionInfoRepository.save(TransactionInfo.builder().txId(txid).signature(signature).sender(sender).reciepient(reciepient).value(amount).inputs(Lists.newArrayList(transactionInput)).outputs(Lists.newArrayList(transactionOutput)).build());
        boolean verify = RSAUtil.verify((sender + reciepient + String.valueOf(amount)).getBytes(), signature, sender);
        // TODO 验证输入总额===输出总额
        if(verify) {
        	blockInfo.transactions.add(transactionInfo);
        	blockInfo.calculateMerkle();
        	blockInfo.setHash(RSAUtil.sha256(previousHash+blockInfo.timeStamp+blockInfo.height+blockInfo.merkleRoot));
        	blockInfoRepository.save(blockInfo);
        	List<TransactionOutput> utxos = transactionOutputRepository.findByReciepient(reciepient);
        	DoubleSummaryStatistics dss = utxos.stream().collect(Collectors.summarizingDouble((TransactionOutput utxo)->utxo.value));
        	System.out.println("钱包金额：" + dss.getSum());
        }else {
        	System.out.println("交易签名验证失败，当前区块无法入链");
        }
	}

}
