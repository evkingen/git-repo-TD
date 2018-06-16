import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by evkingen on 28.05.2018.
 */
public class CloudServer {
    private static final int MAX_OBJ_SIZE = 1024 * 1024 * 100;

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel socketChannel) throws Exception {
                     socketChannel.pipeline().addLast(
                             new ObjectDecoder(MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
                             new ObjectEncoder(),
                             new AuthUserHandler(),
                             new FileBroadcastHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG,128)
             .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(8189).sync();

            f.channel().closeFuture().sync();
        }finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new CloudServer().run();
    }
}
