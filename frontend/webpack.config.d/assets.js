config.module.rules.push(
    {
        test: [/\.(svg|html)$/],
        loader: 'file-loader',
        options: {
            name: '[name].[ext]'
        }

    },
    {
        test: /favicon\.ico$/,
        loader: 'url-loader',
        query: {
            limit: 1,
            name: '[name].[ext]',
        },
    }
);