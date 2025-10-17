package utilities;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class AIAnalysisManager {

    private final GoogleAiGeminiChatModel model;

    public AIAnalysisManager() {
        model = ModelProvider.getModel();
    }

    public String analyzeSymptoms(String symptoms) {
        return new SymptomAnalyzer(model).analyze(symptoms);
    }

    public String analyzeDocument(String filePath) {
        return new DocumentAnalysis(model).analyze(filePath);
    }
}
