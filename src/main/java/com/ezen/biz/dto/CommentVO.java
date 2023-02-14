package com.ezen.biz.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentVO {
	private int comment_seq;
	private int pseq;
	private String content;
	private String writer; // 회원 아이디
	private Date regdate;
	private Date modifydate; // 출력일
}
