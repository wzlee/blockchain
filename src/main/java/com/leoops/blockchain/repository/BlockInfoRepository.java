package com.leoops.blockchain.repository;

import java.io.Serializable;

import com.leoops.blockchain.domain.BlockInfo;

public interface BlockInfoRepository extends BaseRepository<BlockInfo,Serializable> {
	
	BlockInfo findFirstByOrderByTimeStampDesc();
	
}
