package com.example.transfer.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.transfer.exception.BusinessValidationException;
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
	 * GETリクエストで乗り換え登録画面の初期表示。(ユーザーが入力できるように最初の画面を表示する。)
	 * getリクエストだから、URLに遷移先がついてくる。
	 * 新しいRegistFormのオブジェクト(型)をモデルに追加して、画面を表示する。
	 * @param model Thymeleaf(HTMLテンプレート)にデータを渡すためのModelオブジェクト
	 * @return 登録画面のテンプレートに渡す
	 */
	
	@GetMapping("/regist-transfer")
	public String showRegistForm(Model model){
		// 初回表示や、レダイレクトでの画面表示の時に記入中のフォームっがないのか否かをチェックする
		// なければ、新しい初期画面状態のformを追加してあげる。
		
		if(!model.containsAttribute("registForm")) {
			model.addAttribute("registForm", new RegistForm());
		}
		
		return "register-trans"; //"登録画面用のHTMLに戻す"
		
	}
	
	
	/*
	 * 乗り換え情報を登録して、送信するまで。(POSTリクエスト)
	 * フォーム入力→おおまかなフォームバリデーションの細かいバリデーション(serviceに記述)の確認はservice層が細かい確認をしてくれる。
	 * 
	 * @param form 入力されたRegistFormのデータ
	 * @param bindingResult ファームバリデーションの結果
	 * @param model オブジェクト(thymeleafにデータ送る)
	 * @param redirectAttributes リダイレクト時に属性を渡すためのオブジェクト
	 * @return 結果に応じて、遷移するページのURL
	 */
	
	
	// 乗り換え画面を表示するためのリクエスト
	@PostMapping("/regist-transfer")
	public String registTransfer(
			@ModelAttribute("regisitForm") @Valid RegistForm form,
			BidingResult bindingResult, Model model, RedirectAttributes reditectAttributes){
		
		// フォームバリデーションのチェック
		if(bindingResult.hasErrors()) {
			// エラーがあれば、入力値はそのままでエラーメッセージを持ちながら、登録画面に戻る。
			// @ModelAttributeで、フォーみは自動的にmodelに追加される。
			model.addAttribute("errormessage", "入力内容に誤りがあります。ご確認ください:");
			
			return "register-trans";
		}
		
		// サービス層へ委譲する(細かい中身のバリデーションチェック)
		// エラーを拾う(try-catch)
		
		try {
			transferService.registerTransfer(form);
			// 登録に問題ない時は、
		}
		
		
	}
	
	@PostMapping("/regist-trans/confirm")
	public String confirmTransfer(
		@ModelAttribute("registForm") @Valid RegistForm form,
		BindingResult bindingResult,
        Model model) {
        if(bindingResult.hasErrors()) {
        	model.addAttribute("errorMessage", "入力に誤りがあります。ご確認ください");
        	return "regist-trans";
        	}
        
        try {
        	// Servicew専用のメソッドを呼ぶ(下記のバリデーションだけを確認してほしいため)
        	transferService.validateRegistForm(form);
        		// バリデーションを通過できれば確認画面んは遷移
        		return "confirm-regist";
        }catch(BusinessValidationException e) {
        	model.addAttribute("errorMessage", e.getMessage());
        }
        
        }

}
