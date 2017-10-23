
config.module.rules.push(
    { test: /\.css$/, loader: "style-loader!css-loader" }
);

/*
config.module.rules.push(
    {
        test: /\.css$/,
        loader: 'file-loader',
        options: {
            name: '[name].css'
        }

    }
);
*/
