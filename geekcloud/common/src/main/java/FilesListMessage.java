import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by evkingen on 30.05.2018.
 */
public class FilesListMessage extends AbstractMessage{
    private List<String> filesList = new ArrayList<String>();

    public List<String> getFilesList() {
        return filesList;
    }

    public FilesListMessage() {

    }

    public void initMessage(Path path) {
        try {
            if (!Files.exists(path)) {
                System.out.println("sozdat" + path);
                Files.createDirectory(path);
            }
            if (Files.list(path).count()!=0)
                this.filesList = Files.list(path).map(new Function<Path, String>() {
                @Override
                public String apply(Path path) {
                    return path.getFileName().toString();
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
