import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.sql.*;
/**
 * Created by evkingen on 04.06.2018.
 */
public class AuthUserHandler extends ChannelInboundHandlerAdapter {
    boolean isAuthorize;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SQLHandler.SQLСonnect();
        System.out.println("Active");
        isAuthorize = false;
        // super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Inactive");
        isAuthorize = false;
        SQLHandler.SQLDisconnect();
        //super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg == null) return;
        System.out.println("isAuthorize - " + isAuthorize);
        if (isAuthorize)
            ctx.fireChannelRead(msg);
        else{
        if (!(msg instanceof CommandMessage)) {
            ReferenceCountUtil.release(msg);
        }else {
            String ans = "/warning unknown command";
            SQLHandler.getTable();
            CommandMessage command = (CommandMessage)msg;
            ResultSet resultSet = SQLHandler.getStatement().executeQuery("SELECT login,pass FROM users  WHERE login='" + command.getArguments()[0] + "'");
            switch (command.getCommand()) {
                case "/auth":

                   if (resultSet.next()) {
                        System.out.println(resultSet.getString("login") + " " + resultSet.getString("pass"));
                        if (resultSet.getString("login").equals(command.getArguments()[0]) && resultSet.getString("pass").equals(command.getArguments()[1])) {
                            isAuthorize = true;
                            ans = "/authok "+command.getArguments()[0] +" Welcome your Cloud Store!";
                        } else {
                            ans = "/authfail IncorrectLoginOrPass!";
                        }
                    } else {
                        ans = "/authfail IncorrectLoginOrPass!";
                    }
                    break;
                case "/reg":
                    if (resultSet.next()) {
                        ans = "/authfail This login exists!";
                    }else {
                        SQLHandler.getStatement().executeUpdate("INSERT INTO users (login, pass) VALUES ('" + command.getArguments()[0] + "','" + command.getArguments()[1] + "');");
                        isAuthorize=true;
                        ans = "/authok "+command.getArguments()[0] +" Welcome your Cloud Store!";
                    }
                    break;
                default:
                    break;
            }
            ctx.writeAndFlush(new CommandMessage(ans));
            if(isAuthorize) ctx.fireChannelRead(new CommandMessage("/getfileslist " + command.getArguments()[0]));
        }
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
