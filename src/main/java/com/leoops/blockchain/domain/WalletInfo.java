package com.leoops.blockchain.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 钱包
 * @author leo
 *
 */
@Document(collection="wallet_info")
@Data
@Builder
@AllArgsConstructor
public class WalletInfo {

	@Id
	private String id;
	
	private String name;
	
    public String privateKey;
    
    public String publicKey;

    @DBRef
    @Builder.Default
    public List<TransactionOutput> utxos = new ArrayList<>();

}
