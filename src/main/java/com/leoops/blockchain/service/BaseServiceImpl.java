package com.leoops.blockchain.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.leoops.blockchain.repository.BaseRepository;

public class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T, ID> {

	@Autowired
	BaseRepository<T, Serializable> baseRepository;
	
	@Override
	public T save(T t) {
		return baseRepository.save(t);
	}

	@Override
	public T get(Serializable id) {
		return baseRepository.findById(id).get();
	}

	@Override
	public void delete(Serializable id) {
		baseRepository.deleteById(id);
	}

	@Override
	public void delete(T t) {
		baseRepository.delete(t);
	}

	@Override
	public boolean exists(Serializable id) {
		return baseRepository.existsById(id);
	}
	
	@Override
	public long count() {
		return baseRepository.count();
	}

	@Override
	public List<T> findList() {
		return baseRepository.findAll();
	}

	@Override
	public List<T> findList(Sort sort) {
		return baseRepository.findAll(sort);
	}

	@Override
	public Page<T> findPage(Pageable pageable) {
		return baseRepository.findAll(pageable);
	}

	@Override
	public Iterable<T> findList(List<Serializable> ids) {
		return baseRepository.findAllById(ids);
	}

}
