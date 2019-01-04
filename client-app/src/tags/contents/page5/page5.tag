<page5>
  <div class="row">

    <!-- 業務タイトル -->
    <div class="col-sm-12 pt-3 pb-3">
      <h4>ユーザ新規登録</h4>
    </div>

    <!-- 登録条件 -->
    <div class="col-sm-12 searchCondition">
      <h2>登録条件</h2>
      <table>
        <tr>
          <td><label>部署名</label></td>
          <td>
            <select ref="sectionId" style="width:180px;">
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
          <td><button type="button" class="btn" onclick={regist}>登録</button></td>
        </tr>
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

    // 部署情報一覧
    self.sectionInfoList = [];

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

    // 登録ボタン押下
    self.regist = () => {

      // 部署ID
      console.log('sectionId:', self.refs.sectionId.value);
      // 利用者ID
      console.log('personId:', self.refs.personId.value);
      // 利用者名
      console.log('personName:', self.refs.personName.value);

      // リクエストデータ
      let requestData = {
          sectionId: self.refs.sectionId.value,
          personId: self.refs.personId.value,
          personName: self.refs.personName.value
      };

      // 個人情報登録
      fetch(webApiUrl + '/personInfo/regist', {
        method: 'POST',
        headers: headerData,
        body: JSON.stringify(requestData)
      }).then(response => {
        checkStatus(response);
        return response.json();
      }).then(json => {
        alert('個人情報を新規登録しました');
        console.log('処理結果:', json.resultInfo);
        self.update();
      }).catch(err => {
        alert('個人情報登録取得に失敗しました');
        console.error(err);
        return;
      });
    }

  </script>
</page5>
