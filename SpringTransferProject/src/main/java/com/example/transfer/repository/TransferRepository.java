package com.example.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferRoute, Integer> {
	    // JpaRepositoryを継承することで、基本的なCRUD操作（保存、検索など）が自動的に提供される

}
