package utilities;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import io.github.cdimascio.dotenv.Dotenv;

public class ModelProvider {

    private static GoogleAiGeminiChatModel model;

    public static GoogleAiGeminiChatModel getModel() {
        if (model == null) {
            Dotenv dotenv = Dotenv.load(); // Loads from root .env
            String apiKey = dotenv.get("GEMINI_API_KEY");

            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("GEMINI_API_KEY not found in .env file!");
            }

            model = GoogleAiGeminiChatModel.builder()
                    .apiKey(apiKey)
                    .modelName("gemini-2.5-flash") // Use your desired Gemini version
                    .temperature(0.7) // Optional: controls creativity
                    .build();
        }
        return model;
    }
}
