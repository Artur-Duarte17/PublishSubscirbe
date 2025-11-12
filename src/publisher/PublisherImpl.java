package publisher;

import message.Message;
import service.PubSubService;


public class PublisherImpl implements Publisher {
	
	// Aqui está a implementação do método exigido pelo contrato
	public void publish(Message message, PubSubService pubSubService) {
		pubSubService.addMessageToQueue(message);
	}
}