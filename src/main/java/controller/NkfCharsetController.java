package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NkfCharsetController {

    public NkfCharsetController() { }

    public String getCharset(String filepath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("nkf", "-g", filepath);
        Process process = pb.start();
        process.waitFor();
        List<String> nkfResult = convertToList(process.getInputStream());
        if(nkfResult.isEmpty()) {
            return null;
        } else {
            return nkfResult.get(0);
        }
    }

    private List<String> convertToList(InputStream is) throws IOException {
        List<String> returnList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			for (;;) {
				String line = br.readLine();
				if (line == null) {
                    break;
                } else {
                    returnList.add(line);
                }
			}
		}
        return returnList;
	}


}
