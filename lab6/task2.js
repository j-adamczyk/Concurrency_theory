let counter = 0;

function printAsync(s, cb)
{
    const delay = Math.floor((Math.random() * 1000) + 500);
    setTimeout(
        function()
        {
            console.log(s);
            if (cb)
                cb();
        },
        delay);
}

function inparallel(parallel_functions, final_function) {
    for (let f of parallel_functions)
    {
        f(function ()
        {
            counter++;
            if (counter === parallel_functions.length)
                final_function()
        });
    }
}

A = function(cb){ printAsync("A", cb); };
B = function(cb){ printAsync("B", cb); };
C = function(cb){ printAsync("C", cb); };
Done = function(cb){ printAsync("Done", cb); };

inparallel([A, B, C], Done);