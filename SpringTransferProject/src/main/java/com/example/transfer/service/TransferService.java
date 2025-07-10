package com.example.transfer.service;

import org.springframework.stereotype.Service;

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
	
	

}
