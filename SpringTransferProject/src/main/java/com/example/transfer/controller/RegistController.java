package com.example.transfer.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.transfer.entity.TransferRoute;
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
		@ModelAttribute("registForm") @Valid RegistForm form,
		BindingResult bindingResult,
		RedirectAttributes redirectAttributes,
		SessionStatus status) {
		
		if(bindingResult.hasErrors()) {
			// エラーがあればエラーメッセージをフラッシュ属性で渡した上で、入力画面い強制遷移
			redirectAttributes.addFlashAttribute("errorMessage", "入力内容に誤りがあります。再度ご確認ください。");
			
			// フォームのセッションデータは残っている(@ModelAttribute("registForm)ため、そのデータも合わせてリダイレクト先に表示
			
			return "redirect:/regist-trans";
		}
		
		
		/*
		 *バリデーションチェックを通過したら、確認画面に遷移したいから、return部分の記述修正が必須!! 
		 */
		
		try {
			// service層のSQL保存まで含むメソッドを呼び出す
			// バリデーションを呼び出しているので、ここで最終チェック。
			TransferRoute savedRoute = transferService.registerTransfer(form);
			
			//登録が成功したら、セッションからフォームデータをクリア
			status.setComplete();
			
			redirectAttributes.addFlashAttribute("successMessage", "乗り換え情報の登録に成功しました");
			return "redirect:/display-trans" + savedRoute.getId(); // 登録後の乗り換え一覧画面などにリダイレクト&IDをパス変数として返す
		
		}catch (BusinessValidationException e) {
			// service層における、ビジネスロジック部分のバリデーションエラーについての対処
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			
			return "redirect:/regist-trans";
			
		}catch(Exception e) {
			// そのほかの予期せぬエラー
			redirectAttributes.addFlashAttribute("errorMessage", "乗り換え情報の登録中に予期せぬエラーが発生しました。");
			e.printStackTrace(); // 開発中はスタックトレースを出力して原因を特定する(エラー原因がわかるようになるコード)
			
			return "reditrect:/regist-trans";
		}
		
	}
	
	//特定の乗り換えルートを表示するメソッド
	
	@GetMapping("/display-trans/{id}")
	public String displaySingleTransfer(@PathVariable("id") Long id, Model model) {
		//途中までの記述
		
		return "display-trans";
	}
	

	// 表示する一覧(リストに格納してから、一覧で表示する。)
	
	@GetMapping("favorite-list")
	public String diaplayAllTransfers(Model model) {
		//　登録された乗り換え情報の一覧を表示する
		List<TransferRoute>transfers = transferService.findAlltransfers();
		model.addAttribute("transfers", transfers);
		
		return "favorite-list";
	}
	
	
}
