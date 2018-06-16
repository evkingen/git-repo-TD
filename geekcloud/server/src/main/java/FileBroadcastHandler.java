import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
//import common.src.main.java.AbstractMessage;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;

/**
 * Created by evkingen on 28.05.2018.
 */
public class FileBroadcastHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client get access");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            if (msg == null) return;
            System.out.println(msg.getClass());
            if (msg instanceof CommandMessage) {
                CommandMessage command = (CommandMessage)msg;
                FilesListMessage list;
                switch(command.getCommand()){
                    case "/getfileslist":
                        list = new FilesListMessage();
                        list.initMessage(Paths.get("serverDir\\" + command.getArguments()[0]));
                        ctx.writeAndFlush(list);
                        break;
                    case "/getfile":
                        FileMessage file = new FileMessage(Paths.get("serverDir\\" +  command.getArguments()[0]));
                        ctx.writeAndFlush(file);
                        break;
                    case "/deletefile":
                        Path filePath = Paths.get("serverDir\\" + command.getArguments()[0]);
                        Files.deleteIfExists(filePath);
                        break;
                    default:
                        break;
                }
            } else if (msg instanceof FileMessage) {
               String owner = ((FileMessage) msg).getOwner();
               String fileName = ((FileMessage) msg).getName();
               byte[] content = ((FileMessage) msg).getContent();

               System.out.println("Owner:" + owner);
               System.out.println("FileName: " + fileName);
               System.out.println("Content:" + (new String(content)));

               InputStream is = new BufferedInputStream(new ByteArrayInputStream(content));
               Path newFile = Paths.get("serverDir\\" + owner + "\\" + fileName);
               if(!Files.exists(newFile)) Files.createFile(newFile);
               Files.copy(is, newFile, StandardCopyOption.REPLACE_EXISTING);
            } else {
                System.out.printf("Server received wrong object!");
                return;
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
