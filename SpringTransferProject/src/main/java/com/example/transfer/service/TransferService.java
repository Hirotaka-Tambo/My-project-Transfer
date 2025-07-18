package com.example.transfer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transfer.entity.TransferRoute;
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
	public void validateRegistForm(RegistForm form) throws BusinessValidationException{
		/*
		 * ビジネスロジックのバリデーション
		 * 出発駅と到着駅のチェック 同じ駅になっていないかの確認
		 */
		if(form.getStartStation() != null && form.getStartStation().equals(form.getEndStation())) {
			throw new BusinessValidationException("出発駅と到着駅は異なる駅を入力してください");
		}
		
		/*
		 * 経由路線と到着路線の統一のバリデーション
		 */
		
		/*
		 * 経由駅のチェック①出発駅および到着駅とそれぞれ異なる駅であることの確認
		 * [注意事項]経由駅はnullでもいいと設定したため、nullチェックをスルーできるようにしないといけない
		 * つまり、nullやisEmptyでない時(何かしらの入力がされている場合)に初めて、判定を行う
		 */
		if(form.getViaStation() != null && !form.getViaStation().isEmpty()) {
			if(form.getViaStation().equals(form.getStartStation()) || form.getViaStation().equals(form.getEndStation())) {
				throw new BusinessValidationException("経由駅は、出発駅と到着駅と異なる駅を入力してください");
			}
		}
		
		/*
		 * 目的地のチェック(到着駅と目的地は同じでOK)
		 */
		
		// 目的地と出発駅が異なることの確認
		
		if(form.getDestination() != null && form.getDestination().equals(form.getStartStation())) {
			throw new BusinessValidationException("目的地と出発地は同じにはできません");
		}
		
		// 目的地と経由地が異なることの確認
		
		if(form.getDestination() != null && form.getDestination().equals(form.getEndStation())) {
			throw new BusinessValidationException("目的地と到着地は同じにはできません");
		}
	}	
		
		// contorollerから受け取ったデータをEntityに変換するフェーズ
	@Transactional
	public void registerTransfer(RegistForm form)throws BusinessValidationException{	
		
		validateRegistForm(form);
		
		
		TransferRoute transferRoute = new TransferRoute();
		
		transferRoute.setStartLine(form.getStartLine());
		transferRoute.setStartStation(form.getStartStation());
		transferRoute.setStartCar(form.getStartCar());
		transferRoute.setStartDoor(form.getStartDoor());
		
		transferRoute.setViaLine(form.getViaLine());
		transferRoute.setViaStation(form.getViaStation());
		transferRoute.setViaCar(form.getViaCar());
		transferRoute.setViaDoor(form.getViaDoor());
		
		transferRoute.setEndLine(form.getEndLine());
		transferRoute.setEndStation(form.getEndStation());
		transferRoute.setEndCar(form.getEndCar());
		transferRoute.setEndDoor(form.getEndDoor());
		
		transferRoute.setDestination(form.getDestination());
		
		
		// Repositoryを介してのデータベースに保存
		
		transferRepository.save(transferRoute);
		
	}
	
	
	// 登録している乗り換え情報の取得
	// SQLを読み取るもののため、@transactionalを用意。
	
	@Transactional(readOnly = true)
	public List<TransferRoute>findAlltransfers(){
		// Repositoryから全ての登録情報を持ち出してくる
		return transferRepository.findAll();
	}

}
