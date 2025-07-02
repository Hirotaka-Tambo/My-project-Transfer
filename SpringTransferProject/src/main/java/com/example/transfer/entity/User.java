package com.example.transfer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// ★この行を新しく追加してください★
import jakarta.persistence.Table;

@Entity // データベースのテーブルに対応するクラスであることを示す
@Table(name = "app_users")
public class User {

    @Id // 主キーであることを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成されるID
    private Long id;
    private String name;
    private String email;

    // コンストラクタ (JPAにはデフォルトコンストラクタが必須)
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // GetterとSetter (Eclipseで自動生成できます)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
