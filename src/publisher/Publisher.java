package publisher;

import service.PubSubService;
import message.Message;

public interface Publisher {
	
	void publish(Message message, PubSubService pubsubService);
	
}
