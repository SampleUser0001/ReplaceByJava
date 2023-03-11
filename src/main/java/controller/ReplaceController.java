package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import bean.ReplaceBean;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReplaceController {
    
    private Logger logger = LogManager.getLogger();

    private Map<String, String> replaceMap;
    private NkfCharsetController nkfCharsetController = new NkfCharsetController();
    private String fromHome;
    private String toHome;

    public ReplaceController(String from, String to, String replaceMapFile) throws StreamReadException, DatabindException, IOException {
        this.fromHome = from;
        this.toHome = to;

        ObjectMapper mapper = new ObjectMapper();
        this.replaceMap = mapper.readValue(new File(replaceMapFile), new TypeReference<List<ReplaceBean>>(){})
                                .stream()
                                .collect(Collectors.toMap(ReplaceBean::getSearch, ReplaceBean::getReplace));

    }

    public void replace(Path from) {
        Path to = Paths.get(from.toAbsolutePath().toString().replace(this.fromHome, this.toHome));
        // ディレクトリ or ファイル判定
        if(Files.isDirectory(from) ) {
            // ディレクトリ作成
            to.toFile().mkdirs();
        } else {
            // ファイルコピー

            // 文字コード取得
            String nkfCharset = "";
            BufferedWriter writer = null;
            try {
                nkfCharset = nkfCharsetController.getCharset(from.toString());
                Charset charset = Charset.forName(nkfCharset);

                // 出力先ファイルをオープンする
                writer = Files.newBufferedWriter(to, charset);
                // ファイルを読み込んで置換する。
                String inputPath = from.toString();
                for(String line : Files.readAllLines(Paths.get(inputPath), charset)) {
                    for(Map.Entry<String,String> entry : replaceMap.entrySet()){
                        line = line.replace(entry.getKey(),entry.getValue());
                    }
                    writer.write(line);
                    writer.write(System.getProperty("line.separator"));
                }

            } catch (IllegalCharsetNameException e) {
                // nkf -> Charset変換に失敗
                logger.warn("{} is not supported charset. {}", nkfCharset, from);
                this.copy(from, to);
            } catch (InterruptedException | IllegalArgumentException e) {
                // nkfでエラーになった
                // nkfで文字コードが特定できない == null;
                logger.warn("It can not be identified. {}", from);
                this.copy(from, to);
            } catch (Throwable t) {
                logger.warn(t.getStackTrace());
                this.copy(from, to);
            } finally {
                if(writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        logger.error(e.getStackTrace());
                    }
                }
            }

        }
    }

    private void copy(Path from, Path to) {
        try {
            Files.copy(from, to);
        } catch(IOException e) {
            logger.error(e);
        }
    }
}
