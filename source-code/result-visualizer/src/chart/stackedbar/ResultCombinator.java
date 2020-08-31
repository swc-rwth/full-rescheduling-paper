package chart.stackedbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import chart.data.Usage;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ResultCombinator {
	
	private static List<UsageResultEntry> usageResultEntries;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ArgumentParser parser = ArgumentParsers.newFor("result-combi").build()
				.defaultHelp(true)
                .description("Combine the results of scheduling simulation.");
		parser.addArgument("-d", "--dir")
				.required(true)
		        .help("Specify directory path of the result");
		
		Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        File file = new File(ns.getString("dir"));
		if (!file.exists()) {
			System.out.print("Invalid dir!!!");
			return;
		}
		if (!file.isDirectory()) {
			System.out.print("Invalid path, full path must be specified!!!");
			return;
		}
		DecimalFormat df = new DecimalFormat("#,###.##");
		usageResultEntries = new ArrayList<UsageResultEntry>();
		File[] envs = file.listFiles(File::isDirectory);
		for (File e : envs) {
			File[] d = e.listFiles(File::isDirectory);
			if (d.length < 1)
				throw new Exception("No result director.");
			File algorithms[] = d[0].listFiles(File::isDirectory);
			for (File algo : algorithms) {
				UsageResultEntry entry = new UsageResultEntry();
				entry.setEnvironment(e.getName());
				entry.setAlgorithm(algo.getName());
				String resultFilePath = algo.getAbsolutePath() + "/" + "summary.txt";
				BufferedReader reader = new BufferedReader(new FileReader(resultFilePath));;
				//Move to 4th line
				reader.readLine();
				reader.readLine();
				reader.readLine();
				//Getting result form 4th and 5th line
				for (int i = 0; i < 2; i++) {
					String line = reader.readLine();
					if (!(line.startsWith("Pv") || line.startsWith("Pc")))
						break;
					String[] s = line.split("\\t");
					System.out.println(line);
					entry.getTaskCounts().add(Integer.parseInt(s[1]));
					entry.getUsageTimes().add(df.parse(s[2]).doubleValue());
				}
				reader.close();
				usageResultEntries.add(entry);
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath() + "/" + "usage-result.csv"));
		usageResultEntries.sort(Comparator.comparing(UsageResultEntry::getEnvironment).thenComparing(UsageResultEntry::getAlgorithm));
		int r = 0;
		for (UsageResultEntry entry : usageResultEntries) {
			writer.write(String.format("%s,%s,", entry.getEnvironment(), entry.getAlgorithm()));
			writer.write(String.format("%d", entry.getTaskCounts().get(0)));
			writer.write(",");
			for (int i = 1; i < entry.getTaskCounts().size(); i++) {
				writer.write(String.format("%d", entry.getTaskCounts().get(i)));
			}
			writer.write(",");
			writer.write(String.format("%.2f", entry.getUsageTimes().get(0)));
			writer.write(",");
			for (int i = 1; i < entry.getTaskCounts().size(); i++) {
				writer.write(String.format("%.2f", entry.getUsageTimes().get(i)));
			}
			if (r % 2 == 1)
				writer.newLine();
			else
				writer.write(",");
			r++;
		}
		writer.close();
	}

}
