const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = env => {

  let isDev = env && env.develop;
  const cssLoader = 'css-loader' + (isDev ? '' : '?minimize');
  const uglifyJsExclude = isDev ? /.*/ : /\/exclude/;
  
  return {

    context: `${__dirname}/src`,

    entry: {
      eecr: [
        "./scripts/index.js",
        "./styles/eecr.css",
        "./index.html"
      ],
      vendor: [
        "jquery",
        "popper.js",
        "bootstrap",
        "bootstrap/dist/css/bootstrap.css",
        "font-awesome/css/font-awesome.css",
        "riot",
        "navigo",
        "es6-promise-promise",
        "isomorphic-fetch",
      ]
    },
    
    output: {
      path: `${__dirname}/dist/eecr/`,
      filename: 'scripts/[name].bundle.js',
      publicPath: '/eecr/'
    },

    // 開発用サーバの設定
    devServer: {
      contentBase: 'src',
      port: 8080
    },
    
    module: {
      rules: [
        {
          test: /\.html$/,
          loader: 'file-loader?name=[path][name].[ext]'
        },
        {
          test: /\.js$/,
          exclude: /node_modules/,
          loader: 'babel-loader'
        },
        {
          test: /\.tag$/,
          exclude: /node_modules/,
          loader: 'riot-tag-loader',
          query: {
            type: 'es6', // transpile the riot tags using babel
            hot: false,
            debug: true
          }
        },
        {
          test: /\.css$/,
          loader: ExtractTextPlugin.extract({fallback: 'style-loader', use:[cssLoader, 'postcss-loader']})
        },
        {
          test: /\.(woff|woff2|eot|ttf|svg)(\?v=\d+\.\d+\.\d+)?$/,
          loader: `file-loader?name=./fonts/[name].[ext]`
        },
      ],
    },
    
    plugins: [
    
      // グローバルオブジェクトを参照可能にする
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        'window.jQuery': 'jquery',
        Popper: ['popper.js', 'default'],
        riot: 'riot',
        Navigo: 'navigo',
        Promise: 'es6-promise-promise',
        fetch: 'isomorphic-fetch'
        
      }),
      
      // CSSを1ファイルに統合する
      new ExtractTextPlugin({
        filename: `./styles/[name].css`
      }),

      // javascriptをminifyする
      new webpack.optimize.UglifyJsPlugin({
        exclude: uglifyJsExclude,
        uglifyOptions: {
          beautify: false,
        }
      }),

      // 共通で読み込むライブラリはvendorとする
      new webpack.optimize.CommonsChunkPlugin({
        name: "vendor",
        minChunks: Infinity,
      }),
      
    ],
    
    // ソースマップ
    devtool: 'inline-source-map',

  };
};