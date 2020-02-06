const fs = require('fs');


var naiveData = fs.readFileSync('logs/naiveData.log', 'utf-8').split('\n');
var asymmetricalData = fs.readFileSync('logs/asymmetricalData.log', 'utf-8').split('\n');
var waiterData = fs.readFileSync('logs/waiterData.log', 'utf-8').split('\n');
naiveData.pop();
asymmetricalData.pop();
waiterData.pop();

function compare(a, b) {
    if (a.i == b.i) {
        if (a.type == "start")
            return 1;
        return -1;
    }
    return (a.i > b.i) ? 1 : -1;
}

function prepareFile(data, filename) {
    if (!fs.existsSync("./imls"))
        fs.mkdirSync("./imls");
    var outputStream = fs.createWriteStream("imls/" + filename + '.iml', {'flags': 'a'});
    for (var i in data) {
        var str = data[i];
        data[i] = JSON.parse(str);
    }
    data.sort(compare);
    var sum = 0;
    var count = 0;
    var lindex = 0;
    for (var i in data) {
        var object = data[i];
        if (lindex != object.i) {
            outputStream.write(Math.floor(sum / count) + '\n');
            sum = 0;
            count = 0;
            lindex = object.i;
        }
        if (object.type == "stop")
            sum += Math.floor(object.time * 1000000);
        else if (object.type == "start")
            sum -= Math.floor(object.time * 1000000);
        count++;
    }
    outputStream.write(Math.floor(sum / count) + '\n');
}

prepareFile(naiveData, "naiveResults");
prepareFile(asymmetricalData, "asymmetricalResults");
prepareFile(waiterData, "waiterResults");