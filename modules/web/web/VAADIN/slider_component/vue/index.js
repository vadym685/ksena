const vuetify = new Vuetify({});

var file = "./index.html";

// var fs = require("fs");
// fs.readFile(file, "utf-8", (err, data) => {
//     if (err) {
//         console.log(err)
//     }
//     console.log(data);
// })

var data = {
    settings: {
        min: 1587900000,
        max: 1587964489,
        range: [1587910000, 1587962489],
        incorrectMsg: "Date {0} is incorrect!"
    },
}

formatString = function (a, arguments) {
    for (k in arguments) {
        a = a.replace("{" + k + "}", arguments[k])
    }
    return a
}

function getTimeInString(time) {
    // console.log("getTimeInString")
    var format = 'DD-MM-YYYY, HH:mm:ss';
    var result = moment.unix(time).format(format);
    // console.log("Result string:" + result);
    return result;
}

var connector = {}

const app = new Vue({
    el: '#slider_app',
    vuetify: vuetify,
    data: data,
    methods: {
        changeMin: function (time) {
            // connector.changeMin(time);
            return getTimeInString(time);
        },
        changeMax: function (time) {
            // connector.changeMax(time);
            return getTimeInString(time);
        },
        changeValue: function (range, index) {
            // connector.changeValue(data.settings);
            var time = range[index]
            return getTimeInString(time);
        },
        stringToValue: function (index, event, oldValue) {

            var format = 'DD-MM-YYYY, HH:mm:ss';
            // console.log("stringToValue:" + event);


            if (moment(event, format).isValid()) {
                var newValue = moment(event, format).unix();
                console.log("result:" + newValue);
                data.settings.range[index] = newValue;

                if (data.settings.max < newValue) {
                    data.settings.max = newValue
                }

                if (data.settings.min > newValue) {
                    data.settings.min = newValue
                }

                console.log(data.settings);
                return newValue;
            } else {
                alert(formatString(data.settings.incorrectMsg, [event]));
                return oldValue;
            }

        },
    },
})