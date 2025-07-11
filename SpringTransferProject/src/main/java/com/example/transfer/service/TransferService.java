package com.example.transfer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transfer.exception.BusinessValidationException;
import com.example.transfer.form.RegistForm;
import com.example.transfer.repository.TransferRepository;


/*ビジネスロジックの記述
 * ビジネスロジックとは・・・設定したビジネスルールを理論としてservice層で定義すること。
 * ビジネスルール・・・開発者自身が設定する、サービス用に策定するルール。
 *　　　　　　　　　　お気に入り登録の上限や例外処理、バリデーションなど、
 *                 サービス提供においての「ルールブック」のようなもの。  
*/


@Service
public class TransferService {
	
	/*TransferRepositoryをコンストラクタ経由で依存性注入する。
	  private + final とすることで、外部からの書き換えを防ぎ、保守性・安全性を高めている。
	  TransferService内のメソッドから、transferRepositoryを通じて
	  SQL（DB）にアクセスできるようにするための準備。
	  ※Springはコンストラクタの引数と一致する型のBeanを自動的に注入する。
	 */
	
	private final TransferRepository transferRepository;
	
	// コンストラクタインジェクション
	public TransferService(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}
	
	/*
	 * 乗り換え情報の登録用のロジック
	 * フォームデータ　ーー　エンティティに変換 ーー　ビジネスバリデーションエクセプションで正誤判定 ーー ようやくDBに保存する
	 * @param ーー　form 登録フォームデータ
	 * @throws ーー BuisnessValidationExceptionがエラー出た場合の例外処理
	 */
	
	@Transactional
	public void registerTransfer(RegistForm form)throws BusinessValidationException{
		/*
		 * ビジネスロジックのバリデーション
		 * 出発駅と到着駅のチェック 同じ駅になっていないかの確認
		 */
		if(form.getStartStation() != null && form.getStartStation().equals(form.getEndStation())) {
			throw new BusinessValidationException("出発駅と到着駅は異なる駅を入力してください");
		}
		/*
		 * 経由駅のチェック①出発駅および到着駅とそれぞれ異なる駅であることの確認
		 * [注意事項]経由駅はnullでもいいと設定したため、nullチェックをスルーできるようにしないといけない
		 * つまり、nullやisEmptyでない時(何かしらの入力がされている場合)に初めて、判定を行う
		 */
		if(form.getViaStation() != null && !form.getViaStation().isEmpty()) {
			if(form.getViaStation().equals(form.getStartStation()) || form.getViaStation().equals(form.getEndStation())) {
				throw new BusinessValidationException("経由駅は、出発地と到着地と異なる駅を入力してください");
			}
		}
		
		/*
		 * 目的地のチェック 
		 */
	}
	
	
	
	
	

}
