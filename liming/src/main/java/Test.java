import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @date 2014-05-28 9:54
 * @id $Id$
 * @since 2.1.0
 */
class  Tt {
	public BigDecimal a;
	public BigDecimal b;

}
public class Test {

	public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
		readPatientStatistic();
		updatePatientStatistic();

//		Tt tt1 = new Tt();
//		Tt tt2 = new Tt();
//		tt1.a = new BigDecimal("11.11");
//		tt2.b = tt1.a;
//		System.out.println(tt2.b);
//		tt1.a = BigDecimal.ONE;
//		System.out.println(tt2.b);
//		BigDecimal b = new BigDecimal("1.114");
//		BigDecimal a = new BigDecimal("1.115");
//
//		a.setScale(2,BigDecimal.ROUND_DOWN);
//		b.setScale(2,BigDecimal.ROUND_DOWN);
//
//		System.out.println(a.setScale(2,BigDecimal.ROUND_HALF_UP));
//		System.out.println(b.setScale(2,BigDecimal.ROUND_DOWN));

	/*	String s = "abc_abc_def";
		s = s.replaceFirst("abc_", "");
		System.out.println(s);

		if(true)
		return;

		Integer aa = 3;
		switch (aa){
		case 1:{
			System.out.println(1);
		}
		case 3:{
			System.out.println(2);
		}
		case 8: {
			System.out.println(8);

			break;
		}
		default:
				System.out.println("default");
		}*/

		/*Integer a = new Integer(2);
		Integer b = new Integer(2);
		System.out.print(a == b);

		Class cache = Integer.class.getDeclaredClasses()[0];
		Field c = cache.getDeclaredField("cache");
		c.setAccessible(true);
		Integer[] array = (Integer[]) c.get(cache);
		array[132] = array[133];

		System.out.printf("%d",2 + 2);*/
//		String fileName = "E:\\SVN\\test\\src\\main\\resources\\1.txt";
//		readPatientNoOutPutFile(fileName);

		//readBillNum();
	}

//c.charge_id	c.medical_insurance_charge	c.disct_charge	c.receivable_charge	c.round_up	c.total_charge	s.patient_id	s.hospital_id
	public static void readPatientStatistic() throws IOException {
		BufferedReader reader1 = null;
		BufferedWriter writer = null;
		try {
			File file1 = new File("E:\\SVN\\test\\src\\main\\resources\\2.txt");
			reader1 = new BufferedReader(new FileReader(file1));
			writer = new BufferedWriter(new FileWriter("E:\\SVN\\test\\src\\main\\resources\\insert.sql"));
			String line = null;
			while ((line = reader1.readLine()) != null) {
				String[] cols = line.split("\t");
				StringBuilder insertString = new StringBuilder
						("insert into t_patient_charge_statistic (patient_id,hospital_id,charge_type,tatal_charge_sum,disct_charge_sum,charge_up_sum,round_up_sum,receivable_charge_sum,create_time,modify_time) values(");
				insertString.append(cols[6]).append(",").append(cols[7]).append(",1,").append(cols[5]).append(",").append(cols[2]).append(",").append(cols[1]).append(",").append(cols[4]).append(",").append(cols[3]).append(",current_date,current_date);");
				writer.write(insertString.toString());
				writer.write("\r\n");
			}
		}finally {
			reader1.close();
			writer.close();
		}
	}

	public static  void updatePatientStatistic() throws IOException{
//		update t_patient_charge_statistic set tatal_charge_sum = tatal_charge_sum+1,disct_charge_sum = disct_charge_sum+1,charge_up_sum = charge_up_sum+1,
//				round_up_sum = round_up_sum+1,receivable_charge_sum = receivable_charge_sum +1
//		where hospital_id = 2 and patient_id = 478;

		BufferedReader reader1 = null;
		BufferedWriter writer = null;
		try {
			File file1 = new File("E:\\SVN\\test\\src\\main\\resources\\1.txt");
			reader1 = new BufferedReader(new FileReader(file1));
			writer = new BufferedWriter(new FileWriter("E:\\SVN\\test\\src\\main\\resources\\update.sql"));
			String line = null;
			while ((line = reader1.readLine()) != null) {
				String[] cols = line.split("\t");
				StringBuilder updateStr = new StringBuilder
						("update t_patient_charge_statistic set tatal_charge_sum = tatal_charge_sum +");
				updateStr.append(cols[5]).append(",disct_charge_sum = disct_charge_sum+").append(cols[2]).append(",charge_up_sum = charge_up_sum+")
				.append(cols[1]).append(",round_up_sum = round_up_sum+").append(cols[4]).append(",receivable_charge_sum = receivable_charge_sum+").append(cols[3])
				.append(",modify_time=current_date where hospital_id = ").append(cols[7]).append(" and patient_id = ").append(cols[6]).append(";");
				writer.write(updateStr.toString());
				writer.write("\r\n");
			}
		}finally {
			reader1.close();
			writer.close();
		}
	}

	public static void readBillNum() throws  IOException{

		File file1 = new File("E:\\SVN\\test\\src\\main\\resources\\2.txt");
		BufferedReader reader1 = null;
		BufferedReader reader2 = null;

		reader1 = new BufferedReader(new FileReader(file1));

		String tempString1 = null;
		String tempString2 = null;

		while ((tempString1 = reader1.readLine()) != null) {
			String[] roleAndHospitalId = tempString1.split("\t");
			String roleId = roleAndHospitalId[0];
			String hospitalId = roleAndHospitalId[1];
			File file2 = new File("E:\\SVN\\test\\src\\main\\resources\\1.txt");
			reader2 = new BufferedReader(new FileReader(file2));
			while((tempString2 = reader2.readLine()) != null){
				String[] privilageIdAndCode = tempString2.split("\t");
				String privilageId = privilageIdAndCode[0];
				String privilageCode = privilageIdAndCode[1];
				StringBuilder sb = new StringBuilder("insert into t_role_privilege (role_id,privilege_id,privilege_code,hospital_id,sys_type,create_time,modify_time) values (");
				sb.append(roleId).append(",").append(privilageId).append(",'").append(privilageCode).append("',").append(hospitalId).append(",")
				.append("1,now(),now());");
				System.out.println(sb);
			}
		}

	}

//	public static void readPatient(String fileName) throws IOException {
//
//		File file = new File(fileName);
//		BufferedReader reader = null;
//		int line = 0;
//
//		reader = new BufferedReader(new FileReader(file));
//		BufferedWriter  writer = new BufferedWriter(new FileWriter("E:\\SVN\\HIS2.0\\his\\tcgroup-his-web\\src\\main\\java\\com\\tc\\his\\web\\utils\\2_1.txt"));
//		String tempString = null;
//		Set<String> m = new HashSet<String>();
//
//		// 一次读入一行，直到读入null为文件结束
//		int i = 0;
//		int j = 2;
//		int k = 0;
//		while ((tempString = reader.readLine()) != null) {
//			// 显示行号
//			String[] strs = tempString.split("\t");
//			k++;
//			if(strs[10].trim().length() > 15){
//				System.out.println(k);
//				continue;
//			}
//			String s = "insert into t_patient ("
//
//
//					+ "parent_hospital_id,"
//					+ "medical_card,"//就诊卡号
//					+ "medical_insurance_card," // character varying(64), -- 医保卡号
//					+ "patient_name,"
//					+ "patient_sex,"
//					+ "patient_birth,"
//					+"latest_medical_day," // 6 timestamp without time zone, -- 最新就诊日期
//					+"patient_code,"
//					+ "patient_nation,"
//					+"patient_home_addr," //character varying(64), -- 家庭地址
//					+"patient_company," // character varying(64), -- 单位名/地址
//					+"patient_mobi," // character varying(15), -- 手机
//					+"patient_type," // character varying(12), -- 人员类型
//					+"patient_spell," // character varying(20) NOT NULL, -- 拼音码
//					//                      +"is_delete," // boolean, -- 删除标识
//					+"create_time," // timestamp without time zone, -- 新增时间
//					+"modify_time" // timestamp without time zone, -- 修改时间
//					+") values (13,'"; // character varying(40), -- 病人编码"
//			s = s + strs[0].trim() + "','" + strs[1].trim() + "','"+ strs[2].trim() + "',";
//			if(strs[3].trim().equals("男")){
//				s = s + "1,'";
//			}
//			else{
//				s = s + "0,'";
//			}
//			s = s +strs[4]+"','"+strs[5]+"','" +strs[6] + "','" +strs[7]+"','"+strs[8]+"','" +strs[9] +"','"+strs[10]+"',0,'"
//					+ PingYinUtil.getFirstSpell(strs[2].trim())+"',current_date,current_date);";
//			writer.write(s);
//			writer.write("\r\n");
//			i++;
//
//			if(i % 20000 == 0){
//				writer.flush();
//				writer.close();
//				writer = new BufferedWriter(new FileWriter("E:\\SVN\\HIS2.0\\his\\tcgroup-his-web\\src\\main\\java\\com\\tc\\his\\web\\utils\\2_" + j++ + ".txt"));
//
//			}
//		}
//		writer.flush();
//		writer.close();
//
//	}


//	public static void readPatientNoOutPutFile(String fileName) throws IOException {
//
//		File file = new File(fileName);
//		BufferedReader reader = null;
//		int line = 0;
//
//		reader = new BufferedReader(new FileReader(file));
//		String tempString = null;
//		Set<String> m = new HashSet<String>();
//
//		// 一次读入一行，直到读入null为文件结束
//		int i = 0;
//		int j = 2;
//		int k = 0;
//		while ((tempString = reader.readLine()) != null) {
//			// 显示行号
//			String[] strs = tempString.split("\t");
//			k++;
//			String s = "insert into t_patient ("
//					+ "parent_hospital_id,"
//					+ "medical_card,"//就诊卡号
////					+ "medical_insurance_card," // character varying(64), -- 医保卡号
//					+ "patient_name,"
//					+ "patient_sex,"
//					+ "patient_birth,"
//					+"latest_medical_day," // 6 timestamp without time zone, -- 最新就诊日期
////					+"patient_code,"
////					+ "patient_nation,"
//					+"patient_home_addr," //character varying(64), -- 家庭地址
////					+"patient_company," // character varying(64), -- 单位名/地址
//					+"patient_mobi," // character varying(15), -- 手机
////					+"patient_type," // character varying(12), -- 人员类型
//					+"patient_spell," // character varying(20) NOT NULL, -- 拼音码
//					//                      +"is_delete," // boolean, -- 删除标识
//					+"create_time," // timestamp without time zone, -- 新增时间
//					+"modify_time,patient_code,patient_type" // timestamp without time zone, -- 修改时间
//					+") values (52,'"; // character varying(40), -- 病人编码"
//			s = s + strs[4].trim() + "','" + strs[0].trim() + "',";
//			if(strs[1].trim().equals("男")){
//				s = s + "1,'";
//			}
//			else{
//				s = s + "0,'";
//			}
//			Integer age = Integer.parseInt(strs[2].trim());
//			net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination e;
//			s = s + (2014 - age) + "-06-05','" + strs[8].trim()+"','";
//			String home = "";
//			if(StringUtils.isNotBlank(strs[6].trim())){
//				home = strs[7].trim() + "(" + strs[6].trim() + ")','";
//			}
//			else{
//				home =  strs[7].trim() + "','";
//			}
//			s = s + home + strs[5].trim()+"','"+PingYinUtil.getFirstSpell(strs[0].trim())
//			+"',now(),now(),'"+strs[4].trim()+"',0);";
//			System.out.println(s);
//		}
//	}
}
