package com.epam.ankov.FtpBridge.services;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FtpReader {

    private static final Logger log = LoggerFactory.getLogger(FtpReader.class);

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

        String host = "localhost";
        String user = "admin";
        String pass = "admin";
        String dirPath = "/";

        try {
            FTPClient ftp = getFtpClient(host, user, pass, dirPath);
            FTPFile[] ftpFiles = ftp.listFiles();

            for (FTPFile file : ftpFiles) {
                log.info(file.getName());
                dataAppender.append(readFile(ftp, file.getName()));
            }

            ftp.disconnect();

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }

        log.info("Read from FTP completed");
    }

    private FTPClient getFtpClient(String host, String user, String pass, String dirPath) throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(host);
        ftp.login(user, pass);
        if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.disconnect();
            throw new IOException("login fail!");
        }
        ftp.enterLocalPassiveMode();
        ftp.changeWorkingDirectory(dirPath);
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
