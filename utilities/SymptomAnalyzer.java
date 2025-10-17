package utilities;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class SymptomAnalyzer implements AIAnalyzer {

    private final GoogleAiGeminiChatModel model;

    public SymptomAnalyzer(GoogleAiGeminiChatModel model) {
        this.model = model;
    }

    @Override
    public String analyze(String input) {
        String prompt = """
            You are an expert medical assistant.
            Analyze the following patient symptoms and provide:
            1. Possible conditions or diseases.
            2. Recommended next steps.
            3. Urgency level (Low / Medium / High).

            Symptoms:
            %s
            """.formatted(input);

        return model.chat(prompt);
    }
}
