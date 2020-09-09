# Concurrent-Word-Distribution-Tool
A JavaFX desktop application for concurrently computing the distribution of specified words in directories/files and plotting the results on a graph.

## Overview
The system functions as a pipeline that consists of several types of components working concurrently in conjunction.<br>
There are three types of components:
1. Input component - data entry point
2. Cruncher component - processing data
3. Output component - storing and visualizing results

Every component runs in its own thread and has a dedicated <b>thread pool.</b><br>
The user can make multiple instances of each component and link them in a way he sees fit.<br>
Input components provide input to the cruncher component, which then provides input to the output component.<br>
The architecture of the system makes it easy to integrate new types of components.
