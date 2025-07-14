package com.example.transfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.transfer.form.RegistForm;
import com.example.transfer.service.TransferService;


@Controller
public class RegistController {
	
	private final TransferService transferService;
	
	// コンストラクタインジェクション(依存性)
	
	public RegistController(TransferService transferService){
		this.transferService = transferService;
	}
	
	/*
	 * GETリクエストで乗り換え登録画面の初期表示。(ユーザーが入力できるように最初の画面を持ってくる。)
	 * 新しいRegistFormのオブジェクト(型)をモデルに追加して、画面を表示する。
	 * @param
	 * @return
	 */
	
	/*乗り換え登録画面の前準備(必要な情報を探してくる)をリクエスト*/
	@GetMapping("/regist-transfer")
	public String registTansfer(RegistForm form){
		return "register-trans"; //"登録ページ用のHTMLに戻す"
		
	}
	
	// 乗り換え画面を表示するためのリクエスト
	@PostMapping("/regist-transfer-return")
	public String registTransferReturn(@ModelAttribute RegistForm form) {
		return "register-trans";
	}

}
