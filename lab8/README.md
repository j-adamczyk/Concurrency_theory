# Laboratory 8 - RecursiveTask, asynchronous paralel REST requests

## Task 1
Choose large text (over 100k words), randomly insert 100 double spaces.  
Count words using one and many threads, use Stream interface.

## Task 2
Sum an array (at least 10 million elements) using RecursiveTask pattern.

## Task 3
Compare synchronous and asynchronous requests to a chosen REST API.  
In second solution I used CompletableFuture, we were also required to get at least 30x 
speedup compared to synchronous version. I've managed to get 33x speedup using an 
array of CompletableFutures and using allOf().get() after initializing the whole array and 
therefore all tasks.