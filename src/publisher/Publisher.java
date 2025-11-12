package publisher;

import message.Message;
import service.PubSubService;

public interface Publisher {
	
	void publish(Message message, PubSubService pubsubService);
	
}
