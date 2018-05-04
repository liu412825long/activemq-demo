package guo.examples.mq02.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ProducerServiceImpl implements ProducerService {

  private JmsTemplate jmsTemplate;

  /**
   * 向指定队列发送消息
   */
  public void sendMessage(Destination destination, final String msg) {
    System.out.println("ProducerService向队列" + destination.toString() + "发送了消息：\t" + msg);
    jmsTemplate.send(destination, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(msg);
      }
    });
  }

  /**
   * 向默认队列发送消息
   */
  public void sendMessage(final String msg) {
    String destination = jmsTemplate.getDefaultDestination().toString();
    System.out.println("ProducerService向队列" + destination + "发送了消息：\t" + msg);
    jmsTemplate.send(new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(msg);
      }
    });

  }

  public void sendMessage(Destination destination, final String msg, final Destination response) {
    System.out.println("ProducerService向队列" + destination + "发送了消息：\t" + msg);
    jmsTemplate.send(destination, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        TextMessage textMessage = session.createTextMessage(msg);
        textMessage.setJMSReplyTo(response);
        return textMessage;
      }
    });
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

}
