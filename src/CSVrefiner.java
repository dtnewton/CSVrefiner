import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class CSVrefiner
{
	public static final int NUM_COLS = 12;//this may change
	
	public static void main(String[] args)
	{			
		ArrayList<String> genres = CSVrefiner.buildGenres();
		CSVrefiner.buildCSV(genres);	          	      
	}	
	
	private static ArrayList<String> buildGenres() 
	{
		ArrayList<String> genres = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("genre.txt"), "UTF-8")))
		{		
			String ln;
			while((ln = br.readLine()) != null)
			{
				if(ln.startsWith("\uFEFF"))
				{
					ln = ln.substring(1);
				}
				genres.add(ln);
			}			
			Collections.sort(genres);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return genres;
	}

	private static void buildCSV(ArrayList<String> genres) 
	{
		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<String> vals = new ArrayList<String>();
		String line = null;
			
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("movie_profiles_small.csv"), "UTF-8")))
		{
			File file = null;
			try {
				file = new File("movie_profiles_small_updated.csv");
				if(!file.exists())
				{
					file.createNewFile();
				}
			}catch(Exception e) {
				e.printStackTrace();
				
			}
				
			BufferedWriter writer = new BufferedWriter(new FileWriter(file)); 
			
			/*first line - column labels***********************************/
			line = reader.readLine();
			if(line.startsWith("\uFEFF"))
			{
				line = line.substring(1);
			}
			
			String[] splitStr = line.trim().split(",");
			
			for(int i = 0; i < splitStr.length; i++)
			{
			
				if(splitStr[i].equals("genres"))
				{
					for(int j = 0; j < genres.size(); j++)
					{
						headers.add(genres.get(j));
					}
				}
				else
				{
					headers.add(splitStr[i]);
				}
			}
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < headers.size(); i++)
			{

				sb.append(headers.get(i));
				if(i < headers.size() - 1) sb.append(",");
			
			}
					
			line = sb.toString();
			writer.write(line);
			writer.newLine();
			
			
			String[] fullLine, gList;
			ArrayList<String> gs = new ArrayList<String>();
			
			//*********************************************************************
			while((line = reader.readLine())!=null)
			{
				vals.clear();
				gs.clear();
				
				/* COLLECT VALS */
				fullLine = line.trim().split(",");
				for(int i = 0; i < fullLine.length; i++)
				{
					if(i == 1)//maybe refine this check?
					{
						gList = fullLine[i].split("\\|");
						
						for(int k = 0; k < gList.length; k++)
						{
							gs.add(gList[k]);
						}
						
						for(int j = 0; j < genres.size(); j++)
						{
							if(gs.contains(genres.get(j)))
							{
								vals.add("1");
							}
							else
							{
								vals.add("0");
							}
						}
					}
					else
					{
						vals.add(fullLine[i]);
					}
					
				}
					
				/* WRITE */
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