package jp.co.humane.rio.common.exception;

/**
 * 業務例外クラス。
 * 詳細な例外を作る際は本クラスを継承すること。
 * @author terada
 *
 */
public class ApplicationException extends RuntimeException {

    /**
     * デフォルトコンストラクタ。
     */
    public ApplicationException() {
        super();
    }

    /**
     * メッセージ指定ありのコンストラクタ。
     * @param message メッセージ。
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * 例外指定ありのコンストラクタ。
     * @param ex 例外。
     */
    public ApplicationException(Throwable ex) {
        super(ex);
    }

    /**
     * メッセージ、例外指定ありのコンストラクタ。
     * @param message メッセージ。
     * @param ex 例外。
     */
    public ApplicationException(String message, Throwable ex) {
        super(message, ex);
    }

}
