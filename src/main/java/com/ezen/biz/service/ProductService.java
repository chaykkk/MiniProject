package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.ProductVO;

import utils.Criteria;

public interface ProductService {

	List<ProductVO> getNewProductList();
	
	List<ProductVO> getBestProductList();
	
	public ProductVO getProduct(ProductVO vo);
	
	public List<ProductVO> getProductListByKind(String kind);
	
	public int getcountProductList(String name);
	
	public List<ProductVO> getlistProduct(String name);
	
	public void insertProduct(ProductVO vo);
	
	public void updateProduct(ProductVO vo);
	
	public List<ProductVO> getListProductWithPaging(Criteria criteria, String name);
}