package service;

import message.Message;
import subscriber.Subscriber;

import java.util.*;

public class PubSubService {
	
	//    - Map: Para associar a Chave (String do Tópico) ao Valor (O Set)
	//    - Set: Para garantir que um assinante não se inscreva 2x no mesmo tópico
	Map<String, Set<Subscriber>> subscribersTopicMap = new HashMap<String, Set<Subscriber>>();
	
	// 2. A Fila de Mensagens:
	Queue<Message> messagesQueue = new LinkedList<Message>();
	
	// --- MÉTODOS ---
	
	// Chamado pelo Publicador. Simplesmente joga a mensagem na fila.
	public void addMessageToQueue(Message message) {
		messagesQueue.add(message);
	}
	
	// Chamado pelo Assinante.
	public void addSubscriber(String topic, Subscriber subscriber) {
		
		// Se o tópico já existe no mapa...
		if (subscribersTopicMap.containsKey(topic)) {
			// Pega o conjunto de assinantes daquele tópico
			Set<Subscriber> subscribers = subscribersTopicMap.get(topic);
			// Adiciona o novo assinante ao conjunto
			subscribers.add(subscriber);
			// Atualiza o mapa
			subscribersTopicMap.put(topic, subscribers);
		} else {
			// Se o tópico é novo...
			// Cria um novo conjunto
			Set<Subscriber> subscribers = new HashSet<Subscriber>();
			// Adiciona o assinante
			subscribers.add(subscriber);
			// Coloca o novo tópico e o novo conjunto no mapa
			subscribersTopicMap.put(topic, subscribers);
		}
	}
	
	// Chamado pelo Assinante para cancelar.
	public void removeSubscriber(String topic, Subscriber subscriber) {
		if (subscribersTopicMap.containsKey(topic)) {
			Set<Subscriber> subscribers = subscribersTopicMap.get(topic);
			subscribers.remove(subscriber); // Simplesmente remove do conjunto
			subscribersTopicMap.put(topic, subscribers);
		}
	}
	
	// A "MÁGICA": Transmitir mensagens para todos os assinantes.
	public void broadcast() {
		if (messagesQueue.isEmpty()) {
			System.out.println("No messages from publishers to display");
		} else {
			// Enquanto houver mensagens na fila...
			while (!messagesQueue.isEmpty()) {
				// 1. Tira a mensagem mais antiga da fila
				Message message = messagesQueue.remove();
				String topic = message.getTopic();
				
				// 2. Pega a lista de assinantes daquele tópico
				Set<Subscriber> subscribersOfTopic = subscribersTopicMap.get(topic);
				
				// 3. Se houver assinantes...
				if (subscribersOfTopic != null) {
					// 4. Envia a mensagem para CADA um deles
					for (Subscriber subscriber : subscribersOfTopic) {
						// Pega a lista de mensagens *pessoais* do assinante
						List<Message> subscriberMessages = subscriber.getSubscriberMessages();
						// Adiciona a nova mensagem
						subscriberMessages.add(message);
						// Atualiza a lista pessoal dele
						subscriber.setSubscriberMessages(subscriberMessages);
					}
				}
			}
		}
	}
	
	// Método bônus (não é o "broadcast" padrão, mas o projeto tem)
	// Pega mensagens de um tópico específico para um assinante específico
	public void getMessagesForSubscriberOfTopic(String topic, Subscriber subscriber) {
		// (Lógica similar ao broadcast, mas com mais filtros)
		if (messagesQueue.isEmpty()) {
			System.out.println("No messages from publishers to display");
		} else {
			while (!messagesQueue.isEmpty()) {
				Message message = messagesQueue.remove();
				
				if (message.getTopic().equalsIgnoreCase(topic)) {
					
					Set<Subscriber> subscribersOfTopic = subscribersTopicMap.get(topic);
					
					for (Subscriber _subscriber : subscribersOfTopic) {
						if (_subscriber.equals(subscriber)) { // Apenas para o assinante que pediu
							List<Message> subscriberMessages = subscriber.getSubscriberMessages();
							subscriberMessages.add(message);
							subscriber.setSubscriberMessages(subscriberMessages);
						}
					}
				}
			}
		}
	}
	
}
