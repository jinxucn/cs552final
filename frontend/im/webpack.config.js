/*
 * @Author: Jin X
 * @Date: 2020-12-12 16:25:09
 * @LastEditTime: 2020-12-13 20:22:03
 */
const HtmlWebpackPlugin = require("html-webpack-plugin");
const path = require("path");

module.exports = {
    entry: "./src/index.js",
    mode: "production",
    output: {
        path: path.resolve(__dirname, "dist"),
        filename: "bundle.js",
    },

    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: "babel-loader",
            },
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"],
            },
        ],
    },
    plugins: [new HtmlWebpackPlugin({ template: "./public/index.html" })],
    devServer: {
        contentBase: path.resolve(__dirname, "dist"),
        quiet: false,
        noInfo: false,
        hot: true,
        inline: true,
        lazy: false,
        progress: true,
        // compress: true,
        port: 9000,
    },
};
