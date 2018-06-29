package com.leoops.blockchain.repository;

import java.io.Serializable;
import java.util.List;

import com.leoops.blockchain.domain.TransactionOutput;

public interface TransactionOutputRepository extends BaseRepository<TransactionOutput,Serializable> {
		
	List<TransactionOutput> findByReciepient(String reciepient);
}
