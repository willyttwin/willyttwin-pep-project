package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;


public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController () {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessage);
        app.patch("/messages/{message_id}", this::handleUpdateMessage);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByAccountId);

        return app;
    }

    private void handleRegister(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account createdAccount = accountService.registerAccount(account);
        if (createdAccount != null) {
            context.status(200).json(createdAccount);
        } else {
            context.status(400);
        }
    }
    private void handleLogin(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account result = accountService.login(account);

        if(result != null) {
            context.status(200).json(result);
        } else {
            context.status(401);
        }
    }
    private void handleCreateMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message created = messageService.createMessage(message);
        if (created != null) {
            context.status(200).json(created);
        } else {
            context.status(400);
        }
    }
    private void handleGetAllMessages(Context context) {
        context.status(200).json(messageService.getAllMessages());
    }
    private void handleGetMessageById(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message found = messageService.getMessageById(id);
        if(found != null) {
            context.status(200).json(found);
        } else {
            context.status(200).result("");
        }
    }
    private void handleDeleteMessage(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(id);
        if(deleted != null) {
            context.status(200).json(deleted);
        } else {
            context.status(200).result("");
        }
    }
    private void handleUpdateMessage(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message incoming = context.bodyAsClass(Message.class);
        Message updated = messageService.updateMessage(id, incoming.getMessage_text());
        if (updated != null) {
            context.status(200).json(updated);
        } else {
            context.status(400);
        }
    }
    private void handleGetMessagesByAccountId(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.status(200).json(messageService.getMessagesByAccount(accountId));
    }


}