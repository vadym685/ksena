connectorInit = function () {
    var connector = this;
    var element = connector.getElement();

    var appBody = "<div id=\"slider_app\">\n" +
        "    <v-app id=\"inspire\" >\n" +
        "    <v-container>\n" +
        "                        <v-range-slider\n" +
        "                                v-model=\"settings.range\"\n" +
        "                                :max=\"settings.max\"\n" +
        "                                :min=\"settings.min\"\n" +
        "                                hide-details\n" +
        "                                class=\"align-center\"\n" +
        "                        >\n" +
        "                            <template v-slot:prepend>\n" +
        "                                <v-text-field\n" +
        "                                        :value=\"changeValue(settings.range,0)\"\n" +
        "                                        class=\"mt-0 pt-0\"\n" +
        "                                        hide-details\n" +
        "                                        single-line\n" +
        "                                        outlined\n" +
        "                                        type=\"string\"\n" +
        "                                        style=\"width: 180px\"\n" +
        "                                        @change=\"$set(settings.range, 0, stringToValue( 0, $event,settings.range[0]))\"\n" +
        "                                ></v-text-field>\n" +
        "                            </template>\n" +
        "                            <template v-slot:append>\n" +
        "                                <v-text-field\n" +
        "                                        :value=\"changeValue(settings.range,1)\"\n" +
        "                                        class=\"mt-0 pt-0\"\n" +
        "                                        hide-details\n" +
        "                                        single-line\n" +
        "                                        outlined\n" +
        "                                        type=\"string\"\n" +
        "                                        style=\"width: 180px\"\n" +
        "                                        @change=\"$set(settings.range, 1, stringToValue( 1, $event,settings.range[1]))\"\n" +
        "                                ></v-text-field>\n" +
        "                            </template>\n" +
        "                        </v-range-slider>\n" +
        "     </v-container>\n" +
        "    </v-app>\n" +
        "</div>";

    element.innerHTML = appBody;

    const vuetify = new Vuetify({});


    var data = {
        settings: {
            min: null,
            max: null,
            range: [null, null],
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

    const app = new Vue({
        el: '#slider_app',
        vuetify: vuetify,
        data: data,
        methods: {
            changeMin: function (time) {
                connector.changeMin(time);
                return getTimeInString(time);
            },
            changeMax: function (time) {
                connector.changeMax(time);
                return getTimeInString(time);
            },
            changeValue: function (range, index) {
                connector.changeValue(data.settings);
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

    // Handle changes from the server-side
    connector.onStateChange = function () {
        var state = connector.getState();
        var data = state.data;

//         console.log("Information state")
//         console.log(state)
//         console.log("Information data")
//         console.log(data)
//         console.log("Information settings")
//         console.log(data.settings)

        app.settings = data.settings

        console.log("Information app data", app.settings);


    };

};