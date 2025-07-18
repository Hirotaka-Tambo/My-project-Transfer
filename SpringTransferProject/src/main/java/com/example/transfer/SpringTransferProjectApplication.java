package com.example.transfer; // あなたのパッケージ名に合わせる
import org.springframework.boot.CommandLineRunner; // これを追加
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; // これを追加

import com.example.transfer.entity.User;
import com.example.transfer.repository.UserRepository;

@SpringBootApplication
public class SpringTransferProjectApplication { 

    public static void main(String[] args) {
        SpringApplication.run(SpringTransferProjectApplication.class, args);
    }

    // アプリケーション起動後に実行されるコードを定義するBean
    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // データベースに新しいユーザーを保存
            repository.save(new User("Taro Yamada", "taro@example.com"));
            repository.save(new User("Hanako Tanaka", "hanako@example.com"));

            // データベースからすべてのユーザーを取得して表示
            System.out.println("--- Users found from database ---");
            for (User user : repository.findAll()) {
                System.out.println(user.toString());
            }
            System.out.println("-------------------------------");
        };
    }
}