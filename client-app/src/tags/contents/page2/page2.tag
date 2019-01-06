<page2>
  <div class="row">

    <!-- 業務タイトル -->
    <div class="col-sm-12 pt-3 pb-3">
      <h4>在室状況照会</h4>
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
          <td></td>
          <td><button type="button" class="btn" onclick={search}>検索</button></td>
        </tr>
      </table>
    </div>

    <!-- 検索結果 -->
    <div class="col-sm-12 searchResult" show={isDispSearchResult}>
      <h2>在室状況一覧</h2>
      <table>
        <thead>
          <tr>
            <th>No</th>
            <th>部屋ID</th>
            <th>建物名</th>
            <th>部屋名</th>
            <th>在室人数</th>
            <th>照会</th>
          </tr>
        </thead>
        <tbody>
          <tr each={ searchResultInfo, i in searchResultInfoList }>
            <td scope="row">{i+1}</td>
            <td>{searchResultInfo.roomId}</td>
            <td>{searchResultInfo.buildingName}</td>
            <td>{searchResultInfo.roomName}</td>
            <td>{searchResultInfo.entrancePersonNum}</td>
<!-- TODO：建物IDと部屋名をセットしてpage3へ遷移できるようにしたい -->
<!--
            <td><button type="button" class="btn" onclick={pageNext.bind(this, searchResultInfo.buildingId, searchResultInfo.roomId)}>詳細</button></td>
            <td><a href="#menu3/{searchResultInfo.buildingId}/{searchResultInfo.roomId}" class="btn">詳細</a></td>
-->
            <td></td>
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
        return response.json();
      }).then(json => {
        self.roomInfoList = json.resultInfo;
        self.update();
      }).catch(err => {
        alert('建物情報一覧取得に失敗しました');
        console.error(err);
        return;
      });
    }

    // 検索ボタン押下
    self.search = () => {

      // 建物ID
      console.log('buildingId:', self.refs.buildingId.value);
      // 部屋ID
      console.log('roomId:', self.refs.roomId.value);

      // リクエストデータ
      let requestData = {
          buildingId: self.refs.buildingId.value,
          roomId: self.refs.roomId.value
      };

      // 入室者情報一覧取得
      fetch(webApiUrl + '/entrancePersonInfo/selectList', {
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
        alert('入室者情報一覧取得に失敗しました');
        console.error(err);
        return;
      });
    }

    // 詳細ボタン押下
    self.pageNext = (buildingId, roomId) => {
      console.log('buildingId:', buildingId);
      console.log('roomId:', roomId);
      riot.mount('#menu4');
    }

  </script>
</page2>
