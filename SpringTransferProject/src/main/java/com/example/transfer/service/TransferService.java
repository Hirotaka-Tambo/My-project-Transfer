package com.example.transfer.service;

import org.springframework.stereotype.Service;

import com.example.transfer.repository.TransferRepository;


@Service
public class TransferService {
	
	// 依存性を注入(finalで定数化、privateのため、下記から情報を取得する必要がある)
	// TransferServiceメソッドで、TransferRepositoryから値(今回はsqlに格納されているデータ)を取得してくる、
	// それを privatefinal で保守性を高めてここで格納。
	private final TransferRepository transferRepository;
	
	
	public TransferService(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}

}
