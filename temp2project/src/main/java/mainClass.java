import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class mainClass {

	public static void main(String args []) throws ParseException {
		System.out.println("asd");

		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time=new Long(445555555);
		String d = format.format(time);
		Date date=format.parse(d);
		System.out.println("Format To String(Date):"+d);
		System.out.println("Format To Date:"+date);

		Date now = new Date();

		System.out.print("Format To times:"+now.getTime());

	}
}
