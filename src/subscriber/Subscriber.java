package subscriber;

import java.util.ArrayList;
import java.util.List;
import message.Message;
import service.PubSubService;


public abstract class Subscriber {
	
	// Cada assinante "tem" sua própria lista de mensagens.
	private List<Message> subscriberMessages = new ArrayList<Message>();
	
	// Getters e Setters para a lista de mensagens
	public List<Message> getSubscriberMessages() {
		return subscriberMessages;
	}
	public void setSubscriberMessages(List<Message> subscriberMessages) {
		this.subscriberMessages = subscriberMessages;
	}
	
	// --- Métodos Abstratos ---
	
	// "Todo assinante deve saber como se adicionar a um tópico"
	public abstract void addSubscriber(String topic, PubSubService pubSubService);
	
	// "Todo assinante deve saber como se remover de um tópico"
	public abstract void unSubscribe(String topic, PubSubService pubSubService);
	
	// "Todo assinante deve saber como pedir suas mensagens"
	public abstract void getMessagesForSubscriberOfTopic(String topic, PubSubService pubSubService);
	
	// --- Método Concreto ---
	
	// Imprime todas as mensagens que este assinante recebeu
	public void printMessages(){
		for(Message message : subscriberMessages){
			System.out.println("Message Topic -> "+ message.getTopic() + " : " + message.getPayload());
		}
	}
}