/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon.rescheduling;

import xklusac.algorithms.SchedulingPolicy;
import xklusac.environment.Scheduler;
import xklusac.extensions.Output;

/**
 *
 * @author sophoan
 */
public interface ReschedulablePolicy extends SchedulingPolicy {
    Scheduler getScheduler();
    void reschedule();
    void printSummary();
    void saveSummary(Output out, String filePath);
}
