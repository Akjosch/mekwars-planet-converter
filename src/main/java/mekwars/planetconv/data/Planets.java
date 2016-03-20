package mekwars.planetconv.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DOCUMENT")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Planets {
	@XmlElementWrapper(name="MEGAMEKNETPLANETDATA") @XmlElement(name="PLANET") public List<Planet> planets;
	
	public Planets() {
		planets = new ArrayList<Planet>();
	}
}
