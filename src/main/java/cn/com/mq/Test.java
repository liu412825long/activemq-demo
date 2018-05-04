package cn.com.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 一个生产者和一个消费者
 * @author Administrator
 *
 */
public class Test {
	
	public static void main(String[] args) throws JMSException {
		productor();
		consumer();
	}
	
	public static  void productor() throws JMSException {
		ConnectionFactory factory=new ActiveMQConnectionFactory();
		Connection connection=factory.createConnection();
		connection.start();
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue=session.createQueue("mq");
		MessageProducer messageProducer=session.createProducer(queue);
		TextMessage textMessage=session.createTextMessage("hello world");
		messageProducer.send(textMessage);
		messageProducer.close();
		session.close();
		connection.close();
	}
	
	public static void consumer() throws JMSException {
		ConnectionFactory factory=new ActiveMQConnectionFactory();
		Connection connection=factory.createConnection();
		connection.start();
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue=session.createQueue("mq");
		MessageConsumer messageConsumer=session.createConsumer(queue);
		messageConsumer.setMessageListener(new MessageListener() {
			
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				if(message instanceof TextMessage) {
					try {
						String text=((TextMessage) message).getText();
						System.out.println(text);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		messageConsumer.close();
		session.close();
		connection.close();

	}

}
