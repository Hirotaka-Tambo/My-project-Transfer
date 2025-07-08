package com.example.transfer.service;

import org.springframework.stereotype.Service;

import com.example.transfer.repository.TransferRepository;


@Service
public class TransferService {
	
	/*TransferRepositoryをコンストラクタ経由で依存性注入する。
	  private + final とすることで、外部からの書き換えを防ぎ、保守性・安全性を高めている。
	  TransferService内のメソッドから、transferRepositoryを通じて
	  SQL（DB）にアクセスできるようにするための準備。
	  ※Springはコンストラクタの引数と一致する型のBeanを自動的に注入する。
	 */
	
	private final TransferRepository transferRepository;
	
	
	public TransferService(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}

}
