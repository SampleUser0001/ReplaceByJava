
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.IOException;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.core.exc.StreamReadException;

import controller.ReplaceController;

/**
 * フォーマットファイルを読み込んで置換する。
 */
public class Replace {
    private ReplaceController replaceController;    

    public void replace(String args[]) throws StreamReadException, DatabindException, IOException {
        int argsIndex = 0;
        String from = args[argsIndex++];
        String to = args[argsIndex++];
        String replaceJson = args[argsIndex++];

        this.replaceController = new ReplaceController(from, to, replaceJson);
        Files.walk(Paths.get(from)).forEach( path -> replaceController.replace(path));
    }

    public static void main( String[] args ) throws IOException {
        new Replace().replace(args);
    }
}
