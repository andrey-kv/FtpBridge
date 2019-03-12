package com.epam.ankov.FtpBridge.services;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class FtpReader {

    private static final Logger log = LoggerFactory.getLogger(FtpReader.class);

    private static final String HOST_NAME = "localhost";
    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String DIR_PATH = "/";

    @Autowired
    private final RecordValidator recordValidator;

    @Autowired
    private final DataAppender dataAppender;

    public FtpReader(RecordValidator recordValidator, DataAppender dataAppender) {
        this.recordValidator = recordValidator;
        this.dataAppender = dataAppender;
    }

    @EventListener(ApplicationReadyEvent.class)
    // @Scheduled(cron = "0 0/1 * * * ?")
    public void read() {
        log.info("Read from FTP");

        try {
            readFromFtp();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        log.info("Read from FTP completed");
    }

    private void readFromFtp() throws IOException {
        FTPClient ftp = getFtpClient();
        FTPFile[] ftpFiles = ftp.listFiles();

        for (FTPFile file : ftpFiles) {
            log.info(file.getName());
            dataAppender.append(readFile(ftp, file.getName()));
        }
        ftp.disconnect();
    }

    private FTPClient getFtpClient() throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(HOST_NAME);
        ftp.login(USER_NAME, PASSWORD);
        if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.disconnect();
            throw new IOException("Login fail!");
        }
        ftp.enterLocalPassiveMode();
        ftp.changeWorkingDirectory(DIR_PATH);
        return ftp;
    }

    private List<String> readFile(FTPClient ftp, String fileName) throws IOException {

        log.info("Read file " + fileName);

        ArrayList<String> list = new ArrayList<>();
        InputStream stream = ftp.retrieveFileStream(fileName);
        Scanner sc = new Scanner(stream);

        int recordNumber = 0;
        while (sc.hasNextLine()) {
            recordNumber++;
            String record = sc.nextLine();
            if (recordValidator.validate(record)) {
                list.add(record);
            } else {
                log.info("Invalid record at line " + recordNumber + " : " + record);
            }
        }
        sc.close();
        stream.close();
        ftp.completePendingCommand();
        return list;
    }
}
