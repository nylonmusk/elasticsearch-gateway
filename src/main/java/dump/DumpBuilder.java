package dump;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import constant.Dump;
import view.Log;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class DumpBuilder {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Jackson JSR310 모듈 등록
        // 날짜 및 시간 객체를 JSON으로 직렬화 및 역직렬화하기 위한 Jackson 모듈
        objectMapper.registerModule(new JavaTimeModule());
        // JSON 출력을 포맷팅하여 가독성 향상
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void makeJson(List<Map<String, Object>> data, Map<String, Object> dumpConfig) {
        final String dumpFilePath = dumpConfig.get(Dump.FILE_PATH.get()).toString();

        try (FileWriter fileWriter = new FileWriter(dumpFilePath)) {
            String jsonData = objectMapper.writeValueAsString(data);
            fileWriter.write(jsonData);
            fileWriter.flush();
            Log.info(DumpBuilder.class.getName(), "created JSON file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}