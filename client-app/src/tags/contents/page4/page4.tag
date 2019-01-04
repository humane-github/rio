<page4>
  <div class="row">

    <!-- 業務タイトル -->
    <div class="col-sm-12 pt-3 pb-3">
      <h4>ユーザ管理</h4>
    </div>

    <!-- 検索条件 -->
    <div class="col-sm-12 searchCondition">
      <h2>検索条件</h2>
      <table>
        <tr>
          <td><label style="width:80px;">利用者ID</label></td>
          <td><input type="text" ref="personId" size="10" maxlength="10"></td>
        </tr>
        <tr>
          <td></td>
          <td><button type="button" class="btn" onclick={search}>検索</button></td>
        </tr>
      </table>
    </div>

    <!-- 検索結果 -->
    <div class="col-sm-12 userEdit" show={isDispSearchResult}>
      <h2>ユーザ編集</h2>
      <table>
        <tr>
          <td><label style="width:80px;">利用者名</label></td>
          <td><input type="text" ref="personName" size="25" maxlength="20"></td>
          <td></td>
          <td></td>
        </tr>

        <tr show={isRead1}>
          <td><label>正面画像</label></td>
          <td><a href={registeredFilePath1} target="_blank">正面画像ファイル.png</a></td>
          <td><button type="button" class="editBtn" onclick={change1}>変更</button></td>
          <td></td>
        </tr>
        <tr show={isEntry1}>
          <td><label>正面画像</label></td>
          <td><input type="file" id="upload1" ref="entry1"></td>
          <td><button type="button" class="editBtn" onclick={complete1}>確定</button></td>
          <td><label>ファイルを選択して確定ボタンを押下して下さい。</label></td>
        </tr> 
        <tr show={isComplete1}>
          <td><label>正面画像</label></td>
          <td><label>正面画像ファイル.png</label></td>
          <td><button type="button" class="editBtn" onclick={cancel1}>取消</button></td>
          <td><label>保存ボタンを押下すると対象のファイルを登録します。</label></td>
        </tr>

        <tr show={isRead2}>
          <td><label>左側画像</label></td>
          <td><a href={registeredFilePath2} target="_blank">左側画像ファイル.png</a></td>
          <td><button type="button" class="editBtn" onclick={change2}>変更</button></td>
          <td></td>
        </tr>
        <tr show={isEntry2}>
          <td><label>左側画像</label></td>
          <td><input type="file" id="upload2" ref="entry2"></td>
          <td><button type="button" class="editBtn" onclick={complete2}>確定</button></td>
          <td><label>ファイルを選択して確定ボタンを押下して下さい。</label></td>
        </tr>
        <tr show={isComplete2}>
          <td><label>左側画像</label></td>
          <td><label>左側画像ファイル.png</label></td>
          <td><button type="button" class="editBtn" onclick={cancel2}>取消</button></td>
          <td><label>保存ボタンを押下すると対象のファイルを登録します。</label></td>
        </tr>

        <tr show={isRead3}>
          <td><label>右側画像</label></td>
          <td><a href={registeredFilePath3} target="_blank">右側画像ファイル.png</a></td>
          <td><button type="button" class="editBtn" onclick={change3}>変更</button></td>
          <td></td>
        </tr>
        <tr show={isEntry3}>
          <td><label>右側画像</label></td>
          <td><input type="file" id="upload3" ref="entry3"></td>
          <td><button type="button" class="editBtn" onclick={complete3}>確定</button></td>
          <td><label>ファイルを選択して確定ボタンを押下して下さい。</label></td>
        </tr>
        <tr show={isComplete3}>
          <td><label>右側画像</label></td>
          <td><label>右側画像ファイル.png</label></td>
          <td><button type="button" class="editBtn" onclick={cancel3}>取消</button></td>
          <td><label>保存ボタンを押下すると対象のファイルを登録します。</label></td>
        </tr>

        <tr>
          <td></td>
          <td></td>
          <td><button type="button" class="saveBtn" onclick={save}>保存</button></td>
        </tr>
      </table>
    </div>
  </div>
  
  <script>
    let self = this;

    let webApiUrl = 'http://192.168.0.90/rio/mng/room-watch/v1.0';

    // 検索結果表示有無
    self.isDispSearchResult = false;

    // 登録済み正面画像
    self.isRegistered1 = false;
    self.registeredFilePath1 = '';

    // 登録済み左側画像
    self.isRegistered2 = false;
    self.registeredFilePath2 = '';

    // 登録済み右側画像
    self.isRegistered3 = false;
    self.registeredFilePath3 = '';

    // 正面画像アップロード表示有無
    self.isRead1 = false;
    self.isEntry1 = false;
    self.isComplete1 = false;

    // 左側画像アップロード表示有無
    self.isRead2 = false;
    self.isEntry2 = false;
    self.isComplete2 = false;

    // 右側画像アップロード表示有無
    self.isRead3 = false;
    self.isEntry3 = false;
    self.isComplete3 = false;

    // 検索結果一覧
    self.searchResultInfo = {
        personId: '',
        personName: '',
        frontImageFilePath: '',
        leftImageFilePath: '',
        rightImageFilePath: ''
    };

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

    // 検索結果初期表示
    self.setDispSearchResult = () =>  {
      // 検索0件の場合はエラーメッセージを出力し、検索結果表示無にして戻る
      if (self.searchResultInfo == null) {
        self.isDispSearchResult = false;
        alert('指定した利用者IDのユーザは見つかりませんでした');
        return;
      }

      // 検索結果表示有無
      self.isDispSearchResult = true;
      // 利用者名
      self.refs.personName.value = self.searchResultInfo.personName;
      // 画像登録済み有無設定
      self.setIsRegisteredImage();
      // 正面画像アップロード初期表示有無設定
      self.refs.entry1.value = '';
      self.setInitDispUpload1();
      // 左側画像アップロード初期表示有無設定
      self.refs.entry2.value = '';
      self.setInitDispUpload2();
      // 右側画像アップロード初期表示有無設定
      self.refs.entry3.value = '';
      self.setInitDispUpload3();
    }

    // 登録済み画像設定
    self.setIsRegisteredImage = () =>  {
      // 正面画像
      self.isRegistered1 = false;
      self.registeredFilePath1 = '';
      if (self.searchResultInfo.frontImageFilePath != null && self.searchResultInfo.frontImageFilePath != '') {
        self.isRegistered1 = true;
        self.registeredFilePath1 = 'http://192.168.0.90' + self.searchResultInfo.frontImageFilePath;
      }
      // 左側画像
      self.isRegistered2 = false;
      self.registeredFilePath2 = '';
      if (self.searchResultInfo.leftImageFilePath != null && self.searchResultInfo.leftImageFilePath != '') {
        self.isRegistered2 = true;
        self.registeredFilePath2 = 'http://192.168.0.90' + self.searchResultInfo.leftImageFilePath;
      }
      // 右側画像
      self.isRegistered3 = false;
      self.registeredFilePath3 = '';
      if (self.searchResultInfo.rightImageFilePath != null && self.searchResultInfo.rightImageFilePath != '') {
        self.isRegistered3 = true;
        self.registeredFilePath3 = 'http://192.168.0.90' + self.searchResultInfo.rightImageFilePath;
      }
    }

    // 正面画像アップロード初期表示有無設定
    self.setInitDispUpload1 = () =>  {
      if (self.isRegistered1) {
        self.isRead1 = true;
        self.isEntry1 = false;
        self.isComplete1 = false;
      }
      else {
        self.isRead1 = false;
        self.isEntry1 = true;
        self.isComplete1 = false;
      }
    }

    // 左側画像アップロード初期表示有無設定
    self.setInitDispUpload2 = () =>  {
      if (self.isRegistered2) {
        self.isRead2 = true;
        self.isEntry2 = false;
        self.isComplete2 = false;
      }
      else {
        self.isRead2 = false;
        self.isEntry2 = true;
        self.isComplete2 = false;
      }
    }

    // 右側画像アップロード初期表示有無設定
    self.setInitDispUpload3 = () =>  {
      if (self.isRegistered3) {
        self.isRead3 = true;
        self.isEntry3 = false;
        self.isComplete3 = false;
      }
      else {
        self.isRead3 = false;
        self.isEntry3 = true;
        self.isComplete3 = false;
      }
    }

    // 検索ボタン押下
    self.search = () => {

      // 利用者ID
      console.log('personId:', self.refs.personId.value);

      // ヘッダデータ
      let headerData = new Headers();
      headerData.append('Content-Type', 'application/json');

      // リクエストデータ
      let requestData = {
          personId: self.refs.personId.value
      };

      // 個人情報取得
      fetch(webApiUrl + '/personInfo/select', {
        method: 'POST',
        headers: headerData,
        body: JSON.stringify(requestData)
      }).then(response => {
        checkStatus(response);
        return response.json();
      }).then(json => {
        self.searchResultInfo = json.resultInfo;
        self.setDispSearchResult();
        self.update();
      }).catch(err => {
        alert('個人情報取得に失敗しました');
        console.error(err);
        return;
      });
    }

    // 正側画像　確定／変更／取消ボタン押下
    self.complete1 = () => {
      if (self.refs.entry1.value == '') {
        alert('ファイルを選択してください');
      }
      else {
        self.isRead1 = false;
        self.isEntry1 = false;
        self.isComplete1 = true;
      }
    }
    self.change1 = () => {
      self.isRead1 = false;
      self.isEntry1 = true;
      self.isComplete1 = false;
    }
    self.cancel1 = () => {
      self.setInitDispUpload1();
    }

    // 左側画像　確定／変更／取消ボタン押下
    self.complete2 = () => {
      if (self.refs.entry2.value == '') {
        alert('ファイルを選択してください');
      }
      else {
        self.isRead2 = false;
        self.isEntry2 = false;
        self.isComplete2 = true;
      }
    }
    self.change2 = () => {
      self.isRead2 = false;
      self.isEntry2 = true;
      self.isComplete2 = false;
    }
    self.cancel2 = () => {
      self.setInitDispUpload2();
    }

    // 右側画像　確定／変更／取消ボタン押下
    self.complete3 = () => {
      if (self.refs.entry3.value == '') {
        alert('ファイルを選択してください');
      }
      else {
        self.isRead3 = false;
        self.isEntry3 = false;
        self.isComplete3 = true;
      }
    }
    self.change3 = () => {
      self.isRead3 = false;
      self.isEntry3 = true;
      self.isComplete3 = false;
    }
    self.cancel3 = () => {
      self.setInitDispUpload3();
    }

    // 保存ボタン押下
    self.save = () => {

      // アップロードファイルデータ取得
      let fileField1 = document.querySelector('#upload1');
      let fileField2 = document.querySelector('#upload2');
      let fileField3 = document.querySelector('#upload3');

      // 利用者ID
      console.log('personId:', self.refs.personId.value);
      // 利用者名
      console.log('personName:', self.refs.personName.value);
      // 正面画像
      console.log('正面画像:', self.refs.entry1.value);
      // 左側画像
      console.log('左側画像:', self.refs.entry2.value);
      // 右側画像
      console.log('右側画像:', self.refs.entry3.value);

      // ヘッダデータ
      let headerData = new Headers();

      // フォームデータ
      let formData = new FormData();
      formData.append('personId', self.refs.personId.value);
      formData.append('personName', self.refs.personName.value);
      if (self.isComplete1) {
        formData.append('frontImageFile', fileField1.files[0]);
      }
      if (self.isComplete2) {
        formData.append('leftImageFile', fileField2.files[0]);
      }
      if (self.isComplete3) {
        formData.append('rightImageFile', fileField3.files[0]);
      }

      // 個人情報更新
      fetch(webApiUrl + '/personInfo/update', {
        method: 'POST',
        headers: headerData,
        body: formData
      }).then(response => {
        checkStatus(response);
        return response.json();
      }).then(json => {
        self.isDispSearchResult = false;
        alert('個人情報を更新しました');
        console.log('処理結果:', json.resultInfo);
        self.update();
      }).catch(err => {
        self.isDispSearchResult = false;
        alert('個人情報更新に失敗しました');
        console.error(err);
        self.update();
        return;
      });
    }

  </script>
</page4>
