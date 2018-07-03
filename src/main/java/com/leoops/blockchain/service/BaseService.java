package com.leoops.blockchain.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BaseService<T,ID extends Serializable> {
		
	T save(T t);
	
	T get(Serializable id);

	void delete(Serializable id);
	
	void delete(T t);
	
	boolean exists(Serializable id);
	
	long count();
	
	List<T> findList();
	
	Iterable<T> findList(List<Serializable> ids);
	
	List<T> findList(Sort sort);
	
	Page<T> findPage(Pageable pageable);
	
}
