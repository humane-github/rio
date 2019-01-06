<contents>

  <!--- サイドメニュー -->
  <div class="col-sm-3" style="padding-top: 1em">
    <div id="menu" class="list-group">
      <a href="#menu1" type="button" class="list-group-item list-group-item-action {active: menu === 'menu1'}"><i class="fa fa-id-card pr-3"></i>入退室履歴照会</a>
      <a href="#menu2" type="button" class="list-group-item list-group-item-action {active: menu === 'menu2'}"><i class="fa fa-id-card pr-3"></i>在室状況照会</a>
      <a href="#menu3" type="button" class="list-group-item list-group-item-action {active: menu === 'menu3'}"><i class="fa fa-id-card pr-3"></i>在室者照会</a>
      <a href="#menu4" type="button" class="list-group-item list-group-item-action {active: menu === 'menu4'}"><i class="fa fa-address-book-o pr-3"></i>ユーザ管理</a>
      <a href="#menu5" type="button" class="list-group-item list-group-item-action {active: menu === 'menu5'}"><i class="fa fa-address-book-o pr-3"></i>ユーザ新規登録</a>
    </div>
  </div>
  
  <!-- メインページ -->
  <div class="col-sm-9" >
    <page1 if={menu === 'menu1'}></page1>
    <page2 if={menu === 'menu2'}></page2>
    <page3 if={menu === 'menu3'}></page3>
    <page4 if={menu === 'menu4'}></page4>
    <page5 if={menu === 'menu5'}></page5>
  </div>
  
  <script>
    
    require('./page1/page1.tag');
    require('./page2/page2.tag');
    require('./page3/page3.tag');
    require('./page4/page4.tag');
    require('./page5/page5.tag');
    
    let self = this;
    self.menu = 'menu1';

    // ルータ設定
    let url = location.protocol + '//' + location.host + location.pathname;
    let router = new Navigo(null, true);
    router.on({
      'menu1': () => { self.menu = 'menu1'; self.update(); },
      'menu2': () => { self.menu = 'menu2'; self.update(); },
      'menu3': () => { self.menu = 'menu3'; self.update(); },
      'menu4': () => { self.menu = 'menu4'; self.update(); },
      'menu5': () => { self.menu = 'menu5'; self.update(); }
    });
    router.notFound((query) => {console.log('xxx');});
    router.resolve();

  </script>
  
</contents>
