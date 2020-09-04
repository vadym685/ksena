connectorInit = function () {
    var connector = this;
    var element = connector.getElement();
    element.innerHTML =
               "<div id=\"app\">\n" +
                       "    <v-app id=\"inspire\">\n" +
                       "\n" +
                       "        <div v-for=\"item in items\" style=\"padding: 3px;\">\n" +
                       "            <!--            <v-card width=\"100%\" :color=item.cardColor style=\"padding: 8px;\">-->\n" +
                       "            <v-card width=\"100%\">\n" +
                       "\n" +
                       "                <v-card-title v-text=\"item.title\"></v-card-title>\n" +
                       "                <v-card-subtitle>{{changeTimeFormate(item.time)}}</v-card-subtitle>\n" +
                       "                <v-card-actions>\n" +
                       "                    <v-btn\n" +
                       "                            icon\n" +
                       "                            @click=\" item.show = ! item.show\"\n" +
                       "                    >\n" +
                       "                        <v-icon>{{ item.show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>\n" +
                       "                    </v-btn>\n" +
                       "                    <v-spacer></v-spacer>\n" +
                       "                    <v-btn text style=\"font-size: 22px ; background-color: #afe034; color: white\"\n" +
                       "                           @click=onOkBtnClick(item)\n" +
                       "                           v-show=\"!item.isWatched\"\n" +
                       "                    >\n" +
                       "                        OK\n" +
                       "                    </v-btn>\n" +
                       "                </v-card-actions>\n" +
                       "                <v-expand-transition>\n" +
                       "                    <div v-show=\"item.show\">\n" +
                       "                        <v-divider></v-divider>\n" +
                       "\n" +
                       "                        <v-card-text v-text=\"item.description\" style=\"font-size: 16px\"></v-card-text>\n" +
                       "                    </div>\n" +
                       "                </v-expand-transition>\n" +
                       "            </v-card>\n" +
                       "            <!--            </v-card>-->\n" +
                       "        </div>\n" +
                       "\n" +
                       "    </v-app>\n" +
                       "</div>";

    var app = new Vue({
                    el: '#app',
                    vuetify: new Vuetify(),
                    data: {
                      items: [],
                    },
                    methods: {
                        changeTimeFormate: function (time) {
                          var format = 'DD-MM-YYYY, HH:mm:ss';
                          return moment.unix(time).format(format);
                        },
                        onOkBtnClick: function(item){
//                          alert( item.title + item.description );
                           item.isWatched = ! item.isWatched
                          connector.onOkBtnClick(item.title);
                            console.log("onOkBtnClick")
                        }
                    },
                  })

// Handle changes from the server-side
    connector.onStateChange = function () {
        var state = connector.getState();
        var data = state.data;

        console.log("Information state")
        console.log(state)
        console.log("Information data")
        console.log(data)
        console.log("Information items",data.items)

        app.items = data.items;

        console.log("Information app items",app.items);


    };

//    connector
};