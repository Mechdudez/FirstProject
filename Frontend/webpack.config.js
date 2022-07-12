const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');


module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    reviewPage: path.resolve(__dirname, 'src', 'pages', 'reviewPage.js'),
    restaurantPage: path.resolve(__dirname, 'src', 'pages', 'restaurantPage.js'),
    loginPage: path.resolve(__dirname, 'src', 'pages', 'loginPage.js')
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,

    port: 8080,

    open: true,

    openPage: 'http://localhost:8080/restaurant.html',

    // disableHostChecks, otherwise we get an error about headers and the page won't render

    disableHostCheck: true,

    contentBase: 'packaging_additional_published_artifacts',

    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings

    overlay: true,

    proxy: [

      {

        context: [

          '/restaurant',
          '/login',
          '/review'

        ],

        target: 'http://localhost:5001'

      }

    ]},
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/review.html',
      filename: 'review.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/restaurant.html',
      filename: 'restaurant.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/login.html',
      filename: 'login.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
