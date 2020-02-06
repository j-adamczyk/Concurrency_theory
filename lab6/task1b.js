const async = require('async');

async.waterfall
(
    [
        function(callback)
        {
            callback(null, '1');
        },
        function(arg1,  callback)
        {
            var str = '1\n';
            str += '2\n';
            callback(null, str);
        },
        function(str, callback)
        {
            str += '3\n';
            callback(null, str);
        }
    ],
    function (err, str)
    {
        for (let i = 0; i < 4; i++)
            console.log(str);
        console.log('done');
    }
);
