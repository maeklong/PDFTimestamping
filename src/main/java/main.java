import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class main {
	public static void main(String[] args) // throws IOException, GeneralSecurityException
	{

		/**** Sample Input for username password authen ****/
		/*  
		 * String inputFile = resources/pdfA3.pdf;
		 * String tsaUrl = "https://time-test.teda.th"; 
		 * String tsaUsername = "tsaUsername"; 
		 * String tsaPassword = "tsaPassword";
		 */

		/**** Sample Input for certificate authen ****/
		/*
		 * String inputFile = resources/pdfA3.pdf; 
		 * String tsaUrl = "https://time-test.teda.th";
		 * String keystoreFile = "keystoreFile.p12";
		 * String keystorePassword = "keystorePassword";
		 * String keystoreType = "PKCS12";
		 * String LogType "1"; // 1:not write logfile , 2 : write log to file
		 */

		LogFileWriter wrFile = new LogFileWriter();
		try {
			String inputFile = args[0];
			wrFile.setInputFile(inputFile);
			String tsaUrl = args[1];
			wrFile.setTsaUrl(tsaUrl);
			String keystoreFile = args[2];
			wrFile.setKeystoreFile(keystoreFile);
			String keystorePassword = args[3];
			String keystoreType = args[4];
			String LogType = args[5];
			wrFile.setType_out(LogType);
			
			// sign PDF (Certificate authen)
			CreateSignedTimeStamp signing = new CreateSignedTimeStamp(tsaUrl, keystoreFile, keystorePassword,keystoreType,LogType);

			// sign PDF (Username , Password authen)
			// CreateSignedTimeStamp signing = new CreateSignedTimeStamp(tsaUrl,tsaUsername,tsaPassword);

			File inFile = new File(inputFile);
			String name = inFile.getName();
			String substring = name.substring(0, name.lastIndexOf('.'));
			String msDone = "********TimeStamp Done**********"+System.getProperty("line.separator");
			File outFile = new File(inFile.getParent(), substring + "_timestamped.pdf");
			wrFile.readAndWrite_DateAndMessage();
			try {
				signing.signDetached(inFile, outFile);
				wrFile.readAndWrite_mesLog(msDone);
			} catch (Exception e) {
				// e.printStackTrace();
				wrFile.excepToString(e);
			}
		} catch (Exception ex) {
			wrFile.readAndWrite_DateAndMessage();
			wrFile.excepToString(ex);
		}
		// System.out.println("********TimeStamp Done**********");
	}
}
