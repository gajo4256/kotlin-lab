
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

config.module.rules.push(
    {
        test: /\.svg$/,
        loader: 'file-loader',
        options: {
            name: '[name].svg'
        }

    }
);

config.module.rules.push(
    {
        test: /\.html$/,
        loader: 'file-loader',
        options: {
            name: '[name].html'
        }
    }
);
