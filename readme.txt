Brian Jimenez
UtA ID: 1001388904
Java code

Structure of code:
I created nodes using objects and then I created methods. I created two methods, one for a* algo and the other for uniform cost search. The are named like how I said right now, the methods are fairly the same only that in a* you have to include the heuristic and set the objects in order based on the heuristic. The other stuff is that I created a fringe array list of objects and visited nodes array list of objects.    

To run the code:
1. You first have to compile the code so once you are in terminal and are inside the file you type in:
javac find_route.java

2. This should then compile and no errors should appear

3. Once that is done, you will now run the code, an example will be as follows:
java find_route inf input.txt Dresden Kassel h_kassel.txt
This one is an example of what to type to check for the informed algo. Here Dresden is the start city and Kassel is the end city. For inf there are two files to be read. 

4. Now to check the uninformed you have to do as follows:
java find_route uninf input.txt London Bremen
Here London is the start city and Bremen is the end city. For Uninf there is only one file to be read. 

If there is any questions regarding my code, you could contact me at any time. 
brian.jimenez@mavs.uta.edu
