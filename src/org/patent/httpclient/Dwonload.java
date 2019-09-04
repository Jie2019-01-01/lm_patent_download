package org.patent.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.patent.entity.Patent_Info;
import org.patent.utils.DBOperation;

/**
 * 	下载
 * @author 123
 *
 */
public class Dwonload {

	private static DBOperation db = new DBOperation();
	
	public static void main(String[] args) {
		List<Patent_Info> list = db.getFailData();
		for(Patent_Info p :list) {
			try {
				for(int i=0;i<5;i++) {
					if(download(p)) {
						db.update(p);
						Thread.sleep(10000);
						break;
					}else {
						Thread.sleep(10000);
					}
				}
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *	 下载
	 */
	public static boolean download(Patent_Info p) throws IOException {
		
		String ipc_code = p.getIpc_code();
		String date = p.getDate();
		
		String downlaodurl = "https://www.lens.org/lens/export?q=classification_ipcr%3A("+ipc_code+"*)&l=en&st=true&dates=%2Bpub_date%3A"+date.replaceAll("-", "")+"-"+date.replaceAll("-", "")+"&preview=true&n=1000&ef=csv&efn=&undefined=&p=0&async=false";
        System.out.println("["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"]下载地址: "+downlaodurl);
        
        // 目录是否存在
//        String path = "D:/liming/patent/2016";
        String path = "data/liming/patent";
        File savePath = new File(path);
        if(!savePath.exists()) {
        	savePath.mkdirs();
        }

        InputStream is = null;
        OutputStream out = null;
        
        System.out.println(" ==============="+date+"-"+ipc_code+"文件 开始下载============");

    	// 文件是否存在
        String filename = date+"-"+ipc_code+".csv";
        File file = new File(path + "/" + filename);
        if(file.exists()) {
        	return true;
        }
        
    	// 定义访问参数
        URL url = new URL(downlaodurl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
        con.setRequestProperty("cookie","__uzma=d8759239-91bf-a2fa-b181-b2e88e1aa1d9; __uzmb=1565101729; __uzmc=6061741878537; __uzmd=1567562508; AWSALB=sXFTwHhwlcQ2+kttOJD2fme4SFVX/qnjDeJuSDmxCpRyapjeEw93WPOGYoY1t/1IksWG2Tf6xIR2qOZnpK+CVte5bMVU9MEZOmG2bgFf2GIW0Ib1x/7OLEsEKVs/591a1H080S7bBNbSRLayvg0dOQsQwS3ThvWqQt3NchgZHWktI1AIxAYgOF3d4tT37/1nqfCPRviK3OtivqfUR0Ec6cwGEO77S1t5n5NqR1fUvsJYsTjpf32SV+Tqeg5AluY=; _pk_id.1.2a81=9e828073ea6c734e.1565101753.17.1567562508.1567562499.; uzdbm_a=7d7218a3-9434-c0b1-c913-98e868edb6c9; LENS_SESSION_ID=8B05A19B9466FE2092B30B666E98BCC1; TZ=Asia%2FShanghai; _pk_ses.1.2a81=1");            
        //设置是否要从 URL 连接读取数据,默认为true
        con.setDoInput(true);
        con.setReadTimeout(60000);
        con.setConnectTimeout(60000);
        
        // 获取响应码
        if(con.getResponseCode()==200) {
        	con.connect();

            is = con.getInputStream();
            // 创建字节输出流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bs = new byte[4*1024];
            int len;
            while ((len = is.read(bs)) != -1) {
                byteArrayOutputStream.write(bs, 0, len);
            }
            // 通过传入的文件路径进行写文件的操作
            out = new FileOutputStream(file);
            // 将字节输出流数据写入文件输出流
            out.write(byteArrayOutputStream.toByteArray());
            System.out.println("**************"+date+": 下载结束**********");
            
            out.flush();
            is.close();
            out.close();
            
            return true;
        }else {
        	return false;
        }
	}
}
