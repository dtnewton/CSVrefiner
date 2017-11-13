import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TextCleaner {

	public static void main(String[] args)
	{
		ArrayList<String> vals = new ArrayList<String>();
		String line = null;

		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("test_set_reduced_clean.arff"), "UTF-8")))
		{
			File file = null;

			try
			{
				file = new File("test_set_reduced_rating_removed.csv");
				if(!file.exists())
				{
					file.createNewFile();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			/*first line(column labels)*/
			line = reader.readLine();
			if(line.startsWith("\uFEFF"))
			{
				line = line.substring(1);
			}

			String[] splitStr = line.trim().split(",");

			for(int i = 0; i < splitStr.length; i++)
			{

				vals.add(splitStr[i]);
			}

			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < vals.size(); i++)
			{
			
				sb.append(vals.get(i));
				if(i < vals.size() - 1) sb.append(",");

			}

			line = sb.toString();
			writer.write(line);
			writer.newLine();
			vals.clear();
			String[] fullLine;

			//*********************************************************************
			while((line = reader.readLine())!=null)
			{
				vals.clear();
				fullLine = line.trim().split(",");
				for(int i = 0; i < fullLine.length; i++)
				{
					vals.add(fullLine[i]);
				}
				for(int i = 0; i < vals.size();i++)
				{
					
						if(!(i == (vals.size() - 1)))
						{
							writer.write(vals.get(i));
							writer.write(",");
						}
						else
						{
							writer.write(vals.get(i));
						}
				}
					
			

				writer.newLine();
			}
			//*********************************************************************
			writer.close();
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
