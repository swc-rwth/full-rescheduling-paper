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
public class DetailTestFCFS {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{"10_40"},
			{"11_40"},
			{"12_40"},
			{"13_40"},
			{"14_40"},
			{"15_40"},
			{"16_40"},
			{"17_40"},
			{"18_40"},
			{"19_40"},
			{"20_40"},
			{"21_40"},
			{"22_40"},
			{"23_40"},
			{"24_40"},
			{"25_40"},
			{"26_40"},
			{"27_40"},
			{"28_40"},
			{"29_40"},
			{"30_40"},
			{"31_40"},
			{"32_40"},
			{"33_40"},
			{"34_40"},
			{"35_40"},
			{"36_40"},
			{"37_40"},
			{"38_40"},
			{"39_40"},
			{"40_40"},
			{"41_40"},
			{"42_40"},
			{"43_40"},
			{"44_40"},
			{"45_40"}
		});
	}
	
	private String baseDir = "./results/";
	// parameters
	private String dir;
	
	
	public DetailTestFCFS(String dir) {
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
