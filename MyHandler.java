import java.text.*;
import java.util.*;
public class MyHandler {
	public static void main(String[] args) throws ParseException
	{		
		int maxDay = 0;	
		int firstDay = 0;	
		int currentDay = 0;		
		
		System.out.println("请输入一个日期：格式为：2020-02-02");	
		try (Scanner sc = new Scanner(System.in)) {
			String str = sc.nextLine();	//键盘输入日期格式的字符串
 
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(str);	//将字符串转化为指定的日期格式
				
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);	//将日期转化为日历
			maxDay = calendar.getActualMaximum(Calendar.DATE);	//当前日期中当前月对应的最大天数
			currentDay = calendar.get(Calendar.DATE);	//当前日期中的当前天
			calendar.set(Calendar.DATE, 1); // 设置为当前月的第一天
			firstDay = calendar.get(Calendar.DAY_OF_WEEK);	//当前日期中当前月第一天对应的星期数
		}
		System.out.println("------------------------------------------------------");
		System.out.println("周日\t周一\t周二\t周三\t周四\t周五\t周六\n");
		System.out.println("------------------------------------------------------");
		for (int j = 1; j < firstDay; j++) //当前月第一天之前以空格输出
		{
			System.out.print("\t");
		} 
		for (int i = 1; i <= maxDay; i++) //输出当月每一天的号数
		{
			if (i == currentDay) //以*i表示当天日期
			{
				System.out.print("*");
			}
			System.out.print(i + "\t");
			if ((i - (8 - firstDay)) % 7 == 0) //以七天为一个循环输出所有天数
			{
				System.out.println("\n");
			} 
		}
	}
}