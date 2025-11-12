import publisher.Publisher;
import publisher.PublisherImpl;
import service.PubSubService;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;
import message.Message;

public class Main {
	public static void main(String[] args) {
		
		// --- 1. Criação dos Objetos ---
		
		// Simula dois sistemas (publicadores) diferentes que enviam ofertas
		Publisher publicadorRestaurantes = new PublisherImpl();
		Publisher publicadorEletronicos = new PublisherImpl();
		
		// Simula três usuários (assinantes) diferentes
		Subscriber assinanteAmanteDeComida = new SubscriberImpl();
		Subscriber assinanteAmanteDeTech = new SubscriberImpl();
		Subscriber assinanteCacadorDeOfertas = new SubscriberImpl();
		
		// Cria o Broker
		PubSubService servicoDeNotificacao = new PubSubService();
		
		// --- 2. Publicar Mensagens (Ofertas) ---
		// (As ofertas ficam na fila do serviço, aguardando distribuição)
		
		Message ofertaRestaurante1 = new Message("RESTAURANTE", "Pizza em dobro hoje!");
		publicadorRestaurantes.publish(ofertaRestaurante1, servicoDeNotificacao);
		
		Message ofertaRestaurante2 = new Message("RESTAURANTE", "Bebida grátis no almoço.");
		publicadorRestaurantes.publish(ofertaRestaurante2, servicoDeNotificacao);
		
		Message ofertaEletronico1 = new Message("ELETRONICOS", "Novo fone de ouvido com 20% de desconto.");
		publicadorEletronicos.publish(ofertaEletronico1, servicoDeNotificacao);
		
		// --- 3. Assinantes se Inscrevem nos Tópicos ---
		
		// O 'AmanteDeComida' só quer saber de "RESTAURANTE"
		assinanteAmanteDeComida.addSubscriber("RESTAURANTE", servicoDeNotificacao);
		
		// O 'AmanteDeTech' só quer saber de "ELETRONICOS"
		assinanteAmanteDeTech.addSubscriber("ELETRONICOS", servicoDeNotificacao);
		
		// O 'CacadorDeOfertas' quer saber de AMBOS
		assinanteCacadorDeOfertas.addSubscriber("RESTAURANTE", servicoDeNotificacao);
		assinanteCacadorDeOfertas.addSubscriber("ELETRONICOS", servicoDeNotificacao);
		
		// --- 4. O Broker Transmite! ---
		// Agora o serviço vai esvaziar a fila e entregar as ofertas
		System.out.println("--- DISTRIBUIÇÃO DE OFERTAS (1ª Rodada) ---");
		servicoDeNotificacao.broadcast();
		
		// --- 5. Verificação ---
		// Vamos ver quem recebeu o quê
		
		System.out.println("\nNotificações do 'Amante de Comida':");
		assinanteAmanteDeComida.printMessages(); // Deve ter 2 msgs de RESTAURANTE
		
		System.out.println("\nNotificações do 'Amante de Tech':");
		assinanteAmanteDeTech.printMessages(); // Deve ter 1 msg de ELETRONICOS
		
		System.out.println("\nNotificações do 'Caçador de Ofertas':");
		assinanteCacadorDeOfertas.printMessages(); // Deve ter as 3 msgs
	}
}