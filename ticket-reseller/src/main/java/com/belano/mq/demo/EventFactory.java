package com.belano.mq.demo;

import java.io.StringReader;
import java.util.logging.*;

import javax.jms.JMSException;
import javax.jms.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class EventFactory {

  private static final Logger logger = Logger.getLogger("com.belano.mq.demo");

  public static Event newEventFromMessage(Message message) {
    Event event = null;
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Event.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      event = (Event) jaxbUnmarshaller.unmarshal(new StringReader(message.getBody(String.class)));
    }
    catch (JAXBException e) {
      logger.warning("XML Errors detected parsing Event Message");
      e.printStackTrace();
    }
    catch (JMSException e)
    {
      logger.warning("JMS Errors detected parsing Event Message");
      e.printStackTrace();
    }
    return event;
  }
}
