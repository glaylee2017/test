package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Demo {
	public static void main(String[] args) {
//		System.out.println(System.currentTimeMillis());
//		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Calendar c = Calendar.getInstance();
//		System.out.println(c.getTime());
		
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.MINUTE, +2);
		System.out.println(c.getTime());
	}
}
