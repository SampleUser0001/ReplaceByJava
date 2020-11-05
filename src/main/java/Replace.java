
import java.util.Map;
import java.util.HashMap;

import java.util.stream.Stream;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.IOException;
/**
 * フォーマットファイルを読み込んで置換する。
 */
public class Replace {
    public static void main( String[] args ) throws IOException {
        Map<String,String> replaceMap = new HashMap<String,String>();
        
        for(int index=1 ; index<args.length ; index++){
            replaceMap.put("{replace_"+index+"}" , args[index]);
        }
        
        Files
            .lines(Paths.get(args[0]))
            .forEach(line -> {
                 for(Map.Entry<String,String> entry : replaceMap.entrySet()){
                     line = line.replace(entry.getKey(),entry.getValue());
                 }
                 System.out.println(line);
             });
        
        
    }
}
