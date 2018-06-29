package com.leoops.blockchain.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.leoops.blockchain.utils.RSAUtil;

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
@Document(collection = "block_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockInfo {

	@Id
	public String id;
	
	public String hash;

	public String previousHash;

	public String merkleRoot;

	@DBRef
	@Builder.Default
	public List<TransactionInfo> transactions = new ArrayList<TransactionInfo>();

	public long timeStamp;

	public int nonce;

	public long height;

	/**
	 * 默克尔树（又叫哈希树）是一种二叉树，由一个根节点、一组中间节点和一组叶节点组成。最下面的叶节点包含存储数据或其哈希值，每个中间节点是它的两个孩子节点内容的哈希值，根节点也是由它的两个子节点内容的哈希值组成。
	 */
	public void calculateMerkle() {
		int count = this.transactions.size();
		List<String> previousTreeLayer = transactions.stream().map(TransactionInfo::getTxId).collect(Collectors.toList());
		System.out.println(JSON.toJSONString(previousTreeLayer, true));
		List<String> treeLayer = previousTreeLayer;
		while (count > 1) {
			treeLayer = new ArrayList<String>();
			for (int i = 1; i < previousTreeLayer.size(); i += 2) {
				treeLayer.add(RSAUtil.sha256(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		this.merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
	}

}
