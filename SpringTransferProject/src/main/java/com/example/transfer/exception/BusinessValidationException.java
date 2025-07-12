package com.example.transfer.exception; // exceptionパッケージ

/**
 * ビジネスロジック上のバリデーションエラーが発生した場合にスローされるカスタム例外。
 */
public class BusinessValidationException extends RuntimeException {

    // コンストラクタ
    // エラーメッセージを受け取り、RuntimeExceptionのコンストラクタに渡す
    public BusinessValidationException(String message) {
        super(message);
    }

    // 必要に応じて、エラーコードや、複数のエラー情報を保持するための
    // 追加のコンストラクタやフィールドを定義することもできます。
    /*
    private String errorCode;

    public BusinessValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
    */
}