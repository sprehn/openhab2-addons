/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.17 at 06:25:15 PM CET 
//


package org.openhab.ui.cometvisu.internal.config.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for multitrigger complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="multitrigger"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="layout" type="{}layout" minOccurs="0"/&gt;
 *         &lt;element name="label" type="{}label" minOccurs="0"/&gt;
 *         &lt;element name="address" type="{}address" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="showstatus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button1label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button1value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button2label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button2value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button3label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button3value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button4label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="button4value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute ref="{}flavour"/&gt;
 *       &lt;attribute ref="{}mapping"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "multitrigger", propOrder = {
    "layout",
    "label",
    "address"
})
public class Multitrigger {

    protected Layout layout;
    protected Label label;
    @XmlElement(required = true)
    protected List<Address> address;
    @XmlAttribute(name = "showstatus")
    protected String showstatus;
    @XmlAttribute(name = "button1label")
    protected String button1Label;
    @XmlAttribute(name = "button1value")
    protected String button1Value;
    @XmlAttribute(name = "button2label")
    protected String button2Label;
    @XmlAttribute(name = "button2value")
    protected String button2Value;
    @XmlAttribute(name = "button3label")
    protected String button3Label;
    @XmlAttribute(name = "button3value")
    protected String button3Value;
    @XmlAttribute(name = "button4label")
    protected String button4Label;
    @XmlAttribute(name = "button4value")
    protected String button4Value;
    @XmlAttribute(name = "flavour")
    protected String flavour;
    @XmlAttribute(name = "mapping")
    protected String mapping;

    /**
     * Gets the value of the layout property.
     * 
     * @return
     *     possible object is
     *     {@link Layout }
     *     
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Sets the value of the layout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Layout }
     *     
     */
    public void setLayout(Layout value) {
        this.layout = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link Label }
     *     
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link Label }
     *     
     */
    public void setLabel(Label value) {
        this.label = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the address property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Address }
     * 
     * 
     */
    public List<Address> getAddress() {
        if (address == null) {
            address = new ArrayList<Address>();
        }
        return this.address;
    }

    /**
     * Gets the value of the showstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShowstatus() {
        return showstatus;
    }

    /**
     * Sets the value of the showstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShowstatus(String value) {
        this.showstatus = value;
    }

    /**
     * Gets the value of the button1Label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton1Label() {
        return button1Label;
    }

    /**
     * Sets the value of the button1Label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton1Label(String value) {
        this.button1Label = value;
    }

    /**
     * Gets the value of the button1Value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton1Value() {
        return button1Value;
    }

    /**
     * Sets the value of the button1Value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton1Value(String value) {
        this.button1Value = value;
    }

    /**
     * Gets the value of the button2Label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton2Label() {
        return button2Label;
    }

    /**
     * Sets the value of the button2Label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton2Label(String value) {
        this.button2Label = value;
    }

    /**
     * Gets the value of the button2Value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton2Value() {
        return button2Value;
    }

    /**
     * Sets the value of the button2Value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton2Value(String value) {
        this.button2Value = value;
    }

    /**
     * Gets the value of the button3Label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton3Label() {
        return button3Label;
    }

    /**
     * Sets the value of the button3Label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton3Label(String value) {
        this.button3Label = value;
    }

    /**
     * Gets the value of the button3Value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton3Value() {
        return button3Value;
    }

    /**
     * Sets the value of the button3Value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton3Value(String value) {
        this.button3Value = value;
    }

    /**
     * Gets the value of the button4Label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton4Label() {
        return button4Label;
    }

    /**
     * Sets the value of the button4Label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton4Label(String value) {
        this.button4Label = value;
    }

    /**
     * Gets the value of the button4Value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getButton4Value() {
        return button4Value;
    }

    /**
     * Sets the value of the button4Value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setButton4Value(String value) {
        this.button4Value = value;
    }

    /**
     * Gets the value of the flavour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavour() {
        return flavour;
    }

    /**
     * Sets the value of the flavour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavour(String value) {
        this.flavour = value;
    }

    /**
     * Gets the value of the mapping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapping() {
        return mapping;
    }

    /**
     * Sets the value of the mapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapping(String value) {
        this.mapping = value;
    }

}
