import java.util.Map;

import java.io.File;
import java.io.BufferedWriter;
import java.util.List;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;

import java.util.stream.Collectors;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import bean.ReplaceBean;

/**
 * フォーマットファイルを読み込んで置換する。
 */
public class Replace {
    public static final int ARGS_INDEX_JSON_PATH = 0;
    public static final int ARGS_INDEX_INPUT_PATH = 1;
    public static final int ARGS_INDEX_INPUT_CHARSET = 2;
    public static final int ARGS_INDEX_OUTPUT_PATH = 3;
    public static final int ARGS_INDEX_OUTPUT_CHARSET = 4;

    public static void main( String[] args ) throws IOException {
        // jsonを読み込む
        String jsonPath = args[ARGS_INDEX_JSON_PATH];
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> replaceMap
            = mapper.readValue(new File(jsonPath), new TypeReference<List<ReplaceBean>>(){})
                    .stream()
                    .collect(Collectors.toMap(ReplaceBean::getSearch, ReplaceBean::getReplace));
        
        // 出力先ファイルをオープンする
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[ARGS_INDEX_OUTPUT_PATH]), Charset.forName(args[ARGS_INDEX_OUTPUT_CHARSET]))){
            // ファイルを読み込んで置換する。
            String inputPath = args[ARGS_INDEX_INPUT_PATH];
            for(String line : Files.readAllLines(Paths.get(inputPath), Charset.forName(args[ARGS_INDEX_INPUT_CHARSET]))) {
                for(Map.Entry<String,String> entry : replaceMap.entrySet()){
                    line = line.replace(entry.getKey(),entry.getValue());
                }
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
        } catch(IOException e){
            throw e;
        }

    }
}
