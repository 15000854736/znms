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
                {"id": 0, "x": 410, "y": 100, "name": "12K-1"},
                {"id": 1, "x": 410, "y": 280, "name": "12K-2"},
                {"id": 2, "x": 660, "y": 280, "name": "Of-9k-03"},
                {"id": 3, "x": 660, "y": 100, "name": "Of-9k-02"},
                {"id": 4, "x": 180, "y": 190, "name": "Of-9k-01"}
            ],
            links: [
                {"source": 0, "target": 1},
                {"source": 1, "target": 2},
                {"source": 1, "target": 3},
                {"source": 4, "target": 1},
                {"source": 2, "target": 3},
                {"source": 2, "target": 0},
                {"source": 3, "target": 0},
                {"source": 3, "target": 0},
                {"source": 3, "target": 0},
                {"source": 0, "target": 4},
                {"source": 0, "target": 4},
                {"source": 0, "target": 3}
            ]
        };






        var topo;
        var app;
        (function (nx) {
            var windowHeight = $(window).height();
            var windowWidth = $(window).width();


            nx.define('ZNMSTopology', nx.ui.Component, {
                view: {
                    props: {
                        'class': ['n-topology-container'],
                        tabindex: '0'
                    },
                    content: {
                        type: 'nx.graphic.Topology',
                        props: {
                            width: windowWidth-180,
                            height: windowHeight-70,
                            initWidth:windowWidth-180,
                            initHeight:windowHeight-70,
                            nodeConfig: {
                                label: 'model.name'
                            },
                            showIcon: true,
                            data: topologyData
                        }
                    }
                }
            });

            var app = new nx.ui.Application();
            var topo = new ZNMSTopology();
            topo.attach(app);

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

        }

        setTimeout('test()',5000);




    </script>
</body>
</html>
