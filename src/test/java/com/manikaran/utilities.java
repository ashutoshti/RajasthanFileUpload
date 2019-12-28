package com.manikaran;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class utilities extends FileUpload {

    public static void screenshot() {
        try {
            File source = ((TakesScreenshot) FileUpload.driver).getScreenshotAs(OutputType.FILE);
            String filename = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss'.png'").format(new Date());
            String currentDir = System.getProperty("user.dir");
            File Destination = new File(currentDir + "/screenshots/" + filename);
            org.apache.commons.io.FileUtils.copyFile(source, Destination);
            System.out.println("ScreenShot taken");
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
