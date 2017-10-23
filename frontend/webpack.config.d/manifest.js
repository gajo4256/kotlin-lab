config.module.rules.push({
    test: /manifest\.json$/,
    loader: 'file-loader',
    options: {
        name: '[name].[ext]'
    }
});