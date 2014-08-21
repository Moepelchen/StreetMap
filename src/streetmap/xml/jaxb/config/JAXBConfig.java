
package streetmap.xml.jaxb.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JAXBConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JAXBConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="drawsides" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="drawtiles" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="drawanchors" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="drawlanes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tilesize" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JAXBConfig", propOrder = {
    "drawsides",
    "drawtiles",
    "drawanchors",
    "drawlanes",
    "tilesize",
    "width",
    "height"
})
public class JAXBConfig {

    protected Boolean drawsides;
    protected Boolean drawtiles;
    protected Boolean drawanchors;
    protected Boolean drawlanes;
    protected Double tilesize;
    protected Double width;
    protected Double height;

    /**
     * Gets the value of the drawsides property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDrawsides() {
        return drawsides;
    }

    /**
     * Sets the value of the drawsides property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDrawsides(Boolean value) {
        this.drawsides = value;
    }

    /**
     * Gets the value of the drawtiles property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDrawtiles() {
        return drawtiles;
    }

    /**
     * Sets the value of the drawtiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDrawtiles(Boolean value) {
        this.drawtiles = value;
    }

    /**
     * Gets the value of the drawanchors property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDrawanchors() {
        return drawanchors;
    }

    /**
     * Sets the value of the drawanchors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDrawanchors(Boolean value) {
        this.drawanchors = value;
    }

    /**
     * Gets the value of the drawlanes property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDrawlanes() {
        return drawlanes;
    }

    /**
     * Sets the value of the drawlanes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDrawlanes(Boolean value) {
        this.drawlanes = value;
    }

    /**
     * Gets the value of the tilesize property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTilesize() {
        return tilesize;
    }

    /**
     * Sets the value of the tilesize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTilesize(Double value) {
        this.tilesize = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWidth(Double value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHeight(Double value) {
        this.height = value;
    }

}
