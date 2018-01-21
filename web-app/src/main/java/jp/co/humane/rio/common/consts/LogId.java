package jp.co.humane.rio.common.consts;

/**
 * ログメッセージを定義するクラス。
 * @author terada
 *
 */
public class LogId {

    /** リクエスト情報 */
    public static final String D_CMN_REQ = "D_CMN_001 リクエストを受け付けました。{}";

    /** 起動ログ */
    public static final String I_CMN_START = "I_CMN_001 アプリケーションが正常に起動しました。version={}、profile={}";

    /** バリデーションチェックエラー */
    public static final String I_CMN_VALID_ERR = "I_CMN_002 バリデーションチェックでエラーが発生しました。{}";

    /** エラー時のリクエスト情報 */
    public static final String I_CMN_ERR_REQ = "I_CMN_003 次のリクエストでエラーが発生しました。{}";

    /** 業務例外 */
    public static final String E_CMN_APP_ERR = "E_CMN_001 業務例外が発生しました。";

    /** OpenCVエラー:ファイル名不正 */
    public static final String E_CMN_OPENCV_INVALID_NAME = "E_CMN_002 OpenCVライブラリファイル名が不正です。想定：{}、指定値：{}";

    /** OpenCVエラー：ファイルが存在しない */
    public static final String E_CMN_OPENCV_NOT_FOUND = "E_CMN_003 OpenCVライブラリファイルが存在しません。{}";

    /** OpenCVエラー：読み取り権限不足 */
    public static final String E_CMN_OPENCV_FAIL_ACCESS = "E_CMN_004 OpenCVライブラリファイルの読み取り権限がありません。{}";

    /** OpenBR入力画像ファイル作成失敗 */
    public static final String E_AUTH_IMG_FILE_SAVE_ERR_BR = "E_AUTH_001 OpenBR入力用の画像ファイルの作成に失敗しました。{}";

    /** OpenBR実行タイムアウト */
    public static final String E_AUTH_BR_TIMEOUT = "E_AUTH_002 OpenBRでの顔認識処理が指定時間経過しても終了。対象ファイル：{}、タイムアウト：{}秒、出力メッセージ：{}";

    /** OpenBR実行異常終了 */
    public static final String E_AUTH_ERROR_EXIT = "E_AUTH_003 OpenBRでの顔認識処理が異常終了しました。終了コード：{}、出力メッセージ：{}";

    /** OpenBR結果ファイル読み取りエラー */
    public static final String E_AUTH_BR_CSV_NOT_READ = "E_AUTH_004 OpenBRの顔認識結果CSVの読み取りに失敗しました。{}";

    /** 履歴画像ファイル保存失敗 */
    public static final String E_AUTH_CREATE_DIR_ERR = "E_AUTH_005 履歴ディレクトリの作成に失敗しました。{}";

    /** 履歴画像ファイル保存失敗 */
    public static final String E_AUTH_IMG_FILE_SAVE_ERR = "E_AUTH_006 履歴ディレクトリへの画像ファイル保存に失敗しました。{}";

    /** 更新対象の個人情報が存在しない */
    public static final String I_PERSON_UPD_PERSON_NOT_FOUND = "I_PERSON-UPD_001 存在しない個人IDに対する更新が行われました。個人ID={}";

    /** ファイル削除失敗 */
    public static final String E_PERSON_UPD_FILE_DEL_ERR = "E_PERSON-UPD_001 ファイル削除に失敗しました。{}";

    /** ファイル保存失敗 */
    public static final String E_PERSON_UPD_IMG_FILE_SAVE_ERR = "E_PERSON-UPD_002 ファイルの保存に失敗しました。{}";

    /** 更新ファイル作成失敗 */
    public static final String E_PERSON_UPD_UPD_FILE_MAKE_ERR = "E_PERSON-UPD_003 更新ファイルの作成に失敗しました。{}";

    /** 予期せぬエラー */
    public static final String E_CMN_OTHER_ERR = "E_CMN_002 予期せぬエラーが発生しました。";

}
