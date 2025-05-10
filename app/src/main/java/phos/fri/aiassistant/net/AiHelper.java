package phos.fri.aiassistant.net;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import phos.fri.aiassistant.entity.NewChatRequest;

public class AiHelper {
    // 创建Chat的api在客户端hard code数据
    public static NewChatRequest createChat(String datasetId) {
        List<String> idList = new ArrayList<>();
        idList.add(datasetId);
        String name = "新的聊天" + UUID.randomUUID();
        NewChatRequest.LLMParams llmParams = new NewChatRequest.LLMParams(
                0.7, 12800, "Qwen2.5-Coder-32B-Instruct___OpenAI-API",
                0.4, 0.1, 0.3
        );
        return new NewChatRequest(idList, name, "Chinese", llmParams);
    }
}
