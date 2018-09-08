package jp.co.humane.rio.common.consts;

/**
 * 処理結果コード
 * @author terada
 *
 */
public class ResultCode {

    /** 正常終了 */
    public static final String SUCCESS = "001";

    /** バリデーションエラー */
    public static final String VALIDATION_ERROR = "100";

    /** 重複エラー */
    public static final String DUPLICATE_ERROR = "101";

    /**
     * デフォルトコンストラクタ。
     */
    private ResultCode() {
        super();
    }

}
