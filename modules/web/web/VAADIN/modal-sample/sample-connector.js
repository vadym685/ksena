com_planarry_chat_Vue = function () {
    var connector = this;
    var element = connector.getElement();
    element.innerHTML =
        "   <body>\n" +
        "     <div id=\"app\"></div>\n" +
        "   </body>"

    import Vue        from 'vue'
    import App        from './App.vue'
    import VueJsModal from 'plugin'

    Vue.use(VueJsModal, {
      dialog: true,
      dynamic: true,
      dynamicDefaults: {
        foo: 'foo'
      }
    })

    new Vue({
      el: '#app',
      render: h => h(App)
    })

};