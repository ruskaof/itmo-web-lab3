const path = require('path');

module.exports = {
    entry: './src/main/webapp/resources/src/js/index.js',
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, "src/main/webapp/resources/target"),
    },
    module: {
        rules: [
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
        ],
    },
};