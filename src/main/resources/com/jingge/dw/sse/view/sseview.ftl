<html>
<body>

<head>
    <meta charset="utf-8">
    <title>Kafka SSE Demo</title>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.js"></script>
</head>

<style>
    div.bar {
        display: inline-block;
        width: 20px;
        height: 75px;
        background-color: teal;
        margin-right: 2px;
    }
</style>

<h1>sse url: ${sseUrl?html}</h1>

<script>

    function drawBar(data) {
        var dataset = new Array();
        // dataset[0] = 10;
        dataset[1] = parseInt(data);

        d3.select("body")
                .data(dataset)
                .enter()
                .append("div")
                .attr("class", "bar")
                .style("background-color", function(d) {
                    if (d % 10 > 5) {
                        return "red";
                    } else {
                        return "teal";
                    }
                })
                .style("height", function(d) {
                    var barHeight = d * 5;
                    return barHeight + "px";
                });
    }

    function getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    function setupEventSource() {
        var output = document.getElementById("output");
        if (typeof(EventSource) !== "undefined") {
            var source = new EventSource("/kafkasse");
            source.onmessage = function(event) {
                if (isNaN(parseInt(event.data))) {
                    output.innerHTML += "<b>Ops, only int is supported: " + event.data + "</b>, "
                } else {
                    output.innerHTML += event.data + ", ";
                    drawBar(event.data);
                }
            };
        } else {
            output.innerHTML = "Sorry, Server-Sent Event is not supported in your browser";
        }
        return false;
    }


</script>

<div>
    <input type="button" id="sendID" value="Send" onclick="setupEventSource()"/>
</div>
<hr/>
<div id="output"></div>
</body>
</html>