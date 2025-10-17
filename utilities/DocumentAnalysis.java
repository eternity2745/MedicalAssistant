package utilities;

import java.io.File;
import java.io.IOException;

import dev.langchain4j.data.message.PdfFileContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class DocumentAnalysis implements AIAnalyzer {

    private final GoogleAiGeminiChatModel model;

    public DocumentAnalysis(GoogleAiGeminiChatModel model) {
        this.model = model;
    }

    @Override
    public String analyze(String filePath) {
        try {
            File pdfFile = new File(filePath);
            if (!pdfFile.exists()) return "File not found: " + filePath;

            // Convert PDF to Base64
            String base64Pdf = PDFUtils.encodePdfToBase64(pdfFile);

            String prompt = """
                    You are a professional medical AI assistant.
                    Analyze the following patient report or document and summarize:
                    1. The key medical observations or findings
                    2. Possible medical conditions or diseases
                    3. Recommended next steps or tests

                    Document content:
                    """;

            ChatResponse response = model.chat(
                UserMessage.from(
                    PdfFileContent.from(base64Pdf, "application/pdf"),
                    TextContent.from(prompt)
                )
            );

            return response.aiMessage().text();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file: " + e.getMessage();
        }
    }
}
