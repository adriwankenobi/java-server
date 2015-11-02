# java-server

#Tech notes:

- Java version 7 and Eclipse JUNO (4.2.2)
- Eclipse may throw restriction access errors regarding 'com.sun.net.httpserver.HttpServer' because some JVM's don't support it.
  Those errors can be removed by ignoring them in the project preferences.
- The error output prints errors and the standard outputs prints messages, instead of using logging libraries.
- JUnit 4 is used for unit testing.
- Apache HttpClient 4.3.4 is used to emulate the client in the unit tests. Included necessary dependencies are:
  - commons-codec-1.6
  - commons-logging-1.1.3
  - fluent-hc-4.3.4
  - httpclient-4.3.4
  - httpclient-cache-4.3.4
  - httpcore-4.3.2
  - httpmime-4.3.4

#Design notes:

- Login does not delete old session keys. That means several session keys might be valid at the same time for the same user.
  Instead, there is another specific thread to clean old sessions from time to time.
- Only the highest score in a level for a user is stored. That means there is not historical record.
- Sessions object has been implemented using ConcurrentHashMap indexed by session key. It guarantees thread safe put, get and delete methods.
- Rankings object has been implemented using ConcurrentHashMap indexed by level. Again, it guarantees thread safety.
- However, each ranking is implemented as a SortedSet of RankingElements:
  - Inserts in the right position so the Set is sorted.
  - Get the highest X elements is efficient since they are the first ones in the SortedSet.
  - Replace is costly, it has to go through the whole set to find the old value.
  - Post score into the level-ranking is synchronized, since other players could be posting scores in the same level-ranking.
    It is synchronized on a level lock. Also, a global lock for creating new levels is used.
- It has been assumed that 'get high scores' action will occur much more frequently than post score action.
- Post actions are done using a queue, so the server can execute the actions if there is time.
  Several properties like queue size, pool size, etc can be configured according to each server.

#Structure notes:

- Configuration: com.acerete
  - Properties file with some configurations like host, port and other game values. 
- Network layer: com.acerete.httpserver
  - Server class contains the 'main' method. It creates the server, runs it and waits for termination.
  - ClientConnection contains methods to send responses to the client. Also stores user id for secured messages.
  - CustomHttpServer implements 'com.sun.net.httpserver.HttpServer'
  - ParamsFilter reads to input request and validates it and its parameters.
  - HttpRequestsHandler creates a ClientConnection object, validates security if necessary, and handles the request
- Services layer: com.acerete.services
  - message sub-package defines request and response types
  - users sub-package contains service requests, responses and handlers for user actions: In this case, login action.
  - scores sub-package contains service requests, responses and handlers for score actions: In this case, post and get highscores actions.
  	- scores also contains implementation of the pool executor for posting scores
- Data layer: com.acerete.data
  - Usually the DAO layer, but in this case is just memory.
  - Scores and rankings (more in design notes)