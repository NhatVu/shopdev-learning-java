## Section 2

Introduction about Http compression. 

In brief, client will send with header `Accept-Encoding: br, gzip`. If server supports one of these algorithms, it will do compress and return with header `Content-Encoding: gzip`, if gzip is used. 

Task
- create a string with length ~ 10, and repeat it 100k times
- without compression: response size ~ 1.7mb
- with compression: response size ~ 4.5kb
- Compare them by using Network tab on Chrome, which one is efficient in time and size?