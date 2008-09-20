import java.io.*;

/**
 * Converts a CSV file into an SQL file
 * 
 * @author Israa Taha
 * @version September 15, 2008
 */
@SuppressWarnings("unused")
public class CSVToSQLConverter {
	
	public CSVToSQLConverter() {
	
		try {
			BufferedReader in = new BufferedReader(new FileReader("AirportFacilities.csv"));
			
			FileOutputStream outFile = new FileOutputStream("airport.sql");
			PrintStream out = new PrintStream(outFile);
			
			String sqlCommand;
			String str = in.readLine();
			while (str != null) {
				sqlCommand = "INSERT INTO airport VALUES(";
		
				//process string
				int startIndex = 0;
				int endIndex = str.indexOf(",");
				
				//while there are still delimiters in the string
				while (endIndex != -1) {
					if  (startIndex != 0)
						sqlCommand = sqlCommand.concat(",");
					
					sqlCommand = sqlCommand.concat("'"+str.substring(startIndex, endIndex)+"'");
					
					startIndex = endIndex + 1;
					endIndex = str.indexOf(",", startIndex);
				}
				sqlCommand = sqlCommand.concat(");");
				out.println(sqlCommand);
				str = in.readLine();
			}
			in.close();
			out.close();
		}
		catch (IOException e){
			
		}
	}
	
	public static void main(String[] args) {
		CSVToSQLConverter converter = new CSVToSQLConverter();
	}

}