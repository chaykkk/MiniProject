package com.ezen.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.ProductVO;

import utils.Criteria;

@Repository("productDao")
public class ProductDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 신상품 목록 얻어오기
	public List<ProductVO> getNewProductList() {
		return mybatis.selectList("productMapper.getNewProductList");
	}
	
	// 베스트상품 목록 얻어오기
	public List<ProductVO> getBestProductList() {
		return mybatis.selectList("productMapper.getBestProductList");
	}
	
	// 상품 상세정보 조회, mapping에 조건이 있을 경우 반드시 매개변수로 선언해야 함
	public ProductVO getProduct(ProductVO vo) {
		return mybatis.selectOne("productMapper.getProduct", vo);
	}
	
	// 상품 종류별 상품 목록 얻어오기
	public List<ProductVO> getProductListByKind(String kind) {
		return mybatis.selectList("productMapper.getProductListByKind", kind);
	}
	
	// 총 상품 목록의 개수 조회
	public int countProductList(String name) {
		return mybatis.selectOne("productMapper.countProductList", name);
	}
	
	// 상품 목록 조회
	public List<ProductVO> listProduct(String name) {
		return mybatis.selectList("productMapper.listProduct", name);
	}
	
	// 상품 추가
	public void insertProduct(ProductVO vo) {
		mybatis.insert("productMapper.insertProduct", vo);
	}
	
	// 상품 수정
	public void updateProduct(ProductVO vo) {
		mybatis.update("productMapper.updateProduct", vo);
	}
	
	// 페이지별 상품 목록 조회
	public List<ProductVO> listProductWithPaging(Criteria criteria, String name) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("name", name);
		
		return mybatis.selectList("productMapper.listProductWithPaging", map);
	}
}
