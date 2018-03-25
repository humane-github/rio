@rem RTCを起動
@echo off

@rem ファイルの存在するディレクトリのパスに移動
set CURRENT_DIR=%~dp0
cd %CURRENT_DIR%

@rem コマンドプロンプトタイトル変更
title %CURRENT_DIR:*bin\=%%~nx0
@rem 日本語を扱えるようにする
chcp 932

@rem jarファイルの名前（artifactId）
set JAR_NAME=server-communication.jar
@rem メインクラス
set MAIN_CLASS=jp.co.humane.rtc.servercommunication.ServerCommunicationImpl
@rem opencvライブラリパス
set OPENCV_LIB=C:\work\tool\opencv
@rem confファイルの名前
set CONF_NAME=rtc.conf
@rem カメラID
set CAMERA_ID=c111

@rem クラスパスを設定
set CLASSPATH=%CLASSPATH%;"%JAR_NAME%"

@rem RTC起動
java -classpath %CLASSPATH% -Djava.library.path=%OPENCV_LIB% -Dcamera.id=%CAMERA_ID% %MAIN_CLASS% -f "%CONF_NAME%"

pause
