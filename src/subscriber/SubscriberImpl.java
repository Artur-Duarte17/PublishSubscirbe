package subscriber;

import service.PubSubService;


public class SubscriberImpl extends Subscriber {
	
	public void addSubscriber(String topic, PubSubService pubSubService) {
		pubSubService.addSubscriber(topic, this);
	}
	
	public void unSubscribe(String topic, PubSubService pubSubService) {
		pubSubService.removeSubscriber(topic, this);
	}
	
	public void getMessagesForSubscriberOfTopic(String topic, PubSubService pubSubService) {
		pubSubService.getMessagesForSubscriberOfTopic(topic, this);
	}
}