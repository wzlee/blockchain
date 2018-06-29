package com.leoops.blockchain.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易输出(未消费交易余额)
 * @author leo
 *
 */
@Document(collection="transaction_output")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOutput {

	@Id
    public String id;
	
    public String reciepient;
    
    public double value;
    
    public String txId;

    public boolean isMine(String publicKey) {
        return (publicKey == reciepient);
    }
}
