# Concurrent-Word-Distribution-Tool
A JavaFX desktop application for concurrently computing the distribution of specified words in large directories/files and plotting the results on a graph. (Distribution - the number of times each word appears in a text file)

## Overview
The system functions as a pipeline that consists of several types of components working concurrently in conjunction.<br>
There are three types of components:
1. Input component - data entry point
2. Cruncher component - data processing
3. Output component - storing and visualizing results

The user can make multiple instances of each component and link them in a way he sees fit.<br>
Every component instance runs in its own thread and every component type has a dedicated <b>thread pool.</b><br>
Input components provide input to cruncher components, which then provide input to the output components.<br>
Component communication (data flow) is based on shared <b>blocking queues</b>. <br>
The architecture of the system makes it easy to integrate new types of components.<br>
Components and the main app follow the <b>MVC</b> design pattern.<br><br>

![Alt text](images/wdt.png?raw=true "")<br><br><br>

## Usage example

![Alt text](images/de4.png?raw=true "")<br><br>
Input 0 is linked to Cruncher 0 which is automatically linked to the default output component.<br>
Input 0 is active and currently reading one text file (see the blue label).<br>
Cruncher 0 is currently computing the distribution in three files that Input 0 has provided.<br>
Cruncher progress can also be monitored in the output component, if an item in the list has a prefix, then results for that file are not ready yet (cruncher still working).
<br><br><br><br>

![Alt text](images/de5.png?raw=true "")<br><br>
In this image the Input and Cruncher components have finished their work from the previous image.<br>
The output component is showing the distribution of words in the file wiki-7.txt.<br>
It is also currently computing the sum distribution that the user specified.<br><br><br><br>

![Alt text](images/de6.png?raw=true "")<br><br>
In this example the output component is computing the specified distribution sum and is waiting for the final file results to become available to finish.

## Component details:
Every component instance runs in its own thread and every component type has a dedicated thread pool for completing its main tasks.<br>
Components communicate among each other using blocking queues. Every component has a blocking queue that its predecessors can write to.<br>

### Input components:
Every input component can be linked to one or more cruncher components.<br>
The main objective of input components is to scan directories for text files which are then read and supplied to linked crunchers.<br>
Reading of text files is done in a separate task within the input thread pool.<br>
Input components are tied to a disk (drive) that the user specifies when creating a new instance. <br>
Only directories on the specified disk can be scanned, and only one reading task can be active in the thread pool per disk. <br>
After one scan cycle is finished, the component pauses for a certain duration before the next cycle (specified in the config file). <br>
The user can manualy pause and resume input components. <br>
The last modified value of scanned directories is tracked, so if a directory has been modified, it is scanned again (the text files are read again). <br>

### Cruncher components:
In the current implementation, cruncher components are automatically linked to one default output component, but the code supports multiple output components.
The main objective of cruncher components is to count the word distribution in text objects that linked input components provided, and supply linked output components with the results. <br>
Upon receiving input text, a new <b>RecursiveTask</b> is created within the cruncher <b>thread pool</b> and the <b>Future</b> object is forwarded to all linked output components.<br>
The task recursively creates new tasks and splits the job (text) into smaller chunks (chunk size specified in the config file), after which the distribution computation is done, and finally the results are combined.
Every cunrhcer instance has a specified arity number.<br>
If arity = <b>1</b> the cruncher counts the number of times every <b>single</b> word appears in a text,<br>
if arity = 3 the cruncher counts the number of times every 3 consecutive words, in exactly that order, appear in a text , etc.<br>

### Output components:
Output components store results provided by the linked crunchers.<br>
The results can be aggregated (this is done within the output component thread pool), and/or plotted on the graph.
Output components are aware of all created jobs, even unfinished ones (active jobs have * as a prefix). <br>
The component offers get (blocking) and poll (not blocking) methods for retrieving results.
Single result plotting uses the poll method and notifies the user if results are not ready yet. <br>
The aggregation task uses the get method and waits (is blocked) if some results are not ready yet. <br>
