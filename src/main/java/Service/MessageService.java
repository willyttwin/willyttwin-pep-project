package Service;

import DAO.MessageDAO;
import Model.Message;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    //no args constructor
    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    //constructor for mocking
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = new AccountDAO();
    }
    //create message
    /* Message cant be blank
     * Message must not be over 255 characters
     * posted_by refers to a real, existing user
     */
    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank()) {
            return null;
        }
        if (message.getMessage_text().length() > 255) {
            return null;
        }
        if(!accountDAO.doesAccountExist(message.getPosted_by())) {
            return null;
        }

        return messageDAO.insertMessage(message);
    }

    //get all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    //get message by id
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }
    //delete message by id
    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }
    //update message by id
    public Message updateMessage(int message_id, String newMessage) {
        if (newMessage == null || newMessage.isBlank() || newMessage.length() > 255) {
            return null;
        }
        return messageDAO.updateMessageById(message_id, newMessage);
    }
    //get all messages by account_id
    public List<Message> getMessagesByAccount(int account_id) {
        return messageDAO.getMessagesByAccountId(account_id);
    }
}
