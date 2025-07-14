package com.example.transfer.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.transfer.exception.BusinessValidationException;
import com.example.transfer.form.RegistForm;
import com.example.transfer.service.TransferService;


@Controller
// フォームが入力画面→確認画面と遷移するため、sessionAttributesのアノーテーションを追加する。(データを引き継ぐため)
@org.springframework.web.bind.annotation.SessionAttributes("registForm")
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
	public String showRegistForm(Model model) {
		
		 // @ModelAttribute("registForm") public RegistForm setupForm() がフォームオブジェクトを準備するため、
        // ここでの model.addAttribute は基本的に不要だが、
        // リダイレクトで戻ってきた際にFlash Attributesで渡されたメッセージを表示する準備は必要。
		
		return "register-trans"; //"登録画面用のHTMLに戻す"
		
	}
	
	
	/*
	 * 乗り換え情報の確認処理 (POSTリクエスト)。
     * フォームの入力値を受け取り、フォームレベルとビジネスレベルのバリデーションを実行後、
     * エラーがなければ確認画面に遷移します。
     *
	 * * @param form               入力されたRegistFormデータ
     * @param bindingResult      フォームバリデーションの結果
     * @param model              Thymeleafにデータを渡すためのModelオブジェクト
     * @return 処理結果に応じたテンプレート名
	 */
	
	@PostMapping("/regist-transfer/confirm")
	public String confirmTransfer(
		@ModelAttribute("registForm") @Valid RegistForm form,
		BindingResult bindingResult,
        Model model) {
		// バリデーションのチェック
        if(bindingResult.hasErrors()) {
        	model.addAttribute("errorMessage", "入力に誤りがあります。ご確認ください");
        	return "regist-trans";
        	}
        
        // service層でのビジネスロジックのバリデーション(保存はまだ行わない)
        try {
        	// Servicew専用のメソッドを呼ぶ(下記のバリデーションだけを確認してほしいため)
        	transferService.validateRegistForm(form);
        		// バリデーションを通過できれば確認画面は遷移
        		return "confirm-regist";
        }catch(BusinessValidationException e) {
        	model.addAttribute("errorMessage", e.getMessage());
        	return "regist-trans";
        }
        
	}
	
	
	/*
	 * 乗り換え情報の登録実行処理
	 * 確認画面で「regist」が押されたら、SQLへの保存を実行する。
	 * 
	 * @param form セッションで保持されていた入力データ(RegistForm)
	 * @param bindingResukt(形式的に引数として残す)
	 * @param edirectAttributes リダイレクト時に属性を渡すオブジェクト
	 * @param status セッションからresisiformをクリアする時に使われる
	 * @return 結果に応じたURLへの遷移
	 */
	
	@PostMapping("/regist-transfer/execute") //SQL登録へのあたらしいメソッド
	public String executeRegisterTransfer(
		@ModelAttribute("registForm") RegistForm form,
		BindingResult bindingResult,
		RedirectAttributes redirectAttributes,
		SessionStatus status) {
		
		if(bindingResult.hasErrors()) {
			// エラーがあればエラーメッセージをフラッシュ属性で渡した上で、入力画面い強制遷移
			redirectAttributes.addFlashAttribute("errorMessage", "入力内容に誤りがあります。再度ご確認ください。");
			
			// フォームのセッションデータは残っている(@ModelAttribute("registForm)ため、そのデータも合わせてリダイレクト先に表示
			
			return "redirect:/regist-transfer";
		}
		
		// 7/15はここから記述
		return "redirect;/display-trans";
		
	}

	
	
}
