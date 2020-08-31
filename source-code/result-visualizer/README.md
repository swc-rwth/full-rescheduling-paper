## How to run

The main class to run the simulation is `chart.visualizer.Runner`.

4 optional arguments can be provided when running the main class:
 - -d, --dir: to specify directory path of the result
 - -e, --execution: to specify filename of the result for task exectuion
 - -u, --usage: to specify filename of the result for resource usage
 - -m, --mode : either `gui` or `cli`

For example:

    java Runner -d "./results/20_40/data.swf/2-FCFSRescheduling" -e "jobs_800_data.swf_exact_bez.csv" -u "detail_event_usage_.csv" -m "gui"

***Extra:***
To consolidate all the resource usage result into single excel sheet, use the `chart.stackedbar.ResultCombinator`

For example:

    java ResultCombinator -d "./results"


