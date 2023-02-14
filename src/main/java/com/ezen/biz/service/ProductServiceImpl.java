package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.ProductDAO;
import com.ezen.biz.dto.ProductVO;

import utils.Criteria;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDao;
	
	// 신상품 목록 얻어오기
	@Override
	public List<ProductVO> getNewProductList() {
		return productDao.getNewProductList();
	}

	// 베스트상품 목록 얻어오기
	@Override
	public List<ProductVO> getBestProductList() {
		return productDao.getBestProductList();
	}
	
	// 상품 상세정보 조회
	@Override
	public ProductVO getProduct(ProductVO vo) {
		return productDao.getProduct(vo);
	}
	
	// 상품 종류별 상품 목록 조회
	@Override
	public List<ProductVO> getProductListByKind(String kind) {
		return productDao.getProductListByKind(kind);
	}

	// 총 상품 목록의 개수 조회
	public int getcountProductList(String name) {
		return productDao.countProductList(name);
	}
	
	// 상품 목록 조회
	@Override
	public List<ProductVO> getlistProduct(String name) {
		return productDao.listProduct(name);
	}

	// 상품 추가
	@Override
	public void insertProduct(ProductVO vo) {
		productDao.insertProduct(vo);
	}

	// 상품 수정
	@Override
	public void updateProduct(ProductVO vo) {
		productDao.updateProduct(vo);
	}

	@Override
	public List<ProductVO> getListProductWithPaging(Criteria criteria, String name) {
		return productDao.listProductWithPaging(criteria, name);
	}
	
}
