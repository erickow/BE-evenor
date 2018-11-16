package com.ericko.evenor.service.storage;

import com.ericko.evenor.configuration.FilesLocationConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
@Qualifier("DocumentStorageService")
public class DocumentStorageServiceImpl extends StorageServiceImpl implements StorageService {
    DocumentStorageServiceImpl() {
        this.rootLocation = Paths.get(FilesLocationConfig.Document.LOCATION);
    }

    @Override
    public ArrayList<String> store(MultipartFile[] file, String filename) throws FileFormatException {
        return super.store(file, filename);
    }

    @Override
    public String load(String filename) throws IOException, InvalidFormatException {
        String result = null;
        String fileExt = FileUtils.getExtension(filename);
        if(fileExt.equalsIgnoreCase("doc") || fileExt.equalsIgnoreCase("docx")){
            FileInputStream fis = new FileInputStream(filename);
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            try (XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc)) {
                result = new String(extractor.getText());
            } finally {
                xdoc.close();
            }
        }else if(fileExt.equalsIgnoreCase("pdf")){
            File file = new File(filename);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            result = new String(pdfStripper.getText(document));
            document.close();
        }
        return result;
    }

    private String cleansing(String content){
        return null;
    }
}
