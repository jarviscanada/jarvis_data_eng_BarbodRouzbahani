# Introduction
This Java application imitates the feature of the Linux 'grep' tool. Three arguements are required : outFile, rootPath, and regex (the pattern to be searched for). It does a recursive search over the specified directory for any matches from any file in the directory or any of its subdirectories using the provided regex expression. All the lines including the pattern will then be written to outFile.   This application makes use of certain fundamental Java features, including array lists, object-oriented programming, interfaces, and encapsulation. The management of the project was also done using Maven.

# Quick Start
```
regex=".*Romeo.*Juliet.*"
rootpath="./data"
outfile=grep_$(date +%F_%T).txt
docker run --rm \
-v `pwd`/data:/data -v `pwd`/out:/out .../grep \
${regex} ${rootpath} /out/${outfile}
cat out/$outfile
```

# Implemenation
## Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)
 writeToFile(matchedLines)
```

## Performance Issue
The app might run out of heap memory if the directory being searched is larger than the heap. Solution to this problem, in case it happens, is setting a bigger heap size for JVM, using Stream APIs or BufferedReader, setting the garbage collector to perform more often, or using off heap memory.

# Test
Testing was done by using sample data prepared specifically for this app, to ensure that the resulting output is the same as the expected output.

# Deployment
The deployment process was done by creating dockerfile, packing the grep app, building a new docker image locally, and pushing the image to Docker Hub.

# Improvement
An imporvement could be to omit the filename needed in command line, so that the application becomes more similar to the grep tool itself.
