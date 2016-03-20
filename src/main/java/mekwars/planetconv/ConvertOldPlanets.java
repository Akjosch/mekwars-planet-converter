package mekwars.planetconv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import mekwars.planetconv.data.Planet;
import mekwars.planetconv.data.Planets;

public final class ConvertOldPlanets {
	private static int intval(String val) {
		return (null != val && val.length() > 0) ? Integer.valueOf(val) : 0;
	}
	
	private static double doubleval(String val) {
		return (null != val && val.length() > 0) ? Double.valueOf(val) : 0.0;
	}
	
	private static boolean boolval(String val) {
		return (null != val) && (val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("true"));
	}
	
	public static void main(String[] args) throws IOException, JAXBException {
		String dataDir = "planets";
		if( args.length > 0 && args[0].length() > 0 ) {
			dataDir = args[0];
		}
		
		File data = new File(dataDir);
		if( !data.isDirectory() ) {
			System.err.println(String.format("Data directory '%s' is missing.", dataDir));
			System.exit(-1);
		}
		
		Planets result = new Planets();
		File[] files = data.listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.toLowerCase(Locale.ROOT).endsWith(".dat");
			}
		});
		
		for( File file : files ) {
			try( BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
				String line = reader.readLine();
				if( null == line || line.length() < 4 || !line.startsWith("PL#") ) {
					// Invalid record, ignore
					continue;
				}
				Planet planet = new Planet();
				Queue<String> linedata = new LinkedList<String>(Arrays.asList(line.split("#")));
				linedata.poll(); // skip "PL"
				try {
					planet.name = linedata.poll();
					planet.compProduction = intval(linedata.poll());
					int factories = intval(linedata.poll());
					if( factories > 0 ) {
						planet.factories = new ArrayList<Planet.Factory>(factories);
					}
					for( int i = 0; i < factories; ++ i ) {
						String[] factData = linedata.poll().split("\\*");
						Planet.Factory factory = new Planet.Factory();
						factory.name = factData[1];
						factory.size = factData[2];
						factory.founder = factData[3];
						// This seems to be constant
						factory.timeZone = new Planet.TimeZone();
						factory.timeZone.name = "3025";
						factory.timeZone.probability = 100;
						switch(intval(factData[7])) {
							case 1:
								factory.types = Arrays.asList("Mek");
							case 2:
								factory.types = Arrays.asList("Vehicle");
							case 3:
								factory.types = Arrays.asList("Vehicle", "Mek");
							default:
								// There's a bunch of others. Should we care?
								break;
						}
						planet.factories.add(factory);
					}
					planet.xCoord = doubleval(linedata.poll());
					planet.yCoord = doubleval(linedata.poll());
					String[] influences = linedata.poll().split("\\$");
					planet.influences = new ArrayList<Planet.Influence>(influences.length / 2);
					for( int i = 0; i < influences.length; i += 2 ) {
						planet.influences.add(new Planet.Influence(influences[i], intval(influences[i + 1])));
					}
					int continents = intval(linedata.poll());
					planet.continents = new ArrayList<Planet.Continent>(continents);
					for( int i = 0; i < continents; ++ i ) {
						int contSize = intval(linedata.poll());
						String contName = linedata.poll();
						planet.continents.add(new Planet.Continent(contName, "temperate", contSize));
					}
					planet.desc = linedata.poll();
					linedata.poll(); // "bays provided" - ignore
					linedata.poll(); // "conquerable" - ignore
					linedata.poll(); // "timestamp" - ignore
					linedata.poll(); // "id" - ignore
					planet.xMapSize = intval(linedata.poll());
					planet.yMapSize = intval(linedata.poll());
					planet.xBoardSize = intval(linedata.poll());
					planet.yBoardSize = intval(linedata.poll());
					planet.lowTemperature = intval(linedata.poll());
					planet.highTemperature = intval(linedata.poll());
					planet.gravity = doubleval(linedata.poll());
					planet.vacuum = boolval(linedata.poll());
					linedata.poll(); // "chance" - ignore
					linedata.poll(); // "mod" - ignore
					linedata.poll(); // "min planet ownership" - ignore
					linedata.poll(); // "home world" - ignore
					linedata.poll(); // "original owner" - ignore
					linedata.poll(); // "flags" - ignore
					linedata.poll(); // "conquest points" - ignore
					
					result.planets.add(planet);
				} catch( Exception ex ) {
					System.err.println(line);
					ex.printStackTrace();
				}
			}
		}
		
		System.out.println("Read " + result.planets.size() + " planets, writing them out to planets.xml");
		
		JAXBContext context = JAXBContext.newInstance(Planets.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		try( PrintStream os = new PrintStream("planets.xml") ) {
			// Header. We don't care, but MekWars might.
			os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE DOCUMENT SYSTEM \"planets.dtd\">");
			marshaller.marshal(result, os);
		}
	}
}
