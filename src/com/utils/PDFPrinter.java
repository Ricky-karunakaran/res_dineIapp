/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.PageLayout;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PDFPrinter {
    public static void printSceneAsPDF(Scene scene, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                if (printerJob != null && printerJob.showPrintDialog(stage)) {
                    Node node = scene.getRoot();
                    PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
//                    double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
//                    double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
//                    node.getTransforms().add(new javafx.scene.transform.Scale(scaleX, scaleY));
                    WritableImage image = node.snapshot(new SnapshotParameters(), null);
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    Paper paper = pageLayout.getPaper();
                    page.setMediaBox(new org.apache.pdfbox.pdmodel.common.PDRectangle(
                            (float) paper.getWidth(), (float) paper.getHeight()));
                    document.addPage(page);

                    PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.drawImage(pdImage, 0, 0, (float) paper.getWidth(), (float) paper.getHeight());
                    contentStream.close();

                    document.save(file);
                    document.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


