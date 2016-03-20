package mekwars.planetconv.data;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PLANET")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Planet {
	@XmlElement(name="NAME") public String name;
	@XmlElement(name="COMPPRODUCTION") public Integer compProduction;
	@XmlElement(name="XCOOD") public Double xCoord;
	@XmlElement(name="YCOOD") public Double yCoord;
	@XmlElementWrapper(name="INFLUENCE") @XmlElement(name="INF") public List<Influence> influences;
	@XmlElement(name="CONTINENT") public List<Continent> continents;
	@XmlElement(name="UNITFACTORY") public List<Factory> factories;
	@XmlElement(name="DESCRIPTION") public String desc;
	@XmlElement(name="XMAP") public Integer xMapSize;
	@XmlElement(name="YMAP") public Integer yMapSize;
	@XmlElement(name="XBOARD") public Integer xBoardSize;
	@XmlElement(name="YBOARD") public Integer yBoardSize;
	@XmlElement(name="LOWTEMP") public Integer lowTemperature;
	@XmlElement(name="HITEMP") public Integer highTemperature;
	@XmlElement(name="GRAVITY") public Double gravity;
	@XmlElement(name="VACUUM") public Boolean vacuum;
	
	@XmlRootElement(name="INF")
	public static class Influence {
		@XmlElement(name="FACTION") public String faction;
		@XmlElement(name="AMOUNT") public Integer amount;
		
		public Influence(String faction, int amount) {
			this.faction = Objects.requireNonNull(faction);
			this.amount = Integer.valueOf(amount);
		}
		
		@SuppressWarnings("unused")
		private Influence() {} // for JAXB
	}
	
	@XmlRootElement(name="CONTINENT")
	public static class Continent {
		@XmlElement(name="TERRAIN") public String terrain;
		@XmlElement(name="ADVTERRAIN") public String climate;
		@XmlElement(name="SIZE") public Integer size;
		
		public Continent(String terrain, String climate, int size) {
			this.terrain = Objects.requireNonNull(terrain);
			this.climate = Objects.requireNonNull(climate);
			this.size = size;
		}
		
		@SuppressWarnings("unused")
		private Continent() {} // for JAXB
	}
	
	@XmlRootElement(name="UNITFACTORY")
	public static class Factory {
		@XmlElement(name="FACTORYNAME") public String name;
		@XmlElement(name="SIZE") public String size;
		@XmlElement(name="FOUNDER") public String founder;
		@XmlElement(name="TIMEZONE") public TimeZone timeZone;
		@XmlElement(name="TYPE") public List<String> types;
	}
	
	@XmlRootElement(name="TIMEZONE")
	public static class TimeZone {
		@XmlElement(name="TIMEZONENAME") public String name;
		@XmlElement(name="TIMEZONEPROPABILITY") public Integer probability;
	}
}
