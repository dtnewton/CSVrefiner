import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CSVrefiner
{
	public static final int NUM_COLS = 12;//this may change
	
	public static void main(String[] args)
	{			
		ArrayList<String> genres = CSVrefiner.buildGenres();
		Map<String, Integer> gMap = CSVrefiner.buildGenreMap(genres);
		System.out.print(gMap.toString());
		//TODO - build cast/crew list?
		CSVrefiner.buildCSV(gMap);	          	      
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
	private static Map<String, Integer> buildGenreMap(ArrayList<String> genres)
	{
		
		int numVal = 0;
		Map<String, Integer> gmap = new HashMap<String, Integer>();
		for(String s : genres)
		{	
			gmap.put(s,numVal);
			numVal++;
		}
		
		return gmap;
	}
	private static void buildCSV(Map<String, Integer> gMap) 
	{
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
				if(vals.get(i).equals("genres"))
				{		
						sb.append("genre_val");
						sb.append(",");	
				}
				else
				{
					sb.append(vals.get(i));
					if(i < vals.size() - 1) sb.append(",");
				}
				
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
					if(i != 1)
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
					else
					{	
						int numericVal = 0;
						if(vals.get(i).equals(""))
						{
							numericVal = 0;
							writer.write(Integer.toString(numericVal));
							writer.write(",");
						}
						else if(vals.get(i).contains("|"))
						{
							numericVal = 0;
							String[] temp = vals.get(i).split("\\|");
							//System.out.println(vals.get(i));
							
							ArrayList<String> innerList = new ArrayList<String>();
							for(int j = 0; j < temp.length; j++)
							{
								innerList.add(temp[j]);
								numericVal += gMap.get(temp[j]);

							}
												
							writer.write(Integer.toString(numericVal));
							writer.write(",");
							
						}
						else
						{
							numericVal = 0;
							numericVal = gMap.get(vals.get(i));
							writer.write(Integer.toString(numericVal));
							writer.write(",");
						}
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
		//System.out.println(line);	
	}
}