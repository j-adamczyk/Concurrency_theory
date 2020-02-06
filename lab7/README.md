# Laboratory 7 - 4 solutions of dining philosophers

We had to implement all 4 tasks in 3 languages: Java, JS and one freely chosen.  
I chose Python to check if it's really that bad in concurrency using threads. With default CPython 
it is, but with Cython results should be much better.

## Task 1
Naive implementation: every philosopers waits for left fork, takes it, waits for right fork and takes it.  
This solution risks deadlock.

## Task 2
Implementation with risk of starvation: every philosopher checks if both forks are available and takes them both simultaneously.  
Risks starvation, since both neighbors of a given philosopher may be eating all the time.

## Task 3
Asymmetrical solution: philosophers are numbered, "even" philosopers take right fork first, "odd" philosophers take left fork first.  
It turns out that this simple modification works best, being just and several orders of magnitude faster.

## Task 4
Just solution: outside arbiter (waiter) gives only at most N-1 philosopers access to the table at any moment. It guarantees that 
no deadlock and/or starvation occurs, but it's the slowest solution.