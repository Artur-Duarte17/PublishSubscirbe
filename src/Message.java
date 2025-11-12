package message;

public class Message {
	
	private String topic;   // O "assunto" da mensagem (ex: "Java", "Python")
	private String payload; // O "conteúdo" real da mensagem
	
	
	public Message() {
	}
	
	
	public Message(String topic, String payload) {
		this.topic = topic;
		this.payload = payload;
	}
	
	// --- Métodos Getters e Setters ---
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
}