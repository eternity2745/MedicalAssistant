package utilities;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ImageAnalysis implements AIAnalyzer {

    private final GoogleAiGeminiChatModel model;

    public ImageAnalysis(GoogleAiGeminiChatModel model) {
        this.model = model;
    }

    @Override
    public String analyze(String filePath) {
        try {
            File imageFile = new File(filePath);
            if (!imageFile.exists()) return "File not found: " + filePath;

            String mimeType = Files.probeContentType(imageFile.toPath());

            // Convert image to Base64
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            // Construct prompt
            String prompt = """
                    You are a professional medical AI assistant.
                    Analyze the following patient's image and identify:
                    1. Visible medical conditions or skin symptoms
                    2. Possible diseases
                    3. Recommended next steps
                    Provide your answer clearly and concisely.
                    """;


            ChatResponse response = model.chat(
                UserMessage.from(
                    ImageContent.from(base64Image, mimeType),
                    TextContent.from(prompt)
                )
            );

            return response.aiMessage().text();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error analyzing image: " + e.getMessage();
        }
    }
}
