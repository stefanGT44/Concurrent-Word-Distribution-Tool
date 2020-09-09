# Concurrent-Word-Distribution-Tool
A JavaFX desktop application for concurrently computing the distribution of specified words in large directories/files and plotting the results on a graph.

## Overview
The system functions as a pipeline that consists of several types of components working concurrently in conjunction.<br>
There are three types of components:
1. Input component - data entry point
2. Cruncher component - processing data
3. Output component - storing and visualizing results

Every component runs in its own thread and has a dedicated <b>thread pool.</b><br>
The user can make multiple instances of each component and link them in a way he sees fit.<br>
Input components provide input to the cruncher component, which then provides input to the output component.<br>
The architecture of the system makes it easy to integrate new types of components.<br>
Components follow the <b>MVC</b> design pattern.<br>

![Alt text](images/wdt.png?raw=true "")<br><br><br>

## Usage example

![Alt text](images/de2.png?raw=true "")<br>
Input 0 is linked to Cruncher 0 and Cruncher 1. Input 0 is started and currently idle (job of reading files is done).
Input 1 is linked to Cruncher 2. Input 1 is not started yet.
Cruncher 0 is currently working on 3 text files, and Cruncher 1 on one.
Cruncher progress can also be viewed in the Output component, in the item list if an item has * as a prefix, the results are not available yet (cruncher still working).
Currently 2 files are done, and 4 are being worked on.
*
![Alt text](images/de3.png?raw=true "")<br>
