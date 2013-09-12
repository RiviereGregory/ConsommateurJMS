package fr.treeptik.consommateurjms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

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

	}

}
