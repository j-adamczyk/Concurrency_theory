# Laboratory 9 - alternative readers and writers solution

For this class we had to choose a classic concurrent problem, find an article with alternative 
version and compare them.  
I chose readers and writers problem. In classical version it uses semaphores with global variable 
to keep track of readers. It's slow and readers may easily starve writers, since they have the advantage 
in locking access to the data. This block is completely unnecessary, since writers should block, readers 
can read data without exclusive access to it.  
H. Ballhausen's solution from a paper that I've found proposed a simple modification, essentially giving 
writers priority and having them aquire locks from all readers one by one. This way writers can get access 
faster and readers can read data in paralel.  
It turns out that this very simple change (it's only a few lines of code) results in several orders of magniture 
in speedup.