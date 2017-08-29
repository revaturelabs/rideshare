const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const webpack = require('webpack');

let source = path.join(__dirname, 'src', 'main', 'webapp', 'static');
let output = path.join(__dirname, 'src', 'main', 'resources', 'static');

module.exports = (env = {}) => {
  let config = {
    context: source,
    target: 'web',
    // devtool: 'source-map',
    devServer: {
      publicPath: '/',
      contentBase: [ output ]
    },
    resolve: {
      extensions: [ '.js' ],
      modules: [ './node_modules' ]
    },
    resolveLoader: {
      modules: [ './node_modules' ]
    },
    entry: {
      app: [ './app.js' ]
    },
    output: {
      path: output,
      filename: '[name].bundle.js'
    },
    module: {
      rules: [
        {
          test: /\.js$/,
          exclude: [ /\/node_modules\// ],
          use: {
            loader: 'babel-loader',
            options: {
              presets: [ 'env' ]
            }
          }
        }
      ]
    },
    plugins: [
      new CopyWebpackPlugin(
        [
          { from: `${source}/css`, to: `${output}/css` },
          { from: `${source}/images`, to: `${output}/images` },
          { from: `${source}/partials`, to: `${output}/partials` },
          { from: `${source}/js/googleMapAPI`, to: `${output}/js/googleMapAPI` },
          { from: `${source}/js/moment.js`, to: `${output}/js/moment.js` },
          { from: `${source}/index.html`, to: `${output}/index.html` },
        ]
      ),
      new webpack.NoEmitOnErrorsPlugin()
    ]
  };
  return config;
};
