package com.ezen.biz.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QnaVO {
	private int qseq;
	private String subject; // 제목
	private String content; // 문의 내용
	private String reply; // 답변 내용
	private String id; // 질문한 사용자 ID
	private String rep; // 답변 유무
	private Date indate;
}
