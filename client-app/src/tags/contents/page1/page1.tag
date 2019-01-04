<page1>
  <div class="row">

    <!-- 業務タイトル -->
    <div class="col-sm-12 pt-3 pb-3">
      <h4>入退室履歴照会</h4>
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
          <td><label>入退室種別</label></td>
          <td>
            <span each={ value, key in entryTypeInfoList }>
              <input name="entryType" type="radio" value={value[0]} checked={value[1]} onchange={editEntryType.bind(this,value[0])}>
              <label>{key}</label>
            </span>
          </td>
        </tr>
        <tr>
          <td><label>入退室日時</label></td>
          <td>
            <input type="text" ref="certifyDateFrom" size="15" maxlength="16">
            <label>&nbsp;～&nbsp;</label>
            <input type="text" ref="certifyDateTo" size="15" maxlength="16">
          </td>
        </tr>
        <tr>
          <td><label>部署名</label></td>
          <td>
            <select ref="sectionId" style="width:180px;">
              <option value="">すべて</option>
              <option each={ sectionInfo in sectionInfoList } value={sectionInfo.sectionId}>
                  {sectionInfo.sectionName}</option>
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
          <td></td>
          <td><button type="button" class="btn" onclick={search}>検索</button></td>
        </tr>
      </table>
    </div>

    <!-- 検索結果 -->
    <div class="col-sm-12 searchResult" show={isDispSearchResult}>
      <h2>入退室履歴一覧</h2>
      <table>
        <thead>
          <tr>
            <th>No</th>
            <th>部屋ID</th>
            <th>建物名</th>
            <th>部屋名</th>
            <th>入退室種別</th>
            <th>入退室日時</th>
            <th>認証成否</th>
            <th>部署名</th>
            <th>利用者ID</th>
            <th>利用者名</th>
            <th>認証画像</th>
          </tr>
        </thead>
        <tbody>
          <tr each={ searchResultInfo, i in searchResultInfoList }>
            <td scope="row">{i+1}</td>
            <td>{searchResultInfo.roomId}</td>
            <td>{searchResultInfo.buildingName}</td>
            <td>{searchResultInfo.roomName}</td>
            <td>{getIotypeValue(searchResultInfo.ioType)}</td>
            <td>{searchResultInfo.certifyDate}</td>
            <td>{getCertifyResultValue(searchResultInfo.certifyResult)}</td>
            <td>{searchResultInfo.sectionName}</td>
            <td>{searchResultInfo.personId}</td>
            <td>{searchResultInfo.personName}</td>
            <td><a if="{isShowCertifyImage(searchResultInfo.certifyImageFilePath)}" href="{getCertifyImageFilePathValue(searchResultInfo.certifyImageFilePath)}" class="btn" target="_blank">画像確認</a></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  
  <script>
    let self = this;
    let parent = this.parent;

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
    // 入退室種別情報一覧
    self.entryTypeInfoList = {
      'すべて': ['', 'checked'],
      '入室': ['1'],
      '退室': ['2']
    };
    // 入退室種別
    self.entryTypeValue = '';
    // 部署情報一覧
    self.sectionInfoList = [];
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
      // 部署情報一覧取得
      fetch(webApiUrl + '/sectionInfo/selectList', {
        method: 'POST',
        headers: headerData
      }).then(response => {
        checkStatus(response);
        return response.json();
      }).then(json => {
        self.sectionInfoList = json.resultInfo;
        self.update();
      }).catch(err => {
        alert('部署情報一覧取得に失敗しました');
        console.error(err);
        return;
      });
    }

    // 入退室種別取得
    self.getIotypeValue = (ioType) => {
      let value = '';
      switch(ioType) {
        case '1':
          value = '入室';
          break;
        case '2':
          value = '退室';
          break;
        default:
          break;
      }
      return value;
    }

    // 認証成否取得
    self.getCertifyResultValue = (certifyResult) => {
      let value = '×';
      if(certifyResult) {
        value = '〇';
      }
      return value;
    }

    // 認証画像リンク表示有無
    self.isShowCertifyImage = (certifyImageFilePath) => {
      let isShow = false;
      if(certifyImageFilePath != null && certifyImageFilePath != '') {
        isShow = true;
      }
      return isShow;
    }

    // 認証画像ファイルパス取得
    self.getCertifyImageFilePathValue = (certifyImageFilePath) => {
      let value = '';
      if(certifyImageFilePath != null && certifyImageFilePath != '') {
        value = 'http://192.168.0.90' + certifyImageFilePath;
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


    // 入退室種別ラジオボタン押下
    self.editEntryType = (entryType) => {
      self.entryTypeValue = entryType;
    }

    // 検索ボタン押下
    self.search = () => {

      // 建物ID
      console.log('buildingId:', self.refs.buildingId.value);
      // 部屋ID
      console.log('roomId:', self.refs.roomId.value);
      // 入退室種別
      console.log('entryTypeValue:', self.entryTypeValue);
      // 入退室日時(From)
      console.log('certifyDateFrom:', self.refs.certifyDateFrom.value);
      // 入退室日時(To)
      console.log('certifyDateTo:', self.refs.certifyDateTo.value);
      // 部署ID
      console.log('sectionId:', self.refs.sectionId.value);
      // 利用者ID
      console.log('personId:', self.refs.personId.value);
      // 利用者名
      console.log('personName:', self.refs.personName.value);

      // リクエストデータ
      let requestData = {
          buildingId: self.refs.buildingId.value,
          roomId: self.refs.roomId.value,
          ioType: self.entryTypeValue,
          certifyDateFrom: self.refs.certifyDateFrom.value,
          certifyDateTo: self.refs.certifyDateTo.value,
          sectionId: self.refs.sectionId.value,
          personId: self.refs.personId.value,
          personName: self.refs.personName.value
      };

      // 入退室履歴情報一覧取得
      fetch(webApiUrl + '/entryHistoryInfo/selectList', {
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
        alert('入退室履歴情報一覧取得に失敗しました');
        console.error(err);
        return;
      });
    }

  </script>
</page1>
