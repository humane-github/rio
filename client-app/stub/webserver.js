
var http = require('http');


http.createServer(function(req, res) {

  // CORS対応
  if (req.method === 'OPTIONS') {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'POST,GET,OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
    res.end();
    return;
  }
  
  var bufs = [];
  bufs.totalLength = 0;
  req.on('data', function(chunk) {
    bufs.push(chunk);
    bufs.totalLength += chunk.length;
  });
  req.on('end', function() {
    var data = Buffer.concat(bufs, bufs.totalLength);
    response(req, res, data);
  });
    
}).listen(8081);

console.log('listening 127.0.0.1:8081');

function responseJson(res, data) {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.writeHead(200, {'Content-Type': 'application/json; charset=utf-8'});
  res.write(JSON.stringify(data));
  res.end();
};

function response(req, res, reqData) {

  // 処理結果コード
  let resultCode = '001';
  // エラー情報リスト
  let errorInfoList = [];
  // 処理結果
  let resultInfo = null;
  // レスポンスデータ
  let responseData = null;

  switch(req.url) {

    // 入退室履歴情報一覧取得
    case '/room-watch/v1.0/entryHistoryInfo/selectList':

      resultInfo = [
        {
          roomId: 'R0001',
          buildingName: '建物Ａ',
          roomName: '部屋Ａ１',
          ioType: '1',
          certifyDate: '2999/01/02 01:02:03',
          certifyResult: true,
          sectionName: '総務部',
          personId: 'P0001',
          personName: '山田太郎',
          certifyImageFilePath: '/rio/history/2999/01/02/00001.png'
        },
        {
          roomId: 'R0002',
          buildingName: '建物Ｂ',
          roomName: '部屋Ｂ１',
          ioType: '2',
          certifyDate: '2999/11/12 11:12:13',
          certifyResult: false,
          sectionName: '営業部',
          personId: 'P0002',
          personName: '山田花子',
          certifyImageFilePath: '/rio/history/2999/11/12/00002.png'
        }
      ];

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;

    // 入室者情報一覧取得
    case '/room-watch/v1.0/entrancePersonInfo/selectList':

      resultInfo = [
        {
          buildingId: 'B0001',
          roomId: 'R0011',
          buildingName: '建物Ａ',
          roomName: '部屋Ａ１',
          entrancePersonNum: '2'
        },
        {
          buildingId: 'B0001',
          roomId: 'R0012',
          buildingName: '建物Ａ',
          roomName: '部屋Ａ２',
          entrancePersonNum: '0'
        },
        {
          buildingId: 'B0002',
          roomId: 'R0021',
          buildingName: '建物Ｂ',
          roomName: '部屋Ｂ１',
          entrancePersonNum: '1'
        },
        {
          buildingId: 'B0002',
          roomId: 'R0022',
          buildingName: '建物Ｂ',
          roomName: '部屋Ｂ２',
          entrancePersonNum: '0'
        },
        {
          buildingId: 'B0002',
          roomId: 'R0023',
          buildingName: '建物Ｂ',
          roomName: '部屋Ｂ３',
          entrancePersonNum: '0'
        }
      ];

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;

    // 入室者詳細情報一覧取得
    case '/room-watch/v1.0/entrancePersonDetailInfo/selectList':

      resultInfo = [
        {
          sectionName: '総務部',
          personId: 'P0001',
          personName: '山田太郎',
          ioType: '1',
          entranceLastDate: '2999/01/02 01:02:03',
          exitLastDate: ''
        },
        {
          sectionName: '営業部',
          personId: 'P0003',
          personName: '山田次郎',
          ioType: '1',
          entranceLastDate: '2999/03/03 01:02:03',
          exitLastDate: ''
        },
        {
          sectionName: '総務部',
          personId: 'P0002',
          personName: '山田花子',
          ioType: '2',
          entranceLastDate: '2999/11/12 11:12:13',
          exitLastDate: '2999/11/12 12:22:23'
        }
      ];

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;

    // 建物情報一覧取得
    case '/room-watch/v1.0/buildingInfo/selectList':

      resultInfo = [
        {
          buildingId: 'B0001',
          buildingName: '建物Ａ'
        },
        {
          buildingId: 'B0002',
          buildingName: '建物Ｂ' 
        },
        {
          buildingId: 'B0003',
          buildingName: '建物Ｃ'
        },
        {
          buildingId: 'B0004',
          buildingName: '建物Ｄ'
        },
        {
          buildingId: 'B0005',
          buildingName: '建物Ｅ'
        }
      ];

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;

    // 部屋情報一覧取得
    case '/room-watch/v1.0/roomInfo/selectList':

      resultInfo = [
        {
          roomId: 'R0011',
          roomName: '部屋Ａ１'
        },
        {
          roomId: 'R0012',
          roomName: '部屋Ａ２'
        },
        {
          roomId: 'R0021',
          roomName: '部屋Ｂ１'
        },
        {
          roomId: 'R0022',
          roomName: '部屋Ｂ２'
        },
        {
          roomId: 'R0023',
          roomName: '部屋Ｂ３'
        },
        {
          roomId: 'R0031',
          roomName: '部屋Ｃ１'
        }
      ];

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;

    // 部署情報一覧取得
    case '/room-watch/v1.0/sectionInfo/selectList':

      resultInfo = [
        {
          sectionId: 'S0001',
          sectionName: '総務部'
        },
        {
          sectionId: 'S0002',
          sectionName: '営業部'
        },
        {
          sectionId: 'S0003',
          sectionName: '開発部'
        },
        {
          sectionId: 'S0004',
          sectionName: '製造部'
        }
      ];

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;


    // 個人情報取得
    case '/room-watch/v1.0/personInfo/select':

      resultInfo = 
        {
          personId: 'P0001',
          personName: '山田太郎',
          frontImageFilePath: '/rio/auth/P0001/front.png',
          leftImageFilePath: '/rio/auth/P0001/left.png',
          rightImageFilePath: ''
//          rightImageFilePath: '/rio/auth/P0001/right.png'
        }
      ;

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;

    // 個人情報更新
    case '/room-watch/v1.0/personInfo/update':

      resultInfo = true;

      responseData = {resultCode, errorInfoList, resultInfo};
      responseJson(res, responseData);
      break;

    default:
      console.log('no match');
      responseJson(res, {message: 'no match'});
      break;
  
  }
}

