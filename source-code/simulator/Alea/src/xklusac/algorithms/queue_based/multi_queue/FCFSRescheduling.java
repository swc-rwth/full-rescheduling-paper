/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xklusac.algorithms.queue_based.multi_queue;

import addon.extension.Debugger;
import addon.rescheduling.RescheduledGridlet;
import alea.core.AleaSimTags;
import gridsim.AllocPolicy;
import java.util.Date;
import gridsim.GridSim;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import xklusac.algorithms.SchedulingPolicy;
import xklusac.environment.ComplexGridlet;
import xklusac.environment.ExperimentSetup;
import xklusac.environment.GridletInfo;
import xklusac.environment.ResourceInfo;
import xklusac.environment.Scheduler;
import xklusac.extensions.EndTimeComparator;
import xklusac.extensions.Schedule_Visualizator;
import xklusac.extensions.SchedulingEvent;
import addon.rescheduling.ReschedulablePolicy;
import java.io.IOException;
import xklusac.extensions.Output;

/**
 * Class FCFS<p>
 * Implements FCFS algorithm.
 *
 * @author Dalibor Klusacek
 */
public class FCFSRescheduling extends FCFS implements ReschedulablePolicy {

    private Scheduler scheduler;
    
    private int reschedulingCount = 0;

    public FCFSRescheduling(Scheduler scheduler) {
        super(scheduler);
        this.scheduler = scheduler;
    }

    @Override
    public void addNewJob(GridletInfo gi) {
        Debugger.debug(String.format("Add new job %d", gi.getGridlet().getGridletID()));
        double current_time = GridSim.clock();
        double runtime1 = new Date().getTime();
        int index = Scheduler.all_queues_names.indexOf(gi.getQueue());
        if (index == -1 || ExperimentSetup.by_queue == false) {
            index = 0;
        }

        LinkedList queue = Scheduler.all_queues.get(index);
        if (gi.getGridlet() instanceof RescheduledGridlet)
        {
            queue.addFirst(gi);
        } else{
           queue.addLast(gi); 
        }
        Scheduler.runtime += (new Date().getTime() - runtime1);
        //System.out.println(gi.getID()+ " has been received by FCFS");
    }

//    @Override
//    public int selectJob() {
//        //System.out.println("Selecting job by FCFS...");
//        int scheduled = 0;
//        ResourceInfo r_cand = null;
//        for (int q = 0; q < Scheduler.all_queues.size(); q++) {
//            Scheduler.queue = Scheduler.all_queues.get(q);
//            for (int i = 0; i < Scheduler.queue.size(); i++) {
//                GridletInfo gi = (GridletInfo) Scheduler.queue.get(i);
//                for (int j = 0; j < Scheduler.resourceInfoList.size(); j++) {
//                    ResourceInfo ri = (ResourceInfo) Scheduler.resourceInfoList.get(j);
//
//                    if (Scheduler.isSuitable(ri, gi) && ri.canExecuteNow(gi)) {
//                        r_cand = ri;
//                        //Priotize to select private cloud
//                        if (ri.resource.getResourceName().startsWith("Pv")){
//                            break;
//                        }
//                    }
//                }
//                if (r_cand != null) {
//                    gi = (GridletInfo) Scheduler.queue.remove(i);
//                    //System.err.println(gi.getID()+" PEs size = "+gi.PEs.size());
//                    //scheduler.logJobSubmit(gi.getGridlet(), r_cand.resource.getResourceID());
//                    r_cand.addGInfoInExec(gi);
//                    // set the resource ID for this gridletInfo (this is the final scheduling decision)
//                    gi.setResourceID(r_cand.resource.getResourceID());
//                    scheduler.submitJob(gi.getGridlet(), r_cand.resource.getResourceID());
//                    r_cand.is_ready = true;
//                    //scheduler.sim_schedule(GridSim.getEntityId("Alea_3.0_scheduler"), 0.0, AleaSimTags.GRIDLET_SENT, gi);
//                    Debugger.debug(gi.getID()+": sended on res="+r_cand.resource.getResourceName()+" resFree="+r_cand.getNumFreePE()+" req="+gi.getNumPE());
//                    scheduled++;
//                    r_cand = null;
//                    i--;
//                    return scheduled;
//                } else {
//                    return scheduled;
//                }
//            }
//        }
//        return scheduled;
//    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void reschedule() {
        double delay = ExperimentSetup.reschedulingDelay;
        double reschedulingLowLimit = 0;
        ArrayList<ResourceInfo> resourceInfoList = Scheduler.resourceInfoList;
        
        ResourceInfo cloudResource = null;
        ResourceInfo privateResource = null;
        //check for running task in the public cloud
        for (ResourceInfo r : resourceInfoList){
           if (r.resource.getResourceName().startsWith("Pc") && r.resInExec.size() > 0){
                double remaining = r.resInExec.get(0).getGridlet().getGridletLength() - (GridSim.clock() - r.resInExec.get(0).getGridlet().getExecStartTime());
                Debugger.debug("Gridlet " + r.resInExec.get(0).getID() + " remains " + remaining);
                if (remaining > reschedulingLowLimit){
                    Debugger.debug("There is running task in the cloud to reschedule");
                    cloudResource = r;
                    break;
                }
           }
        }
        if (cloudResource != null){
            //check for empty resource in the private cloud
            for (ResourceInfo r : resourceInfoList){
               if (r.resource.getResourceName().startsWith("Pv") && r.resInExec.size() == 0){
                   Debugger.debug("There is idle resource to reschedule");
                   privateResource = r;
                   break;
               }
            }
        }
        
        if (privateResource != null && cloudResource != null){
            Debugger.debug("******Rescheduling******");
            reschedulingCount++;
            ComplexGridlet glToStop = cloudResource.resInExec.get(0).getGridlet();
            RescheduledGridlet glToStart;
            glToStart = new RescheduledGridlet(glToStop);
            if (delay < 1){
                delay = glToStart.getGridletLength() * delay;
            }
            double newLength = glToStop.getGridletLength() - (GridSim.clock() - glToStop.getExecStartTime()) + delay;
            //TODO Verify how to calculate the remaining lenght
            glToStart.setGridletLength(newLength);
            glToStart.setEstimatedLength(glToStart.getGridletLength());
            glToStart.setArrival_time(GridSim.clock());
            glToStart.setRelease_date(GridSim.clock());
            glToStart.setJobLimit((long)glToStart.getGridletLength());
            glToStart.setExpectedFinishTime(GridSim.clock() + glToStart.getGridletLength());
            glToStop.setGridletLength(1);
            for (int i = 0; i < ExperimentSetup.local_schedulers.size(); i++){
                AllocPolicy p = (AllocPolicy)ExperimentSetup.local_schedulers.get(i);
                if (p.gridletStatus(glToStop.getGridletID(), glToStop.getUserID()) >= 0){
                    p.gridletSubmit(glToStop, true);
                }
            }
            //((AllocPolicy)ExperimentSetup.local_schedulers.get(2)).gridletSubmit(glToStop, true);
            ExperimentSetup.scheduler.sim_schedule(GridSim.getEntityId("Alea_3.0_scheduler"), 0, AleaSimTags.GRIDLET_INFO, glToStart);
            
        }
    }

    @Override
    public void printSummary() {
        System.out.println("***********Rescheduling Summary*************");
        System.out.println("N. of Reschedule: " + reschedulingCount);
    }
    
    @Override
    public void saveSummary(Output out, String filePath){
        try {
            out.writeString(filePath, "N. of Reschedule: " + reschedulingCount);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
