# Concurrent-Word-Distribution-Tool
A JavaFX desktop application for concurrently computing the distribution of specified words in large directories/files and plotting the results on a graph.<br>
Distribution in this case is the count of each word in a text file.

## Overview
The system functions as a pipeline that consists of several types of components working concurrently in conjunction.<br>
There are three types of components:
1. Input component - data entry point
2. Cruncher component - data processing
3. Output component - storing and visualizing results

Every component runs in its own thread and has a dedicated <b>thread pool.</b><br>
The user can make multiple instances of each component and link them in a way he sees fit.<br>
Input components provide input to cruncher components, which then provide input to the output components.<br>
The architecture of the system makes it easy to integrate new types of components.<br>
Components follow the <b>MVC</b> design pattern.<br>

![Alt text](images/wdt.png?raw=true "")<br><br><br>

## Usage example

![Alt text](images/de4.png?raw=true "")<br><br>
Input 0 is linked to Cruncher 0 which is automatically linked to the default output component.<br>
Input 0 is active and currently reading one text file (see the blue label).<br>
Cruncher 0 is currently computing the distribution in three files that Input 0 provided.<br>
Cruncher progress can also be monitored in the output component, if an item in the file list has * before its name, then results for that file are still not ready (cruncher still working on them). <br><br><br>

![Alt text](images/de5.png?raw=true "")<br><br>
In this image the Input and Cruncher components have finished their work from the previous image.<br>
The output component is showing the distribution of words in the file wiki-7.txt.<br>
It is also currently computing the sum distribution that the user specified.<br><br><br>

![Alt text](images/de6.png?raw=true "")<br>
