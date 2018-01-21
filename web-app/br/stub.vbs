Option Explicit

dim imgFileName, csvFileName, csvContent

imgFileName = WScript.Arguments(0)
csvFileName = WScript.Arguments(1)

WScript.Echo "arg1 : " & imgFileName
WScript.Echo "arg2 : " & csvFileName


' 出力する内容
' ユーザIDは p1～p3 として出力
csvContent = "File,p1/front.png,p1/left.png,p1/right.png,p2/front.png,p2/left.png,p2/right.png,p3/front.png,p3/left.png,p3/right.png" & vbLf _
           & "rcgn/" & imgFileName & ",-0.109406,-0.266249,0.150037,-0.44257,-0.0475555,0.713979,0.561389,0.378258,-0.501369" & vbLf

' ファイルを出力
Dim fso, cwd, file
Set fso = CreateObject("Scripting.FileSystemObject")
cwd = fso.getParentFolderName(WScript.ScriptFullName)

WScript.Echo "csv : " & cwd & "\result\" & csvFileName

Set file = fso.OpenTextFile(cwd & "\result\" & csvFileName, 2, True)
file.Write csvContent
file.Close



