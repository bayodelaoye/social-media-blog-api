package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;


    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::postCreateAccount);
        app.post("login", this::postLogin);
        app.post("messages", this::postCreateMessage);
        app.get("messages", this::getAllMessages);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessage);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postCreateAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.createAccount(account);
        if(createdAccount!=null){
            ctx.json(mapper.writeValueAsString(createdAccount));
        }else{
            ctx.status(400);
        }

    }

    private void postLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account login = accountService.login(account);
        if(login!=null){
            ctx.json(mapper.writeValueAsString(login));
        }else{
            ctx.status(401);
        }

    }

    private void postCreateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage!=null){
            ctx.json(mapper.writeValueAsString(createdMessage));
        }else{
            ctx.status(400);
        }

    }

    private void getAllMessages(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        if (messageService.getMessageById(messageId) == null) {
            ctx.json("");
        } else {
            ctx.json(messageService.getMessageById(messageId));
        }
    }

    private void deleteMessage(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        if (messageService.deleteMessage(messageId) == null) {
            ctx.json("");
        } else {
            ctx.json(messageService.deleteMessage(messageId));
        }
    }

    private void updateMessage(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(messageId, message);
        if(updatedMessage!=null){
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getMessagesByUser(Context ctx) throws JsonProcessingException {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(accountId);
        ctx.json(messages);
    }

}