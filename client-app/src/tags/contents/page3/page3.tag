<page3>
  <div class="row">

    <!-- 業務タイトル -->
    <div class="col-sm-12 pt-3 pb-3">
      <h4>在室者照会</h4>
    </div>

    <!-- 検索条件 -->
    <div class="col-sm-12 searchCondition">
      <h2>検索条件</h2>
      <table>
        <tr>
          <td><label style="width:80px;">建物名</label></td>
          <td>
            <select ref="buildingId" style="width:180px;" onchange={updateRoomInfoList}>
              <option value="">すべて</option>
              <option each={ buildingInfo in buildingInfoList } value={buildingInfo.buildingId}>
                  {buildingInfo.buildingName}</option>
            </select>
          </td>
        </tr>
        <tr>
          <td><label>部屋名</label></td>
           <td>
            <select ref="roomId" style="width:180px;">
              <option value="">すべて</option>
              <option each={ roomInfo in roomInfoList } value={roomInfo.roomId}>
                  {roomInfo.roomName}</option>
            </select>
          </td>
        </tr>
        <tr>
          <td><label>利用者ID</label></td>
          <td><input type="text" ref="personId" size="10" maxlength="10"></td>
        </tr>
        <tr>
          <td><label>利用者名</label></td>
          <td><input type="text" ref="personName" size="25" maxlength="20"></td>
        </tr>
        <tr>
<!-- TODO：page2から遷移したときは表示、ボタン押下時はpage2の引き渡し項目をセットして戻れるようにしたい -->
<!--
          <td><button type="button" class="backBtn" onclick={pageback}>&lt;&nbsp;戻る</button></td>
          <td><a href="#menu2/xxx" class="backBtn">&lt;&nbsp;戻る</a></td>
-->
          <td></td>
          <td><button type="button" class="btn" onclick={search}>検索</button></td>
        </tr>
      </table>
    </div>

    <!-- 検索結果 -->
    <div class="col-sm-12 searchResult" show={isDispSearchResult}>
      <h2>在室者一覧</h2>
      <table>
        <thead>
          <tr>
            <th>No</th>
            <th>部署名</th>
            <th>利用者ID</th>
            <th>利用者名</th>
            <th>在室有無</th>
            <th>最終入室日時</th>
            <th>最終退室日時</th>
          </tr>
        </thead>
        <tbody>
          <tr each={ searchResultInfo, i in searchResultInfoList }>
            <td scope="row">{i+1}</td>
            <td>{searchResultInfo.sectionName}</td>
            <td>{searchResultInfo.personId}</td>
            <td>{searchResultInfo.personName}</td>
            <td>{getIotypeValue(searchResultInfo.ioType)}</td>
            <td>{searchResultInfo.entranceLastDate}</td>
            <td>{searchResultInfo.exitLastDate}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  
  <script>
    let self = this;

    let webApiUrl = 'http://192.168.0.90/rio/mng/room-watch/v1.0';

    // ヘッダデータ
    let headerData = new Headers();
    headerData.append('Content-Type', 'application/json');

    // 検索結果表示有無
    self.isDispSearchResult = false;
    // 建物情報一覧
    self.buildingInfoList = [];
    // 部屋情報一覧
    self.roomInfoList = [];
    // 検索結果一覧
    self.searchResultInfoList = [];

    // ステータスチェック
    function checkStatus(response) {
      if (!response.ok) {
        return response.json().then(err => {
          throw Error(err.message);
        });
      } else {
        return response;
      }
    }

    // 初期表示
    {
      // 建物情報一覧取得
      fetch(webApiUrl + '/buildingInfo/selectList', {
        method: 'POST',
        headers: headerData
      }).then(response => {
        checkStatus(response);
        return response.json();
      }).then(json => {
        self.buildingInfoList = json.resultInfo;
        self.update();
      }).catch(err => {
        alert('建物情報一覧取得に失敗しました');
        console.error(err);
        return;
      });
    }

    // 在室有無取得
    self.getIotypeValue = (ioType) => {
      let value = '';
      switch(ioType) {
        case '1':
          value = '在室中';
          break;
        case '2':
          value = '退室済';
          break;
        default:
          break;
      }
      return value;
    }

    // 部屋情報一覧更新
    self.updateRoomInfoList = () => {

      // 建物ID
      let buildingId = self.refs.buildingId.value;
      console.log('buildingId:', buildingId);

      // 建物名の選択が「すべて」になった場合は部屋情報一覧クリア
      if(buildingId == '') {
        self.roomInfoList = [];
        return;
      }

      // リクエストデータ
      let requestData = {
          buildingId: buildingId
      };

      // 部屋情報一覧取得
      fetch(webApiUrl + '/roomInfo/selectList', {
        method: 'POST',
        headers: headerData,
        body: JSON.stringify(requestData)
      }).then(response => {
        checkStatus(response);
        return response.json();
      }).then(json => {
        self.roomInfoList = json.resultInfo;
        self.update();
      }).catch(err => {
        alert('部屋情報一覧取得に失敗しました');
        console.error(err);
        return;
      });
    }

    // 戻るボタン押下
    self.pageback = () => {
      console.log('戻るボタン押下');
    }

    // 検索ボタン押下
    self.search = () => {

      // 建物ID
      console.log('buildingId:', self.refs.buildingId.value);
      // 部屋ID
      console.log('roomId:', self.refs.roomId.value);
      // 利用者ID
      console.log('personId:', self.refs.personId.value);
      // 利用者名
      console.log('personName:', self.refs.personName.value);

      // リクエストデータ
      let requestData = {
          buildingId: self.refs.buildingId.value,
          roomId: self.refs.roomId.value,
          personId: self.refs.personId.value,
          personName: self.refs.personName.value
      };

      // 入室者詳細情報一覧取得
      fetch(webApiUrl + '/entrancePersonDetailInfo/selectList', {
        method: 'POST',
        headers: headerData,
        body: JSON.stringify(requestData)
      }).then(response => {
        checkStatus(response);
        return response.json();
      }).then(json => {
        self.searchResultInfoList = json.resultInfo;
        self.isDispSearchResult = true;
        self.update();
      }).catch(err => {
        alert('入室者詳細情報一覧取得に失敗しました');
        console.error(err);
        return;
      });
    }

  </script>
</page3>
