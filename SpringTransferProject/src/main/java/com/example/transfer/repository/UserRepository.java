package com.example.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.transfer.domain.User;

@Repository // Spring Beanとして登録
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepositoryを継承することで、基本的なCRUD操作（保存、検索など）が自動的に提供される
}