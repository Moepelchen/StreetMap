//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.27 at 03:44:27 PM CET 
//


package streetmap.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StreetTemplate complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="StreetTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LaneTemplates" type="{}LaneTemplates"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StreetTemplate", propOrder = {
        "name",
        "laneTemplates"
})
public class StreetTemplate {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "LaneTemplates", required = true)
    protected LaneTemplates laneTemplates;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the laneTemplates property.
     *
     * @return possible object is
     *         {@link LaneTemplates }
     */
    public LaneTemplates getLaneTemplates() {
        return laneTemplates;
    }

    /**
     * Sets the value of the laneTemplates property.
     *
     * @param value allowed object is
     *              {@link LaneTemplates }
     */
    public void setLaneTemplates(LaneTemplates value) {
        this.laneTemplates = value;
    }

}
