package com.ericko.evenor.configuration;

public interface FilesLocationConfig {
    String ROOT = "media";

    interface Image {
        String LOCATION = ROOT+"/image";
        String[] FILE_EXTENSION_ALLOWED = {"jpeg","jpg","png"};
    }
    interface Document {
        String LOCATION = ROOT+"/document";
        String [] FILE_EXTENSION_ALLOWED = {"docx","pdf","doc","odt"};
    }

}
