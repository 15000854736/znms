<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="description" content="">
    <meta name="author" content="">
    <%@ include file="../common/include.jsp"%>
    <link rel="stylesheet" href="<%=path %>/css/next/next-topology.css">
    <script src="<%=path %>/js/next/next-base.js"></script>
    <script src="<%=path %>/js/next/next-topology.js"></script>




</head>

<body>
<!-- 头部菜单 start-->
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/leftMenu.jsp"%>
<style type="text/css">
    .n-topology-container{
        margin-left: 170px;
    }
</style>

    <script>
        var topologyData = {
            nodes: [
                {"id": 0, "x": 0, "y": 0, "icon":"gateway","hostIp":"192.168.1.10"},
                {"id": 1, "x": 100, "y": 100,"icon":"virtual-core-switch","hostIp":"10.40.255.253"},
                {"id": 2, "x": 200, "y": 200, "icon":"ac","hostIp":"192.168.1.12"},
                {"id": 3, "x": 900, "y": 600, "icon":"ap","hostIp":"192.168.1.116"},
                {"id": 4, "x": 500, "y": 500, "icon":"core-switch","hostIp":"192.168.1.2"}
            ],
            links: [
                {"source": 0, "target": 1,"hostIp":"10.40.255.253","graphId":"9a7fbfa52869e983308185b646419013","maxBandwidth":"100M"},
                {"source": 1, "target": 2},
                {"source": 1, "target": 3},
                {"source": 4, "target": 1},
                {"source": 2, "target": 3},
                {"source": 2, "target": 0},
                {"source": 3, "target": 0},
                {"source": 0, "target": 4},
            ]
        };






        var topo;
        var app;
        (function (nx) {
            //注册图标

            nx.graphic.Icons.registerIcon("ac", "innerFile/topo-icon/z-ac.png", 40, 39);
            nx.graphic.Icons.registerIcon("ap", "innerFile/topo-icon/z-ap.png", 41, 31);
            nx.graphic.Icons.registerIcon("cache", "innerFile/topo-icon/z-cache.png", 37, 30);
            nx.graphic.Icons.registerIcon("elog", "innerFile/topo-icon/z-elog.png", 38, 26);
            nx.graphic.Icons.registerIcon("eportal", "innerFile/topo-icon/z-eportal.png", 38, 26);
            nx.graphic.Icons.registerIcon("log", "innerFile/topo-icon/z-log.png", 38, 26);
            nx.graphic.Icons.registerIcon("nms", "innerFile/topo-icon/z-nms.png", 38, 26);
            nx.graphic.Icons.registerIcon("os", "innerFile/topo-icon/z-os.png", 38, 26);
            nx.graphic.Icons.registerIcon("poe", "innerFile/topo-icon/z-poe接入交换机.png", 41, 30);
            nx.graphic.Icons.registerIcon("portal", "innerFile/topo-icon/z-portal.png", 38, 26);
            nx.graphic.Icons.registerIcon("riil", "innerFile/topo-icon/z-riil.png", 38, 26);
            nx.graphic.Icons.registerIcon("sam", "innerFile/topo-icon/z-sam.png", 38, 26);
            nx.graphic.Icons.registerIcon("sam-self", "innerFile/topo-icon/z-sam-self.png", 38, 26);
            nx.graphic.Icons.registerIcon("switch", "innerFile/topo-icon/z-switch.png", 41, 30);
            nx.graphic.Icons.registerIcon("vpn", "innerFile/topo-icon/z-vpn.png", 41, 30);
            nx.graphic.Icons.registerIcon("access-switch", "innerFile/topo-icon/z-接入交换机.png", 41, 30);
            nx.graphic.Icons.registerIcon("access-router", "innerFile/topo-icon/z-接入路由器.png", 37, 33);
            nx.graphic.Icons.registerIcon("edu", "innerFile/topo-icon/z-教育网.png", 41, 30);
            nx.graphic.Icons.registerIcon("core-switch", "innerFile/topo-icon/z-核心交换机.png", 40, 34);
            nx.graphic.Icons.registerIcon("core-router", "innerFile/topo-icon/z-核心路由器.png", 37, 33);
            nx.graphic.Icons.registerIcon("gray-cloud", "innerFile/topo-icon/z-灰云.png", 41, 31);
            nx.graphic.Icons.registerIcon("green-cloud", "innerFile/topo-icon/z-绿云.png", 41, 30);
            nx.graphic.Icons.registerIcon("gateway", "innerFile/topo-icon/z-网关.png", 41, 31);
            nx.graphic.Icons.registerIcon("ctc", "innerFile/topo-icon/z-电信.png", 41, 30);
            nx.graphic.Icons.registerIcon("cmcc", "innerFile/topo-icon/z-移动.png", 41, 30);
            nx.graphic.Icons.registerIcon("cuc", "innerFile/topo-icon/z-联通.png", 41, 30);
            nx.graphic.Icons.registerIcon("blue-cloud", "innerFile/topo-icon/z-蓝云.png",  41, 31);
            nx.graphic.Icons.registerIcon("virtual-core-switch", "innerFile/topo-icon/z-虚拟核心交换机.png", 80, 70);
            nx.graphic.Icons.registerIcon("virtual-core-router", "innerFile/topo-icon/z-虚拟核心路由器.png", 72, 62);
            nx.graphic.Icons.registerIcon("firewall", "innerFile/topo-icon/z-防火墙.png", 37, 37);
            nx.graphic.Icons.registerIcon("virtual-point", "innerFile/topo-icon/z-虚拟点.png", 40, 40);

            //nx.graphic.Icons.registerIcon("rect-backgroud", "innerFile/topo-icon/线框背景.png", 9, 9);



            nx.graphic.Icons.registerIcon("ac-red", "innerFile/topo-icon-red/z-ac.png", 40, 39);
            nx.graphic.Icons.registerIcon("ap-red", "innerFile/topo-icon-red/z-ap.png", 41, 31);
            nx.graphic.Icons.registerIcon("cache-red", "innerFile/topo-icon-red/z-cache.png", 37, 30);
            nx.graphic.Icons.registerIcon("elog-red", "innerFile/topo-icon-red/z-elog.png", 38, 26);
            nx.graphic.Icons.registerIcon("eportal-red", "innerFile/topo-icon-red/z-eportal.png", 38, 26);
            nx.graphic.Icons.registerIcon("log-red", "innerFile/topo-icon-red/z-log.png", 38, 26);
            nx.graphic.Icons.registerIcon("nms-red", "innerFile/topo-icon-red/z-nms.png", 38, 26);
            nx.graphic.Icons.registerIcon("os-red", "innerFile/topo-icon-red/z-os.png", 38, 26);
            nx.graphic.Icons.registerIcon("poe-red", "innerFile/topo-icon-red/z-poe接入交换机.png", 41, 30);
            nx.graphic.Icons.registerIcon("portal-red", "innerFile/topo-icon-red/z-portal.png",  38, 26);
            nx.graphic.Icons.registerIcon("riil-red", "innerFile/topo-icon-red/z-riil.png",  38, 26);
            nx.graphic.Icons.registerIcon("sam-red", "innerFile/topo-icon-red/z-sam.png",  38, 26);
            nx.graphic.Icons.registerIcon("sam-self-red", "innerFile/topo-icon-red/z-sam-self.png",  38, 26);
            nx.graphic.Icons.registerIcon("switch-red", "innerFile/topo-icon-red/z-switch.png", 41, 30);
            nx.graphic.Icons.registerIcon("vpn-red", "innerFile/topo-icon-red/z-vpn.png", 41, 30);
            nx.graphic.Icons.registerIcon("access-switch-red", "innerFile/topo-icon-red/z-接入交换机.png", 41, 30);
            nx.graphic.Icons.registerIcon("access-router-red", "innerFile/topo-icon-red/z-接入路由器.png", 37, 33);
            nx.graphic.Icons.registerIcon("edu-red", "innerFile/topo-icon-red/z-教育网.png", 41, 30);
            nx.graphic.Icons.registerIcon("core-switch-red", "innerFile/topo-icon-red/z-核心交换机.png",  40, 34);
            nx.graphic.Icons.registerIcon("core-router-red", "innerFile/topo-icon-red/z-核心路由器.png", 37, 33);
            nx.graphic.Icons.registerIcon("gray-cloud-red", "innerFile/topo-icon-red/z-灰云.png", 41, 31);
            nx.graphic.Icons.registerIcon("green-cloud-red", "innerFile/topo-icon-red/z-绿云.png", 41, 30);
            nx.graphic.Icons.registerIcon("gateway-red", "innerFile/topo-icon-red/z-网关.png", 41, 31);
            nx.graphic.Icons.registerIcon("ctc-red", "innerFile/topo-icon-red/z-电信.png", 41, 30);
            nx.graphic.Icons.registerIcon("cmcc-red", "innerFile/topo-icon-red/z-移动.png", 41, 30);
            nx.graphic.Icons.registerIcon("cuc-red", "innerFile/topo-icon-red/z-联通.png", 41, 30);
            nx.graphic.Icons.registerIcon("blue-cloud-red", "innerFile/topo-icon-red/z-蓝云.png", 41, 31);
            nx.graphic.Icons.registerIcon("virtual-core-switch-red", "innerFile/topo-icon-red/z-虚拟核心交换机.png", 80, 70);
            nx.graphic.Icons.registerIcon("virtual-core-router-red", "innerFile/topo-icon-red/z-虚拟核心路由器.png", 72, 62);
            nx.graphic.Icons.registerIcon("firewall-red", "innerFile/topo-icon-red/z-防火墙.png", 37, 37);
            nx.graphic.Icons.registerIcon("virtual-point-red", "innerFile/topo-icon-red/z-虚拟点.png", 40, 40);




            var windowHeight = $(window).height();
            var windowWidth = $(window).width();

            nx.define("ZNMSRect",nx.graphic.Topology.Layer,{
                properties:{
                    x1:{},
                    y1:{},
                    x2:{},
                    y2:{},
                    title:{},
                    icon:{},
                    name:{},
                    stageScale:{}
                },
                methods:{
                    init: function (args) {
                        this.inherited(args);
                        this.sets(args);
                    },
                    draw:function () {
                        //var stageScale = this.stageScale();
                        var stageScale = topo.view('topo').matrix().scale();

                        //console.log("draw,stageScale["+stageScale+"]");
                        //线框
                        var rect = this.rect = new nx.graphic.Rect();
                        var leftLine = new nx.graphic.Line();
                        var bottomLine = new nx.graphic.Line();
                        var rightLine = new nx.graphic.Line();
                        var topLine = new nx.graphic.Line();
                        //title容器
                        var titleContainer = new nx.graphic.Polygon();
                        //title
                        var title = new nx.graphic.Text();



                        var titleHeight = 20/stageScale;
                        var titleWidth = (this.title().length*13)/stageScale;
                        var textOffset = 5/stageScale;
                        var fontSize = 12/stageScale;


                        var name = this.name();
                        var titleText = this.title();
                        title.sets({
                            text:titleText,
                            x:this.x2()-titleWidth,
                            y:this.y1()-textOffset,
                            /*x:10,
                            y:10,*/
                            'font-size':fontSize+'px',
                            fill:'#000066',
                            id:name+'-title-id'
                        });

                        /*title.on('mouseup',function (sender,event) {
                            console.log('mouseup '+sender.innerHTML);
                        })*/
                        titleContainer.on('mouseenter',function (sender,event) {
                            topo.view('topo').dom().addClass('n-dragCursor');
                           /* var currentSceneName = topo.view('topo').currentSceneName();
                            if (currentSceneName != 'moveRect') {
                                topo.view('topo').activateScene('moveRect');
                                this._prevSceneName = currentSceneName;
                            }*/
                        });

                        titleContainer.on('mouseleave',function (sender,event) {
                            topo.view('topo').dom().removeClass('n-dragCursor');
                            nx.dom.Document.html().removeClass('n-dragCursor');
                           /* var currentSceneName = topo.view('topo').currentSceneName();
                            if (currentSceneName != 'default') {
                                topo.view('topo').activateScene('default');
                                this._prevSceneName = currentSceneName;
                            }*/
                        });

                        title.on('mouseenter',function (sender,event) {
                            topo.view('topo').dom().addClass('n-dragCursor');
                        });

                        title.on('mouseleave',function (sender,event) {
                            topo.view('topo').dom().removeClass('n-dragCursor');
                            nx.dom.Document.html().removeClass('n-dragCursor');
                        });

                        title.on('mouseup',function (sender,event) {
                            var pos = {'x':event.clientX,'y':event.clientY};
                            topo.view('topo')._tooltipManager.openRectContent(pos,titleText,name);
                        });

                        titleContainer.on('mouseup',function (sender,event) {
                            var pos = {'x':event.clientX,'y':event.clientY};
                            topo.view('topo')._tooltipManager.openRectContent(pos,titleText,name);
                        });

                        var strokeWidth  = 1/stageScale;

                        titleContainer.sets({
                            nodes:[
                                {
                                    x:(this.x2()-titleWidth),
                                    y:(this.y1()-titleHeight)
                                },{
                                    x:this.x2(),
                                    y:this.y1()-titleHeight
                                },{
                                    x:this.x2(),
                                    y:this.y1()
                                },{
                                    x:this.x2()-titleWidth-titleHeight,
                                    y:this.y1()
                                }
                            ],
                            fill:'#00ccff',
                            'stroke-width':strokeWidth+'px',
                            stroke:'#00ccff',
                        });

                        rect.sets({
                            x:this.x1(),
                            y:this.y1(),
                            width:this.x2()-this.x1(),
                            height:this.y2()-this.y1(),
                            'stroke-width':strokeWidth+'px',
                            stroke:'#00ccff',
                            //stroke:'url(#rectGradient)',
                            //'fill-opacity':0.9,
                            fill:"url(#rect-backgroud)",
                            //fill:"#00ccff",
                            //filter:"url(#dropshadow)"
                        });


                        /*leftLine.sets({
                            x1:this.x1()-10,
                            y1:this.y1(),
                            x2:this.x1()-10,
                            y2:this.y2(),
                            stroke:'#00ccff',
                            'stroke-width':strokeWidth+'px',
                            //filter:"url(#SVGID_0)"
                        });


                        leftLine.attach(this);*/

                        rect.attach(this);
                        titleContainer.attach(this);
                        title.attach(this);
                    }
                }
            });

            nx.define("MyLayer", nx.graphic.Topology.Layer, {
                methods: {
                    draw: function () {
                        var line = this.line = new nx.graphic.BezierCurves({
                            radian: 2
                        });
                        line.sets({
                            'stroke-width': '10px',
                            stroke: '#f00'

                        });
                        this.topology().on("zoomend", function () {
                            this._updateLine();
                        }, this);
                        this.topology().on("resetzooming", function () {
                            this._updateLine();
                        }, this);


                        this._updateLine();
                        line.attach(this);


                    },
                    _updateLine: function () {
                        var topo = this.topology();
                        var sourceNode = topo.getNode(2);
                        var targetNode = topo.getNode(4);
                        //console.log("x:"+sourceNode.x()+",y:"+sourceNode.y());
                        //console.log("x:"+targetNode.x()+",y:"+targetNode.y());
                        this.line.sets({
                            x1: sourceNode.x(),
                            y1: sourceNode.y(),
                            x2: targetNode.x(),
                            y2: targetNode.y()
                        })
                    }
                }
            });

            $.ajax({
                url : 'topology/get',
                cache : false,
                async : true,
                type : "POST",
                dataType : 'json',
                success : function (result){

                    var nodeAndLinkData = [];
                    nodeAndLinkData["nodes"] = result.nodes;
                    nodeAndLinkData["links"] = result.links;

                    nx.define('ZNMSTopology', nx.ui.Component, {
                        properties: {
                            icon: {
                                value: function() {
                                    return function(vertex) {
                                        return vertex.get("icon");
                                    }
                                }
                            }
                        },
                        view: {
                            props: {
                                'class': ['n-topology-container'],
                                tabindex: '0',

                            },
                            content: {
                                name: 'topo',
                                type: 'nx.graphic.Topology',
                                props: {
                                    width: windowWidth-180,
                                    height: windowHeight-70,
                                    initWidth:windowWidth-180,
                                    initHeight:windowHeight-70,
                                    identityKey: 'id',
                                    nodeConfig: {
                                        label: '',
                                        iconType: '{#icon}'
                                    },
                                    linkConfig: {
                                        color: function(link, model) {
                                            return "#99ff00";
                                        },
                                        width: function() {
                                            return 2;
                                        }
                                    },
                                    showIcon: true,
                                    //supportMultipleLink:false,
                                    //layoutType: 'enterpriseNetworkLayout',
                                    data: nodeAndLinkData
                                    //data:topologyData
                                },
                                events: {
                                    'topologyGenerated': '{#_topologyGenerated}'
                                }
                            }
                        },
                        methods:{
                            _topologyGenerated:function () {

                                /*topo.view('topo').resize(window.screen.width,window.screen.height);
                                topo.view('topo').fit();*/


                                /*var fullScreenElement = document.getElementsByClassName("n-topology");
                                topo.view('topo').view('nav').toggleFull(fullScreenElement[0]);*/

                                //console.log("current scale 1,"+topo.view('topo').matrix().scale());

                                /*topo.view('topo').resize(topo.view('topo').initWidth(),topo.view('topo').initHeight());
                                topo.view('topo').fit();*/
                                //topo.view('topo').view('nav').dom().setStyle('display', 'block');
                                //topo.view('topo').view('nav').cancelFullScreen(document);

                                //console.log("current scale 2,"+topo.view('topo').matrix().scale());

                                var rectData = result.rects;

                                $.each(rectData,function (index,rect) {

                                    var name = 'rect-'+new Date().getTime()+"-"+Math.floor(Math.random()*10000);
                                    var newrect = new ZNMSRect({
                                        x1:rect.x1,
                                        y1:rect.y1,
                                        x2:rect.x2,
                                        y2:rect.y2,
                                        title:rect.title,
                                        name:name,
                                        stageScale:topo.view('topo').matrix().scale()
                                        //stageScale:21.225138477338966
                                    });
                                    topo.view('topo').attachLayer(name, newrect,0);
                                });



                                /*var layerArray = new Array();
                                topo.view('topo').eachLayer(function (layer,id) {
                                    //线框是通过layer来实现，名称以rect-开头

                                    var temp = {};
                                    if(layer.name){
                                        var name = layer.name();
                                        if (name.substring(0,5)=="rect-"){
                                            var rectName = layer.name();
                                            var icon = layer.icon();
                                            var x1 = layer.x1();
                                            var y1 = layer.y1();
                                            var x2 = layer.x2();
                                            var y2 = layer.y2();
                                            var title = $("#"+rectName+"-title-id").html();
                                            var deleteIndex;
                                            topo.view('topo').getLayer(name).dispose();
                                            delete topo.view('topo').layersMap()[name];
                                            nx.each(topo.view('topo').stage()._content._data,function(value,index){
                                                if(value._name == name){
                                                    deleteIndex = index;
                                                }
                                            });
                                            if (deleteIndex!=undefined){
                                                topo.view('topo').stage()._content._data.splice(deleteIndex,1);
                                            }

                                            var rect={"x1":x1,"y1":y1,"x2":x2,"y2":y2,"title":title,"name":rectName};
                                            layerArray.push(rect);
                                        }
                                    }
                                });


                                for(var i = 0;i < layerArray.length; i++) {
                                    var temp = layerArray[i];
                                    var rect = new ZNMSRect({
                                        x1:temp.x1,
                                        y1:temp.y1,
                                        x2:temp.x2,
                                        y2:temp.y2,
                                        title:temp.title,
                                        name:temp.name,
                                        stageScale:topo.view('topo').matrix().scale()
                                    });
                                    topo.view('topo').attachLayer(temp.name, rect,0);
                                }

                                topo.view('topo').view('nav').dom().setStyle('display', 'block');
                                topo.view('topo').cancelFullScreen(document);
                                topo.view('topo').fire("leaveFullScreen");*/


                               /* var screenHeight = window.screen.height;
                                var screenWidth = window.screen.width;

                                topo.view('topo').resize(screenWidth,screenHeight);
                                topo.view('topo').fit();


                                var initWidth = topo.view('topo').initWidth();
                                var initHeight = topo.view('topo').initHeight();
                                console.log("resize topo.width:"+initWidth+",height:"+initHeight);
                                topo.view('topo').resize(initWidth,initHeight);
                                topo.view('topo').fit();

                                var layerArray = new Array();
                                topo.view('topo').eachLayer(function (layer,id) {
                                    //线框是通过layer来实现，名称以rect-开头

                                    var temp = {};
                                    if(layer.name){
                                        var name = layer.name();
                                        if (name.substring(0,5)=="rect-"){
                                            var rectName = layer.name();
                                            var icon = layer.icon();
                                            var x1 = layer.x1();
                                            var y1 = layer.y1();
                                            var x2 = layer.x2();
                                            var y2 = layer.y2();
                                            var title = $("#"+rectName+"-title-id").html();
                                            var deleteIndex;
                                            topo.view('topo').getLayer(name).dispose();
                                            delete topo.view('topo').layersMap()[name];
                                            nx.each(topo.view('topo').stage()._content._data,function(value,index){
                                                if(value._name == name){
                                                    deleteIndex = index;
                                                }
                                            });
                                            if (deleteIndex!=undefined){
                                                topo.view('topo').stage()._content._data.splice(deleteIndex,1);
                                            }

                                            var rect={"x1":x1,"y1":y1,"x2":x2,"y2":y2,"title":title,"name":rectName};
                                            layerArray.push(rect);
                                        }
                                    }
                                });


                                for(var i = 0;i < layerArray.length; i++) {
                                    var temp = layerArray[i];
                                    var rect = new ZNMSRect({
                                        x1:temp.x1,
                                        y1:temp.y1,
                                        x2:temp.x2,
                                        y2:temp.y2,
                                        title:temp.title,
                                        name:temp.name,
                                        stageScale:topo.view('topo').matrix().scale()
                                    });
                                    topo.view('topo').attachLayer(temp.name, rect,0);
                                }*/



                            }
                        }
                    });

                    app = new nx.ui.Application();
                    topo = new ZNMSTopology();
                    topo.attach(app);
                    topo.view('topo').registerPattern();


                    /*var screenHeight = window.screen.height;
                    var screenWidth = window.screen.width;

                    topo.view('topo').resize(screenWidth,screenHeight);
                    topo.view('topo').fit();

                    var initWidth = topo.view('topo').initWidth();
                    var initHeight = topo.view('topo').initHeight();
                    console.log("resize topo.width:"+initWidth+",height:"+initHeight);
                    topo.view('topo').resize(initWidth,initHeight);
                    topo.view('topo').fit();*/


                    //var fullScreenElement = topo.view('topo').view('nav').dom().parentElement.parentElement.parentElement;
                    //topo.view('topo').view('nav').toggleFull();



                }
            });

        })(nx);

/*        function disable() {
            var links = topo.getLayer('links').links();
            var link = links[1];
            link.enable(false);
            link.update();
            topo.getNode(0).enable(false);
        }

        function enable() {
            var links = topo.getLayer('links').links();
            var link = links[1];
            link.enable(true);
            link.update();
            topo.getNode(0).enable(true);
        }

        setTimeout('disable()',5000);
        setTimeout('enable()',10000);*/

        function test() {

            /*topo.height(windowHeight);
            topo.width(windowWidth);*/

            //设置位置
            /*var sourceNode = topo.view('topo').getNode(2);
            sourceNode.absolutePosition({'x':100,'y':100});*/

            //添加node
            /*var newNode = new nx.graphic.Topology.Node();
            topo.view('topo').addNode(newNode,{
                label: 'model.id'
            });
            newNode.absolutePosition({'x':100,'y':100});
            newNode.label('newNode');*/


            /*var topoMatrix = topo.view('topo').matrix();
            var stageScale = topoMatrix.scale();

            var x = (200-topoMatrix.x())/stageScale;
            var y = (200-topoMatrix.y())/stageScale;

            topo.view('topo').addNode({
                "id": 5,
                "x":x,
                "y":y,
                "name": "newNode1"
            });
            topo.view('topo').addNode({
                "id": 6,
                "x":x+200,
                "y":y+200,
                "name": "newNode2"
            });*/

            /*topo.view('topo').addLink({
                source:4,
                target:3
            });*/
            //topo.getAbsolutePosition(node.position())

            //topo.view('topo').getLayer('rdRect').recover();


            //topo.view('topo').sync();

            /*topo.view('topo').eachLink(function (link,index) {
                link.color("#a61c00");
            });*/

        }

        //setTimeout('test()',5000);

        function updateTopo() {
            $.ajax({
                url : 'topology/sync',
                cache : false,
                async : true,
                type : "POST",
                dataType : 'json',
                success : function (result){
                    var downIp = result.downIp;
                    var yellowLink = result.yellowLink;
                    var redLink = result.redLink;

                    //返回值为所有已经down机的ip，逗号分隔
                    topo.view('topo').eachNode(function (node,id) {
                        var hostIp = node.model().getData().hostIp;
                        var icon = node.model().getData().icon;
                        var newIcon;
                        //验证hostIp是否在结果中
                        if(downIp.indexOf(hostIp)>=0){
                            if(icon.indexOf('red')==-1){
                                newIcon = icon+"-red";
                            }
                        }else if(icon.indexOf('red')>=0){
                            newIcon = icon.slice(0,-4);
                        }

                        if(newIcon){
                            node.view('icon').iconType(newIcon);
                            node.model().getData().icon = newIcon;
                            topo.view('topo').sync();
                        }
                    });

                    //遍历每个链接线
                    topo.view('topo').eachLink(function (link,id) {
                        var graphId = link.model().getData().graphId;
                        var maxBandwidth = link.model().getData().maxBandwidth;

                        if(graphId&&maxBandwidth){
                            var currentColor = link.color();
                            var temp = graphId+"-"+maxBandwidth;
                            if(yellowLink.indexOf(temp)>=0){
                                if(currentColor!='#ff00ff'){
                                    link.color("#ff00ff");
                                }
                            }else if(redLink.indexOf(temp)>=0){
                                if(currentColor!='#a61c00'){
                                    link.color("#a61c00");
                                }
                            }else if(currentColor!='#99ff00'){
                                link.color("#99ff00");
                            }

                        }

                    });
                }
            });
        }

        setInterval(function(){
            updateTopo();
        }, 10*1000);


    </script>
</body>
</html>
