package jp.co.humane.rio.common.dto;

/**
 * エラー情報
 * @author terada
 *
 */
public class ErrorInfo {

    /** JSONキー名 */
    private String keyName = null;

    /** メッセージ */
    private String message = null;

    /**
     * デフォルトコンストラクタ。
     */
    public ErrorInfo() {
        super();
    }

    /**
     * keyNameを取得する。
     * @return keyName keyName。
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * keyNameを設定する。
     * @param keyName keyName。
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * messageを取得する。
     * @return message message。
     */
    public String getMessage() {
        return message;
    }

    /**
     * messageを設定する。
     * @param message message。
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
