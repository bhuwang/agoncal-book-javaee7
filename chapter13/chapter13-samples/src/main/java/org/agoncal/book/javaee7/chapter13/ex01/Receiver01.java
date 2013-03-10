package org.agoncal.book.javaee7.chapter13.ex01;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 7 with Glassfish 4
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 */
public class Receiver01 {

  // ======================================
  // =           Public Methods           =
  // ======================================

  public static void main(String[] args) {

    try {
      // Gets the JgetTopicName()NDI context
      Context jndiContext = new InitialContext();

      // Looks up the administered objects
      ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/javaee7/ConnectionFactory");
      Queue queue = (Queue) jndiContext.lookup("jms/javaee7/Queue");

      // Creates the needed artifacts to connect to the queue
      Connection connection = connectionFactory.createConnection();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageConsumer consumer = session.createConsumer(queue);
      connection.start();

      // Loops to receive the messages
      System.out.println("\nInfinite loop. Waiting for a message...");
      while (true) {
        TextMessage message = (TextMessage) consumer.receive();
        System.out.println("Message received: " + message.getText());
      }

    } catch (NamingException | JMSException e) {
      e.printStackTrace();
    }
  }
}