# java-server

- HTTP-based mini game back-end which registers game scores for different users and levels, with the capability to return high score lists per level. 
Uses a simple login system without authentication.
- Not external frameworks except for testing.
- No persistence in disk.
- Java version 7
- The error output prints errors and the standard outputs prints messages, instead of using logging libraries.
- JUnit 4 for unit testing
- Apache HttpClient 4.3.4 is used to emulate the client in the unit tests. Included necessary dependencies are:
  - commons-codec-1.6
  - commons-logging-1.1.3
  - fluent-hc-4.3.4
  - httpclient-4.3.4
  - httpclient-cache-4.3.4
  - httpcore-4.3.2
  - httpmime-4.3.4