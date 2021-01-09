package com.belano.mq.demo;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestTickets {

  private int eventID;
  private String title;
  private String time;
  private String location;
  private int numberRequested;

  public int getEventID() {
    return eventID;
  }

  @XmlElement
  public void setEventID(int eventID) {
    this.eventID = eventID;
  }

  public String getTitle() {
    return title;
  }

  @XmlElement
  public void setTitle(String title) {
    this.title = title;
  }

  public String getTime() {
    return time;
  }

  @XmlElement
  public void setTime(String time) {
    this.time = time;
  }

  public String getLocation() {
    return location;
  }

  @XmlElement
  public void setLocation(String location) {
    this.location = location;
  }

  public int getNumberRequested() {
    return numberRequested;
  }

  @XmlElement
  public void setNumberRequested(int numberRequested) {
    this.numberRequested = numberRequested;
  }

  public RequestTickets(Event event, int numberRequested) {
    super();
    this.eventID = event.getEventID();
    this.title = event.getTitle();
    this.time = event.getTime();
    this.location = event.getLocation();
    this.numberRequested = numberRequested;
  }

  public RequestTickets() {
    super();
    this.eventID = 0;
    this.title = "";
    this.time = "";
    this.location = "";
    this.numberRequested = 0;
  }

  public String toXML() throws JAXBException {
    StringWriter writer = new StringWriter();
    JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

    // output pretty printed
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    jaxbMarshaller.marshal(this, writer);
    return writer.toString();
  }

}
