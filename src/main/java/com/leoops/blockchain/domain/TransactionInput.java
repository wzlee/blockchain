package com.leoops.blockchain.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易输入
 * @author leo
 *
 */
@Document(collection="transaction_input")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInput {
	
	@Id
    public String id;
    
	@DBRef
    public TransactionOutput UTXO;

}
