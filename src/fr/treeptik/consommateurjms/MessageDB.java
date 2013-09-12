package fr.treeptik.consommateurjms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import fr.treeptik.samplejms.xml.Client;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/TestQueue") })
public class MessageDB implements MessageListener {

	@Override
	public void onMessage(Message message) {

		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println(textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}

		// Pour l'envoie de fichier xml
		JAXBContext jaxbContext;

		try {
			jaxbContext = JAXBContext.newInstance("fr.treeptik.samplejms.xml");
			// System.out.println("Dans try jaxb");
			File fileEcriture = new File("client.xml");
			if (!fileEcriture.exists()) {
				try {
					// System.out.println("Creation Fichier xml");
					fileEcriture.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileEcriture, false));

			bufferedWriter.write(textMessage.getText());

			bufferedWriter.close();

			// Lecture du fichier client
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Client newClient = (Client) unmarshaller.unmarshal(new File("client.xml"));
			System.out.println(newClient.getPersonne().size());

		} catch (JAXBException | IOException | JMSException e) {
			System.out.println("Dans catch jaxb");
			e.printStackTrace();
		}

		// try {
		// ObjectMessage objectMessage = (ObjectMessage) message;
		// Produit produit = (Produit) objectMessage.getObject();
		//
		// System.out.println("Ref : " + produit.getRef());
		// System.out.println("Desc : " + produit.getDescription());
		// } catch (JMSException e) {
		// e.printStackTrace();
		// }

	}
}
