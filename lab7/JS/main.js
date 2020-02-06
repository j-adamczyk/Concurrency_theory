let waterfall = require("async/waterfall");
const {performance} = require('perf_hooks');
const fs = require('fs');

let forkWait = 0;
let condWait = 0;
if (!fs.existsSync("./logs"))
    fs.mkdirSync("./logs");
var naiveStream = fs.createWriteStream('logs/naiveData.log', {'flags': 'a'});
var asymmetricalStream = fs.createWriteStream('logs/asymmetricalData.log', {'flags': 'a'});
var waiterStream = fs.createWriteStream('logs/waiterData.log', {'flags': 'a'});

function random(a, b) {
    return Math.random() * (b - a) + a;
}

let Fork = function () {
    this.state = 0;
    return this;
};

Fork.prototype.acquire = function (cb) {
    let wait = function (waitTime, fork, cb) {
        forkWait += waitTime;
        if (fork.state === 0) {
            fork.state = 1;
            cb();
        } else {
            setTimeout(function () {
                wait(waitTime * 2, fork, cb)
            }, waitTime);
        }
    };
    let initWaitTime = 1;
    forkWait += initWaitTime;
    let fork = this;
    setTimeout(function () {
        wait(initWaitTime * 2, fork, cb)
    }, initWaitTime);
};

Fork.prototype.release = function () {
    this.state = 0;
};

let eat = function (id, fork1, fork2, cb) {
    setTimeout(function () {
        fork1.release();
        fork2.release();
        cb();
    }, 0);
};

let Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
};

Philosopher.prototype.startNaive = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    let tasks = [];

    for (let i = 0; i < count; i++) {
        tasks.push(function (cb) {
            setTimeout(cb, random(0, 5));
        });
        tasks.push(function (cb) {
            naiveStream.write('{"type":"start", "i":' + i + ', "time":' + performance.now() + '}\n');
            forks[f1].acquire(cb);
        });
        tasks.push(function (cb) {
            forks[f2].acquire(cb);
        });
        tasks.push(function (cb) {
            naiveStream.write('{"type":"stop", "i":' + i + ', "time":' + performance.now() + '}\n');
            eat(id, forks[f1], forks[f2], cb);
        });
    }

    waterfall(tasks);
};

Philosopher.prototype.startAsym = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    if (id % 2 === 1) {
        f2 = this.f1;
        f1 = this.f2;
    }

    let tasks = [];

    for (let i = 0; i < count; i++) {
        tasks.push(function (cb) {
            setTimeout(cb, random(0, 5));
        });
        tasks.push(function (cb) {
            asymmetricalStream.write('{"type":"start", "i":' + i + ', "time":' + performance.now() + '}\n');
            forks[f1].acquire(cb);
        });
        tasks.push(function (cb) {
            forks[f2].acquire(cb)
        });
        tasks.push(function (cb) {
            asymmetricalStream.write('{"type":"stop", "i":' + i + ', "time":' + performance.now() + '}\n');
            eat(id, forks[f1], forks[f2], cb);
        });
    }

    waterfall(tasks);
};

let Waiter = function (n) {
    this.state = n - 1;
    return this;
};

Waiter.prototype.acquire = function (cb) {
    let wait = function (cond, waitTime, cb) {
        condWait += waitTime;
        if (cond.state > 0)
        {
            cond.state--;
            cb();
        }
        else
        {
            setTimeout(function ()
            {
                wait(cond, waitTime * 2, cb)
            }, waitTime);
        }
    };
    let cond = this;
    let initWaitTime = 1;
    condWait += initWaitTime;
    setTimeout(function () {
        wait(cond, initWaitTime * 2, cb)
    }, initWaitTime);
};

Waiter.prototype.release = function (cb) {
    this.state++;
    cb();
};

let N = 5;
let cond = new Waiter(N);

Philosopher.prototype.startWaiter = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    let tasks = [];

    for (let i = 0; i < count; i++) {
        tasks.push(function (cb) {
            setTimeout(cb, random(0, 5));
        });
        tasks.push(function (cb) {
            cond.acquire(cb)
        });
        tasks.push(function (cb) {
            waiterStream.write('{"type":"start", "i":' + i + ', "time":' + performance.now() + '}\n');
            forks[f1].acquire(cb);
        });
        tasks.push(function (cb) {
            forks[f2].acquire(cb);
        });
        tasks.push(function (cb) {
            waiterStream.write('{"type":"stop", "i":' + i + ', "time":' + performance.now() + '}\n');
            eat(id, forks[f1], forks[f2], cb);
        });
        tasks.push(function (cb) {
            cond.release(cb)
        });
    }

    waterfall(tasks);
};

let forks = [];
let philosophers = [];
for (let i = 0; i < N; i++)
    forks.push(new Fork());

for (let i = 0; i < N; i++)
    philosophers.push(new Philosopher(i, forks));

// deadlock, do not run
//for (let i = 0; i < N; i++)
//    philosophers[i].startNaive(100);

//for (let i = 0; i < N; i++)
//    philosophers[i].startAsym(100);

for (let i = 0; i < N; i++)
    philosophers[i].startWaiter(100);
