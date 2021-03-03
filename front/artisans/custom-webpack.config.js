console.log('custom web pack config for sass compilation');

module.exports = {
    module: {
        rules: [
            {
                test: /\.scss$|\.sass$/,
                use: [
                    {
                        loader: require.resolve('fast-sass-loader'),
                        options: {
                            sourceMap: false,
                            includePaths:['src/scss']
                        },
                    },
                ],
            }
        ],
    },

};