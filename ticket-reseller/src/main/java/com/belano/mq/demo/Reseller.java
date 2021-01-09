package com.belano.mq.demo;

import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Message;
import javax.jms.Session;

public class Reseller {

  private static final Level LOGLEVEL = Level.ALL;
  private static final Logger logger = Logger.getLogger("com.belano.mq.demo");

  private static String DESTINATION_NAME = "newTickets";

  /**
   * Main method
   *
   * @param args
   */
  public static void main(String[] args) {
    initialiseLogging();
    logger.info("Reseller Application is starting");

    // Challenge : Connect to a queue manager
    Session session = SessionBuilder.connect();

    if (session != null) {
      // Challenge : Subscribes to topic
      TicketSubscriber ticketSubscriber = new TicketSubscriber(session, DESTINATION_NAME);
      TicketRequester ticketRequester = new TicketRequester(session);
      if (ticketSubscriber.isGood()) {
        logger.fine("Entering wait loop for event tickets");
        while (true) {
          // Challenge : Receives a publication
          try {
            Message message = ticketSubscriber.waitForPublish();
            if (message != null) {
              logger.fine("Tickets have been made available");

              // Challenge : Processes a publication
              int numToReserve = howMany(message);

              logger.fine(String.format("Sending request to purchase %d tickets over peer to peer", numToReserve));

              // Challenge : Receiving a publication triggers a put
              // then requests to purchase a batch of tickets
              String correlationID = ticketRequester.put(message, numToReserve);
              if (correlationID != null) {
                logger.fine("Request has been sent, waiting response from Event Booking System");
                // Challenge : Our reseller application does a get from this queue
                if (ticketRequester.get(correlationID)) {
                  logger.info("Tickets secured!");
                } else {
                  logger.info("No tickets reserved!");
                }
              }
            }
          } catch (PublishWaitException e) {
            logger.warning("Repeated Exceptions thrown while waiting for response");
            e.printStackTrace();
            break; // The while true loop
          }
        }
      }

      SessionBuilder.close(session);
    } else {
      logger.severe("Was unable to connect to MQ");
    }
    logger.info("Reseller Application is closing");
  }

  /**
   * Initialise the logging by switching off default logging and setting the log level to the desired level. The Default
   * logger is first removed to prevent duplication of INFO and above logs.
   *
   * @return None
   */
  private static void initialiseLogging() {
    Logger defaultLogger = Logger.getLogger("");
    Handler[] handlers = defaultLogger.getHandlers();
    if (handlers != null && handlers.length > 0) {
      defaultLogger.removeHandler(handlers[0]);
    }

    Handler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(LOGLEVEL);
    logger.addHandler(consoleHandler);

    logger.setLevel(LOGLEVEL);
    logger.finest("Logging initialised");
  }

  /**
   * Processes the publication, by extracting the details from the received Message and determining how many tickets to
   * request.
   * <p>
   * Challenge : Processes a publication
   *
   * @param message the Message that was received by the subscription
   * @return int the quantity of tickets to be requested.
   */
  private static int howMany(Message message) {
    Event event = EventFactory.newEventFromMessage(message);

    // getTitle
    // getTime
    // getLocation
    // getCapacity
    // getEventID

    String title = event.getTitle();
    int capacity = event.getCapacity();

    System.out.printf("There are %d tickets available for %s \n", capacity, title);
    return getRandomNumber(capacity);
  }

  private static int getRandomNumber(int max) {
    Random random = new Random();
    return random.ints(1, max)
        .findFirst()
        .getAsInt();
  }

}
