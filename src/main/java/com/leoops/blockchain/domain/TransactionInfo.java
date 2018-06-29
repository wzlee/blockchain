package com.leoops.blockchain.domain;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础交易
 * @author leo
 *
 */
@Document(collection="transaction_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInfo {

	@Id
    public String id;
	
    public String txId;
	
    public String sender;
    
    public String reciepient;
    
    public double value;
    
    public byte[] signature;

    @DBRef
    @Builder.Default
    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    
    @DBRef
    @Builder.Default
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    @Builder.Default
    private static int sequence = 0; //A rough count of how many transactionInfos have been generated

}
