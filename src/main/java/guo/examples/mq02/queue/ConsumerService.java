package guo.examples.mq02.queue;

import javax.jms.Destination;

public interface ConsumerService {
  public void receive(Destination queueDestination);
}
