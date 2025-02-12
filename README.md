
# WordCount-Using-MapReduce-Hadoop

This repository is designed to test MapReduce jobs using a simple word count dataset.

## Project Overview

This project is a Hadoop-based Word Count program that processes large text files in a distributed manner using the MapReduce framework. The program reads an input text file, tokenizes the words, and computes the frequency of each unique word in the dataset. It consists of three main components:

Controller (Driver) Class – Manages job configuration and execution.
Mapper Class – Extracts words from input text and emits key-value pairs.
Reducer Class – Aggregates word occurrences and produces final word counts.
This implementation helps in efficiently processing and analyzing large-scale text data by leveraging Hadoop's distributed computing capabilities.

## Objectives

By completing this activity, students will:

1. **Understand Hadoop's Architecture:** Learn how Hadoop's distributed file system (HDFS) and MapReduce framework work together to process large datasets.
2. **Build and Deploy a MapReduce Job:** Gain experience in compiling a Java MapReduce program, deploying it to a Hadoop cluster, and running it using Docker.
3. **Interact with Hadoop Ecosystem:** Practice using Hadoop commands to manage HDFS and execute MapReduce jobs.
4. **Work with Docker Containers:** Understand how to use Docker to run and manage Hadoop components and transfer files between the host and container environments.
5. **Analyze MapReduce Job Outputs:** Learn how to retrieve and interpret the results of a MapReduce job.

## Approach and Implementation

###### Mapper Logic (WordMapper Class)

Input: A line of text (key: byte offset, value: line content).
Processing:
Converts the line into a string.
Splits the string into words based on whitespace.
Converts words to lowercase and emits each word as a key with a value of 1.
Output: (word, 1) pairs.
###### Reducer Logic (WordReducer Class)

Input: Key-value pairs where the key is a word, and values are a list of occurrences.
Processing:
Iterates over values to sum up the occurrences.
Stores the total count for each unique word.
Output: (word, total count) pairs.

## Execution Steps

### 1. **Start the Hadoop Cluster**

Run the following command to start the Hadoop cluster:

```bash
docker compose up -d
```

### 2. **Build the Code**

Build the code using Maven:

```bash
mvn clean project
```

### 3. **Copy JAR to Docker Container**

Copy the JAR file to the Hadoop ResourceManager container:

```bash
docker cp target/WordCountUsingHadoop-0.0.1-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. **Move Dataset to Docker Container**

Copy the dataset to the Hadoop ResourceManager container:

```bash
 docker cp shared-folder/input/data/input.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 6. **Connect to Docker Container**

Access the Hadoop ResourceManager container:

```bash
docker exec -it resourcemanager /bin/bash
```

Navigate to the Hadoop directory:

```bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 7. **Set Up HDFS**

Create a folder in HDFS for the input dataset:

```bash
hadoop fs -mkdir -p /input/dataset
```

Copy the input dataset to the HDFS folder:

```bash
hadoop fs -put ./input.txt /input/dataset
```

### 8. **Execute the MapReduce Job**

Run your MapReduce job using the following command:

```bash
hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/WordCountUsingHadoop-0.0.1-SNAPSHOT.jar com.example.controller.Controller /input/dataset/input.txt /output
```

### 9. **View the Output**

To view the output of your MapReduce job, use:

```bash
hadoop fs -cat /output/*
```

### 10. **Copy Output from HDFS to Local OS**

To copy the output from HDFS to your local machine:

1. Use the following command to copy from HDFS:
    ```bash
    hdfs dfs -get /output /opt/hadoop-3.2.1/share/hadoop/mapreduce/
    ```

2. use Docker to copy from the container to your local machine:
   ```bash
   exit 
   ```
    ```bash
    docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/ shared-folder/output/
    ```
3. Commit and push to your repo so that we can able to see your output

## Challenges Faced & Solutions

##### 1. Challenge: 
The docker cp command might fail if the resourcemanager container is not running or the destination path is incorrect.
##### Solution:
Run docker ps to check if the resourcemanager container is active.
Ensure that the destination path exists inside the container (docker exec -it resourcemanager /bin/bash and ls /opt/hadoop-3.2.1/share/hadoop/mapreduce/).

##### 2. Challenge: 
Unable to access the resourcemanager container due to a missing or incorrect container name.
##### Solution:
Run docker ps to confirm the container name before executing docker exec -it resourcemanager /bin/bash.

##### 3. Challenge: 
Errors when creating HDFS directories or copying files, such as No such file or directory or Permission denied.
##### Solution:
Verify HDFS is running properly: hadoop fs -ls /.

##### 4. Challenge: 
Job execution fails due to missing JAR, incorrect class name, or insufficient cluster resources.
##### Solution:
Ensure the JAR file is copied to the correct location (ls /opt/hadoop-3.2.1/share/hadoop/mapreduce/).

## Sample Input and Output

#### Input Format

Hello world
Hello Hadoop
Hadoop is powerful
Hadoop is used for big data
    
#### Expected Output

Hadoop 3
Hello 2
is 2
used 1
for 1
big 1
data 1
powerful 1
world 1
    
