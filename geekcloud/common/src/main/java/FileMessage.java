import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by evkingen on 28.05.2018.
 */
public class FileMessage extends AbstractMessage {
    private String owner;
    private String name;
    private byte[] content;

    public FileMessage(Path path) {
        this.owner = "unknown";
        this.name = path.getFileName().toString();
        try {
            this.content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    public FileMessage(Path path, String owner) {
        this.owner = owner;
        this.name = path.getFileName().toString();
        try {
            System.out.println(path);
            System.out.println(path.toAbsolutePath());
            System.out.println(path.getFileName());
            this.content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }


}
