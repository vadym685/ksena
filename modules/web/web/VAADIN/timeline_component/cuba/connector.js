connectorInit = function () {
    var connector = this;
    var element = connector.getElement();


    var appBody = "<div id=\"visualization\"></div>\n";

    element.innerHTML = appBody;

    //Добавляем кастомный стиль выбора элементов
//    var customSelectionStyle = ".vis-item.vis-selected { box-shadow: 0 0 30px black; }"
//    addStyle(customSelectionStyle);

        var now = Date.now();

        var minusDay = 0.5
        var plusDay = 0.5

        var options = {
            maxHeight: 400,
          stack: true, //Могут ли налаживаться компоненты друг на дружку - false - налаживаются
           groupEditable: true, //Можно ли драгать группы
          horizontalScroll: true,
          verticalScroll: true,
          zoomKey: "ctrlKey",
          orientation: { //Настройки осей
            axis: "top",
            item: "top",
          },

          showCurrentTime: false,
          moment: function (date) {
            return vis.moment(date).utc(); //Для показа временной линии по Гринвичу - а то смещенеие в зависимости от временной зоны
          },
          start: Date.now() - 1000 * 60 * 60 * 24 * minusDay, // minus days
          end: Date.now() + 1000 * 60 * 60 * 24 * plusDay, // plus days
        };

        var items = new vis.DataSet();
        var groups = new vis.DataSet();
        var lastSelectedItem = "";
        var windowStartTime = "";
        var windowEndTime = "";


        function isContainsStyleInHtml(styleName){
            var styletags = document.getElementsByTagName("style");

            //loop over all the style tags
            for(var i = 0; i < styletags.length; i++)
            {
              var selectedStyle = styletags[i].innerHTML;
                // console.log(styletags[i].innerHTML)
               if(selectedStyle.includes(styleName)){
                 // console.log("Contains" +styletags[i].innerHTML +"||");
                 return true;
               }
              else{
                // console.log("Not contains");
              }
            }
           return false;
        }


        // Handle changes from the server-side
        connector.onStateChange = function () {
            var state = connector.getState();
            var data = state.data;

            items = data.usesItems
             console.log("Items", items);
            groups = data.usesGroups
            lastSelectedItem = data.lastSelectedItem
            windowStartTime = data.windowStartTime
            windowEndTime = data.windowEndTime

            console.log("State data: ", data);

            items.forEach(function(item, i, arr) {
              var color = item.color

              if(color==null || color=="")
              {
                  color = getRandomColor();
              }

              var temple = '.vis-background.';
              if(item.itemType=="item"){
                temple = '.vis-item.';
              }

              var styleName = temple+'bg-'+item.id;

              var isContains = isContainsStyleInHtml(styleName);

                if(isContains){
                    console.log("Contains " +styleName+"||");
                }
                else{
                    console.log("Not contains "+styleName);
                    addStyle(styleName+'{background-color:'+color+'; color:white; font-size:14px;}')
                }

            });

            timeline.setGroups(groups);
            timeline.setItems(items);

            //Смещаем окно на последнее местоположение
            if(windowStartTime ===null || windowStartTime === "" ||windowEndTime ===null || windowEndTime === "" ){
            }
            else{
                 console.log("Set windowStartTime ", windowStartTime);
                 console.log("Set windowEndTime ", windowEndTime);
//                timeline.setWindow(windowStartTime,windowEndTime);
                    options.start = windowStartTime;
                    options.end = windowEndTime;
                    timeline.setOptions(options);
            }

            if(lastSelectedItem === null || lastSelectedItem === ""){
            }
            else{
                //Перемещаем на последний выбранный элемент
                console.log("Last selected item id for move ",lastSelectedItem)
                timeline.setSelection(lastSelectedItem);

                setTimeout(() =>{
                    moveToItem(lastSelectedItem, timeline);
                    console.log("moveToItem is end ");

                    setTimeout(() =>{
                        options.start = windowStartTime;
                        options.end = windowEndTime;
                        timeline.setOptions(options);
                    }, 600);
                }, 300);



            }
        };


        // create a Timeline
        var container = document.getElementById("visualization");
        timeline = new vis.Timeline(container);
        timeline.setOptions(options);
        timeline.setGroups(groups);
        timeline.setItems(items);


        timeline.on("click", (e) => { connector.onClick(e) });

        timeline.on("dblclick", (e) => { connector.onDoubleClick(e); });

        timeline.on('select', function (e) {
          console.log('selected items: ' + e.items);

          var itemID = e.items[0];

          if(itemID === null || itemID === ""){
            console.log("Empty object");
                timeline.setSelection(lastSelectedItem);
          }
          else{
              console.log("Object with ID "+itemID);
              lastSelectedItem = itemID
              connector.onItemClick(itemID);
          }

        });

         timeline.on("rangechanged", function (properties) {
           // console.log("rangechanged", properties);
           console.log("Save windowStartTime", properties.start);
           console.log("Save windowEndTime", properties.end);
            connector.onRangeChanged(properties.start,properties.end);
         });

        function getRandomColor() {
          return "#"+((1<<24)*Math.random()|0).toString(16);

        };

        function addStyle(styleText) {
            var style = document.createElement('style');
            style.type = 'text/css';
            style.innerHTML = styleText;
            document.getElementsByTagName('head')[0].appendChild(style);
        };


        /* Work-Around */
        // This is a quick-and-dirty animation for scrolling
        var animateScroll = function(from, to, duration, timeline) {
          var initTime = new Date().valueOf();
          //var duration = 500;
          var easingFunction = function(t) {
            return t < .5 ? 2 * t * t : -1 + (4 - 2 * t) * t
          };

          var defer = $.Deferred();

          var next = function() {
            var now = new Date().valueOf();
            var time = now - initTime;
            var ease = easingFunction(time / duration);
            var done = time > duration;
            var s = done ? to : (from + (to - from) * ease);

            timeline._setScrollTop(-s);
            timeline._redraw();

            if (!done) {
              //setTimeout(next, 20);
              window.requestAnimationFrame(next);
            } else {
              defer.resolve();
            }
          };

          next();

          return defer.promise();
        };

        var moveToItem = function(eventId, timeline, duration) {
            console.log("moveToItem:", lastSelectedItem);

          duration = 200;
          var event = timeline.itemSet.items[eventId];
          var leftHeight = timeline.props.leftContainer.height;
          var contentHeight = timeline.props.left.height;
          var alreadyVisible = false;

          if (event.displayed) {
            alreadyVisible = true;

            if (!event.selected) {
              timeline.setSelection(eventId);
            }
          }

          var groupId = event.data.group;
          var group = timeline.itemSet.groups[groupId] || {
            top: 0,
            height: 0
          }; // Use a default if we don't have a group

          var offset = group.top;
          var orientation = timeline.timeAxis.options.orientation.axis;
          var eventTop = function(event, group) {
            if (orientation == "bottom") {
              return group.height - event.top - event.height;
            } else {
              return event.top;
            }
          };
          var currentScrollHeight = timeline._getScrollTop() * -1;
          var targetOffset = offset + eventTop(event, group);
          var height = event.height;

          if (targetOffset < currentScrollHeight) {
            if (offset + leftHeight <= offset + eventTop(event, group) + height) {
              offset += eventTop(event, group) - timeline.itemSet.options.margin.item.vertical;
            }
          } else {
            if (targetOffset + height > currentScrollHeight + leftHeight) {
              offset += eventTop(event, group) + height - leftHeight + timeline.itemSet.options.margin.item.vertical;
            }
          }

          offset = Math.min(offset, contentHeight - leftHeight);

          if (targetOffset + height > currentScrollHeight + leftHeight || targetOffset < currentScrollHeight) {
            animateScroll(currentScrollHeight, offset, duration, timeline);
            timeline.setSelection(eventId);
            timeline.focus(eventId);
          }
        };

        function funForCall(){
            console.log("call a funForCall function from java !")
        }

//        function debounce(func, wait = 100) {
//          let timeout;
//          return function (...args) {
//            clearTimeout(timeout);
//            timeout = setTimeout(() => {
//              func.apply(this, args);
//            }, wait);
//          };
//        }
//
//        let groupFocus = (e) => {
//          let vGroups = timeline.getVisibleGroups();
//          let vItems = vGroups.reduce((res, groupId) => {
//            let group = timeline.itemSet.groups[groupId];
//            if (group.items) {
//              res = res.concat(Object.keys(group.items));
//            }
//            return res;
//          }, []);
//          timeline.focus(vItems);
//        };
//        this.timeline.on("scroll", debounce(groupFocus, 200));
        // Enabling the next line leads to a continuous since calling focus might scroll vertically even if it shouldn't
        // this.timeline.on("scrollSide", debounce(groupFocus, 200))

}