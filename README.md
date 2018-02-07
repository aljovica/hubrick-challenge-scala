# hubrick-challenge-scala

## How to use app
To run the script, an *sbt* and *scala* installation are required. 

There is a build script to execute it with one parameter, which is the 
full path to the data folder. The data folder has to have following
files:  

    1.departments.csv  
    2.employees.csv  
    3.ages.csv
    
The script is called this way:

    ./build-and-run-app.sh $path
    
The statistics reports are generated in */reports*    

## Solution

1. First csv files are loaded into memory. *departments.csv* and *ages.csv* first and 
then *employees.csv*. During that process it is checked if the employee also exists in 
ages.csv and his department id is valid. Both checks are looking into a map, so that performance
 is not affected. After these checks, data is merged into a list of Employee 
 objects. Invalid lines are skipped and logged.
2. The list is passed to the calculation service, where it is being transformed and the passed to the statistics service to calculate medians, averages or percentile 
statistics.
3. After the calculation, the data is passed to the csv service to write it into the
reports folder.