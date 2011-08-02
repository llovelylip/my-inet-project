package inet.util.my;

import inet.util.Logger;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Test extends Thread {

	/**
	 * @param args
	 */
	static{
		
		new Test().run();
		
	}
	
	public void run() {
		System.out.println("Bat dau test:");
		Scanner sc = new Scanner(System.in);
		String line = null;
		String str = "/news/, /ket-ban/ : 	aaaaMới nhất, http://3g.wap.vn/, 	Dịch vụ, http://3g.wap.vn/services.jsp,	Giao lưu, http://3g.wap.vn/giaoluu.jsp, 	Cá nhân, http://3g.wap.vn/profile/,  		Clip , http://clip.wap.vn,		Game hay, http://3g.wap.vn/java-game/,	Nhạc chuông, http://3g.wap.vn/nhac-chuong/,	Trang chủ, http://3g.wap.vn "+
					"\n/nhac-chuong/ : Nhạc MP3, http://mp3.wap.vn/, 	Tin tức, http://3g.wap.vn/news/, 	Chứng khoán, http://3g.wap.vn/chung-khoan/, 	Đọc truyện,  http://3g.wap.vn/doc-truyen/, 	Làm quen, http://3g.wap.vn/ket-ban/,	Diễn đàn , http://3g.wap.vn/dien-dan/,	Rao vặt, http://3g.wap.vn/rao-vat/, 		Clip, http://clip.wap.vn";
		while(true) {
            try {
            	line = sc.nextLine();
    			System.out.println("===>"+line);
    			if(line!= null && line.startsWith("do")){
    	 			System.out.println("Ghi lai du lieu file config");
					try {
						Menu3gTool.writeFile(str);
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					
    	 			//Stop here
    	 			//break;
    	 		} else if(line!= null && line.startsWith("s")){
    	 			System.out.println("Hien thi ...");
    	 			new Test().show();
    	 		}
                //sleep(1500); //
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

	public static void show() {
		// TODO Auto-generated method stub
		
//		Runnable r = new Runnable() {
//			public void run() {
//				// TODO Auto-generated method stub
//				System.out.println("Thread is running ...");
//				try {
//					Thread.sleep(1500);
//				} catch (InterruptedException ex) {
//					// TODO Auto-generated catch block
//					ex.printStackTrace();
//				}
//			}
//			
//		};
//		Thread t = new Thread(r);	
//		t.start();
		
//		new Thread(){
//			public void run() {
//				// TODO Auto-generated method stub
//				System.out.println("Thread is running ...");
//				try {
//					Thread.sleep(1500);
//				} catch (InterruptedException ex) {
//					// TODO Auto-generated catch block
//					ex.printStackTrace();
//				}
//			}	
//		}.run();
		
		Hashtable hashTest = Menu3gTool.hashTopMenu;
		System.out.println("-------------------------------");
		System.out.println(hashTest.size());
		System.out.println(hashTest);
		System.out.println();
		System.out.println(hashTest.get("/news/"));
		System.out.println(hashTest.get("/ket-ban/"));
		int i=0;
		while(true){
			i++;
			if(i%10==0){
				hashTest = Menu3gTool.hashTopMenu;
			}
			System.out.println(i+" - "+hashTest.get("/news/"));
			try {
				sleep(1500);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] agrs){
		show();
	}

}
