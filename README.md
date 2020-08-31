This repository contains supplemental material for the paper titled *Optimization of Compute Costs in Hybrid Clouds with Full Rescheduling*. The material includes one the one hand the source code for the presented scheduling simulation and on the other hand the complete results of the case study (the paper only presented some of the results). 

## Full Rescheduling

A ***full rescheduling*** strategy is an online scheduling strategythat considers already running tasks as well as queued tasks.The strategy’s scheduler should be called repeatedly on everychange of the environment in order to allow the scheduler toreact to those changes and yield a new schedule.

## Implementation

To study full rescheduling, we designed assessment method that allows us to compare online schedulers with and without the ability of full rescheduling. This assessment method consists of two steps: 

1.  The first step is to simulate scheduling decisions based on a dataset of recorded tasks. The simulation software we implemented for this step is located in `./source-code/simulator/` and is based on the [ALEA scheduling simulator][1].
2.  The second step is to apply the cost model in order to obtain the computation costs. For this we set up a spread sheet located in `./source-code/cost-model/`.

## Case Study Results

All the case study's results are located in `./results/` and include

 - the schedule diagrams for all scenarios;
 - the resource usage diagram for all scenarios
 - the public cloud resource usage diagram; and
 - the estimated resource usage cost diagram.

[1]: https://github.com/aleasimulator/alea
