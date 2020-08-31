Implemented using ALEA ([https://github.com/aleasimulator/alea](https://github.com/aleasimulator/alea))

## How to run

The main class to run the simulation is `xklusac.environment.ExperimentSetup`.

4 optional arguments can be provided when running the main class:
 - configuration file path (default `./data-set`)
 - machine configuration file name
 - specify `-t` to enable testing mode
 - rescheduling delay time in seconds (default 1000)

For example:

    java ExperimentSetup './data-set' '10_40.machines' -t 300

To run all the scenarios from 10 to 45 nodes in the private cloud, use the `alea.test.DetailTest` which contains all test cases and each test case equivalent to one scenario.
