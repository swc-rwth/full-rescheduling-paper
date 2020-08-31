package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import chart.visualizer.Runner;

@RunWith(Parameterized.class)
public class RunnerTestFCFS {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{"10_40"},
			{"15_40"},
			{"20_40"},
			{"25_40"},
			{"30_40"},
			{"35_40"},
			{"40_40"},
			{"45_40"}
		});
	}
	
	private String baseDir = "./results/";
	// parameters
	private String dir;
	
	
	public RunnerTestFCFS(String dir) {
		this.dir = dir;
	}
	
	@Test
	public void testMainFCFS() throws Exception {
		String resultDir = "data.swf/1-FCFS";
		String simResultFilename = "jobs_0_data.swf_exact_bez.csv";
		String usageResultFilename = "detail_event_usage_.csv";
		Runner.main(new String[] {
				"-d",
				baseDir + dir + "/" + resultDir,
				"-e",
				simResultFilename,
				"-u",
				usageResultFilename,
				"-m",
				"cli"
		});
	}

	@Test
	public void testMainFCFSRescheduling() throws Exception {
		String resultDir = "data.swf/2-FCFSRescheduling/";
		String simResultFilename = "jobs_800_data.swf_exact_bez.csv";
		String usageResultFilename = "detail_event_usage_.csv";
		Runner.main(new String[] {
				"-d",
				baseDir + dir + "/" + resultDir,
				"-e",
				simResultFilename,
				"-u",
				usageResultFilename,
				"-m",
				"cli"
		});
	}
	
}
