
package streetmap.xml.jaxb.streets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StreetTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StreetTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSpecial" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="imagePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="imageId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="menuImage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="menuImageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LaneTemplates" type="{}LaneTemplates"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StreetTemplate", propOrder = {
    "name",
    "isSpecial",
    "imagePath",
    "imageId",
    "menuImage",
    "menuImageId",
    "laneTemplates"
})
public class StreetTemplate {

    @XmlElement(required = true)
    protected String name;
    protected boolean isSpecial;
    @XmlElement(required = true)
    protected String imagePath;
    protected int imageId;
    @XmlElement(required = true)
    protected String menuImage;
    @XmlElement(required = true)
    protected String menuImageId;
    @XmlElement(name = "LaneTemplates", required = true)
    protected LaneTemplates laneTemplates;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the isSpecial property.
     * 
     */
    public boolean isIsSpecial() {
        return isSpecial;
    }

    /**
     * Sets the value of the isSpecial property.
     * 
     */
    public void setIsSpecial(boolean value) {
        this.isSpecial = value;
    }

    /**
     * Gets the value of the imagePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the value of the imagePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagePath(String value) {
        this.imagePath = value;
    }

    /**
     * Gets the value of the imageId property.
     * 
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Sets the value of the imageId property.
     * 
     */
    public void setImageId(int value) {
        this.imageId = value;
    }

    /**
     * Gets the value of the menuImage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenuImage() {
        return menuImage;
    }

    /**
     * Sets the value of the menuImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenuImage(String value) {
        this.menuImage = value;
    }

    /**
     * Gets the value of the menuImageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenuImageId() {
        return menuImageId;
    }

    /**
     * Sets the value of the menuImageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenuImageId(String value) {
        this.menuImageId = value;
    }

    /**
     * Gets the value of the laneTemplates property.
     * 
     * @return
     *     possible object is
     *     {@link LaneTemplates }
     *     
     */
    public LaneTemplates getLaneTemplates() {
        return laneTemplates;
    }

    /**
     * Sets the value of the laneTemplates property.
     * 
     * @param value
     *     allowed object is
     *     {@link LaneTemplates }
     *     
     */
    public void setLaneTemplates(LaneTemplates value) {
        this.laneTemplates = value;
    }

}
