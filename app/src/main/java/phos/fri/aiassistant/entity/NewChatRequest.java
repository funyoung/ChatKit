package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewChatRequest {
    @SerializedName("dataset_ids")
    private List<String> datasetIds;

    @SerializedName("name")
    private String name;

    private String language;
    private LLMParams llm;

    public NewChatRequest(List<String> datasetIds, String name, String language,
                             LLMParams llmParams) {
        this.datasetIds = datasetIds;
        this.name = name;
        this.language = language;
        this.llm = llmParams;
    }

    public List<String> getDatasetIds() {
        return datasetIds;
    }

    public void setDatasetIds(List<String> datasetIds) {
        this.datasetIds = datasetIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LLMParams getLlm() {
        return llm;
    }

    public void setLlm(LLMParams llm) {
        this.llm = llm;
    }

    public static class LLMParams {
        public double frequency_penalty;
        public int max_tokens;
        public String model_name;
        public double presence_penalty;
        public double temperature;
        public double top_p;

        public LLMParams(double frequencyPenalty, int maxTokens, String modelName,
                         double presencePenalty, double temperature, double topP) {
            this.frequency_penalty = frequencyPenalty;
            this.max_tokens = maxTokens;
            this.model_name = modelName;
            this.presence_penalty = presencePenalty;
            this.temperature = temperature;
            this.top_p = topP;
        }

        public double getFrequency_penalty() {
            return frequency_penalty;
        }

        public void setFrequency_penalty(double frequency_penalty) {
            this.frequency_penalty = frequency_penalty;
        }

        public int getMax_tokens() {
            return max_tokens;
        }

        public void setMax_tokens(int max_tokens) {
            this.max_tokens = max_tokens;
        }

        public String getModel_name() {
            return model_name;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
        }

        public double getPresence_penalty() {
            return presence_penalty;
        }

        public void setPresence_penalty(double presence_penalty) {
            this.presence_penalty = presence_penalty;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getTop_p() {
            return top_p;
        }

        public void setTop_p(double top_p) {
            this.top_p = top_p;
        }
    }
}

