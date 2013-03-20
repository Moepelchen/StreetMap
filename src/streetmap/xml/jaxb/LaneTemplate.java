//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.06 at 04:50:50 PM CET 
//

package streetmap.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for LaneTemplate complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="LaneTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isStartPoint" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isEndPoint" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="from" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="to" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LaneTemplate", propOrder = {
		"isStartPoint",
		"isEndPoint",
		"from",
		"to"
})
public class LaneTemplate
{

	protected boolean isStartPoint;
	protected boolean isEndPoint;
	@XmlElement(required = true)
	protected String from;
	@XmlElement(required = true)
	protected String to;

	/**
	 * Gets the value of the isStartPoint property.
	 */
	public boolean isIsStartPoint()
	{
		return isStartPoint;
	}

	/**
	 * Sets the value of the isStartPoint property.
	 */
	public void setIsStartPoint(boolean value)
	{
		this.isStartPoint = value;
	}

	/**
	 * Gets the value of the isEndPoint property.
	 */
	public boolean isIsEndPoint()
	{
		return isEndPoint;
	}

	/**
	 * Sets the value of the isEndPoint property.
	 */
	public void setIsEndPoint(boolean value)
	{
		this.isEndPoint = value;
	}

	/**
	 * Gets the value of the from property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	public String getFrom()
	{
		return from;
	}

	/**
	 * Sets the value of the from property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setFrom(String value)
	{
		this.from = value;
	}

	/**
	 * Gets the value of the to property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	public String getTo()
	{
		return to;
	}

	/**
	 * Sets the value of the to property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setTo(String value)
	{
		this.to = value;
	}

}
