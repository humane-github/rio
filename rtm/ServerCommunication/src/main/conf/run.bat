@rem RTC���N��
@echo off

@rem �t�@�C���̑��݂���f�B���N�g���̃p�X�Ɉړ�
set CURRENT_DIR=%~dp0
cd %CURRENT_DIR%

@rem �R�}���h�v�����v�g�^�C�g���ύX
title %CURRENT_DIR:*bin\=%%~nx0
@rem ���{���������悤�ɂ���
chcp 932

@rem jar�t�@�C���̖��O�iartifactId�j
set JAR_NAME=server-communication.jar
@rem ���C���N���X
set MAIN_CLASS=jp.co.humane.rtc.servercommunication.ServerCommunicationImpl
@rem conf�t�@�C���̖��O
set CONF_NAME=rtc.conf
@rem �J����ID
set CAMERA_ID=c111

@rem �N���X�p�X��ݒ�
set CLASSPATH=%CLASSPATH%;"%JAR_NAME%"

@rem RTC�N��
java -classpath %CLASSPATH% -Dcamera.id=%CAMERA_ID% %MAIN_CLASS% -f "%CONF_NAME%"

pause
