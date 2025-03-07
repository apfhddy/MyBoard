package board.ETCClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;

public class MyMybatisMethods {
	
	public static String ClobToString(Clob c) {
		StringBuffer sb = new StringBuffer();
		try(BufferedReader br = new BufferedReader(c.getCharacterStream())){
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} 
		catch (IOException e) {e.printStackTrace();} 
		catch (SQLException e) {e.printStackTrace();}
		return sb.toString();
	}
	
	public static int MapObjectIntToInt(Object intValue) {
		return Integer.parseInt(String.valueOf(intValue));
	}

}
