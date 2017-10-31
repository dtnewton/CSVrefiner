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
	public static void main(String[] args)
	{			
		ArrayList<String> genres = CSVrefiner.buildGenres();
		Map<String, String> gMap = CSVrefiner.buildGenreMap(genres);
		//System.out.print(gMap.toString());
		//TODO - build cast/crew list?
		CSVrefiner.buildCSV(genres);	          	      
	}
	
	private static Map<String, String> buildGenreMap(ArrayList<String> genres)
	{
		
		int numVal = 0;
		Map<String, String> gmap = new HashMap<String, String>();
		for(String s : genres)
		{	
			gmap.put(s,Integer.toString(numVal));
			numVal++;
		}
		
		return gmap;
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
			
			while((line = reader.readLine())!=null)
			{
				System.out.println(line);
			}
			
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