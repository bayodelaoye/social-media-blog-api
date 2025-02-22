package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255 || messageDAO.getUser(message) == null) return null;
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        if (messageDAO.getMessageById(messageId) == null) return null;
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId) {
        Message messageToDelete = messageDAO.getMessageById(messageId);
        if (messageToDelete == null) {
            return null;
        } else {
            messageDAO.deletMessage(messageId);
            return messageToDelete;
        }
    }

    public Message updateMessage(int messageId, Message message) {
        Message messageToUpdate = messageDAO.getMessageById(messageId);
        if (messageToUpdate == null || message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            return null;
        } else {
            Message updatedMessage = new Message(messageToUpdate.getMessage_id(), messageToUpdate.getPosted_by(), message.getMessage_text(), messageToUpdate.getTime_posted_epoch());
            return messageDAO.updateMessage(messageId, updatedMessage);
        }
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageDAO.getMessagesByUser(accountId);
    }
    
}
