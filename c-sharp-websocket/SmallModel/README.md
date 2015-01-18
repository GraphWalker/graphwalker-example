This example will run simulated test using GraphWalker started as a WebSocket service.

 * Download [latest GraphWalker standalone jar](http://graphwalker.org/archive/graphwalker-cli-3.3.0-SNAPSHOT.jar).
 * Start GraphWalker as a WebSocker service: `java -jar graphwalker-cli-3.3.0-SNAPSHOT.jar -d all online`.
 * `git clone https://github.com/GraphWalker/graphwalker-example.git`
 * `cd c-sharp-websocket/SmallModel`
 * Build the SmallModel project.<br>
   On linux: `xbuild `
 * Run the program:
   On linux: `mono SmallModel/bin/Debug/SmallModel.exe`

When run, the SmallModel program will connect to the GraphWalker service, and load a [model in JSON notation](http://graphwalker.org/docs/json_graph).

It will then start the state machine in the GraphWalker service, and query it for steps to execute. The psuedo code would look something like the:
```
    while hasNext()
        step = getNext()
        "Call method step in C# SmallModel class"
        invoke SmallModel.'step'
        print getData()
```


