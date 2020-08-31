/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon.rescheduling;

import gridsim.GridSim;
import xklusac.environment.ComplexGridlet;

/**
 *
 * @author sophoan
 */
public class RescheduledGridlet extends ComplexGridlet {
    
    public RescheduledGridlet(ComplexGridlet gl){
        super(gl.getGridletID(), 
                gl.getUser(), 
                gl.getJobLimit(), 
                gl.getGridletLength(),
                gl.getEstimatedLength(),
                gl.getGridletFileSize(),
                gl.getGridletOutputSize(),
                gl.getOpSystemRequired(),
                gl.getArchRequired(),
                gl.getArrival_time(),
                gl.getDue_date(),
                gl.getPriority(),
                gl.getNumPE(),
                gl.getEstimatedMachine(),
                gl.getQueue(),
                gl.getProperties(),
                gl.getPercentage(),
                gl.getRam(),
                gl.getNumNodes(),
                gl.getPpn()
                );
        setUserID(GridSim.getEntityId("Alea_3.0_scheduler"));
    }
    
    public RescheduledGridlet(int gridletID, String user, long job_limit, double gridletLength, double estimatedLength, long gridletFileSize, long gridletOutputSize, String oSrequired, String archRequired, double arrival_time, double due_date, int priority, int numPE, double estMach, String queue, String properties, double percentage, long ram, int numNodes, int ppn) {
        super(gridletID, user, job_limit, gridletLength, estimatedLength, gridletFileSize, gridletOutputSize, oSrequired, archRequired, arrival_time, due_date, priority, numPE, estMach, queue, properties, percentage, ram, numNodes, ppn);
    }
    
}
