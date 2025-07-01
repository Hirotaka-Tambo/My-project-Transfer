package com.example.transfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.transfer.form.RegistForm;


@Controller
public class RegistController {
	
	/*乗り換え登録画面の前準備(必要な情報を探してくる)をリクエスト*/
	@GetMapping("/regist-transfer")
	public String registTansfer(@ModelAttribute RegistForm form){
		return "register-trans"; //"登録ページ用のHTMLに戻す"
		
	}
	
	// 乗り換え画面を表示するためのリクエスト
	@PostMapping("/regist-transfer-return")
	public String registTransferReturn(@ModelAttribute RegistForm form) {
		return "register-trans";
	}

}
